/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.temple.cla.policydb.billdata;

import edu.temple.cla.policydb.billdata.ChamberState;
import edu.temple.cla.policydb.billdata.Action;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Paul Wolfgang
 */
public class ChamberStateTest {

    public ChamberStateTest() {
    }

    /**
     * Test of processAction method, of class ChamberState.
     */
    @Test
    public void testProcessAction1() {
        System.out.println("processAction1");
        ChamberState instance = new ChamberState("H", "B", "HB001");
        Action action = new Action("01", "H", "Referred to", "LOCAL GOVERNMENT",
                "11/12/08", "4570", null, "Referred to LOCAL GOVERNMENT, Nov. 12, 2008");
        instance.processAction(action);
        assertEquals(0, instance.getFinalStateCode());
    }

    /**
     * Test of processAction method, of class ChamberState.
     */
    @Test
    public void testProcessAction2() {
        System.out.println("processAction1");
        ChamberState instance = new ChamberState("H", "B", "HB0001");
        Action action1 = new Action("01", "H", "Referred to", "JUDICIARY",
                "08/21/07", "2394", null, "Referred to JUDICIARY, Nov. 12, 2008");
        Action action2 = new Action("02", "H", "Reported as committed,", "JUDICIARY",
                "12/04/07", "2394", null, "Reported as committed, Dec. 4, 2007");
        instance.processAction(action1);
        instance.processAction(action2);
        assertEquals(1, instance.getFinalStateCode());
    }

    /**
     * Test of processAction method, of class ChamberState.
     */
    @Test
    public void testProcessAction3() {
        System.out.println("processAction1");
        ChamberState instance = new ChamberState("H", "B", "HB0001");
        Action action1 = new Action("01", "H", "Referred to", "JUDICIARY",
                "08/21/07", "2394", null, "Referred to JUDICIARY, Nov. 12, 2008");
        Action action2 = new Action("02", "H", "Reported as committed,", "JUDICIARY",
                "12/04/07", "2394", null, "Reported as committed, Dec. 4, 2007");
        Action action3 = new Action("04", "H", "Laid on the table,", "",
                "12/04/07", "2394", null, "Laid on the table, Dec. 4, 2007");
        instance.processAction(action1);
        instance.processAction(action2);
        instance.processAction(action3);
        assertEquals(3, instance.getFinalStateCode());
    }

    /**
     * Test of processAction method, of class ChamberState.
     */
    @Test
    public void testProcessAction4() {
        System.out.println("processAction1");
        ChamberState instance = new ChamberState("H", "B", "HB0001");
        Action action1 = new Action("01", "H", "Referred to", "JUDICIARY",
                "08/21/07", "2394", null, "Referred to JUDICIARY, Nov. 12, 2008");
        Action action2 = new Action("02", "H", "Reported as committed,", "JUDICIARY",
                "12/04/07", "2394", null, "Reported as committed, Dec. 4, 2007");
        Action action3 = new Action("04", "H", "Laid on the table,", "",
                "12/04/07", "2394", null, "Laid on the table, Dec. 4, 2007");
        Action action4 = new Action("10", "H", "Third consideration and final passage,",
                "", "04/07/08", "3537", "(199-2)",
                "Third consideration and final passage, April 7, 2008 (199-2)");
        instance.processAction(action1);
        instance.processAction(action2);
        instance.processAction(action3);
        instance.processAction(action4);
        assertEquals(2, instance.getFinalStateCode());
    }

