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

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class to encapsulate action on a bill. Includes method to create an Action
 * from its XML representation
 *
 * @author Paul Wolfgang
 */
public class Action {

    /**
     * @return the votePattern
     */
    public static Pattern getVotePattern() {
        return votePattern;
    }

    /**
     * The sequence in the action list
     */
    private Integer sequence;
    /**
     * The chamber taking the action
     */
    private String actionChamber;
    /**
     * The action taken
     */
    private String verb;
    /**
     * The committee assigned or taking action
     */
    private String committee;
    /**
     * The date action taken
     */
    private Date theDate;
    /**
     * Printer&#39;s numbers acted upon
     */
    private String printersNumber;
    /**
     * Roll Call Vote
     */
    private String rollCallVote;
    /**
     * Yes Vote
     */
    private Integer yesVote;
    /**
     * No Vote
     */
    private Integer noVote;
    /**
     * Action string as reported
     */
    private String actionTaken;

    /**
     * Pattern for a vote withing an action string
     */
    private static final Pattern votePattern
            = Pattern.compile("(\\(*([0-9,]+)\\s*-\\s*([0-9,]+)\\)*,?)|(\\(v\\.?v\\.?\\),?)");

    public Action(String sequence, String chamber, String verb, String committee,
            String date, String printersNumber, String rollCallVote, String fullAction) {
        this.sequence = new Integer(sequence);
        this.actionChamber = chamber;
        this.verb = verb;
        if (verb.trim().isEmpty()) {
            this.verb = fullAction;
        }
        this.committee = committee.trim();
        if (this.committee.isEmpty()) {
            if (this.verb.startsWith("Referred to ")) {
                int posComma = this.verb.indexOf(",");
                if (posComma != -1) {
                    this.committee = this.verb.substring(11, posComma);
                } else {
                    this.committee = this.verb.substring(11);
                }
            }
        }
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        try {
            this.theDate = df.parse(date);
        } catch (ParseException pe) {
            // ignore
        }
        this.printersNumber = printersNumber;
        this.rollCallVote = rollCallVote;
        if (rollCallVote != null) {
            decodeVoteString(rollCallVote);
        }
        this.actionTaken = fullAction;
    }

    /**
     * Method to decode the vote String and set the appropriate fields in the
     * Action object
     *
     * @param voteString The vote string in the form (yyy-nnn) where yyy is the
     * yes vote and nnn is the no vote
     */
    protected final void decodeVoteString(String voteString) {
        if (voteString == null || voteString.isEmpty()) {
            return;
        }
        Matcher voteMatcher = votePattern.matcher(voteString);
        if (voteMatcher.matches()) {
            String yesVoteString = voteMatcher.group(2);
            String noVoteString = voteMatcher.group(3);
            NumberFormat nf = NumberFormat.getIntegerInstance();
            try {
                if (yesVoteString != null) {
                    yesVote = nf.parse(yesVoteString).intValue();
                }
                if (noVoteString != null) {
                    noVote = nf.parse(noVoteString).intValue();
                }
            } catch (ParseException nfex) {
                yesVote = null;
                noVote = null;
            }
        } else {
            yesVote = null;
            noVote = null;
        }
    }

    /**
     * Prohibit construction of an empty Action object except by internal
     * methods and test methods.
     */
    protected Action() {
    }

    /**
     * Convert an XML representation of an Action into an Action object using
     * the XML representation created by the legislature data processing
     * organization.
     *
     * @param node The action element node
     * @return The corresponding Action object
     */
    public static Action decodeAction(Node node) {
        Action action = new Action();
        Element element = (Element) node;
        Attr sequenceAttr = element.getAttributeNode("sequence");
        if (sequenceAttr != null) {
            action.sequence = new Integer(sequenceAttr.getValue());
        }
        Attr actionChamberAttr = element.getAttributeNode("actionChamber");
        if (actionChamberAttr != null) {
            action.actionChamber = actionChamberAttr.getValue();
        }
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = child.getNodeName();
                switch (nodeName) {
                    case "verb":
                        action.verb = child.getTextContent().trim();
                        break;
                    case "committee":
                        action.committee = child.getTextContent().trim();
                        break;
                    case "date":
                        String dateString = child.getTextContent().trim();
                        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
                        try {
                            action.theDate = df.parse(dateString);
                        } catch (ParseException dfex) {
                            df = DateFormat.getDateInstance(DateFormat.SHORT);
                            try {
                                action.theDate = df.parse(dateString);
                            } catch (ParseException dfex2) {
                                action.theDate = null;
                            }
                        }
                        break;
                    case "rollCallVote":
                        String voteString = child.getTextContent().trim();
                        action.decodeVoteString(voteString);
                        break;
                    case "printersNumber":
                        action.printersNumber = child.getTextContent().trim();
                        break;
                    case "fullAction":
                        action.actionTaken = child.getTextContent().trim();
                        break;
                }
            }
        }
        if (action.verb.isEmpty()) {
            action.verb = action.actionTaken;
            if (action.committee.isEmpty()) {
                if (action.verb.startsWith("Referred to ")) {
                    int posComma = action.verb.indexOf(",");
                    if (posComma != -1) {
                        action.committee = action.verb.substring(11, posComma);
                    } else {
                        action.committee = action.verb.substring(11);
                    }
                    action.verb = "Referred to";
                }
            }
        }
        return action;
    }

    private static boolean equals(Object obj1, Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() == getClass()) {
            Action otherAction = (Action) other;
            return equals(actionTaken, otherAction.actionTaken)
                    && equals(theDate, otherAction.theDate)
                    && equals(yesVote, otherAction.yesVote)
                    && equals(noVote, otherAction.noVote)
                    && equals(sequence, otherAction.sequence)
                    && equals(actionChamber, otherAction.actionChamber)
                    && equals(verb, otherAction.verb)
                    && equals(printersNumber, otherAction.printersNumber)
                    && equals(committee, otherAction.committee);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return actionTaken.hashCode();
    }

    @Override
    public String toString() {
        return actionTaken;
    }

    public String getDateAsString() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        return df.format(theDate);
    }

    /**
     * @return the sequence
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * @return the actionChamber
     */
    public String getActionChamber() {
        return actionChamber;
    }

    /**
     * @return the verb
     */
    public String getVerb() {
        return verb;
    }

    /**
     * @return the committee
     */
    public String getCommittee() {
        return committee;
    }

    /**
     * @return the theDate
     */
    public Date getTheDate() {
        return theDate;
    }

    /**
     * @return the printersNumber
     */
    public String getPrintersNumber() {
        return printersNumber;
    }

    /**
     * @return the rollCallVote
     */
    public String getRollCallVote() {
        return rollCallVote;
    }

    /**
     * @return the yesVote
     */
    public Integer getYesVote() {
        return yesVote;
    }

    /**
     * @return the noVote
     */
    public Integer getNoVote() {
        return noVote;
    }

    /**
     * @return the actionTaken
     */
    public String getActionTaken() {
        return actionTaken;
    }

    /**
     * @param theDate the theDate to set
     */
    public void setTheDate(Date theDate) {
        this.theDate = theDate;
    }

}
