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