    @Test
    public void testProcessAction5() {
        System.out.println("processAction1");
        ChamberState instance = new ChamberState("H", "B", "HB0001");
        Action action1 = new Action("01", "H", "Referred to", "JUDICIARY",
                "08/21/07", "2394", null, "Referred to JUDICIARY, Nov. 12, 2008");
        Action action2 = new Action("02", "H", "Reported as committed,", "JUDICIARY",
                "12/04/07", "2394", null, "Reported as committed, Dec. 4, 2007");
        Action action3 = new Action("04", "H", "Laid on the table,", "",
                "12/04/07", "2394", null, "Laid on the table, Dec. 4, 2007");
        Action action4 = new Action("10", "H", "Third consideration and final passage,",
                "", "04/07/08", "3537", "(199-2)",
                "Third consideration and final passage, April 7, 2008 (199-2)");
        Action action5 = new Action("11", "H", "(Remarks see House Journal Page",
                "", "04/07/08", "3537", null, "(Remarks see House Journal Page 863), April 7, 2008");
        instance.processAction(action1);
        instance.processAction(action2);
        instance.processAction(action3);
        instance.processAction(action4);
        instance.processAction(action5);
        assertEquals(2, instance.getFinalStateCode());
    }

    @Test
    public void testProcessAction6() {
        System.out.println("processAction1");
        ChamberState instance = new ChamberState("H", "B", "HB0001");
        Action action1 = new Action("01", "H", "Referred to", "JUDICIARY",
                "08/21/07", "2394", null, "Referred to JUDICIARY, Nov. 12, 2008");
        Action action2 = new Action("02", "H", "Reported as committed,", "JUDICIARY",
                "12/04/07", "2394", null, "Reported as committed, Dec. 4, 2007");
        Action action3 = new Action("04", "H", "Laid on the table,", "",
                "12/04/07", "2394", null, "Laid on the table, Dec. 4, 2007");
        Action action4 = new Action("10", "H", "Third consideration and final passage,",
                "", "04/07/08", "3537", "(199-2)",
                "Third consideration and final passage, April 7, 2008 (199-2)");
        Action action5 = new Action("11", "H", "(Remarks see House Journal Page",
                "", "04/07/08", "3537", null, "(Remarks see House Journal Page 863), April 7, 2008");
        Action action6 = new Action("12", "S", "In the Senate", "", "", "3573", null, "In the Senate");
        instance.processAction(action1);
        instance.processAction(action2);
        instance.processAction(action3);
        instance.processAction(action4);
        instance.processAction(action5);
        instance.processAction(action6);
        assertEquals(2, instance.getFinalStateCode());
    }

    @Test
    public void testProcessAction7() {
        System.out.println("processAction1");
        ChamberState instance = new ChamberState("H", "B", "HB0001");
        Action action1 = new Action("01", "H", "Referred to", "JUDICIARY",
                "08/21/07", "2394", null, "Referred to JUDICIARY, Nov. 12, 2008");
        Action action2 = new Action("02", "H", "Reported as committed,", "JUDICIARY",
                "12/04/07", "2394", null, "Reported as committed, Dec. 4, 2007");
        Action action3 = new Action("04", "H", "Laid on the table,", "",
                "12/04/07", "2394", null, "Laid on the table, Dec. 4, 2007");
        Action action4 = new Action("10", "H", "Third consideration and final passage,",
                "", "04/07/08", "3537", "(199-2)",
                "Third consideration and final passage, April 7, 2008 (199-2)");
        Action action5 = new Action("11", "H", "(Remarks see House Journal Page",
                "", "04/07/08", "3537", null, "(Remarks see House Journal Page 863), April 7, 2008");
        Action action6 = new Action("12", "S", "In the Senate", "", "", "3573", null, "In the Senate");
        Action action7 = new Action("22", "H", "In the House", "", "", "4197", null, "In the House");
        instance.processAction(action1);
        instance.processAction(action2);
        instance.processAction(action3);
        instance.processAction(action4);
        instance.processAction(action5);
        instance.processAction(action6);
        instance.processAction(action7);
        assertEquals(2, instance.getFinalStateCode());
    }

