/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.temple.cla.policydb.billdata;

import edu.temple.cla.policydb.billdata.Sponsor;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Paul Wolfgang
 */
public class SponsorTest {

    public SponsorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    public static Node createSponsorNode(Sponsor sponsor, Document doc)
            throws ParserConfigurationException, DOMException {
        if (doc == null)
                doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element sponsorElement = doc.createElement("sponsor");
        sponsorElement.setAttribute("sequenceNumber", sponsor.getSequenceNumber());
        sponsorElement.setAttribute("fillSequence", sponsor.getFillSequence());
        sponsorElement.setAttribute("party", sponsor.getParty());
        sponsorElement.setAttribute("body", sponsor.getBody());
        sponsorElement.setAttribute("distictNumber", sponsor.getDistrictNumber());
        sponsorElement.setTextContent(sponsor.getName());
        Node node = sponsorElement;
        return node;
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of decodeSponsor method, of class Sponsor.
     */
    @Test
    public void testDecodeSponsor() throws Exception {
        System.out.println("decodeSponsor");
        Node node = createSponsorNode(new Sponsor("01", "0", "R", "H", "87", "VANCE"), null);
        String expResult = "VANCE H R 87";
        Sponsor sponsor = Sponsor.decodeSponsor(node);
        String result = sponsor.toString();
        assertEquals(expResult, result);
        assertEquals("01", sponsor.getSequenceNumber());
        assertEquals("0", sponsor.getFillSequence());
        assertEquals("VANCE", sponsor.getName());
        assertEquals("R", sponsor.getParty());
        assertEquals("H", sponsor.getBody());
        assertEquals("87", sponsor.getDistrictNumber());
    }

}