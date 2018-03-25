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
package edu.temple.cla.policydb.billshibernatedao;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

/**
 * Model of a Bill as it appears in the Bills_Data table.
 * @author Paul
 */
@Entity
@Table(name="Bills_Data")
@SuppressWarnings("serial")
public class Bill implements java.io.Serializable {
    
    @Id
    @GenericGenerator(name="bill_id_gen", strategy="edu.temple.cla.policydb.billshibernatedao.BillIdGen")
    @GeneratedValue(generator="bill_id_gen", strategy=SEQUENCE)
    @Column(name="ID")
    private String id;
    
    @Column(name="Chamber")
    private Short chamber;
     
    @Column(name="Session_")
    private String session_;
     
    @Column(name="Bill")
    private String bill;
     
    @Column(name="SubType")
    private String subtype;
     
    @Column(name="Hyperlink")
    private String hyperlink;
     
    @Column(name="Sponsor")
    private String sponsor;
     
    @Column(name="Sponsor_Party")
    private Short sponsorParty;
     
    @Column(name="Sponsor_County")
    private String sponsorCounty;
     
    
    @Column(name="Sponsor_Terms")
    private String sponsorTerms;
     
    @Column(name="Abstract")
    private String abstract_;
     
    @Column(name="Tax")
    private Boolean tax;
     
    @Column(name="Eld")
    private Boolean eld;
     
    @Column(name="Con_Ammend")
    private Short conAmmend;
     
    @Column(name="Act_No")
    private String actNo;
     
    @Column(name="Date_Enacted")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEnacted;

    @Column(name="Code")
    private Integer code;
      
    @Column(name="Commemorative") 
    private Boolean commemorative;
     
    @Column(name="Appropriations")
    private Boolean appropriations;

    @Column(name="_101P")
    private Boolean $101p;

    @Column(name="_101O")
    private Boolean $101o;

    @Column(name="_102P")
    private Boolean $102p;

    @Column(name="_102O")
    private Boolean $102o;

    @Column(name="_103P")
    private Boolean $103p;

    @Column(name="_103O")
    private Boolean $103o;

    @Column(name="_104P")
    private Boolean $104p;

    @Column(name="_104O")
    private Boolean $104o;

    @Column(name="_105P")
    private Boolean $105p;

    @Column(name="_105O")
    private Boolean $105o;

    @Column(name="_106P")
    private Boolean $106p;

    @Column(name="_106O")
    private Boolean $106o;

    @Column(name="_107P")
    private Boolean $107p;

    @Column(name="_107O")
    private Boolean $107o;

    @Column(name="_108P")
    private Boolean $108p;

    @Column(name="_108O")
    private Boolean $108o;

    @Column(name="_109P")
    private Boolean $109p;

    @Column(name="_109O")
    private Boolean $109o;

    @Column(name="_110P")
    private Boolean $110p;

    @Column(name="_110O")
    private Boolean $110o;

    @Column(name="_111P")
    private Boolean $111p;

    @Column(name="_111O")
    private Boolean $111o;

    @Column(name="_112P")
    private Boolean $112p;

    @Column(name="_112O")
    private Boolean $112o;

    @Column(name="_113P")
    private Boolean $113p;

    @Column(name="_113O")
    private Boolean $113o;

    @Column(name="_114P")
    private Boolean $114p;

    @Column(name="_114O")
    private Boolean $114o;

    @Column(name="_115P")
    private Boolean $115p;

    @Column(name="_115O")
    private Boolean $115o;

    @Column(name="_116P")
    private Boolean $116p;

    @Column(name="_116O")
    private Boolean $116o;

    @Column(name="_117P")
    private Boolean $117p;

    @Column(name="_117O")
    private Boolean $117o;

    @Column(name="_118P")
    private Boolean $118p;

    @Column(name="_118O")
    private Boolean $118o;

    @Column(name="_119P")
    private Boolean $119p;

    @Column(name="_119O")
    private Boolean $119o;

    @Column(name="_120P")
    private Boolean $120p;

    @Column(name="_120O")
    private Boolean $120o;

    @Column(name="_121P")
    private Boolean $121p;

    @Column(name="_121O")
    private Boolean $121o;

    @Column(name="_122P")
    private Boolean $122p;

    @Column(name="_122O")
    private Boolean $122o;

    @Column(name="_123P")
    private Boolean $123p;

    @Column(name="_123O")
    private Boolean $123o;

    @Column(name="_124P")
    private Boolean $124p;

    @Column(name="_124O")
    private Boolean $124o;

    @Column(name="_125P")
    private Boolean $125p;

    @Column(name="_125O")
    private Boolean $125o;

    @Column(name="_126P")
    private Boolean $126p;

    @Column(name="_126O")
    private Boolean $126o;

    @Column(name="_127P")
    private Boolean $127p;

    @Column(name="_127O")
    private Boolean $127o;

    @Column(name="_130P")
    private Boolean $130p;

