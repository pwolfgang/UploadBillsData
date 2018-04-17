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
package edu.temple.cla.policydb.billdata;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class to represent a BillData. Also includes methods read the XML representation
 * as provided by the PA Legislature Data Processing Department.
 *
 * @author Paul Wolfgang
 */
public class BillData implements Comparable<BillData> {

    /**
     * The ID assigned by the legislative DP department Format yyyynctmmmm where
     * yyyy is the session year n is the session sequence c is is chamber (H or
     * S) t is the type (B or R) mmmm is the bill number with leading zero
     * padding
     */
    private String billID;
    /**
     * The session this bill was introduced during Format yyyy-yy[-n]
     */
    private String session;
    /**
     * The session year
     */
    private Integer sessionYear;
    /**
     * The session sequence (0 for regular session)
     */
    private Integer sessionSequence;
    /**
     * The bill number
     */
    private int billNo;
    /**
     * The chamber. Either "H" or "S"
     */
    private String chamber;
    /**
     * The type: either "B" for bill or "R" for resolution
     */
    private String type;
    /**
     * The subtype: "A" for appropriation bill, "B" for other bills "C" for
     * concurrent resolutions, "J" for joint resolutions, "R" for other
     * resolutions
     */
    private String subtype;
    /**
     * The title or abstract of the bill
     */
    private String title;
    /**
     * The sponsors. Last name only if unique for this session
     */
    private Sponsor[] sponsors;
    /**
     * The bills history
     */
    private Action[] history;
    /**
     * The printing history
     */
    private String[] printerNumbers;

    /**
     * Construction of empty bills prohibited
     */
    private BillData() {
    }

    /**
     * Method to generate a BillData from an Node
     *
     * @param node The node being processed
     * @return A BillData object corresponding to the XML element
     */
    public static BillData getBillFromNode(Node node) {
        BillData bill = new BillData();
        processNode(node, bill);
        return bill;
    }

    /**
     * Method to process an XML DOM Node from a BillData file
     *
     * @param node The node being processed
     * @param bill The BillData object which is being constructed
     */
    public static void processNode(Node node, BillData bill) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            String nodeName = node.getNodeName();
            switch (nodeName) {
                case "bill":
                    String billID = ((Element) node).getAttribute("id");
                    bill.billID = billID;
                    for (Node child = node.getFirstChild(); child != null;
                            child = child.getNextSibling()) {
                        processNode(child, bill);
                    }
                    break;
                case "sessionYear":
                    bill.sessionYear = new Integer(node.getTextContent());
                    if (bill.sessionSequence != null) {
                        bill.session
                                = genSessionString(bill.sessionYear, bill.sessionSequence);
                    }
                    break;
                case "session":
                    bill.sessionSequence = new Integer(node.getTextContent());
                    if (bill.sessionYear != null) {
                        bill.session
                                = genSessionString(bill.sessionYear, bill.sessionSequence);
                    }
                    break;
                case "body":
                    bill.chamber = node.getTextContent().trim();
                    break;
                case "type":
                    bill.type = node.getTextContent().trim();
                    break;
                case "subType":
                    bill.subtype = node.getTextContent().trim();
                    break;
                case "number":
                    bill.billNo = Integer.parseInt(node.getTextContent());
                    break;
                case "title":
                case "shortTitle":
                    bill.title = node.getTextContent().trim();
                    break;
                case "sponsors":
                    bill.sponsors = getSponsors(node);
                    break;
                case "printersNumberHistory":
                    bill.printerNumbers = getPrintersNumbers(node);
                    break;
                case "history":
                case "actionHistory":
                    bill.history = getHistory(node);
                    break;
                default:
                    for (Node child = node.getFirstChild(); child != null;
                            child = child.getNextSibling()) {
                        processNode(child, bill);
                    }
                    break;
            }
        } else {
            for (Node child = node.getFirstChild(); child != null;
                    child = child.getNextSibling()) {
                processNode(child, bill);
            }
        }
    }

    /**
     * Method to process the sponsors element
     *
     * @param node The sponsors element node
     * @return An array of strings containing the individual sponsors
     */
    private static Sponsor[] getSponsors(Node node) {
        List<Sponsor> sponsors = new ArrayList<>();
        for (Node child = node.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            if (child.getNodeType() == Node.ELEMENT_NODE
                    && child.getNodeName().equals("sponsor")) {
                Sponsor sponsor = Sponsor.decodeSponsor(child);
                sponsors.add(sponsor);
            }
        }
        return sponsors.toArray(new Sponsor[sponsors.size()]);
    }

    /**
     * Method to get the history from the history element
     *
     * @param node The history element node
     * @return Array of actions
     */
    private static Action[] getHistory(Node node) {
        ArrayList<Action> actions = new ArrayList<Action>();
        for (Node child = node.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            if (child.getNodeType() == Node.ELEMENT_NODE
                    && child.getNodeName().equals("action")) {
                actions.add(Action.decodeAction(child));
            }
        }
        return actions.toArray(new Action[actions.size()]);
    }

    /**
     * Generate a session string of the form yyyy-yy or yyyy-yy-n.
     *
     * @param year The session year
     * @param seq The sequence number (0 represents regular session)
     * @return Session String in the form yyyy-yy[-n]
     */
    public static String genSessionString(int year, int seq) {
        int endYear = year + 1;
        if (seq == 0) {
            return String.format("%04d-%02d", year, endYear % 100);
        } else {
            return String.format("%04d-%02d-%d", year, endYear % 100, seq);
        }
    }

    public static String[] getPrintersNumbers(Node node) {
        List<String> printersNumbers = new ArrayList<String>();
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("number")) {
                printersNumbers.add(child.getTextContent());
            }
        }
        return printersNumbers.toArray(new String[printersNumbers.size()]);
    }

    /**
     * Return the session
     *
     * @return session
     */
    public String getSession() {
        return session;
    }

    /**
     * Return the billNo
     *
     * @return billNo
     */
    public int getBillNo() {
        return billNo;
    }

    /**
     * Return the chamber
     *
     * @return chamber
     */
    public String getChamber() {
        return chamber;
    }

    /**
     * Return the type
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Return the title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the array of sponsors
     *
     * @return sponsors
     */
    public Sponsor[] getSponsors() {
        return (Sponsor[]) sponsors.clone();
    }

    /**
     * Get the array of actions
     *
     * @return history
     */
    public Action[] getHistory() {
        return (Action[]) history.clone();
    }

    public Integer getSessionYear() {
        return sessionYear;
    }

    public Integer getSessionSequence() {
        return sessionSequence;
    }

    public String[] getPrinterNumbers() {
        return (String[]) printerNumbers.clone();
    }

    @Override
    public int compareTo(BillData otherBill) {
        return getBillID().compareTo(otherBill.getBillID());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (this.getClass() == other.getClass()) {
            BillData otherBill = (BillData) other;
            return compareTo(otherBill) == 0;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getBillID().hashCode();
    }

    public String getBillNoString() {
        return String.format("%s%s %d", chamber, type, billNo);
    }

    public String getSubtype() {
        return subtype;
    }

    public String getBillID() {
        if (billID == null) {
            billID = String.format("%4d%d%s%s%04d", sessionYear, sessionSequence, chamber, type, billNo);
        }
        return billID;
    }
}
