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
