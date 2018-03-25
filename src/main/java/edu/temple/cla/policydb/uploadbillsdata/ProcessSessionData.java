/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cla.policydb.uploadbillsdata;

import edu.temple.cla.policydb.billdata.Action;
import edu.temple.cla.policydb.billdata.BillData;
import edu.temple.cla.policydb.billdata.ChamberState;
import edu.temple.cla.policydb.billdata.ConstitutionAmmendmentState;
import edu.temple.cla.policydb.billdata.GovernorState;
import edu.temple.cla.policydb.billdata.Sponsor;
import edu.temple.cla.policydb.billshibernatedao.Bill;
import edu.temple.cla.policydb.billshibernatedao.BillDAO;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Paul Wolfgang
 */
public class ProcessSessionData {
    private final  CommitteeCodes committeeCodes;
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
    private final BillDAO billDAO;
    private static final Logger LOGGER = Logger.getLogger(ProcessSessionData.class);
    
    public ProcessSessionData(SessionFactory sessionFactory) {
        this.billDAO = new BillDAO(sessionFactory);
        committeeCodes = new CommitteeCodes(sessionFactory);
    }
    
    /**
     * A method to process a file. The file is assumed to contain an XML
     * representation of a session as provided by the legislative data
     * processing service. Each billData element is converted a Bill object
     * and stored into the database.
     *
     * @param in The input stream containing the file.
     */
    public Set<String> processFile(InputStream in) {
        billDAO.openSession();
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
            billDAO.closeSession();
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
        billDAO.beginTransaction();
        Bill bill = billDAO.getBill(billData.getBillID());
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
        billDAO.save(bill);
        billDAO.endTransaction();
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
        billDAO.clearAll(bill);
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
                        billDAO.setCommittee(bill, ctyCode, primary, true);
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
            billDAO.setCommittee(bill, 300, false, true);
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
