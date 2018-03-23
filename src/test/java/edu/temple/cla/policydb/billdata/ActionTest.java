/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.temple.cla.policydb.billdata;

import edu.temple.cla.policydb.billdata.Action;
import java.lang.reflect.Field;
import java.util.Date;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import static org.junit.Assert.*;
import org.w3c.dom.Node;

/**
 *
 * @author Paul Wolfgang
 */
public class ActionTest {

    public ActionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    public static Node genActionNode(Action action, Document doc)
            throws ParserConfigurationException, DOMException {
        if (doc == null) doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element actionElement = doc.createElement("action");
        if (action.getSequence() != null) {
            actionElement.setAttribute("sequence", action.getSequence().toString());
        }
        if (action.getActionChamber() != null) {
            actionElement.setAttribute("actionChamber", action.getActionChamber());
        }
        if (action.getVerb() != null) {
            Element verb = doc.createElement("verb");
            verb.setTextContent(action.getVerb());
            actionElement.appendChild(verb);
        }
        if (action.getCommittee() != null) {
            Element committee = doc.createElement("committee");
            committee.setTextContent(action.getCommittee());
            actionElement.appendChild(committee);
        }
        if (action.getTheDate() != null) {
            Element date = doc.createElement("date");
            date.setTextContent(action.getDateAsString());
            actionElement.appendChild(date);
        }
        if (action.getPrintersNumber() != null) {
            Element printersNumber = doc.createElement("printersNumber");
            printersNumber.setTextContent(action.getPrintersNumber());
            actionElement.appendChild(printersNumber);
        }
        if (action.getYesVote() != null) {
            Element rollCallVote = doc.createElement("rollCallVote");
            String voteString = String.format("(%d-%d)", action.getYesVote(), action.getNoVote());
            rollCallVote.setTextContent(voteString);
            actionElement.appendChild(rollCallVote);
        }
        Element fullAction = doc.createElement("fullAction");
        fullAction.setTextContent(action.getActionTaken());
        actionElement.appendChild(fullAction);
        return actionElement;
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test valid vote string
     */
    @Test
    public void testDecodeVoteString1() {
        System.out.println("decodeVoteString 1");
        Action action = new Action();
        String voteString = "(171-10)";
        action.decodeVoteString(voteString);
        assertEquals(new Integer(171), action.getYesVote());
        assertEquals(new Integer(10), action.getNoVote());
    }
    
    /**
     * Test decodeVoteString
     * invalid vote string
     */
    @Test
    public void testDecodeVoteString2() {
        System.out.println("decodeVoteString 1");
        Action action = new Action();
        String voteString = "xxxx";
        action.decodeVoteString(voteString);
        assertNull(action.getYesVote());
        assertNull(action.getNoVote());
    }

    /**
     * Test decodeVoteString
     * Voice Vote
     */
    @Test
    public void testDecodeVoteString3() {
        System.out.println("decodeVoteString 2");
        Action action = new Action();
        String voteString = "(vv.)";
        action.decodeVoteString(voteString);
        assertNull(action.getYesVote());
        assertNull(action.getNoVote());
    }

}