    @Column(name="_130O")
    private Boolean $130o;

    @Column(name="_201P")
    private Boolean $201p;

    @Column(name="_201O")
    private Boolean $201o;

    @Column(name="_202P")
    private Boolean $202p;

    @Column(name="_202O")
    private Boolean $202o;

    @Column(name="_203P")
    private Boolean $203p;

    @Column(name="_203O")
    private Boolean $203o;

    @Column(name="_205P")
    private Boolean $205p;

    @Column(name="_205O")
    private Boolean $205o;

    @Column(name="_207P")
    private Boolean $207p;

    @Column(name="_207O")
    private Boolean $207o;

    @Column(name="_208P")
    private Boolean $208p;

    @Column(name="_208O")
    private Boolean $208o;

    @Column(name="_209P")
    private Boolean $209p;

    @Column(name="_209O")
    private Boolean $209o;

    @Column(name="_210P")
    private Boolean $210p;

    @Column(name="_210O")
    private Boolean $210o;

    @Column(name="_211P")
    private Boolean $211p;

    @Column(name="_211O")
    private Boolean $211o;

    @Column(name="_212P")
    private Boolean $212p;

    @Column(name="_212O")
    private Boolean $212o;

    @Column(name="_213P")
    private Boolean $213p;

    @Column(name="_213O")
    private Boolean $213o;

    @Column(name="_214P")
    private Boolean $214p;

    @Column(name="_214O")
    private Boolean $214o;

    @Column(name="_215P")
    private Boolean $215p;

    @Column(name="_215O")
    private Boolean $215o;

    @Column(name="_216P")
    private Boolean $216p;

    @Column(name="_216O")
    private Boolean $216o;

    @Column(name="_217P")
    private Boolean $217p;

    @Column(name="_217O")
    private Boolean $217o;

    @Column(name="_219P")
    private Boolean $219p;

    @Column(name="_219O")
    private Boolean $219o;

    @Column(name="_221P")
    private Boolean $221p;

    @Column(name="_221O")
    private Boolean $221o;

    @Column(name="_222P")
    private Boolean $222p;

    @Column(name="_222O")
    private Boolean $222o;

    @Column(name="_224P")
    private Boolean $224p;

    @Column(name="_224O")
    private Boolean $224o;

    @Column(name="_225P")
    private Boolean $225p;

    @Column(name="_225O")
    private Boolean $225o;

    @Column(name="_226P")
    private Boolean $226p;

    @Column(name="_226O")
    private Boolean $226o;

    @Column(name="_227P")
    private Boolean $227p;

    @Column(name="_227O")
    private Boolean $227o;

    @Column(name="_228P")
    private Boolean $228p;

    @Column(name="_228O")
    private Boolean $228o;

    @Column(name="_230P")
    private Boolean $230p;

    @Column(name="_230O")
    private Boolean $230o;

    @Column(name="_300")
    private Boolean $300;
     
    @Column(name="VetoIneligible") 
    private Boolean vetoIneligible;
     
    @Column(name="Vetoed")
    private Boolean vetoed;
     
    @Column(name="LineItemVeto")
    private Boolean lineItemVeto;
     
    @Column(name="VetoOverriden")
    private Boolean vetoOverriden;
     
    @Column(name="LawNoGovSig")
    private Boolean lawNoGovSig;
    
    @Column(name="RecalledByLeg") 
    private Boolean recalledByLeg;
     
    @Column(name="House_Last_Action")
    private Integer houseLastAction;
     
    @Column(name="Senate_Last_Action")
    private Integer senateLastAction;
     
    @Column(name="Petition")
    private Boolean petition;
     
    @Column(name="Date_Referred")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateReferred;
     
    @Column(name="SignedbyGov")
    private Boolean signedbyGov;

    public Bill() {
    }

	
    public Bill(String id) {
        this.id = id;
    }
    
