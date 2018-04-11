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
package edu.temple.cla.policydb.billdao;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * Model of a Bill as it appears in the Bills_Data table.
 * @author Paul
 */
public class Bill {
    
    private static final Logger LOGGER = Logger.getLogger(Bill.class);
    
    /**
     * Committee codes
     */
    private static final int[] CTY_CODES = {
        101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113,
        114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126,
        127, 130, 201, 202, 203, 205, 207, 208, 209, 210, 211, 212, 213,
        214, 215, 216, 217, 219, 221, 222, 224, 225, 226, 227, 228, 230};

    private String id;
    
    private short chamber;
     
    private String session_;
     
    private String bill;
     
    private String subtype;
     
    private String hyperlink;
     
    private String sponsor;
     
    private short sponsorParty;
     
    private String sponsorCounty;

    private String sponsorTerms;
     
    private String abstract_;
     
    private boolean tax;
     
    private boolean eld;
     
    private short conAmmend;
     
    private String actNo;
     
    private Date dateEnacted;

    private int code;
      
    private boolean commemorative;
     
    private boolean appropriations;

    private boolean $101p;

    private boolean $101o;

    private boolean $102p;

    private boolean $102o;

    private boolean $103p;

    private boolean $103o;

    private boolean $104p;

    private boolean $104o;

    private boolean $105p;

    private boolean $105o;

    private boolean $106p;

    private boolean $106o;

    private boolean $107p;

    private boolean $107o;

    private boolean $108p;

    private boolean $108o;

    private boolean $109p;

    private boolean $109o;

    private boolean $110p;

    private boolean $110o;

    private boolean $111p;

    private boolean $111o;

    private boolean $112p;

    private boolean $112o;

    private boolean $113p;

    private boolean $113o;

    private boolean $114p;

    private boolean $114o;

    private boolean $115p;

    private boolean $115o;

    private boolean $116p;

    private boolean $116o;

    private boolean $117p;

    private boolean $117o;

    private boolean $118p;

    private boolean $118o;

    private boolean $119p;

    private boolean $119o;

    private boolean $120p;

    private boolean $120o;

    private boolean $121p;

    private boolean $121o;

    private boolean $122p;

    private boolean $122o;

    private boolean $123p;

    private boolean $123o;

    private boolean $124p;

    private boolean $124o;

    private boolean $125p;

    private boolean $125o;

    private boolean $126p;

    private boolean $126o;

    private boolean $127p;

    private boolean $127o;

    private boolean $130p;

    private boolean $130o;

    private boolean $201p;

    private boolean $201o;

    private boolean $202p;

    private boolean $202o;

    private boolean $203p;

    private boolean $203o;

    private boolean $205p;

    private boolean $205o;

    private boolean $207p;

    private boolean $207o;

    private boolean $208p;

    private boolean $208o;

    private boolean $209p;

    private boolean $209o;

    private boolean $210p;

    private boolean $210o;

    private boolean $211p;

    private boolean $211o;

    private boolean $212p;

    private boolean $212o;

    private boolean $213p;

    private boolean $213o;

    private boolean $214p;

    private boolean $214o;

    private boolean $215p;

    private boolean $215o;

    private boolean $216p;

    private boolean $216o;

    private boolean $217p;

    private boolean $217o;

    private boolean $219p;

    private boolean $219o;

    private boolean $221p;

    private boolean $221o;

    private boolean $222p;

    private boolean $222o;

    private boolean $224p;

    private boolean $224o;

    private boolean $225p;

    private boolean $225o;

    private boolean $226p;

    private boolean $226o;

    private boolean $227p;

    private boolean $227o;

    private boolean $228p;

    private boolean $228o;

    private boolean $230p;

    private boolean $230o;

    private boolean $300;
     
    private boolean vetoIneligible;
     
    private boolean vetoed;
     
    private boolean lineItemVeto;
     
    private boolean vetoOverriden;
     
    private boolean lawNoGovSig;
    
    private boolean recalledByLeg;
     
    private int houseLastAction;
     
    private int senateLastAction;
     
    private boolean petition;
     
    private Date dateReferred;
     
    private boolean signedbyGov;

    public Bill() {
    }

	
    public Bill(String id) {
        this.id = id;
    }
    
