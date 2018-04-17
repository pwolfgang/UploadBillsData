/* 
 * Copyright (c) 2018, Temple University
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * All advertising materials features or use of this software must display 
 *   the following  acknowledgement
 *   This product includes software developed by Temple University
 * * Neither the name of the copyright holder nor the names of its 
 *   contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.temple.cla.policydb.uploadbillsdata;

import edu.temple.cla.policydb.billdata.Action;
import edu.temple.cla.policydb.billdata.BillData;
import edu.temple.cla.policydb.billdata.ChamberState;
import edu.temple.cla.policydb.billdata.ConstitutionAmmendmentState;
import edu.temple.cla.policydb.billdata.GovernorState;
import edu.temple.cla.policydb.billdata.Sponsor;
import edu.temple.cla.policydb.billdao.Bill;
import edu.temple.cla.policydb.billdao.BillDAO;
import edu.temple.cla.policydb.zipentrystream.ZipEntryInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Paul Wolfgang
 */
public class ProcessSessionData {
    private final  CommitteeCodes committeeCodes;
    private final BillDAO billDAO;
    private final Set<String> UNKNOWN_COMMITTEES = new TreeSet<>();
    private static final String PALEG = "www.legis.state.pa.us";
    private static final String ROOT = 
            "/cfdocs/billinfo/billinfo.cfm?syear=%4d&sind=%d&body=%s&type=%s&bn=%d";
    private static final Pattern ADOPTED = Pattern.compile("[Aa]dopted");
    private static final Pattern REFERRED_PAT
            = Pattern.compile("(Re-)?[Rr]eferred to(.*)");
    private static final Pattern COMMITTED_PAT
            = Pattern.compile("(Re-)?[Cc]ommitted to(.*)");
    private static final Pattern CONFERENCE_PAT
            = Pattern.compile(".*[Cc]onference.*");
    private static final Logger LOGGER = Logger.getLogger(ProcessSessionData.class);
    private final String tableName;
    private final Connection conn;
    public ProcessSessionData(Connection conn, String tableName) {
        this.conn = conn;
        this.tableName = tableName;
        committeeCodes = new CommitteeCodes(conn);
        billDAO = new BillDAO(conn, tableName);
    }

