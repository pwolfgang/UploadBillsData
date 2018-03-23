/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cla.policydb.billdata;

import java.util.regex.Pattern;

/**
 *
 * @author Paul Wolfgang
 */
public class ConstitutionAmmendmentState implements State {

    private static final Pattern CONST_AMEND_PAT =
            Pattern.compile(".*[Jj]oint.*[Rr]esolution.*[Cc]onstitution.*"
            + "[Cc]ommonwealth.*");
    private static final Pattern PASSED1_PAT =
            Pattern.compile(".*[Pp]assed [Ss]ession.*\\d\\d\\d\\d");
    private static final Pattern PASSED2_PAT =
            Pattern.compile(".*[Pp]assed [Ss]essions.*\\d\\d\\d\\d"
            + "( and )?\\d\\d\\d\\d");
    private static final Pattern PASSED2_PAT2 =
            Pattern.compile(".*and [Ss]ession.*\\d\\d\\d\\d");
    private static final Pattern APPROVED_PAT =
            Pattern.compile(".*[Aa]pproved.*");
    private static final Pattern REJECTED_PAT =
            Pattern.compile(".*[Rr]ejected.*");
    private static final Short NOT_A_CONSTITUTIONAL_AMMENDMENT = 99;
    private static final Short NOT_PASSED = 0;
    private static final Short PASSED_ONCE = 1;
    private static final Short PASSED_TWICE = 2;
    private static final Short APPROVED = 3;
    private static final Short REJECTED = 4;
    private static final Short PARTIALLY_APPROVED = 5;
    private boolean passedOnce = false;
    private boolean passedSecond = false;
    private boolean approved = false;
    private boolean rejected = false;
    private int finalState = NOT_A_CONSTITUTIONAL_AMMENDMENT;

    public ConstitutionAmmendmentState(Bill bill) {
        if (bill.getType().equals("B")
                && bill.getSubtype().equals("J")) {
            finalState = NOT_PASSED;
        }
    }

    public int getFinalState() {
        return finalState;
    }

    public void processAction(Action action) {

        if (finalState == NOT_A_CONSTITUTIONAL_AMMENDMENT) {
            return;
        }

        if (action != null) {
            String actionTaken = action.getActionTaken();
            if (PASSED1_PAT.matcher(actionTaken).matches()) {
                passedOnce = true;
            }
            if (passedOnce) {
                if (PASSED2_PAT.matcher(actionTaken).matches() ||
                        PASSED2_PAT2.matcher(actionTaken).matches()) {
                    approved = false;
                    rejected = false;
                    passedSecond = true;
                }
            }
            if (passedSecond) {
                if (APPROVED_PAT.matcher(actionTaken).matches()) {
                    approved = true;
                }
                if (REJECTED_PAT.matcher(actionTaken).matches()) {
                    rejected = true;
                }
                Integer yesVote = action.getYesVote();
                Integer noVote = action.getNoVote();
                if (yesVote != null && noVote != null
                        && yesVote.compareTo(noVote) > 0) {
                    approved = true;
                }
                if (yesVote != null && noVote != null
                        && yesVote.compareTo(noVote) < 0) {
                    rejected = true;
                }

            }
        } else {
            if (passedSecond) {
                if (approved && !rejected) {
                    finalState = APPROVED;
                }
                if (rejected && !approved) {
                    finalState = REJECTED;
                }
                if (approved && rejected) {
                    finalState = PARTIALLY_APPROVED;
                }
                if (!approved && !rejected) {
                    finalState = PASSED_TWICE;
                }
            } else if (passedOnce) {
                finalState = PASSED_ONCE;
            }
        }
    }
}