    @Test
    public void testProcessAction8() {
        System.out.println("processAction1");
        ChamberState instance = new ChamberState("H", "B", "HB0001");
        Action action1 = new Action("01", "H", "Referred to", "JUDICIARY",
                "08/21/07", "2394", null, "Referred to JUDICIARY, Nov. 12, 2008");
        Action action2 = new Action("02", "H", "Reported as committed,", "JUDICIARY",
                "12/04/07", "2394", null, "Reported as committed, Dec. 4, 2007");
        Action action3 = new Action("04", "H", "Laid on the table,", "",
                "12/04/07", "2394", null, "Laid on the table, Dec. 4, 2007");
        Action action4 = new Action("10", "H", "Third consideration and final passage,",
                "", "04/07/08", "3537", "(199-2)",
                "Third consideration and final passage, April 7, 2008 (199-2)");
        Action action5 = new Action("11", "H", "(Remarks see House Journal Page",
                "", "04/07/08", "3537", null, "(Remarks see House Journal Page 863), April 7, 2008");
        Action action6 = new Action("12", "S", "In the Senate", "", "", "3573", null, "In the Senate");
        Action action7 = new Action("22", "H", "In the House", "", "", "4197", null, "In the House");
        Action action8 = new Action("25", "H", "House concurred in Senate amendments, as amended by the House,",
                "", "07/04/08", "4197", "(202-1)",
                "House concurred in Senate amendments, as amended by the House, July 4, 2008");
        instance.processAction(action1);
        instance.processAction(action2);
        instance.processAction(action3);
        instance.processAction(action4);
        instance.processAction(action5);
        instance.processAction(action6);
        instance.processAction(action7);
        instance.processAction(action8);
        assertEquals(4, instance.getFinalStateCode());
    }

    @Test
    public void testProcessAction9() {
        Action action1 = new Action("01", "H",
                "INTRODUCED AS NONCONTROVERSIAL RESOLUTION UNDER RULE 35,",
                "", "09/26/07", "", null,
                "INTRODUCED AS NONCONTROVERSIAL RESOLUTION UNDER RULE 35, Sept. 26, 2007");
        Action action2 = new Action("02", "H",
                "Re-committed to",
                "ENVIRONMENTAL RESOURCES AND ENERGY",
                "09/27/07", "", null,
                "Re-committed to ENVIRONMENTAL RESOURCES AND ENERGY, Sept. 27, 2007");
        Action action3 = new Action("03", "H",
                "Reported as committed,",
                "", "10/16/07", "", null,
                "Reported as committed, Oct. 16, 2007");
        Action action4 = new Action("04", "H",
                "Adopted,", "", "10/16/07", "", "(199-0)",
                "Adopted, Oct. 23, 2007 (199-0)");
        Action action5 = new Action("05", "H",
                "(Remarks see House Journal Page 2451),",
                "", "10/23/07", "", null,
                "(Remarks see House Journal Page 2451), Oct. 23, 2007");
        Action action6 = new Action("06", "S", "In the Senate",
                "", "", "",null,
                "In the Senate");
        Action action7 = new Action("07", "S",
                "Referred to", "ENVIRONMENTAL RESOURCES AND ENERGY",
                "11/09/07", "", null,
                "Referred to ENVIRONMENTAL RESOURCES AND ENERGY, Nov. 9, 2007");
        Action action8 = new Action("08", "S",
                "Reported as committed,", "",
                "12/12/07", "", null,
                "Reported as committed, Dec. 4, 2007");
        Action action9 = new Action("09", "S",
                "Adopted,", "", "12/12/07", "", null,
                "Adopted, Dec. 12, 2007");
        Action action10 = new Action("10", "S",
                "(Remarks see Senate Journal Page 1481),",
                "", "12/12/07", "", null,
                "(Remarks see Senate Journal Page 1481), Dec. 12, 2007");
        Action[] actions = {action1, action2, action3, action4, action5,
        action6, action7, action8, action9, action10};
        ChamberState houseState = new ChamberState("H", "R", "HR0410");
        ChamberState senateState = new ChamberState("S", "R", "HR0410");
        for (Action a : actions) {
            houseState.processAction(a);
            senateState.processAction(a);
        }
        assertEquals(2, houseState.getFinalStateCode());
        assertEquals(2, senateState.getFinalStateCode());

    }
}