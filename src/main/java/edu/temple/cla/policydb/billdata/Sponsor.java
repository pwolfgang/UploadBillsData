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

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class to represent a Sponsor
 * @author Paul Wolfgang
 */
public class Sponsor {

    private String name;
    private String party;
    private String body;
    private String districtNumber;
    private String sequenceNumber;
    private String fillSequence;

    /** Private constructor to be used by factory method */
    private Sponsor() {}

    /**
     * Construct a Sponsor with a name field only
     * @param name The name of the Sponsor
     */
    public Sponsor(String name) {
        this.name = name;
    }

    /**
     * Construct a Sponsor with all data fields
     * @param sequenceNumber
     * @param fillSequence
     * @param party
     * @param body
     * @param districtNumber
     * @param name
     */
     public Sponsor(String sequenceNumber, String fillSequence, String party,
            String body, String districtNumber, String name) {
        this.sequenceNumber = sequenceNumber;
        this.fillSequence = fillSequence;
        this.party = party;
        this. body = body;
        this.districtNumber = districtNumber;
        this.name = name;
    }

    /**
     * Convert an XML representation of Sponsor into a Sponsor object
     * This works with either the original XML representation or with
     * the XML representation created by the legislature data processing
     * organization.
     * @param node The sponsor element node
     * @return The corresponding Sponsor object
     */
    public static Sponsor decodeSponsor(Node node) {
        Element element = (Element) node;
        Attr sequenceNumberAttr = element.getAttributeNode("sequenceNumber");
        String sequenceNumber = null;
        if (sequenceNumberAttr != null) {
            sequenceNumber = sequenceNumberAttr.getValue();
        }
        String fillSequence = null;
        Attr fillSequenceAttr = element.getAttributeNode("fillSequence");
        if (fillSequenceAttr != null) {
            fillSequence = fillSequenceAttr.getValue();
        }
        Attr partyAttr = element.getAttributeNode("party");
        String party = null;
        if (partyAttr != null)
            party = partyAttr.getValue();
        Attr bodyAttr = element.getAttributeNode("body");
        String body = null;
        if (bodyAttr != null)
            body = bodyAttr.getValue();
        String districtNumber = null;
        Attr districtAttr = element.getAttributeNode("distictNumber");
        if (districtAttr != null)
            districtNumber = districtAttr.getValue();
        String name = node.getTextContent().trim();
        return new Sponsor(sequenceNumber, fillSequence, party, body, districtNumber, name);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the party
     */
    public String getParty() {
        return party;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @return the districtNumber
     */
    public String getDistrictNumber() {
        return districtNumber;
    }

    /**
     * Return a string representation
     * @return The sponsor name, body, party, and district number
     */
    public String toString() {
        return String.format("%s %s %s %s", name, body, party, districtNumber);
    }
    
    /**
     * @return the sequenceNumber
     */
    public String getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * @return the fillSequence
     */
    public String getFillSequence() {
        return fillSequence;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass() == this.getClass()) {
            return toString().equals(o.toString());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
