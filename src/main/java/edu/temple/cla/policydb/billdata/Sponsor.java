// Class to represent a Sponsor
//
// (C) 2010 Paul Wolfgang
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the Common Public License; either
// version 1.0 of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// Common Public License for more details.
//
// You should have received a copy of the Common Public License
// along with this program; if not, the license is available from
// the Open Source Initiative (OSI) website:
//   http://opensource.org/licenses/cpl1.0.txt


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