    public Bill(String id, short chamber, String session_, String bill, String subtype, String hyperlink, String sponsor, short sponsorParty, String sponsorCounty, String sponsorTerms, String abstract_, boolean tax, boolean eld, short conAmmend, String actNo, Date dateEnacted, int code, boolean commemorative, boolean appropriations, boolean $101p, boolean $101o, boolean $102p, boolean $102o, boolean $103p, boolean $103o, boolean $104p, boolean $104o, boolean $105p, boolean $105o, boolean $106p, boolean $106o, boolean $107p, boolean $107o, boolean $108p, boolean $108o, boolean $109p, boolean $109o, boolean $110p, boolean $110o, boolean $111p, boolean $111o, boolean $112p, boolean $112o, boolean $113p, boolean $113o, boolean $114p, boolean $114o, boolean $115p, boolean $115o, boolean $116p, boolean $116o, boolean $117p, boolean $117o, boolean $118p, boolean $118o, boolean $119p, boolean $119o, boolean $120p, boolean $120o, boolean $121p, boolean $121o, boolean $122p, boolean $122o, boolean $123p, boolean $123o, boolean $124p, boolean $124o, boolean $125p, boolean $125o, boolean $126p, boolean $126o, boolean $127p, boolean $127o, boolean $130p, boolean $130o, boolean $201p, boolean $201o, boolean $202p, boolean $202o, boolean $203p, boolean $203o, boolean $205p, boolean $205o, boolean $207p, boolean $207o, boolean $208p, boolean $208o, boolean $209p, boolean $209o, boolean $210p, boolean $210o, boolean $211p, boolean $211o, boolean $212p, boolean $212o, boolean $213p, boolean $213o, boolean $214p, boolean $214o, boolean $215p, boolean $215o, boolean $216p, boolean $216o, boolean $217p, boolean $217o, boolean $219p, boolean $219o, boolean $221p, boolean $221o, boolean $222p, boolean $222o, boolean $224p, boolean $224o, boolean $225p, boolean $225o, boolean $226p, boolean $226o, boolean $227p, boolean $227o, boolean $228p, boolean $228o, boolean $230p, boolean $230o, boolean $300, boolean vetoIneligible, boolean vetoed, boolean lineItemVeto, boolean vetoOverriden, boolean lawNoGovSig, boolean recalledByLeg, int houseLastAction, int senateLastAction, boolean petition, Date dateReferred, boolean signedbyGov) {
       this.id = id;
       this.chamber = chamber;
       this.session_ = session_;
       this.bill = bill;
       this.subtype = subtype;
       this.hyperlink = hyperlink;
       this.sponsor = sponsor;
       this.sponsorParty = sponsorParty;
       this.sponsorCounty = sponsorCounty;
       this.sponsorTerms = sponsorTerms;
       this.abstract_ = abstract_;
       this.tax = tax;
       this.eld = eld;
       this.conAmmend = conAmmend;
       this.actNo = actNo;
       this.dateEnacted = dateEnacted;
       this.code = code;
       this.commemorative = commemorative;
       this.appropriations = appropriations;
       this.$101p = $101p;
       this.$101o = $101o;
       this.$102p = $102p;
       this.$102o = $102o;
       this.$103p = $103p;
       this.$103o = $103o;
       this.$104p = $104p;
       this.$104o = $104o;
       this.$105p = $105p;
       this.$105o = $105o;
       this.$106p = $106p;
       this.$106o = $106o;
       this.$107p = $107p;
       this.$107o = $107o;
       this.$108p = $108p;
       this.$108o = $108o;
       this.$109p = $109p;
       this.$109o = $109o;
       this.$110p = $110p;
       this.$110o = $110o;
       this.$111p = $111p;
       this.$111o = $111o;
       this.$112p = $112p;
       this.$112o = $112o;
       this.$113p = $113p;
       this.$113o = $113o;
       this.$114p = $114p;
       this.$114o = $114o;
       this.$115p = $115p;
       this.$115o = $115o;
       this.$116p = $116p;
       this.$116o = $116o;
       this.$117p = $117p;
       this.$117o = $117o;
       this.$118p = $118p;
       this.$118o = $118o;
       this.$119p = $119p;
       this.$119o = $119o;
       this.$120p = $120p;
       this.$120o = $120o;
       this.$121p = $121p;
       this.$121o = $121o;
       this.$122p = $122p;
       this.$122o = $122o;
       this.$123p = $123p;
       this.$123o = $123o;
       this.$124p = $124p;
       this.$124o = $124o;
       this.$125p = $125p;
       this.$125o = $125o;
       this.$126p = $126p;
       this.$126o = $126o;
       this.$127p = $127p;
       this.$127o = $127o;
       this.$130p = $130p;
       this.$130o = $130o;
       this.$201p = $201p;
       this.$201o = $201o;
       this.$202p = $202p;
       this.$202o = $202o;
       this.$203p = $203p;
       this.$203o = $203o;
       this.$205p = $205p;
       this.$205o = $205o;
       this.$207p = $207p;
       this.$207o = $207o;
       this.$208p = $208p;
       this.$208o = $208o;
       this.$209p = $209p;
       this.$209o = $209o;
       this.$210p = $210p;
       this.$210o = $210o;
       this.$211p = $211p;
       this.$211o = $211o;
       this.$212p = $212p;
       this.$212o = $212o;
       this.$213p = $213p;
       this.$213o = $213o;
       this.$214p = $214p;
       this.$214o = $214o;
       this.$215p = $215p;
       this.$215o = $215o;
       this.$216p = $216p;
       this.$216o = $216o;
       this.$217p = $217p;
       this.$217o = $217o;
       this.$219p = $219p;
       this.$219o = $219o;
       this.$221p = $221p;
       this.$221o = $221o;
       this.$222p = $222p;
       this.$222o = $222o;
       this.$224p = $224p;
       this.$224o = $224o;
       this.$225p = $225p;
       this.$225o = $225o;
       this.$226p = $226p;
       this.$226o = $226o;
       this.$227p = $227p;
       this.$227o = $227o;
       this.$228p = $228p;
       this.$228o = $228o;
       this.$230p = $230p;
       this.$230o = $230o;
       this.$300 = $300;
       this.vetoIneligible = vetoIneligible;
       this.vetoed = vetoed;
       this.lineItemVeto = lineItemVeto;
       this.vetoOverriden = vetoOverriden;
       this.lawNoGovSig = lawNoGovSig;
       this.recalledByLeg = recalledByLeg;
       this.houseLastAction = houseLastAction;
       this.senateLastAction = senateLastAction;
       this.petition = petition;
       this.dateReferred = dateReferred;
       this.signedbyGov = signedbyGov;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public short getChamber() {
        return this.chamber;
    }
    
    public void setChamber(short chamber) {
        this.chamber = chamber;
    }

    public String getSession_() {
        return this.session_;
    }
    
    public void setSession_(String session_) {
        this.session_ = session_;
    }
    public String getBill() {
        return this.bill;
    }
    
    public void setBill(String bill) {
        this.bill = bill;
    }
    
    public String getSubtype() {
        return this.subtype;
    }
    
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
    
    public String getHyperlink() {
        return this.hyperlink;
    }
    
    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }
    public String getSponsor() {
        return this.sponsor;
    }
    
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }
    public short getSponsorParty() {
        return this.sponsorParty;
    }
    
    public void setSponsorParty(short sponsorParty) {
        this.sponsorParty = sponsorParty;
    }
    public String getSponsorCounty() {
        return this.sponsorCounty;
    }
    
    public void setSponsorCounty(String sponsorCounty) {
        this.sponsorCounty = sponsorCounty;
    }
    public String getSponsorTerms() {
        return this.sponsorTerms;
    }
    
    public void setSponsorTerms(String sponsorTerms) {
        this.sponsorTerms = sponsorTerms;
    }
    public String getAbstract_() {
        return this.abstract_;
    }
    
    public void setAbstract_(String abstract_) {
        this.abstract_ = abstract_;
    }
    public boolean getTax() {
        return this.tax;
    }
    
    public void setTax(boolean tax) {
        this.tax = tax;
    }
    public boolean getEld() {
        return this.eld;
    }
    
    public void setEld(boolean eld) {
        this.eld = eld;
    }
    public short getConAmmend() {
        return this.conAmmend;
    }
    
    public void setConAmmend(short conAmmend) {
        this.conAmmend = conAmmend;
    }
    public String getActNo() {
        return this.actNo;
    }
    
    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    public Date getDateEnacted() {
        return this.dateEnacted;
    }

    public void setDateEnacted(Date dateEnacted) {
        this.dateEnacted = dateEnacted;
    }

    public int getCode() {
        return this.code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    public boolean getCommemorative() {
        return this.commemorative;
    }
    
    public void setCommemorative(boolean commemorative) {
        this.commemorative = commemorative;
    }

    public boolean getAppropriations() {
        return this.appropriations;
    }
    
    public void setAppropriations(boolean appropriations){
        this.appropriations = appropriations;
    }
    
    public boolean getVetoIneligible() {
        return this.vetoIneligible;
    }
    
    public void setVetoIneligible(boolean vetoIneligible) {
        this.vetoIneligible = vetoIneligible;
    }
    public boolean getVetoed() {
        return this.vetoed;
    }
    
    public void setVetoed(boolean vetoed) {
        this.vetoed = vetoed;
    }
    public boolean getLineItemVeto() {
        return this.lineItemVeto;
    }
    
    public void setLineItemVeto(boolean lineItemVeto) {
        this.lineItemVeto = lineItemVeto;
    }
    public boolean getVetoOverriden() {
        return this.vetoOverriden;
    }
    
    public void setVetoOverriden(boolean vetoOverriden) {
        this.vetoOverriden = vetoOverriden;
    }
    public boolean getLawNoGovSig() {
        return this.lawNoGovSig;
    }
    
    public void setLawNoGovSig(boolean lawNoGovSig) {
        this.lawNoGovSig = lawNoGovSig;
    }
    public boolean getRecalledByLeg() {
        return this.recalledByLeg;
    }
    
    public void setRecalledByLeg(boolean recalledByLeg) {
        this.recalledByLeg = recalledByLeg;
    }
    public int getHouseLastAction() {
        return this.houseLastAction;
    }
    
    public void setHouseLastAction(int houseLastAction) {
        this.houseLastAction = houseLastAction;
    }
    public int getSenateLastAction() {
        return this.senateLastAction;
    }
    
    public void setSenateLastAction(int senateLastAction) {
        this.senateLastAction = senateLastAction;
    }

    public boolean getPetition() {
        return this.petition;
    }
    
    public void setPetition(boolean petition) {
        this.petition = petition;
    }

    public Date getDateReferred() {
        return this.dateReferred;
    }

    public void setDateReferred(Date dateReferred) {
        this.dateReferred = dateReferred;
    }

    public boolean getSignedbyGov() {
        return this.signedbyGov;
    }
    
    public void setSignedbyGov(boolean signedbyGov) {
        this.signedbyGov = signedbyGov;
    }

    /**
     * @return the $101p
     */
    public boolean get$101p() {
        return $101p;
    }

    /**
     * @param $101p the $101p to set
     */
    public void set$101p(boolean $101p) {
        this.$101p = $101p;
    }

    /**
     * @return the $101o
     */
    public boolean get$101o() {
        return $101o;
    }

    /**
     * @param $101o the $101o to set
     */
    public void set$101o(boolean $101o) {
        this.$101o = $101o;
    }

    /**
     * @return the $102p
     */
    public boolean get$102p() {
        return $102p;
    }

    /**
     * @param $102p the $102p to set
     */
    public void set$102p(boolean $102p) {
        this.$102p = $102p;
    }

    /**
     * @return the $102o
     */
    public boolean get$102o() {
        return $102o;
    }

    /**
     * @param $102o the $102o to set
     */
    public void set$102o(boolean $102o) {
        this.$102o = $102o;
    }

    /**
     * @return the $103p
     */
    public boolean get$103p() {
        return $103p;
    }

    /**
     * @param $103p the $103p to set
     */
    public void set$103p(boolean $103p) {
        this.$103p = $103p;
    }

    /**
     * @return the $103o
     */
    public boolean get$103o() {
        return $103o;
    }

    /**
     * @param $103o the $103o to set
     */
    public void set$103o(boolean $103o) {
        this.$103o = $103o;
    }

    /**
     * @return the $104p
     */
    public boolean get$104p() {
        return $104p;
    }

    /**
     * @param $104p the $104p to set
     */
    public void set$104p(boolean $104p) {
        this.$104p = $104p;
    }

    /**
     * @return the $104o
     */
    public boolean get$104o() {
        return $104o;
    }

    /**
     * @param $104o the $104o to set
     */
    public void set$104o(boolean $104o) {
        this.$104o = $104o;
    }

    /**
     * @return the $105p
     */
    public boolean get$105p() {
        return $105p;
    }

    /**
     * @param $105p the $105p to set
     */
    public void set$105p(boolean $105p) {
        this.$105p = $105p;
    }

    /**
     * @return the $105o
     */
    public boolean get$105o() {
        return $105o;
    }

    /**
     * @param $105o the $105o to set
     */
    public void set$105o(boolean $105o) {
        this.$105o = $105o;
    }

    /**
     * @return the $106p
     */
    public boolean get$106p() {
        return $106p;
    }

    /**
     * @param $106p the $106p to set
     */
    public void set$106p(boolean $106p) {
        this.$106p = $106p;
    }

    /**
     * @return the $106o
     */
    public boolean get$106o() {
        return $106o;
    }

    /**
     * @param $106o the $106o to set
     */
    public void set$106o(boolean $106o) {
        this.$106o = $106o;
    }

    /**
     * @return the $107p
     */
    public boolean get$107p() {
        return $107p;
    }

    /**
     * @param $107p the $107p to set
     */
    public void set$107p(boolean $107p) {
        this.$107p = $107p;
    }

    /**
     * @return the $107o
     */
    public boolean get$107o() {
        return $107o;
    }

    /**
     * @param $107o the $107o to set
     */
    public void set$107o(boolean $107o) {
        this.$107o = $107o;
    }

    /**
     * @return the $108p
     */
    public boolean get$108p() {
        return $108p;
    }

    /**
     * @param $108p the $108p to set
     */
    public void set$108p(boolean $108p) {
        this.$108p = $108p;
    }

    /**
     * @return the $108o
     */
    public boolean get$108o() {
        return $108o;
    }

    /**
     * @param $108o the $108o to set
     */
    public void set$108o(boolean $108o) {
        this.$108o = $108o;
    }

    /**
     * @return the $109p
     */
    public boolean get$109p() {
        return $109p;
    }

    /**
     * @param $109p the $109p to set
     */
    public void set$109p(boolean $109p) {
        this.$109p = $109p;
    }

    /**
     * @return the $109o
     */
    public boolean get$109o() {
        return $109o;
    }

    /**
     * @param $109o the $109o to set
     */
    public void set$109o(boolean $109o) {
        this.$109o = $109o;
    }

    /**
     * @return the $110p
     */
    public boolean get$110p() {
        return $110p;
    }

    /**
     * @param $110p the $110p to set
     */
    public void set$110p(boolean $110p) {
        this.$110p = $110p;
    }

    /**
     * @return the $110o
     */
    public boolean get$110o() {
        return $110o;
    }

    /**
     * @param $110o the $110o to set
     */
    public void set$110o(boolean $110o) {
        this.$110o = $110o;
    }

    /**
     * @return the $111p
     */
    public boolean get$111p() {
        return $111p;
    }

    /**
     * @param $111p the $111p to set
     */
    public void set$111p(boolean $111p) {
        this.$111p = $111p;
    }

    /**
     * @return the $111o
     */
    public boolean get$111o() {
        return $111o;
    }

    /**
     * @param $111o the $111o to set
     */
    public void set$111o(boolean $111o) {
        this.$111o = $111o;
    }

    /**
     * @return the $112p
     */
    public boolean get$112p() {
        return $112p;
    }

    /**
     * @param $112p the $112p to set
     */
    public void set$112p(boolean $112p) {
        this.$112p = $112p;
    }

    /**
     * @return the $112o
     */
    public boolean get$112o() {
        return $112o;
    }

    /**
     * @param $112o the $112o to set
     */
    public void set$112o(boolean $112o) {
        this.$112o = $112o;
    }

    /**
     * @return the $113p
     */
    public boolean get$113p() {
        return $113p;
    }

    /**
     * @param $113p the $113p to set
     */
    public void set$113p(boolean $113p) {
        this.$113p = $113p;
    }

    /**
     * @return the $113o
     */
    public boolean get$113o() {
        return $113o;
    }

    /**
     * @param $113o the $113o to set
     */
    public void set$113o(boolean $113o) {
        this.$113o = $113o;
    }

    /**
     * @return the $114p
     */
    public boolean get$114p() {
        return $114p;
    }

    /**
     * @param $114p the $114p to set
     */
    public void set$114p(boolean $114p) {
        this.$114p = $114p;
    }

    /**
     * @return the $114o
     */
    public boolean get$114o() {
        return $114o;
    }

    /**
     * @param $114o the $114o to set
     */
    public void set$114o(boolean $114o) {
        this.$114o = $114o;
    }

    /**
     * @return the $115p
     */
    public boolean get$115p() {
        return $115p;
    }

    /**
     * @param $115p the $115p to set
     */
    public void set$115p(boolean $115p) {
        this.$115p = $115p;
    }

    /**
     * @return the $115o
     */
    public boolean get$115o() {
        return $115o;
    }

    /**
     * @param $115o the $115o to set
     */
    public void set$115o(boolean $115o) {
        this.$115o = $115o;
    }

    /**
     * @return the $116p
     */
    public boolean get$116p() {
        return $116p;
    }

    /**
     * @param $116p the $116p to set
     */
    public void set$116p(boolean $116p) {
        this.$116p = $116p;
    }

    /**
     * @return the $116o
     */
    public boolean get$116o() {
        return $116o;
    }

    /**
     * @param $116o the $116o to set
     */
    public void set$116o(boolean $116o) {
        this.$116o = $116o;
    }

    /**
     * @return the $117p
     */
    public boolean get$117p() {
        return $117p;
    }

    /**
     * @param $117p the $117p to set
     */
    public void set$117p(boolean $117p) {
        this.$117p = $117p;
    }

    /**
     * @return the $117o
     */
    public boolean get$117o() {
        return $117o;
    }

    /**
     * @param $117o the $117o to set
     */
    public void set$117o(boolean $117o) {
        this.$117o = $117o;
    }

    /**
     * @return the $118p
     */
    public boolean get$118p() {
        return $118p;
    }

    /**
     * @param $118p the $118p to set
     */
    public void set$118p(boolean $118p) {
        this.$118p = $118p;
    }

    /**
     * @return the $118o
     */
    public boolean get$118o() {
        return $118o;
    }

    /**
     * @param $118o the $118o to set
     */
    public void set$118o(boolean $118o) {
        this.$118o = $118o;
    }

    /**
     * @return the $119p
     */
    public boolean get$119p() {
        return $119p;
    }

    /**
     * @param $119p the $119p to set
     */
    public void set$119p(boolean $119p) {
        this.$119p = $119p;
    }

    /**
     * @return the $119o
     */
    public boolean get$119o() {
        return $119o;
    }

    /**
     * @param $119o the $119o to set
     */
    public void set$119o(boolean $119o) {
        this.$119o = $119o;
    }

    /**
     * @return the $120p
     */
    public boolean get$120p() {
        return $120p;
    }

    /**
     * @param $120p the $120p to set
     */
    public void set$120p(boolean $120p) {
        this.$120p = $120p;
    }

    /**
     * @return the $120o
     */
    public boolean get$120o() {
        return $120o;
    }

    /**
     * @param $120o the $120o to set
     */
    public void set$120o(boolean $120o) {
        this.$120o = $120o;
    }

    /**
     * @return the $121p
     */
    public boolean get$121p() {
        return $121p;
    }

    /**
     * @param $121p the $121p to set
     */
    public void set$121p(boolean $121p) {
        this.$121p = $121p;
    }

    /**
     * @return the $121o
     */
    public boolean get$121o() {
        return $121o;
    }

    /**
     * @param $121o the $121o to set
     */
    public void set$121o(boolean $121o) {
        this.$121o = $121o;
    }

    /**
     * @return the $122p
     */
    public boolean get$122p() {
        return $122p;
    }

    /**
     * @param $122p the $122p to set
     */
    public void set$122p(boolean $122p) {
        this.$122p = $122p;
    }

    /**
     * @return the $122o
     */
    public boolean get$122o() {
        return $122o;
    }

    /**
     * @param $122o the $122o to set
     */
    public void set$122o(boolean $122o) {
        this.$122o = $122o;
    }

    /**
     * @return the $123p
     */
    public boolean get$123p() {
        return $123p;
    }

    /**
     * @param $123p the $123p to set
     */
    public void set$123p(boolean $123p) {
        this.$123p = $123p;
    }

    /**
     * @return the $123o
     */
    public boolean get$123o() {
        return $123o;
    }

    /**
     * @param $123o the $123o to set
     */
    public void set$123o(boolean $123o) {
        this.$123o = $123o;
    }

    /**
     * @return the $124p
     */
    public boolean get$124p() {
        return $124p;
    }

    /**
     * @param $124p the $124p to set
     */
    public void set$124p(boolean $124p) {
        this.$124p = $124p;
    }

    /**
     * @return the $124o
     */
    public boolean get$124o() {
        return $124o;
    }

    /**
     * @param $124o the $124o to set
     */
    public void set$124o(boolean $124o) {
        this.$124o = $124o;
    }

    /**
     * @return the $125p
     */
    public boolean get$125p() {
        return $125p;
    }

    /**
     * @param $125p the $125p to set
     */
    public void set$125p(boolean $125p) {
        this.$125p = $125p;
    }

    /**
     * @return the $125o
     */
    public boolean get$125o() {
        return $125o;
    }

    /**
     * @param $125o the $125o to set
     */
    public void set$125o(boolean $125o) {
        this.$125o = $125o;
    }

    /**
     * @return the $126p
     */
    public boolean get$126p() {
        return $126p;
    }

    /**
     * @param $126p the $126p to set
     */
    public void set$126p(boolean $126p) {
        this.$126p = $126p;
    }

    /**
     * @return the $126o
     */
    public boolean get$126o() {
        return $126o;
    }

    /**
     * @param $126o the $126o to set
     */
    public void set$126o(boolean $126o) {
        this.$126o = $126o;
    }

    /**
     * @return the $201p
     */
    public boolean get$201p() {
        return $201p;
    }

    /**
     * @param $201p the $201p to set
     */
    public void set$201p(boolean $201p) {
        this.$201p = $201p;
    }

    /**
     * @return the $201o
     */
    public boolean get$201o() {
        return $201o;
    }

    /**
     * @param $201o the $201o to set
     */
    public void set$201o(boolean $201o) {
        this.$201o = $201o;
    }

    /**
     * @return the $202p
     */
    public boolean get$202p() {
        return $202p;
    }

    /**
     * @param $202p the $202p to set
     */
    public void set$202p(boolean $202p) {
        this.$202p = $202p;
    }

    /**
     * @return the $202o
     */
    public boolean get$202o() {
        return $202o;
    }

    /**
     * @param $202o the $202o to set
     */
    public void set$202o(boolean $202o) {
        this.$202o = $202o;
    }

    /**
     * @return the $203p
     */
    public boolean get$203p() {
        return $203p;
    }

    /**
     * @param $203p the $203p to set
     */
    public void set$203p(boolean $203p) {
        this.$203p = $203p;
    }

    /**
     * @return the $203o
     */
    public boolean get$203o() {
        return $203o;
    }

    /**
     * @param $203o the $203o to set
     */
    public void set$203o(boolean $203o) {
        this.$203o = $203o;
    }

    /**
     * @return the $205p
     */
    public boolean get$205p() {
        return $205p;
    }

    /**
     * @param $205p the $205p to set
     */
    public void set$205p(boolean $205p) {
        this.$205p = $205p;
    }

    /**
     * @return the $205o
     */
    public boolean get$205o() {
        return $205o;
    }

    /**
     * @param $205o the $205o to set
     */
    public void set$205o(boolean $205o) {
        this.$205o = $205o;
    }

    /**
     * @return the $207p
     */
    public boolean get$207p() {
        return $207p;
    }

    /**
     * @param $207p the $207p to set
     */
    public void set$207p(boolean $207p) {
        this.$207p = $207p;
    }

    /**
     * @return the $207o
     */
    public boolean get$207o() {
        return $207o;
    }

    /**
     * @param $207o the $207o to set
     */
    public void set$207o(boolean $207o) {
        this.$207o = $207o;
    }

    /**
     * @return the $208p
     */
    public boolean get$208p() {
        return $208p;
    }

    /**
     * @param $208p the $208p to set
     */
    public void set$208p(boolean $208p) {
        this.$208p = $208p;
    }

    /**
     * @return the $208o
     */
    public boolean get$208o() {
        return $208o;
    }

    /**
     * @param $208o the $208o to set
     */
    public void set$208o(boolean $208o) {
        this.$208o = $208o;
    }

    /**
     * @return the $209p
     */
    public boolean get$209p() {
        return $209p;
    }

    /**
     * @param $209p the $209p to set
     */
    public void set$209p(boolean $209p) {
        this.$209p = $209p;
    }

    /**
     * @return the $209o
     */
    public boolean get$209o() {
        return $209o;
    }

    /**
     * @param $209o the $209o to set
     */
    public void set$209o(boolean $209o) {
        this.$209o = $209o;
    }

    /**
     * @return the $210p
     */
    public boolean get$210p() {
        return $210p;
    }

    /**
     * @param $210p the $210p to set
     */
    public void set$210p(boolean $210p) {
        this.$210p = $210p;
    }

    /**
     * @return the $210o
     */
    public boolean get$210o() {
        return $210o;
    }

    /**
     * @param $210o the $210o to set
     */
    public void set$210o(boolean $210o) {
        this.$210o = $210o;
    }

    /**
     * @return the $211p
     */
    public boolean get$211p() {
        return $211p;
    }

    /**
     * @param $211p the $211p to set
     */
    public void set$211p(boolean $211p) {
        this.$211p = $211p;
    }

    /**
     * @return the $211o
     */
    public boolean get$211o() {
        return $211o;
    }

    /**
     * @param $211o the $211o to set
     */
    public void set$211o(boolean $211o) {
        this.$211o = $211o;
    }

    /**
     * @return the $212p
     */
    public boolean get$212p() {
        return $212p;
    }

    /**
     * @param $212p the $212p to set
     */
    public void set$212p(boolean $212p) {
        this.$212p = $212p;
    }

    /**
     * @return the $212o
     */
    public boolean get$212o() {
        return $212o;
    }

    /**
     * @param $212o the $212o to set
     */
    public void set$212o(boolean $212o) {
        this.$212o = $212o;
    }

    /**
     * @return the $213p
     */
    public boolean get$213p() {
        return $213p;
    }

    /**
     * @param $213p the $213p to set
     */
    public void set$213p(boolean $213p) {
        this.$213p = $213p;
    }

    /**
     * @return the $213o
     */
    public boolean get$213o() {
        return $213o;
    }

    /**
     * @param $213o the $213o to set
     */
    public void set$213o(boolean $213o) {
        this.$213o = $213o;
    }

    /**
     * @return the $214p
     */
    public boolean get$214p() {
        return $214p;
    }

    /**
     * @param $214p the $214p to set
     */
    public void set$214p(boolean $214p) {
        this.$214p = $214p;
    }

    /**
     * @return the $214o
     */
    public boolean get$214o() {
        return $214o;
    }

    /**
     * @param $214o the $214o to set
     */
    public void set$214o(boolean $214o) {
        this.$214o = $214o;
    }

    /**
     * @return the $215p
     */
    public boolean get$215p() {
        return $215p;
    }

    /**
     * @param $215p the $215p to set
     */
    public void set$215p(boolean $215p) {
        this.$215p = $215p;
    }

    /**
     * @return the $215o
     */
    public boolean get$215o() {
        return $215o;
    }

    /**
     * @param $215o the $215o to set
     */
    public void set$215o(boolean $215o) {
        this.$215o = $215o;
    }

    /**
     * @return the $216p
     */
    public boolean get$216p() {
        return $216p;
    }

    /**
     * @param $216p the $216p to set
     */
    public void set$216p(boolean $216p) {
        this.$216p = $216p;
    }

    /**
     * @return the $216o
     */
    public boolean get$216o() {
        return $216o;
    }

    /**
     * @param $216o the $216o to set
     */
    public void set$216o(boolean $216o) {
        this.$216o = $216o;
    }

    /**
     * @return the $217p
     */
    public boolean get$217p() {
        return $217p;
    }

    /**
     * @param $217p the $217p to set
     */
    public void set$217p(boolean $217p) {
        this.$217p = $217p;
    }

    /**
     * @return the $217o
     */
    public boolean get$217o() {
        return $217o;
    }

    /**
     * @param $217o the $217o to set
     */
    public void set$217o(boolean $217o) {
        this.$217o = $217o;
    }

    /**
     * @return the $219p
     */
    public boolean get$219p() {
        return $219p;
    }

    /**
     * @param $219p the $219p to set
     */
    public void set$219p(boolean $219p) {
        this.$219p = $219p;
    }

    /**
     * @return the $219o
     */
    public boolean get$219o() {
        return $219o;
    }

    /**
     * @param $219o the $219o to set
     */
    public void set$219o(boolean $219o) {
        this.$219o = $219o;
    }

    /**
     * @return the $221p
     */
    public boolean get$221p() {
        return $221p;
    }

    /**
     * @param $221p the $221p to set
     */
    public void set$221p(boolean $221p) {
        this.$221p = $221p;
    }

    /**
     * @return the $221o
     */
    public boolean get$221o() {
        return $221o;
    }

    /**
     * @param $221o the $221o to set
     */
    public void set$221o(boolean $221o) {
        this.$221o = $221o;
    }

    /**
     * @return the $222p
     */
    public boolean get$222p() {
        return $222p;
    }

    /**
     * @param $222p the $222p to set
     */
    public void set$222p(boolean $222p) {
        this.$222p = $222p;
    }

    /**
     * @return the $222o
     */
    public boolean get$222o() {
        return $222o;
    }

    /**
     * @param $222o the $222o to set
     */
    public void set$222o(boolean $222o) {
        this.$222o = $222o;
    }

    /**
     * @return the $224p
     */
    public boolean get$224p() {
        return $224p;
    }

    /**
     * @param $224p the $224p to set
     */
    public void set$224p(boolean $224p) {
        this.$224p = $224p;
    }

    /**
     * @return the $224o
     */
    public boolean get$224o() {
        return $224o;
    }

    /**
     * @param $224o the $224o to set
     */
    public void set$224o(boolean $224o) {
        this.$224o = $224o;
    }

    /**
     * @return the $225p
     */
    public boolean get$225p() {
        return $225p;
    }

    /**
     * @param $225p the $225p to set
     */
    public void set$225p(boolean $225p) {
        this.$225p = $225p;
    }

    /**
     * @return the $225o
     */
    public boolean get$225o() {
        return $225o;
    }

    /**
     * @param $225o the $225o to set
     */
    public void set$225o(boolean $225o) {
        this.$225o = $225o;
    }

    /**
     * @return the $226p
     */
    public boolean get$226p() {
        return $226p;
    }

    /**
     * @param $226p the $226p to set
     */
    public void set$226p(boolean $226p) {
        this.$226p = $226p;
    }

    /**
     * @return the $226o
     */
    public boolean get$226o() {
        return $226o;
    }

    /**
     * @param $226o the $226o to set
     */
    public void set$226o(boolean $226o) {
        this.$226o = $226o;
    }

    /**
     * @return the $227p
     */
    public boolean get$227p() {
        return $227p;
    }

    /**
     * @param $227p the $227p to set
     */
    public void set$227p(boolean $227p) {
        this.$227p = $227p;
    }

    /**
     * @return the $227o
     */
    public boolean get$227o() {
        return $227o;
    }

    /**
     * @param $227o the $227o to set
     */
    public void set$227o(boolean $227o) {
        this.$227o = $227o;
    }

    /**
     * @return the $228p
     */
    public boolean get$228p() {
        return $228p;
    }

    /**
     * @param $228p the $228p to set
     */
    public void set$228p(boolean $228p) {
        this.$228p = $228p;
    }

    /**
     * @return the $228o
     */
    public boolean get$228o() {
        return $228o;
    }

    /**
     * @param $228o the $228o to set
     */
    public void set$228o(boolean $228o) {
        this.$228o = $228o;
    }

    /**
     * @return the $230p
     */
    public boolean get$230p() {
        return $230p;
    }

    /**
     * @param $230p the $230p to set
     */
    public void set$230p(boolean $230p) {
        this.$230p = $230p;
    }

    /**
     * @return the $230o
     */
    public boolean get$230o() {
        return $230o;
    }

    /**
     * @param $230o the $230o to set
     */
    public void set$230o(boolean $230o) {
        this.$230o = $230o;
    }

    /**
     * @return the $300
     */
    public boolean get$300() {
        return $300;
    }

    /**
     * @param $300 the $300 to set
     */
    public void set$300(boolean $300) {
        this.$300 = $300;
    }

    /**
     * @return the $127p
     */
    public boolean get$127p() {
        return $127p;
    }

    /**
     * @param $127p the $127p to set
     */
    public void set$127p(boolean $127p) {
        this.$127p = $127p;
    }

    /**
     * @return the $127o
     */
    public boolean get$127o() {
        return $127o;
    }

    /**
     * @param $127o the $127o to set
     */
    public void set$127o(boolean $127o) {
        this.$127o = $127o;
    }

    /**
     * @return the $130p
     */
    public boolean get$130p() {
        return $130p;
    }

    /**
     * @param $130p the $130p to set
     */
    public void set$130p(boolean $130p) {
        this.$130p = $130p;
    }

    /**
     * @return the $130o
     */
    public boolean get$130o() {
        return $130o;
    }

    /**
     * @param $130o the $130o to set
     */
    public void set$130o(boolean $130o) {
        this.$130o = $130o;
    }
    
    /**
     * Set committee
     * @param value
     * @param committeeCode
     * @param primary
     */
    public void setCommittee(boolean value, int committeeCode, boolean primary) {
        String methodName;
        if (committeeCode != 300) {
            methodName = String.format("set$%3d", committeeCode) + (primary ? "p":"o");
        } else {
            methodName = "set$300";
        }
        try {
            Method method = this.getClass().getDeclaredMethod(methodName, boolean.class);
            method.invoke(this, value);
        } catch (Exception e) {
                LOGGER.error("Error setting committee " + methodName, e);
                throw new RuntimeException("Error setting committee " + methodName, e);
        }
    }
    
    public void clearAll() {
        for (int ctyCode : CTY_CODES) {
            setCommittee(false, ctyCode, true);
            setCommittee(false, ctyCode, false);
        }
        setCommittee(false, 300, false);        
    }
    
}


