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

import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author Paul Wolfgang
 */
public class ChamberState implements State {

    private final String billId;
    private final String type;
    private final String chamber;
    private CurrentState currentState;
    private Date dateAdopted = null;
    private static final Pattern REFERRED_PAT =
            Pattern.compile("(Re-)?[Rr]eferred to(.*)");
    private static final Pattern COMMITTED_PAT =
            Pattern.compile("(Re-)?[Cc]ommitted to(.*)");
    private static final Pattern SIGNED_PAT =
            Pattern.compile("[Ss]igned .*");
    private static final Pattern CONFERENCE_PAT =
            Pattern.compile(".*[Cc]onference.*");
    private static final Pattern CONCURR_PAT =
            Pattern.compile(".* concurred in .*amend.*");
    private static final Pattern NON_CONCURR_PAT =
            Pattern.compile(".*non.*concurr.* in .*amend.*");
    private static final Pattern CONF_COMMITTEE_ADOPTED_PAT =
            Pattern.compile(".*[Cc]onference [Cc]ommittee [Rr]eport [Aa]dopted.*");
    private static final Pattern CONF_COMMITTEE_REJECTED_PAT =
            Pattern.compile(".*[Cc]onference [Cc]ommittee [Rr]eport [Rf]ejected.*");
    private static final Pattern CONF_COMMITTEE_UNCONSTITUTIONAL_PAT =
            Pattern.compile(".*[Cc]onference [Cc]ommittee [Rr]eport .*unconstitutional.*");
    private static final Pattern FINAL_PASSAGE_PAT1 =
            Pattern.compile("Final [Pp]assage.*");
    private static final Pattern FINAL_PASSAGE_PAT2 =
            Pattern.compile(".*and [Ff]inal [Pp]assage.*");
    private static final Pattern REMARKS_PAT =
            Pattern.compile(".*[Rr]emarks.*");
    private static final Pattern ADOPTED_PAT =
            Pattern.compile(".*[Aa]dopted.*");
    private static final Pattern NONCONTROVERSIAL_PAT =
            Pattern.compile("INTRODUCED AS NONCONTROVERSIAL.*");
    private static final Pattern TRANSMITTED_PAT =
            Pattern.compile("Transmitted.*");
    private static final Pattern CONSIDERATION_PAT =
            Pattern.compile(".*[Cc]onsideration.*");

    private interface CurrentState {

        public CurrentState processAction(Action action);

        public int getStateCode();
    }

    private class NeverReachedChamber implements CurrentState {

