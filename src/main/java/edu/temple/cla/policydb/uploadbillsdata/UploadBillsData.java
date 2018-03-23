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
import edu.temple.cla.policydb.billdata.Bill;
import edu.temple.cla.policydb.billdata.ChamberState;
import edu.temple.cla.policydb.billdata.ConstitutionAmmendmentState;
import edu.temple.cla.policydb.billdata.GovernorState;
import edu.temple.cla.policydb.billdata.Sponsor;
import edu.temple.cla.policydb.billshibernatedao.BillsData;
import edu.temple.cla.policydb.billshibernatedao.BillsDataDAO;
import edu.temple.cla.policydb.zipentrystream.ZipEntryInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
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
 * This program uploads the Bills Data from the XML files provided by the PA
 * Legislative Data Processing Department into the PAPolicy database
 * Bills_Table.
 *
 * @author Paul Wolfgang
 */
public class UploadBillsData {

    private static final Logger LOGGER = Logger.getLogger(UploadBillsData.class);
    private static BillsDataDAO billsDataDAO;
    private static CommitteeCodes committeeCodes;
    private static final Set<Committee> UNKNOWN_COMMITTEES = new TreeSet<>();
    private static final String PALEG = "www.legis.state.pa.us";
    private static final String ROOT = "/cfdocs/billinfo/billinfo.cfm?syear=%4d&sind=%d&body=%s&type=%s&bn=%d";
    private static final Pattern ADOPTED = Pattern.compile("[Aa]dopted");
    private static final Pattern REFERRED_PAT
            = Pattern.compile("(Re-)?[Rr]eferred to(.*)");
    private static final Pattern COMMITTED_PAT
            = Pattern.compile("(Re-)?[Cc]ommitted to(.*)");
    private static final Pattern CONFERENCE_PAT
            = Pattern.compile(".*[Cc]onference.*");

