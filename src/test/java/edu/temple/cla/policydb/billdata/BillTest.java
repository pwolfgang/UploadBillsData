/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.temple.cla.policydb.billdata;

import edu.templecla.policydb.myxmltest.TestElementFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Element;

/**
 *
 * @author Paul Wolfgang
 */
public class BillTest {

    public BillTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetBillFromNode() throws Exception {
        TestElementFactory testFactory = new TestElementFactory();
        Element billElement = testFactory.newElement(
"        <bill id=\"19690HB0002\">\n" +
"            <sessionYear>1969</sessionYear>\n" +
"            <session>0</session>\n" +
"            <body>H</body>\n" +
"            <type description=\"House Bill\">B</type>\n" +
"            <subType>B</subType>\n" +
"            <number>0002</number>\n" +
"            <shortTitle>A dummy bill</shortTitle>\n" +
"            <sponsors>\n" +
"                <sponsor sequenceNumber=\"01\" fillSequence=\"0\" party=\"R\" body=\"H\" distictNumber=\"139\">MEBUS</sponsor>\n" +
"                <sponsor sequenceNumber=\"02\" fillSequence=\"0\" party=\"D\" body=\"H\" distictNumber=\"49\">GROSS</sponsor>\n" +
"            </sponsors>\n" +
"            <printersNumberHistory>\n" +
"                <number sequence=\"01\">0123</number>\n" +
"            </printersNumberHistory>\n" +
"            <actionHistory>\n" +
"                <action sequence=\"01\" actionChamber=\"H\">\n" +
"                    <verb>Referred to</verb>\n" +
"                    <committee>JUDICIARY</committee>\n" +
"                    <date>01/14/69</date>\n" +
"                    <printersNumber>    </printersNumber>\n" +
"                    <rollCallVote></rollCallVote>\n" +
"                    <fullAction>Referred to JUDICIARY,  Jan. 14, 1969  [House]</fullAction>\n" +
"                </action>\n" +
"             </actionHistory>\n" +
"         </bill>");
        BillData result = BillData.getBillFromNode(billElement);
        assertEquals("1969-70", result.getSession());
        assertEquals(new Integer(1969), result.getSessionYear());
        assertEquals(new Integer(0), result.getSessionSequence());
        assertEquals(2, result.getBillNo());
        assertEquals("H", result.getChamber());
        assertEquals("B", result.getType());
        assertEquals("A dummy bill", result.getTitle());
        assertArrayEquals(new String[]{"0123"}, result.getPrinterNumbers());
        assertEquals("MEBUS H R 139", result.getSponsors()[0].toString());
        assertEquals("GROSS H D 49", result.getSponsors()[1].toString());
        assertEquals("Referred to JUDICIARY,  Jan. 14, 1969  [House]", result.getHistory()[0].toString());
    }
                
}