        private static final int STATE_CODE = 99;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            // Action not for this chamber, then no change
            if (!action.getActionChamber().equals(chamber)) {
                return this;
            } else if (REFERRED_PAT.matcher(action.getActionTaken()).matches()
                    || (COMMITTED_PAT.matcher(action.getActionTaken()).matches())) {
                return new NotReported();
            } else if (type.equals("R")
                    && NONCONTROVERSIAL_PAT.matcher(action.getActionTaken()).matches()) {
                return new NonControversial();
            } else if (type.equals("R")
                    && ADOPTED_PAT.matcher(action.getActionTaken()).matches()) {
                dateAdopted = action.getTheDate();
                return new PassedOnFloor0();
            } else if (FINAL_PASSAGE_PAT1.matcher(action.getActionTaken()).matches()
                    || FINAL_PASSAGE_PAT2.matcher(action.getActionTaken()).matches()) {
                return new PassedOnFloor0();
            } else if (CONSIDERATION_PAT.matcher(action.getActionTaken()).matches()) {
                return new DefetedOnFloor();
            } else {
                return this;
            }
        }
    }

    private class NotReported implements CurrentState {

        private static final int STATE_CODE = 0;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            if (!action.getActionChamber().equals(chamber)) {
                return this;
            } else if (type.equals("R")
                    && ADOPTED_PAT.matcher(action.getActionTaken()).matches()) {
                dateAdopted = action.getTheDate();
                return new PassedOnFloor0();
            } else {
                return new Reported();
            }
        }
    }

    private class Reported implements CurrentState {

        private static final int STATE_CODE = 1;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            if (!action.getActionChamber().equals(chamber)) {
                return this;
            } else if (type.equals("R")
                    && ADOPTED_PAT.matcher(action.getActionTaken()).matches()) {
                dateAdopted = action.getTheDate();
                return new PassedOnFloor0();
            } else {
                return new DefetedOnFloor();
            }
        }
    }

    private class DefetedOnFloor implements CurrentState {

        private static final int STATE_CODE = 3;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            // An action that is in another chamber means that it passed
            if (!action.getActionChamber().equals(chamber)) {
                return new PassedOnFloor1();
            } else {
                String actionPerformed = action.getActionTaken();
                if (FINAL_PASSAGE_PAT1.matcher(actionPerformed).matches()
                        || FINAL_PASSAGE_PAT2.matcher(actionPerformed).matches()) {
                    return new PassedOnFloor0();
                } else if (type.equals("R")
                        && ADOPTED_PAT.matcher(action.getActionTaken()).matches()) {
                    dateAdopted = action.getTheDate();
                    return new PassedOnFloor0();
                } else {
                    return this;
                }
            }
        }
    }

    private class PassedOnFloor0 implements CurrentState {

        private static final int STATE_CODE = 2;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            if (!action.getActionChamber().equals(chamber)) {
                return new PassedOnFloor1();
            } else if (REMARKS_PAT.matcher(action.getActionTaken()).matches()) {
                return this;
            } else if (TRANSMITTED_PAT.matcher(action.getActionTaken()).matches()) {
                return this;
            } else if (SIGNED_PAT.matcher(action.getActionTaken()).matches()) {
                return new PassedOnFloor3();
            } else {
                dateAdopted = null;
                return new DefetedOnFloor();
            }
        }
    }

    private class PassedOnFloor1 implements CurrentState {

        private static final int STATE_CODE = 2;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            if (!action.getActionChamber().equals(chamber)) {
                return this;
            }
            // An action that is in this chamber, then it came back with amendments
            if (action.getActionChamber().equals(chamber)) {
                return new PassedOnFloor2();
            } else {
                return this;
            }
        }
    }

    private class PassedOnFloor2 implements CurrentState {

        private static final int STATE_CODE = 2;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            if (!action.getActionChamber().equals(chamber)) {
                return this;
            }
            String actionPerformed = action.getActionTaken();
            if (CONCURR_PAT.matcher(actionPerformed).matches()) {
                dateAdopted = action.getTheDate();
                return new Concurred();
            } else if (NON_CONCURR_PAT.matcher(actionPerformed).matches()) {
                return new NonConcurred();
            } else if (CONF_COMMITTEE_ADOPTED_PAT.matcher(actionPerformed).matches()) {
                dateAdopted = action.getTheDate();
                return new PassedConferenceReport();
            } else if (CONF_COMMITTEE_REJECTED_PAT.matcher(actionPerformed).matches()) {
                return new DefeatedConferenceReport();
            } else if (CONF_COMMITTEE_UNCONSTITUTIONAL_PAT.matcher(actionPerformed).matches()) {
                return new DefeatedConferenceReport();
            } else if (SIGNED_PAT.matcher(action.getActionTaken()).matches()) {
                return new PassedOnFloor3();
            } else {
                dateAdopted = null;
                return new NonConcurred();
            }
        }
    }

    private class PassedOnFloor3 implements CurrentState {

        private static final int STATE_CODE = 2;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            return this;
        }
    }

    private class NonConcurred implements CurrentState {

        private static final int STATE_CODE = 5;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            if (!action.getActionChamber().equals(chamber)) {
                return this;
            }
            String actionPerformed = action.getActionTaken();
            if (CONCURR_PAT.matcher(actionPerformed).matches()) {
                return new Concurred();
            } else if (CONF_COMMITTEE_ADOPTED_PAT.matcher(actionPerformed).matches()) {
                return new PassedConferenceReport();
            } else if (CONF_COMMITTEE_REJECTED_PAT.matcher(actionPerformed).matches()) {
                return new DefeatedConferenceReport();
            } else if (CONF_COMMITTEE_UNCONSTITUTIONAL_PAT.matcher(actionPerformed).matches()) {
                return new DefeatedConferenceReport();
            } else {
                return this;
            }
        }
    }

    private class Concurred implements CurrentState {

        private static final int STATE_CODE = 4;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            if (!action.getActionChamber().equals(chamber)) {
                return this;
            }
            String actionPerformed = action.getActionTaken();
            if (NON_CONCURR_PAT.matcher(actionPerformed).matches()) {
                return new NonConcurred();
            } else if (CONF_COMMITTEE_ADOPTED_PAT.matcher(actionPerformed).matches()) {
                dateAdopted = action.getTheDate();
                return new PassedConferenceReport();
            } else if (CONF_COMMITTEE_REJECTED_PAT.matcher(actionPerformed).matches()) {
                dateAdopted = null;
                return new DefeatedConferenceReport();
            } else if (CONF_COMMITTEE_UNCONSTITUTIONAL_PAT.matcher(actionPerformed).matches()) {
                dateAdopted = null;
                return new DefeatedConferenceReport();
            } else {
                return this;
            }
        }
    }

    private class DefeatedConferenceReport implements CurrentState {

        private static final int STATE_CODE = 7;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            if (!action.getActionChamber().equals(chamber)) {
                return this;
            }
            String actionPerformed = action.getActionTaken();
            if (CONF_COMMITTEE_ADOPTED_PAT.matcher(actionPerformed).matches()) {
                dateAdopted = action.getTheDate();
                return new PassedConferenceReport();
            } else {
                return this;
            }
        }
    }

    private class PassedConferenceReport implements CurrentState {

        private static final int STATE_CODE = 6;

        public int getStateCode() {
            return STATE_CODE;
        }

        public CurrentState processAction(Action action) {
            if (!action.getActionChamber().equals(chamber)) {
                return this;
            }
            String actionPerformed = action.getActionTaken();
            if (CONF_COMMITTEE_REJECTED_PAT.matcher(actionPerformed).matches()) {
                return new DefeatedConferenceReport();
            } else if (CONF_COMMITTEE_UNCONSTITUTIONAL_PAT.matcher(actionPerformed).matches()) {
                return new DefeatedConferenceReport();
            } else {
                return this;
            }
        }
    }

    private class NonControversial implements CurrentState {

        private static final int STATE_CODE = 3;

        public CurrentState processAction(Action action) {
            if (ADOPTED_PAT.matcher(action.getActionTaken()).matches()) {
                dateAdopted = action.getTheDate();
                return new PassedOnFloor0();
            } else {
                return new DefetedOnFloor();
            }
        }

        public int getStateCode() {
            return STATE_CODE;
        }

        ;
    }

    public void processAction(Action action) {
        if (action != null) {
            currentState = currentState.processAction(action);
        }
    }

    public ChamberState(
            String chamber, String type, String billId) {
        this.billId = billId;
        this.type = type;
        this.chamber = chamber;
        currentState = new NeverReachedChamber();


    }

    public int getFinalStateCode() {
        return currentState.getStateCode();

    }

    public Date getDateAdopted() {
        return dateAdopted;
    }

}