    /**
     * This program uploads the Bills Data from the XML files provided by the PA
     * Legislative Data Processing Department into the PAPolicy database
     * Bills_Table.
     *
     * @param args the command line arguments args[0] is the file or directory
     * containing the XML data args[1] is the name of the table to load the
     * bills data into. This table must have the same structure as the
     * Bills_Data table.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: UploadBillsData <directory><table>");
            System.exit(1);
        }
        if (args[0] == null) {
            System.err.println("args[0] must be non null");
            System.exit(1);
        }
        if (args[1] == null) {
            System.err.println("args[1] must be non null");
        }
        billsDataDAO = new BillsDataDAO(args[1]);
        committeeCodes = new CommitteeCodes(billsDataDAO.getSessionFactory());
        File directory = new File(args[0]);
        processDirectory(directory);
        System.err.println("Done processing directory");
        billsDataDAO.closeSessionFactory();
        PrintWriter pw3 = null;
        try {
            pw3 = new PrintWriter("UnknownCommitees.txt");
            for (Committee committee:UNKNOWN_COMMITTEES) {
                pw3.println(committee);
            }
        } catch (FileNotFoundException ioex) {
            System.err.println("Error creating UnknownCommittees.txt");
        } finally {
            if (pw3 != null) {
                pw3.flush();
                pw3.close();
            }
        }
        System.err.println("Exiting main");
    }

    /**
     * Method to recursively scan directories. If a file is found, it is passed
 to processEntry, otherwise the directory is recursively scanned.
     *
     * @param parent The directory to scan
     * @throws Exception
     */
    private static void processDirectory(File file) {
        try {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            for (File child : children) {
                processDirectory(child);
            }
        } else if (ZipEntryInputStream.isZipFile(file)) {
            try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    System.err.println("Processing " + entry);
                    processEntry(new ZipEntryInputStream(zipInputStream));
                    System.err.println("Done " + entry);
                }
            }
        } else {
            processEntry(new FileInputStream(file));
        }
        } catch (Exception ex) {
            LOGGER.error("Error processing directory " + file.getAbsolutePath(), ex);
            System.exit(1);
        }
    }

    /**
     * A method to process a file. Each entry is assumed to contain an XML
     * representation of a session as provided by the legislative data
     * processing service. Each bill is converted a BillsData object and
     * stored into the database.
     *
     * @param in The input stream containing the entry.
     */
    private static void processEntry(InputStream in) {
        billsDataDAO.openSession();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(in);
            for (Node child = doc.getFirstChild(); child != null; child = child.getNextSibling()) {
                processNode(child);
            }
        } catch (ParserConfigurationException | SAXException | IOException x) {
            LOGGER.error("Error processing entry", x);
            System.exit(1);
        } finally {
            billsDataDAO.closeSession();
        }
    }

    private static void processNode(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("bill")) {
            processBill(node);
        } else {
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
                processNode(child);
            }
        }
    }

    /**
     * Method to insert a bill into the database.
     *
     * @param node The bill node in the XML DOM tree
     */
    private static void processBill(Node node) {
        Bill bill = Bill.getBillFromNode(node);
        billsDataDAO.beginTransaction();
        BillsData billsData = billsDataDAO.getBill(bill.getBillID());
        String session = bill.getSession();
        int year = bill.getSessionYear();
        int specialSessionNo = bill.getSessionSequence();
        int billNo = bill.getBillNo();
        String chamber = bill.getChamber();
        String type = bill.getType();
        String title = bill.getTitle();
        String billString = String.format("%s%s %d", chamber, type, billNo);
        String billId = String.format("%04d%d%s%s%04d", year, specialSessionNo, chamber, type, billNo);
        short chamberNo;
        if (chamber.equals("H")) {
            chamberNo = 1;
        } else {
            chamberNo = 2;
        }
        billsData.setChamber(chamberNo);
        billsData.setSession(session);
        billsData.setBill(billString);
        String resource = String.format(ROOT, year,
                specialSessionNo, chamber, type, billNo);
        billsData.setHyperlink("#http://" + PALEG + resource + "#");
        Sponsor[] sponsors = bill.getSponsors();
        if (sponsors.length > 0) {
            Sponsor sponsor = sponsors[0];
            billsData.setSponsor(sponsor.getName());
            String partyString = sponsor.getParty();
            short party = 0;
            if ("D".equals(partyString)) {
                party = 1;
            }
            billsData.setSponsorParty(party);
            billsData.setSponsorCounty(sponsor.getDistrictNumber());
        }
        billsData.setAbstract_(title);
        Date dateReferred = findDateReferred(bill);
        if (dateReferred != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(dateReferred);
            billsData.setDateReferred(dateReferred);
            billsData.setDayReferred(c.get(Calendar.DAY_OF_MONTH));
            billsData.setMonthReferred(c.get(Calendar.MONTH) + 1);
            billsData.setYearReferred(c.get(Calendar.YEAR));
        }
        if (bill.getType().equals("B")) {
            Action actNo = findActNo(bill);
            if (actNo != null) {
                String actionTaken = actNo.getActionTaken();
                int lastSpace = actionTaken.lastIndexOf(" ");
                billsData.setActNo(actionTaken.substring(lastSpace + 1));
                Date dateEnacted = actNo.getTheDate();
                if (dateEnacted != null) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(dateEnacted);
                    billsData.setDateEnacted(dateEnacted);
                    billsData.setDayEnacted((short) c.get(Calendar.DATE));
                    billsData.setMonthEnacted((short) (c.get(Calendar.MONTH) + 1));
                    billsData.setYearEnacted((short) c.get(Calendar.YEAR));
                }
            }
        } else {
            Date dateAdopted = getDateAdopted(bill);
            if (dateAdopted != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(dateAdopted);
                billsData.setDateEnacted(dateAdopted);
                billsData.setDayEnacted((short) c.get(Calendar.DATE));
                billsData.setMonthEnacted((short) (c.get(Calendar.MONTH) + 1));
                billsData.setYearEnacted((short) c.get(Calendar.YEAR));
            }
        }
        processHistory(bill, billsData);
        setCommittees(bill, billsData);
        billsDataDAO.save(billsData);
        billsDataDAO.endTransaction();
    }

    /**
     * Method to find the date of the first action. This is usually the data it
     * was first referred to a committee, but for Resolutions it may be the date
     * the resolution was introduced.
     *
     * @param bill The bill
     * @returns the date the bill was first referred to a committee.
     */
    private static Date findDateReferred(Bill bill) {
        Action[] history = bill.getHistory();
        for (Action action : history) {
            if (action.getTheDate() != null) {
                return action.getTheDate();
            }
        }
        return null;
    }

    /**
     * Method to find the Act No. and date the bill was enacted.
     *
     * @param bill The bill
     * @return A modified Action that contains the Act No. and the date of the
     * action that preceeded the Act No. action in the history.
     */
    private static Action findActNo(Bill bill) {
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
     * Method to find the data a resolution was adopted.
     *
     * @param bill The resolution
     * @return The data the resolution was adopted, if any
     */
    private static Date getDateAdopted(Bill bill) {
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

    private static void processHistory(Bill bill, BillsData billsData) {

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
            billsData.setVetoIneligible(1);
        } else {
            billsData.setVetoIneligible(0);
            billsData.setVetoed(governorState.isVetoed() ? 1 : 0);
            billsData.setVetoOverriden(governorState.isVetoed() && becameLaw ? 1 : 0);
            billsData.setLineItemVeto(governorState.isLineItemVeto() ? 1 : 0);
            billsData.setSignedbyGov(governorState.isApprovedByGovernor() ? 1 : 0);
            billsData.setLawNoGovSig(governorState.isLawNoGovSig() ? 1 : 0);
        }

    }

    private static void setCommittees(Bill bill, BillsData billsData) {
        boolean inHouse;
        Action[] actions = bill.getHistory();
        boolean primarySenateCommitteeFound = false;
        boolean primaryHouseCommitteeFound = false;
        boolean conferenceCommitteeFound = false;
        billsDataDAO.clearAll(billsData);
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
                Short chamber = inHouse ? (short) 1 : (short) 2;
                Committee committee
                        = new Committee(chamber, committeeNameString);
                Short ctyCode = committeeCodes.get(committee);
                if (ctyCode == null) {
                    UNKNOWN_COMMITTEES.add(committee);
                    System.err.println("Unknown committee " + committee);
                    System.err.println(billsData.getSession() + " " + billsData.getBill());
                    System.err.println(a.getActionTaken());
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
                        billsDataDAO.setCommittee(billsData, ctyCode, primary, 1);
                    }
                } catch (Throwable t) {
                    String message = "Error processing "
                        + billsData.getSession() 
                        + " " + billsData.getBill() 
                        + " ctyCode: " + ctyCode 
                        + " primary: " + primary;
                    LOGGER.error(message, t);
                    System.exit(1);
                }
            }
        }

        if (conferenceCommitteeFound) {
            billsDataDAO.setCommittee(billsData, 300, false, 1);
        }

    }

    private static String toTitleCase(String s) {
        String[] words = s.trim().split("\\s+");
        StringBuilder stb = new StringBuilder();

        boolean first = true;

        for (String word : words) {
            if (first) {
                first = false;

            } else {
                stb.append(" ");

            }
            word = word.toLowerCase();

            try {
                if (!"and".equals(word) && !"on".equals(word) && !word.isEmpty()) {
                    char firstLetter = word.charAt(0);
                    firstLetter = Character.toUpperCase(firstLetter);
                    word = firstLetter + word.substring(1);

                }
            } catch (Exception ex) {
                LOGGER.error("Error processing '" + word + "' from " + s, ex);
                System.exit(1);
            }
            stb.append(word);
        }
        return stb.toString();
    }
    

}