    /**
     * Method to process possibly compressed stream. If comprressed, each
     * entry in the zip file is processed. If the entry is a zip file, this 
     * method is recursively called.
     * @param in The input stream that may be a zip file.
     */
    public Set<String> processStream(InputStream in) {
        BufferedInputStream bufferedStream = new BufferedInputStream(in);
        ZipEntry entry = null;
        try {
            if (!ZipEntryInputStream.isZipEntry(bufferedStream)) {
                return processUncompressedStream(bufferedStream);
            } else {
                ZipInputStream zipInputStream = new ZipInputStream(bufferedStream);
                Set<String> unknownCommittees = new TreeSet<>();
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    LOGGER.info("Processing entry " + entry);
                    unknownCommittees.addAll(processStream(zipInputStream));
                }
                return unknownCommittees;
            }
        } catch (Exception ex) {
            LOGGER.error("Error processing zip entry " + entry, ex);
            throw new RuntimeException("Error processing zip entry " + entry, ex);
        }
    }

    
    /**
     * A method to process a stream. The file is assumed to contain an XML
     * representation of a session as provided by the legislative data
     * processing service. Each billData element is converted a Bill object
     * and stored into the database.
     *
     * @param in The input stream containing the file.
     * @return Set of unknown committees.
     */
    private Set<String> processUncompressedStream(InputStream in) {
        UNKNOWN_COMMITTEES.clear();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(in);
            for (Node child = doc.getFirstChild(); child != null; child = child.getNextSibling()) {
                processNode(child);
            }
        } catch (ParserConfigurationException | SAXException | IOException x) {
            LOGGER.error("Error processing entry", x);
            throw new RuntimeException(x);
        } finally {
            billDAO.updateDatabase();
        }
        return UNKNOWN_COMMITTEES;
    }

    /**
     * Process a node in the XML Dom.
     * If this node is a billData element, it is passed to processBill.
     * @param node
     */
    private void processNode(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("bill")) {
            processBill(node);
        } else {
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
                processNode(child);
            }
        }
    }

    /**
     * Method to insert a billData into the database.
     *
     * @param node The billData node in the XML DOM tree
     */
    private void processBill(Node node) {
        BillData billData = BillData.getBillFromNode(node);
        Bill bill = new Bill(billData.getBillID());
        String session = billData.getSession();
        int year = billData.getSessionYear();
        int specialSessionNo = billData.getSessionSequence();
        int billNo = billData.getBillNo();
        String chamber = billData.getChamber();
        String type = billData.getType();
        String title = billData.getTitle();
        String billString = String.format("%s%s %d", chamber, type, billNo);
        short chamberNo;
        if (chamber.equals("H")) {
            chamberNo = 1;
        } else {
            chamberNo = 2;
        }
        bill.setChamber(chamberNo);
        bill.setSession_(session);
        bill.setBill(billString);
        String resource = String.format(ROOT, year, specialSessionNo, chamber, type, billNo);
        bill.setHyperlink("#http://" + PALEG + resource + "#");
        Sponsor[] sponsors = billData.getSponsors();
        if (sponsors.length > 0) {
            Sponsor sponsor = sponsors[0];
            bill.setSponsor(sponsor.getName());
            String partyString = sponsor.getParty();
            short party = 0;
            if ("D".equals(partyString)) {
                party = 1;
            }
            bill.setSponsorParty(party);
            bill.setSponsorCounty(sponsor.getDistrictNumber());
        }
        bill.setAbstract_(title);
        Date dateReferred = findDateReferred(billData);
        if (dateReferred != null) {
            bill.setDateReferred(dateReferred);
        }
        if (billData.getType().equals("B")) {
            Action actNo = findActNo(billData);
            if (actNo != null) {
                String actionTaken = actNo.getActionTaken();
                int lastSpace = actionTaken.lastIndexOf(" ");
                bill.setActNo(actionTaken.substring(lastSpace + 1));
                Date dateEnacted = actNo.getTheDate();
                if (dateEnacted != null) {
                    bill.setDateEnacted(dateEnacted);
                }
            }
        } else {
            Date dateAdopted = getDateAdopted(billData);
            if (dateAdopted != null) {
                bill.setDateEnacted(dateAdopted);
            }
        }
        processHistory(billData, bill);
        setCommittees(billData, bill);
        billDAO.addToValuesList(bill);
    }

    /**
     * Method to find the date of the first action. This is usually the data it
     * was first referred to a committee, but for Resolutions it may be the date
     * the resolution was introduced.
     *
     * @param bill The billData
     * @returns the date the bill was first referred to a committee.
     */

    private Date findDateReferred(BillData bill) {
        Action[] history = bill.getHistory();
        for (Action action : history) {
            if (action.getTheDate() != null) {
                return action.getTheDate();
            }
        }
        return null;
    }

    private void processHistory(BillData bill, Bill billsData) {
        ChamberState houseState = new ChamberState("H", bill.getType(), bill.getBillID());
        ChamberState senateState = new ChamberState("S", bill.getType(), bill.getBillID());
        ConstitutionAmmendmentState conAmendState = new ConstitutionAmmendmentState(bill);
        boolean becameLaw = billsData.getActNo() != null;
        GovernorState governorState = new GovernorState(becameLaw);
        for (Action a : bill.getHistory()) {
            houseState.processAction(a);
            senateState.processAction(a);
            conAmendState.processAction(a);
            governorState.processAction(a);
        }
        houseState.processAction(null);
        senateState.processAction(null);
        conAmendState.processAction(null);
        governorState.processAction(null);
        billsData.setHouseLastAction(houseState.getFinalStateCode());
        billsData.setSenateLastAction(senateState.getFinalStateCode());
        billsData.setConAmmend((short) conAmendState.getFinalState());
        if (billsData.getConAmmend() != 99 || bill.getType().equals("R")) {
            billsData.setVetoIneligible(true);
        } else {
            billsData.setVetoIneligible(false);
            billsData.setVetoed(governorState.isVetoed());
            billsData.setVetoOverriden(governorState.isVetoed() && becameLaw);
            billsData.setLineItemVeto(governorState.isLineItemVeto());
            billsData.setSignedbyGov(governorState.isApprovedByGovernor());
            billsData.setLawNoGovSig(governorState.isLawNoGovSig());
        }
    }

    /**
     * Method to find the Act No. and date the billData was enacted.
     *
     * @param bill The billData
     * @return A modified Action that contains the Act No. and the date of the
     * action that preceeded the Act No. action in the history.
     */
    private Action findActNo(BillData bill) {
        Action[] history = bill.getHistory();
        for (int i = 0; i < history.length; i++) {
            Action action = history[i];
            String actionTaken = action.getActionTaken();
            int posActNo = actionTaken.indexOf("Act No.");
            if (posActNo == 0) {
                for (int j = i - 1; j >= 0; j--) {
                    if (history[j].getTheDate() != null) {
                        action.setTheDate(history[j].getTheDate());
                        return action;
                    }
                }
                return action;
            }
        }
        return null;
    }

    /**
     * Method to set the committee fields.
     * @param billData The BillData object from the XML data.
     * @param bill The database Bill object to be updated.
     */
    private void setCommittees(BillData billData, Bill bill) {
        boolean inHouse;
        Action[] actions = billData.getHistory();
        boolean primarySenateCommitteeFound = false;
        boolean primaryHouseCommitteeFound = false;
        boolean conferenceCommitteeFound = false;
        bill.clearAll();
        for (Action a : actions) {
            String verb = a.getVerb();
            inHouse = a.getActionChamber().equals("H");
            if (CONFERENCE_PAT.matcher(verb).matches()) {
                conferenceCommitteeFound = true;
            }
            Matcher m1 = REFERRED_PAT.matcher(verb);
            Matcher m2 = COMMITTED_PAT.matcher(verb);
            if (m1.matches() || m2.matches()) {
                String committeeNameString = toTitleCase(a.getCommittee());
                short chamber = inHouse ? (short) 1 : (short) 2;
                Committee committee = new Committee(chamber, committeeNameString);
                Short ctyCode = committeeCodes.get(committee);
                if (ctyCode == null) {
                    UNKNOWN_COMMITTEES.add(committee.toString());
                }
                boolean primary = false;
                if (inHouse && !primaryHouseCommitteeFound) {
                    primary = true;
                    primaryHouseCommitteeFound = true;
                }
                if (!inHouse && !primarySenateCommitteeFound) {
                    primary = true;
                    primarySenateCommitteeFound = true;
                }
                try {
                    if (ctyCode != null) {
                        bill.setCommittee(true, ctyCode, primary);
                    }
                } catch (Throwable t) {
                    String message = "Error processing " + bill.getSession_() 
                            + " " + bill.getBill() + " ctyCode: " + ctyCode 
                            + " primary: " + primary;
                    LOGGER.error(message, t);
                    throw t;
                }
            }
        }
        if (conferenceCommitteeFound) {
            bill.setCommittee(true, 300, false);
        }
    }

    /**
     * Method to find the data a resolution was adopted.
     *
     * @param bill The resolution
     * @return The data the resolution was adopted, if any
     */
    private static Date getDateAdopted(BillData bill) {
        Action[] history = bill.getHistory();
        Date dateAdoptedByHouse = null;
        Date dateAdoptedBySenate = null;
        for (Action action : history) {
            String actionTaken = action.getActionTaken();
            Matcher m = ADOPTED.matcher(actionTaken);
            if (m.find()) {
                switch (action.getActionChamber()) {
                    case "H":
                        dateAdoptedByHouse = action.getTheDate();
                        break;
                    case "S":
                        dateAdoptedBySenate = action.getTheDate();
                        break;
                    default:
                }
            }
        }
        if (bill.getSubtype().equals("C")) {
            if (dateAdoptedByHouse != null && dateAdoptedBySenate != null) {
                if (dateAdoptedByHouse.after(dateAdoptedBySenate)) {
                    return dateAdoptedByHouse;
                } else {
                    return dateAdoptedBySenate;
                }
            } else {
                return null;
            }
        } else {
            if (bill.getChamber().equals("H")) {
                return dateAdoptedByHouse;
            } else {
                return dateAdoptedBySenate;
            }
        }
    }

    /**
     * Method to convert a string to title case.
     * The input string is split into words and the first character of each
     * work is set to uppercase. The words and and on are not converted.
     * @param s String to be converted.
     * @return String converted to title case.
     */
    private static String toTitleCase(String s) {
        String[] words = s.trim().split("\\s+");
        StringJoiner sj = new StringJoiner(" ");
        for (String word : words) {
            word = word.toLowerCase();
            if (!"and".equals(word) && !"on".equals(word) && !word.isEmpty()) {
                char[] chars = word.toCharArray();
                chars[0] = Character.toUpperCase(chars[0]);
                word = new String(chars);
            }
            sj.add(word);
        }
        return sj.toString();
    }

    
}
