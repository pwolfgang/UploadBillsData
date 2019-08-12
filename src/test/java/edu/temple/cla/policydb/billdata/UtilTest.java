/*
 * Copyright (c) 2019, Temple University
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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Paul
 */
public class UtilTest {
    
    public UtilTest() {
    }

    /**
     * Test of convertToXML method, of class Util.
     */
    @Test
    public void testConvertToXML() {
        String source = "<text id=\"foo\">Can't do it & I do not want to</text>";
        String expected = "&lt;text id=&quot;foo&quot;&gt;Can&apos;t do it &amp; I&#160;do not want to&lt;/text&gt;";
        assertEquals(expected, Util.convertToXML(source));
    }

    /**
     * Test of convertFromXML method, of class Util.
     */
    @Test
    public void testConvertFromXML() {
        String expected = "<text id=\"foo\">Can't do it & I do not want to</text>";
        String source = "&lt;text id=&quot;foo&quot;&gt;Can&apos;t do it &amp; I&#160;do not want to&lt;/text&gt;";
        assertEquals(expected, Util.convertFromXML(source));
    }
    
}