    public Bill(String id, Short chamber, String session_, String bill, String subtype, String hyperlink, String sponsor, Short sponsorParty, String sponsorCounty, String sponsorTerms, String abstract_, Boolean tax, Boolean eld, Short conAmmend, String actNo, Date dateEnacted, Integer code, Boolean commemorative, Boolean appropriations, Boolean $101p, Boolean $101o, Boolean $102p, Boolean $102o, Boolean $103p, Boolean $103o, Boolean $104p, Boolean $104o, Boolean $105p, Boolean $105o, Boolean $106p, Boolean $106o, Boolean $107p, Boolean $107o, Boolean $108p, Boolean $108o, Boolean $109p, Boolean $109o, Boolean $110p, Boolean $110o, Boolean $111p, Boolean $111o, Boolean $112p, Boolean $112o, Boolean $113p, Boolean $113o, Boolean $114p, Boolean $114o, Boolean $115p, Boolean $115o, Boolean $116p, Boolean $116o, Boolean $117p, Boolean $117o, Boolean $118p, Boolean $118o, Boolean $119p, Boolean $119o, Boolean $120p, Boolean $120o, Boolean $121p, Boolean $121o, Boolean $122p, Boolean $122o, Boolean $123p, Boolean $123o, Boolean $124p, Boolean $124o, Boolean $125p, Boolean $125o, Boolean $126p, Boolean $126o, Boolean $127p, Boolean $127o, Boolean $130p, Boolean $130o, Boolean $201p, Boolean $201o, Boolean $202p, Boolean $202o, Boolean $203p, Boolean $203o, Boolean $205p, Boolean $205o, Boolean $207p, Boolean $207o, Boolean $208p, Boolean $208o, Boolean $209p, Boolean $209o, Boolean $210p, Boolean $210o, Boolean $211p, Boolean $211o, Boolean $212p, Boolean $212o, Boolean $213p, Boolean $213o, Boolean $214p, Boolean $214o, Boolean $215p, Boolean $215o, Boolean $216p, Boolean $216o, Boolean $217p, Boolean $217o, Boolean $219p, Boolean $219o, Boolean $221p, Boolean $221o, Boolean $222p, Boolean $222o, Boolean $224p, Boolean $224o, Boolean $225p, Boolean $225o, Boolean $226p, Boolean $226o, Boolean $227p, Boolean $227o, Boolean $228p, Boolean $228o, Boolean $230p, Boolean $230o, Boolean $300, Boolean vetoIneligible, Boolean vetoed, Boolean lineItemVeto, Boolean vetoOverriden, Boolean lawNoGovSig, Boolean recalledByLeg, Integer houseLastAction, Integer senateLastAction, Boolean petition, Date dateReferred, Boolean signedbyGov) {
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
    public Short getChamber() {
        return this.chamber;
    }
    
    public void setChamber(Short chamber) {
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
    public Short getSponsorParty() {
        return this.sponsorParty;
    }
    
    public void setSponsorParty(Short sponsorParty) {
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
    public Boolean getTax() {
        return this.tax;
    }
    
    public void setTax(Boolean tax) {
        this.tax = tax;
    }
    public Boolean getEld() {
        return this.eld;
    }
    
    public void setEld(Boolean eld) {
        this.eld = eld;
    }
    public Short getConAmmend() {
        return this.conAmmend;
    }
    
    public void setConAmmend(Short conAmmend) {
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

    public Integer getCode() {
        return this.code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    public Boolean getCommemorative() {
        return this.commemorative;
    }
    
    public void setCommemorative(Boolean commemorative) {
        this.commemorative = commemorative;
    }

    public Boolean getAppropriations() {
        return this.appropriations;
    }
    
    public void setAppropriations(Boolean appropriations){
        this.appropriations = appropriations;
    }
    
    public Boolean getVetoIneligible() {
        return this.vetoIneligible;
    }
    
    public void setVetoIneligible(Boolean vetoIneligible) {
        this.vetoIneligible = vetoIneligible;
    }
    public Boolean getVetoed() {
        return this.vetoed;
    }
    
    public void setVetoed(Boolean vetoed) {
        this.vetoed = vetoed;
    }
    public Boolean getLineItemVeto() {
        return this.lineItemVeto;
    }
    
    public void setLineItemVeto(Boolean lineItemVeto) {
        this.lineItemVeto = lineItemVeto;
    }
    public Boolean getVetoOverriden() {
        return this.vetoOverriden;
    }
    
    public void setVetoOverriden(Boolean vetoOverriden) {
        this.vetoOverriden = vetoOverriden;
    }
    public Boolean getLawNoGovSig() {
        return this.lawNoGovSig;
    }
    
    public void setLawNoGovSig(Boolean lawNoGovSig) {
        this.lawNoGovSig = lawNoGovSig;
    }
    public Boolean getRecalledByLeg() {
        return this.recalledByLeg;
    }
    
    public void setRecalledByLeg(Boolean recalledByLeg) {
        this.recalledByLeg = recalledByLeg;
    }
    public Integer getHouseLastAction() {
        return this.houseLastAction;
    }
    
    public void setHouseLastAction(Integer houseLastAction) {
        this.houseLastAction = houseLastAction;
    }
    public Integer getSenateLastAction() {
        return this.senateLastAction;
    }
    
    public void setSenateLastAction(Integer senateLastAction) {
        this.senateLastAction = senateLastAction;
    }

    public Boolean getPetition() {
        return this.petition;
    }
    
    public void setPetition(Boolean petition) {
        this.petition = petition;
    }

    public Date getDateReferred() {
        return this.dateReferred;
    }

    public void setDateReferred(Date dateReferred) {
        this.dateReferred = dateReferred;
    }

    public Boolean getSignedbyGov() {
        return this.signedbyGov;
    }
    
    public void setSignedbyGov(Boolean signedbyGov) {
        this.signedbyGov = signedbyGov;
    }

    /**
     * @return the $101p
     */
    public Boolean get$101p() {
        return $101p;
    }

    /**
     * @param $101p the $101p to set
     */
    public void set$101p(Boolean $101p) {
        this.$101p = $101p;
    }

    /**
     * @return the $101o
     */
    public Boolean get$101o() {
        return $101o;
    }

    /**
     * @param $101o the $101o to set
     */
    public void set$101o(Boolean $101o) {
        this.$101o = $101o;
    }

    /**
     * @return the $102p
     */
    public Boolean get$102p() {
        return $102p;
    }

    /**
     * @param $102p the $102p to set
     */
    public void set$102p(Boolean $102p) {
        this.$102p = $102p;
    }

    /**
     * @return the $102o
     */
    public Boolean get$102o() {
        return $102o;
    }

    /**
     * @param $102o the $102o to set
     */
    public void set$102o(Boolean $102o) {
        this.$102o = $102o;
    }

    /**
     * @return the $103p
     */
    public Boolean get$103p() {
        return $103p;
    }

    /**
     * @param $103p the $103p to set
     */
    public void set$103p(Boolean $103p) {
        this.$103p = $103p;
    }

    /**
     * @return the $103o
     */
    public Boolean get$103o() {
        return $103o;
    }

    /**
     * @param $103o the $103o to set
     */
    public void set$103o(Boolean $103o) {
        this.$103o = $103o;
    }

    /**
     * @return the $104p
     */
    public Boolean get$104p() {
        return $104p;
    }

    /**
     * @param $104p the $104p to set
     */
    public void set$104p(Boolean $104p) {
        this.$104p = $104p;
    }

    /**
     * @return the $104o
     */
    public Boolean get$104o() {
        return $104o;
    }

    /**
     * @param $104o the $104o to set
     */
    public void set$104o(Boolean $104o) {
        this.$104o = $104o;
    }

    /**
     * @return the $105p
     */
    public Boolean get$105p() {
        return $105p;
    }

    /**
     * @param $105p the $105p to set
     */
    public void set$105p(Boolean $105p) {
        this.$105p = $105p;
    }

    /**
     * @return the $105o
     */
    public Boolean get$105o() {
        return $105o;
    }

    /**
     * @param $105o the $105o to set
     */
    public void set$105o(Boolean $105o) {
        this.$105o = $105o;
    }

    /**
     * @return the $106p
     */
    public Boolean get$106p() {
        return $106p;
    }

    /**
     * @param $106p the $106p to set
     */
    public void set$106p(Boolean $106p) {
        this.$106p = $106p;
    }

    /**
     * @return the $106o
     */
    public Boolean get$106o() {
        return $106o;
    }

    /**
     * @param $106o the $106o to set
     */
    public void set$106o(Boolean $106o) {
        this.$106o = $106o;
    }

    /**
     * @return the $107p
     */
    public Boolean get$107p() {
        return $107p;
    }

    /**
     * @param $107p the $107p to set
     */
    public void set$107p(Boolean $107p) {
        this.$107p = $107p;
    }

    /**
     * @return the $107o
     */
    public Boolean get$107o() {
        return $107o;
    }

    /**
     * @param $107o the $107o to set
     */
    public void set$107o(Boolean $107o) {
        this.$107o = $107o;
    }

    /**
     * @return the $108p
     */
    public Boolean get$108p() {
        return $108p;
    }

    /**
     * @param $108p the $108p to set
     */
    public void set$108p(Boolean $108p) {
        this.$108p = $108p;
    }

    /**
     * @return the $108o
     */
    public Boolean get$108o() {
        return $108o;
    }

    /**
     * @param $108o the $108o to set
     */
    public void set$108o(Boolean $108o) {
        this.$108o = $108o;
    }

    /**
     * @return the $109p
     */
    public Boolean get$109p() {
        return $109p;
    }

    /**
     * @param $109p the $109p to set
     */
    public void set$109p(Boolean $109p) {
        this.$109p = $109p;
    }

    /**
     * @return the $109o
     */
    public Boolean get$109o() {
        return $109o;
    }

    /**
     * @param $109o the $109o to set
     */
    public void set$109o(Boolean $109o) {
        this.$109o = $109o;
    }

    /**
     * @return the $110p
     */
    public Boolean get$110p() {
        return $110p;
    }

    /**
     * @param $110p the $110p to set
     */
    public void set$110p(Boolean $110p) {
        this.$110p = $110p;
    }

    /**
     * @return the $110o
     */
    public Boolean get$110o() {
        return $110o;
    }

    /**
     * @param $110o the $110o to set
     */
    public void set$110o(Boolean $110o) {
        this.$110o = $110o;
    }

    /**
     * @return the $111p
     */
    public Boolean get$111p() {
        return $111p;
    }

    /**
     * @param $111p the $111p to set
     */
    public void set$111p(Boolean $111p) {
        this.$111p = $111p;
    }

    /**
     * @return the $111o
     */
    public Boolean get$111o() {
        return $111o;
    }

    /**
     * @param $111o the $111o to set
     */
    public void set$111o(Boolean $111o) {
        this.$111o = $111o;
    }

    /**
     * @return the $112p
     */
    public Boolean get$112p() {
        return $112p;
    }

    /**
     * @param $112p the $112p to set
     */
    public void set$112p(Boolean $112p) {
        this.$112p = $112p;
    }

    /**
     * @return the $112o
     */
    public Boolean get$112o() {
        return $112o;
    }

    /**
     * @param $112o the $112o to set
     */
    public void set$112o(Boolean $112o) {
        this.$112o = $112o;
    }

    /**
     * @return the $113p
     */
    public Boolean get$113p() {
        return $113p;
    }

    /**
     * @param $113p the $113p to set
     */
    public void set$113p(Boolean $113p) {
        this.$113p = $113p;
    }

    /**
     * @return the $113o
     */
    public Boolean get$113o() {
        return $113o;
    }

    /**
     * @param $113o the $113o to set
     */
    public void set$113o(Boolean $113o) {
        this.$113o = $113o;
    }

    /**
     * @return the $114p
     */
    public Boolean get$114p() {
        return $114p;
    }

    /**
     * @param $114p the $114p to set
     */
    public void set$114p(Boolean $114p) {
        this.$114p = $114p;
    }

    /**
     * @return the $114o
     */
    public Boolean get$114o() {
        return $114o;
    }

    /**
     * @param $114o the $114o to set
     */
    public void set$114o(Boolean $114o) {
        this.$114o = $114o;
    }

    /**
     * @return the $115p
     */
    public Boolean get$115p() {
        return $115p;
    }

    /**
     * @param $115p the $115p to set
     */
    public void set$115p(Boolean $115p) {
        this.$115p = $115p;
    }

    /**
     * @return the $115o
     */
    public Boolean get$115o() {
        return $115o;
    }

    /**
     * @param $115o the $115o to set
     */
    public void set$115o(Boolean $115o) {
        this.$115o = $115o;
    }

    /**
     * @return the $116p
     */
    public Boolean get$116p() {
        return $116p;
    }

    /**
     * @param $116p the $116p to set
     */
    public void set$116p(Boolean $116p) {
        this.$116p = $116p;
    }

    /**
     * @return the $116o
     */
    public Boolean get$116o() {
        return $116o;
    }

    /**
     * @param $116o the $116o to set
     */
    public void set$116o(Boolean $116o) {
        this.$116o = $116o;
    }

    /**
     * @return the $117p
     */
    public Boolean get$117p() {
        return $117p;
    }

    /**
     * @param $117p the $117p to set
     */
    public void set$117p(Boolean $117p) {
        this.$117p = $117p;
    }

    /**
     * @return the $117o
     */
    public Boolean get$117o() {
        return $117o;
    }

    /**
     * @param $117o the $117o to set
     */
    public void set$117o(Boolean $117o) {
        this.$117o = $117o;
    }

    /**
     * @return the $118p
     */
    public Boolean get$118p() {
        return $118p;
    }

    /**
     * @param $118p the $118p to set
     */
    public void set$118p(Boolean $118p) {
        this.$118p = $118p;
    }

    /**
     * @return the $118o
     */
    public Boolean get$118o() {
        return $118o;
    }

    /**
     * @param $118o the $118o to set
     */
    public void set$118o(Boolean $118o) {
        this.$118o = $118o;
    }

    /**
     * @return the $119p
     */
    public Boolean get$119p() {
        return $119p;
    }

    /**
     * @param $119p the $119p to set
     */
    public void set$119p(Boolean $119p) {
        this.$119p = $119p;
    }

    /**
     * @return the $119o
     */
    public Boolean get$119o() {
        return $119o;
    }

    /**
     * @param $119o the $119o to set
     */
    public void set$119o(Boolean $119o) {
        this.$119o = $119o;
    }

    /**
     * @return the $120p
     */
    public Boolean get$120p() {
        return $120p;
    }

    /**
     * @param $120p the $120p to set
     */
    public void set$120p(Boolean $120p) {
        this.$120p = $120p;
    }

    /**
     * @return the $120o
     */
    public Boolean get$120o() {
        return $120o;
    }

    /**
     * @param $120o the $120o to set
     */
    public void set$120o(Boolean $120o) {
        this.$120o = $120o;
    }

    /**
     * @return the $121p
     */
    public Boolean get$121p() {
        return $121p;
    }

    /**
     * @param $121p the $121p to set
     */
    public void set$121p(Boolean $121p) {
        this.$121p = $121p;
    }

    /**
     * @return the $121o
     */
    public Boolean get$121o() {
        return $121o;
    }

    /**
     * @param $121o the $121o to set
     */
    public void set$121o(Boolean $121o) {
        this.$121o = $121o;
    }

    /**
     * @return the $122p
     */
    public Boolean get$122p() {
        return $122p;
    }

    /**
     * @param $122p the $122p to set
     */
    public void set$122p(Boolean $122p) {
        this.$122p = $122p;
    }

    /**
     * @return the $122o
     */
    public Boolean get$122o() {
        return $122o;
    }

    /**
     * @param $122o the $122o to set
     */
    public void set$122o(Boolean $122o) {
        this.$122o = $122o;
    }

    /**
     * @return the $123p
     */
    public Boolean get$123p() {
        return $123p;
    }

    /**
     * @param $123p the $123p to set
     */
    public void set$123p(Boolean $123p) {
        this.$123p = $123p;
    }

    /**
     * @return the $123o
     */
    public Boolean get$123o() {
        return $123o;
    }

    /**
     * @param $123o the $123o to set
     */
    public void set$123o(Boolean $123o) {
        this.$123o = $123o;
    }

    /**
     * @return the $124p
     */
    public Boolean get$124p() {
        return $124p;
    }

    /**
     * @param $124p the $124p to set
     */
    public void set$124p(Boolean $124p) {
        this.$124p = $124p;
    }

    /**
     * @return the $124o
     */
    public Boolean get$124o() {
        return $124o;
    }

    /**
     * @param $124o the $124o to set
     */
    public void set$124o(Boolean $124o) {
        this.$124o = $124o;
    }

    /**
     * @return the $125p
     */
    public Boolean get$125p() {
        return $125p;
    }

    /**
     * @param $125p the $125p to set
     */
    public void set$125p(Boolean $125p) {
        this.$125p = $125p;
    }

    /**
     * @return the $125o
     */
    public Boolean get$125o() {
        return $125o;
    }

    /**
     * @param $125o the $125o to set
     */
    public void set$125o(Boolean $125o) {
        this.$125o = $125o;
    }

    /**
     * @return the $126p
     */
    public Boolean get$126p() {
        return $126p;
    }

    /**
     * @param $126p the $126p to set
     */
    public void set$126p(Boolean $126p) {
        this.$126p = $126p;
    }

    /**
     * @return the $126o
     */
    public Boolean get$126o() {
        return $126o;
    }

    /**
     * @param $126o the $126o to set
     */
    public void set$126o(Boolean $126o) {
        this.$126o = $126o;
    }

    /**
     * @return the $201p
     */
    public Boolean get$201p() {
        return $201p;
    }

    /**
     * @param $201p the $201p to set
     */
    public void set$201p(Boolean $201p) {
        this.$201p = $201p;
    }

    /**
     * @return the $201o
     */
    public Boolean get$201o() {
        return $201o;
    }

    /**
     * @param $201o the $201o to set
     */
    public void set$201o(Boolean $201o) {
        this.$201o = $201o;
    }

    /**
     * @return the $202p
     */
    public Boolean get$202p() {
        return $202p;
    }

    /**
     * @param $202p the $202p to set
     */
    public void set$202p(Boolean $202p) {
        this.$202p = $202p;
    }

    /**
     * @return the $202o
     */
    public Boolean get$202o() {
        return $202o;
    }

    /**
     * @param $202o the $202o to set
     */
    public void set$202o(Boolean $202o) {
        this.$202o = $202o;
    }

    /**
     * @return the $203p
     */
    public Boolean get$203p() {
        return $203p;
    }

    /**
     * @param $203p the $203p to set
     */
    public void set$203p(Boolean $203p) {
        this.$203p = $203p;
    }

    /**
     * @return the $203o
     */
    public Boolean get$203o() {
        return $203o;
    }

    /**
     * @param $203o the $203o to set
     */
    public void set$203o(Boolean $203o) {
        this.$203o = $203o;
    }

    /**
     * @return the $205p
     */
    public Boolean get$205p() {
        return $205p;
    }

    /**
     * @param $205p the $205p to set
     */
    public void set$205p(Boolean $205p) {
        this.$205p = $205p;
    }

    /**
     * @return the $205o
     */
    public Boolean get$205o() {
        return $205o;
    }

    /**
     * @param $205o the $205o to set
     */
    public void set$205o(Boolean $205o) {
        this.$205o = $205o;
    }

    /**
     * @return the $207p
     */
    public Boolean get$207p() {
        return $207p;
    }

    /**
     * @param $207p the $207p to set
     */
    public void set$207p(Boolean $207p) {
        this.$207p = $207p;
    }

    /**
     * @return the $207o
     */
    public Boolean get$207o() {
        return $207o;
    }

    /**
     * @param $207o the $207o to set
     */
    public void set$207o(Boolean $207o) {
        this.$207o = $207o;
    }

    /**
     * @return the $208p
     */
    public Boolean get$208p() {
        return $208p;
    }

    /**
     * @param $208p the $208p to set
     */
    public void set$208p(Boolean $208p) {
        this.$208p = $208p;
    }

    /**
     * @return the $208o
     */
    public Boolean get$208o() {
        return $208o;
    }

    /**
     * @param $208o the $208o to set
     */
    public void set$208o(Boolean $208o) {
        this.$208o = $208o;
    }

    /**
     * @return the $209p
     */
    public Boolean get$209p() {
        return $209p;
    }

    /**
     * @param $209p the $209p to set
     */
    public void set$209p(Boolean $209p) {
        this.$209p = $209p;
    }

    /**
     * @return the $209o
     */
    public Boolean get$209o() {
        return $209o;
    }

    /**
     * @param $209o the $209o to set
     */
    public void set$209o(Boolean $209o) {
        this.$209o = $209o;
    }

    /**
     * @return the $210p
     */
    public Boolean get$210p() {
        return $210p;
    }

    /**
     * @param $210p the $210p to set
     */
    public void set$210p(Boolean $210p) {
        this.$210p = $210p;
    }

    /**
     * @return the $210o
     */
    public Boolean get$210o() {
        return $210o;
    }

    /**
     * @param $210o the $210o to set
     */
    public void set$210o(Boolean $210o) {
        this.$210o = $210o;
    }

    /**
     * @return the $211p
     */
    public Boolean get$211p() {
        return $211p;
    }

    /**
     * @param $211p the $211p to set
     */
    public void set$211p(Boolean $211p) {
        this.$211p = $211p;
    }

    /**
     * @return the $211o
     */
    public Boolean get$211o() {
        return $211o;
    }

    /**
     * @param $211o the $211o to set
     */
    public void set$211o(Boolean $211o) {
        this.$211o = $211o;
    }

    /**
     * @return the $212p
     */
    public Boolean get$212p() {
        return $212p;
    }

    /**
     * @param $212p the $212p to set
     */
    public void set$212p(Boolean $212p) {
        this.$212p = $212p;
    }

    /**
     * @return the $212o
     */
    public Boolean get$212o() {
        return $212o;
    }

    /**
     * @param $212o the $212o to set
     */
    public void set$212o(Boolean $212o) {
        this.$212o = $212o;
    }

    /**
     * @return the $213p
     */
    public Boolean get$213p() {
        return $213p;
    }

    /**
     * @param $213p the $213p to set
     */
    public void set$213p(Boolean $213p) {
        this.$213p = $213p;
    }

    /**
     * @return the $213o
     */
    public Boolean get$213o() {
        return $213o;
    }

    /**
     * @param $213o the $213o to set
     */
    public void set$213o(Boolean $213o) {
        this.$213o = $213o;
    }

    /**
     * @return the $214p
     */
    public Boolean get$214p() {
        return $214p;
    }

    /**
     * @param $214p the $214p to set
     */
    public void set$214p(Boolean $214p) {
        this.$214p = $214p;
    }

    /**
     * @return the $214o
     */
    public Boolean get$214o() {
        return $214o;
    }

    /**
     * @param $214o the $214o to set
     */
    public void set$214o(Boolean $214o) {
        this.$214o = $214o;
    }

    /**
     * @return the $215p
     */
    public Boolean get$215p() {
        return $215p;
    }

    /**
     * @param $215p the $215p to set
     */
    public void set$215p(Boolean $215p) {
        this.$215p = $215p;
    }

    /**
     * @return the $215o
     */
    public Boolean get$215o() {
        return $215o;
    }

    /**
     * @param $215o the $215o to set
     */
    public void set$215o(Boolean $215o) {
        this.$215o = $215o;
    }

    /**
     * @return the $216p
     */
    public Boolean get$216p() {
        return $216p;
    }

    /**
     * @param $216p the $216p to set
     */
    public void set$216p(Boolean $216p) {
        this.$216p = $216p;
    }

    /**
     * @return the $216o
     */
    public Boolean get$216o() {
        return $216o;
    }

    /**
     * @param $216o the $216o to set
     */
    public void set$216o(Boolean $216o) {
        this.$216o = $216o;
    }

    /**
     * @return the $217p
     */
    public Boolean get$217p() {
        return $217p;
    }

    /**
     * @param $217p the $217p to set
     */
    public void set$217p(Boolean $217p) {
        this.$217p = $217p;
    }

    /**
     * @return the $217o
     */
    public Boolean get$217o() {
        return $217o;
    }

    /**
     * @param $217o the $217o to set
     */
    public void set$217o(Boolean $217o) {
        this.$217o = $217o;
    }

    /**
     * @return the $219p
     */
    public Boolean get$219p() {
        return $219p;
    }

    /**
     * @param $219p the $219p to set
     */
    public void set$219p(Boolean $219p) {
        this.$219p = $219p;
    }

    /**
     * @return the $219o
     */
    public Boolean get$219o() {
        return $219o;
    }

    /**
     * @param $219o the $219o to set
     */
    public void set$219o(Boolean $219o) {
        this.$219o = $219o;
    }

    /**
     * @return the $221p
     */
    public Boolean get$221p() {
        return $221p;
    }

    /**
     * @param $221p the $221p to set
     */
    public void set$221p(Boolean $221p) {
        this.$221p = $221p;
    }

    /**
     * @return the $221o
     */
    public Boolean get$221o() {
        return $221o;
    }

    /**
     * @param $221o the $221o to set
     */
    public void set$221o(Boolean $221o) {
        this.$221o = $221o;
    }

    /**
     * @return the $222p
     */
    public Boolean get$222p() {
        return $222p;
    }

    /**
     * @param $222p the $222p to set
     */
    public void set$222p(Boolean $222p) {
        this.$222p = $222p;
    }

    /**
     * @return the $222o
     */
    public Boolean get$222o() {
        return $222o;
    }

    /**
     * @param $222o the $222o to set
     */
    public void set$222o(Boolean $222o) {
        this.$222o = $222o;
    }

    /**
     * @return the $224p
     */
    public Boolean get$224p() {
        return $224p;
    }

    /**
     * @param $224p the $224p to set
     */
    public void set$224p(Boolean $224p) {
        this.$224p = $224p;
    }

    /**
     * @return the $224o
     */
    public Boolean get$224o() {
        return $224o;
    }

    /**
     * @param $224o the $224o to set
     */
    public void set$224o(Boolean $224o) {
        this.$224o = $224o;
    }

    /**
     * @return the $225p
     */
    public Boolean get$225p() {
        return $225p;
    }

    /**
     * @param $225p the $225p to set
     */
    public void set$225p(Boolean $225p) {
        this.$225p = $225p;
    }

    /**
     * @return the $225o
     */
    public Boolean get$225o() {
        return $225o;
    }

    /**
     * @param $225o the $225o to set
     */
    public void set$225o(Boolean $225o) {
        this.$225o = $225o;
    }

    /**
     * @return the $226p
     */
    public Boolean get$226p() {
        return $226p;
    }

    /**
     * @param $226p the $226p to set
     */
    public void set$226p(Boolean $226p) {
        this.$226p = $226p;
    }

    /**
     * @return the $226o
     */
    public Boolean get$226o() {
        return $226o;
    }

    /**
     * @param $226o the $226o to set
     */
    public void set$226o(Boolean $226o) {
        this.$226o = $226o;
    }

    /**
     * @return the $227p
     */
    public Boolean get$227p() {
        return $227p;
    }

    /**
     * @param $227p the $227p to set
     */
    public void set$227p(Boolean $227p) {
        this.$227p = $227p;
    }

    /**
     * @return the $227o
     */
    public Boolean get$227o() {
        return $227o;
    }

    /**
     * @param $227o the $227o to set
     */
    public void set$227o(Boolean $227o) {
        this.$227o = $227o;
    }

    /**
     * @return the $228p
     */
    public Boolean get$228p() {
        return $228p;
    }

    /**
     * @param $228p the $228p to set
     */
    public void set$228p(Boolean $228p) {
        this.$228p = $228p;
    }

    /**
     * @return the $228o
     */
    public Boolean get$228o() {
        return $228o;
    }

    /**
     * @param $228o the $228o to set
     */
    public void set$228o(Boolean $228o) {
        this.$228o = $228o;
    }

    /**
     * @return the $230p
     */
    public Boolean get$230p() {
        return $230p;
    }

    /**
     * @param $230p the $230p to set
     */
    public void set$230p(Boolean $230p) {
        this.$230p = $230p;
    }

    /**
     * @return the $230o
     */
    public Boolean get$230o() {
        return $230o;
    }

    /**
     * @param $230o the $230o to set
     */
    public void set$230o(Boolean $230o) {
        this.$230o = $230o;
    }

    /**
     * @return the $300
     */
    public Boolean get$300() {
        return $300;
    }

    /**
     * @param $300 the $300 to set
     */
    public void set$300(Boolean $300) {
        this.$300 = $300;
    }

    /**
     * @return the $127p
     */
    public Boolean get$127p() {
        return $127p;
    }

    /**
     * @param $127p the $127p to set
     */
    public void set$127p(Boolean $127p) {
        this.$127p = $127p;
    }

    /**
     * @return the $127o
     */
    public Boolean get$127o() {
        return $127o;
    }

    /**
     * @param $127o the $127o to set
     */
    public void set$127o(Boolean $127o) {
        this.$127o = $127o;
    }

    /**
     * @return the $130p
     */
    public Boolean get$130p() {
        return $130p;
    }

    /**
     * @param $130p the $130p to set
     */
    public void set$130p(Boolean $130p) {
        this.$130p = $130p;
    }

    /**
     * @return the $130o
     */
    public Boolean get$130o() {
        return $130o;
    }

    /**
     * @param $130o the $130o to set
     */
    public void set$130o(Boolean $130o) {
        this.$130o = $130o;
    }
    
}


