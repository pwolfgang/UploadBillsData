// Class to contain utility methods
//
// (C) 2009 Paul Wolfgang
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

import java.util.HashMap;
import java.util.Map;

/**
 * Class to contain static utility methods
 * @author Paul Wolfgang
 */
public class Util {

    /** Map between Characters and their XML representation */
    private static final Map<Character, String> xmlCharMap = new HashMap<Character, String>();
    static {
        String[][] xmlTransTable = new String[][] {
            {"<", "&lt;"},
            {">", "&gt;"},
            {"&", "&amp;"},
            {"\"", "&quot;"},
            {"'", "&apos;"}};
        for (String[] entry : xmlTransTable) {
            xmlCharMap.put(entry[0].charAt(0), entry[1]);
        }
    }

    /**
     * Method to convert string content to XML format.
     * @param source The source string
     * @return The converted string
     */
    public static String convertToXML(String source) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            String replacement = xmlCharMap.get(c);
            if (replacement != null) {
                result.append(replacement);
            } else if (c > 127) {
                replacement = String.format("&#%d;", (int) c);
                result.append(replacement);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

}
