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

import java.util.regex.Pattern;

/**
 *
 * @author Paul Wolfgang
 */
public class GovernorState implements State {

    private static final Pattern VETO_PAT =
            Pattern.compile("Veto.*No.*");
    private static final Pattern LINE_ITEM_VETO_PAT1 =
            Pattern.compile(".*[Ii]tem.*[Vv]eto.*");
    private static final Pattern LINE_ITEM_VETO_PAT2 =
            Pattern.compile(".*[Ll]ine.*[Vv]eto.*");
    private static final Pattern LAW_NO_SIG_PAT =
            Pattern.compile(".*without.*[Gg]overnor.*");
    private static final Pattern SIGNED_BY_GOV_PAT =
            Pattern.compile(".*[Aa]pproved.*[Gg]overnor.*");
    private static final Pattern ADOPTED = Pattern.compile("[Aa]dopted");
    private boolean toGovernor;
    private final boolean becameLaw;
    private boolean vetoed;
    private boolean lineItemVeto;
    private boolean approvedByGovernor;
    private boolean lawNoGovSig;

    public GovernorState(boolean becameLaw) {
        this.becameLaw = becameLaw;
        toGovernor = false;
        vetoed = false;
        lineItemVeto = false;
        approvedByGovernor = false;
        lawNoGovSig = false;
    }

    public void processAction(Action action) {
        if (action != null) {
            if (action.getActionChamber().equals("E")) {
                toGovernor = true;
            }
            if (isToGovernor()) {
                String actionTaken = action.getVerb();
                if (VETO_PAT.matcher(actionTaken).matches()) {
                    vetoed = true;
                }
                if (LINE_ITEM_VETO_PAT1.matcher(actionTaken).matches()
                        || LINE_ITEM_VETO_PAT2.matcher(actionTaken).matches()) {
                    lineItemVeto = true;
                }
                if (SIGNED_BY_GOV_PAT.matcher(actionTaken).matches()) {
                    approvedByGovernor = true;
                }
                if (LAW_NO_SIG_PAT.matcher(actionTaken).matches()) {
                    lawNoGovSig = true;
                }
            }
        }
    }

    /**
     * @return the toGovernor
     */
    public boolean isToGovernor() {
        return toGovernor;
    }

    /**
     * @return the becameLaw
     */
    public boolean isBecameLaw() {
        return becameLaw;
    }

    /**
     * @return the vetoed
     */
    public boolean isVetoed() {
        return vetoed;
    }

    /**
     * @return the lineItemVeto
     */
    public boolean isLineItemVeto() {
        return lineItemVeto;
    }

    /**
     * @return the approvedByGovernor
     */
    public boolean isApprovedByGovernor() {
        return approvedByGovernor;
    }

    /**
     * @return the lawNoGovSig
     */
    public boolean isLawNoGovSig() {
        return lawNoGovSig;
    }
}
