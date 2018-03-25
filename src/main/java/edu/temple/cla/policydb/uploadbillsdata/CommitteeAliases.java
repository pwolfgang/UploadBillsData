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
package edu.temple.cla.policydb.uploadbillsdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CommitteeAliases")
@SuppressWarnings("serial")
public class CommitteeAliases  implements java.io.Serializable {


    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=IDENTITY)
    private short id;
    
    @Column(name="CtyCode")
    private Short ctyCode;
    
    @Column(name="AlternateName")
    private String alternateName;
    
    @Column(name="StartYear")
    private Short startYear;
    
    @Column(name="EndYear")
    private Short endYear;
    
    @Column(name="Chamber")
    private Short chamber;
     
    @Column(name="Name")
    private String name;

    public CommitteeAliases() {
    }

	
    public CommitteeAliases(short id) {
        this.id = id;
    }
    public CommitteeAliases(short id, Short ctyCode, String alternateName, Short startYear, Short endYear, Short chamber, String name) {
       this.id = id;
       this.ctyCode = ctyCode;
       this.alternateName = alternateName;
       this.startYear = startYear;
       this.endYear = endYear;
       this.chamber = chamber;
       this.name = name;
    }
   
    public short getId() {
        return this.id;
    }
    
    public void setId(short id) {
        this.id = id;
    }
    public Short getCtyCode() {
        return this.ctyCode;
    }
    
    public void setCtyCode(Short ctyCode) {
        this.ctyCode = ctyCode;
    }
    public String getAlternateName() {
        return this.alternateName;
    }
    
    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }
    public Short getStartYear() {
        return this.startYear;
    }
    
    public void setStartYear(Short startYear) {
        this.startYear = startYear;
    }
    public Short getEndYear() {
        return this.endYear;
    }
    
    public void setEndYear(Short endYear) {
        this.endYear = endYear;
    }
    public Short getChamber() {
        return this.chamber;
    }
    
    public void setChamber(Short chamber) {
        this.chamber = chamber;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

}


