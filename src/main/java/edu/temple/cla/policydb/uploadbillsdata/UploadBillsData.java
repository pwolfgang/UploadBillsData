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

import edu.temple.cla.policydb.dbutilities.SimpleDataSource;
import edu.temple.cla.policydb.zipentrystream.ZipEntryInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 * This program uploads the Bills Data from the XML files provided by the PA
 * Legislative Data Processing Department into the PAPolicy database
 * Bills_Table.
 *
 * @author Paul Wolfgang
 */
public class UploadBillsData {

    private static final Logger LOGGER = Logger.getLogger(UploadBillsData.class);
    private static ProcessSessionData processSessionData;
    private static final Set<String> UNKNOWN_COMMITTEES = new TreeSet<>();

    /**
     * This program uploads the Bills Data from the XML files provided by the PA
     * Legislative Data Processing Department into the PAPolicy database
     * Bills_Table.
     *
     * @param args
     * <dl>
     * <dt>args[0]</dt>
     * <dd>File or directory containing the XML data.</dd>
     * <dt>args[1]</dt>
     * <dd>File containing the datasource properties.</dd< <dt>args[2]</dt>
     * <dd>Name of the table to load the bills data into. This table must have
     * the same structure as the Bills_Data table. If omitted, Bills_Data is
     * used.</dd>
     * </dl>
     */
    public static void main(String[] args) {
        String tableName;
        if (args.length < 2) {
            System.err.println("Usage: UploadBillsData <directory><datasource><table>");
            System.exit(1);
        }
        if (args[0] == null) {
            System.err.println("args[0] must be non null");
            System.exit(1);
        }
        if (args[1] == null) {
            System.err.println("args[1] must be non null");
        }
        if (args[2] != null) {
            tableName = args[2];
        } else {
            tableName = "Bills_Data";
        }
        DataSource dataSource = new SimpleDataSource(args[1]);
        try (Connection conn = dataSource.getConnection();) {
            Statement stmt = conn.createStatement();
            processSessionData = new ProcessSessionData(stmt, tableName);
            File directory = new File(args[0]);
            processDirectory(directory);
            System.err.println("Done processing directory");
            PrintWriter pw3 = null;
            try {
                pw3 = new PrintWriter("UnknownCommitees.txt");
                for (String committee : UNKNOWN_COMMITTEES) {
                    pw3.println(committee);
                }
            } catch (FileNotFoundException ioex) {
                System.err.println("Error creating UnknownCommittees.txt");
            } finally {
                if (pw3 != null) {
                    pw3.flush();
                    pw3.close();
                }
            }
            System.err.println("Exiting main");
        } catch (Exception ex) {
            LOGGER.error("Error uploading bills data", ex);
        }
    }

    /**
     * Method to recursively scan directories. If a file is found, it is passed
     * to processFile, otherwise the directory is recursively scanned.
     *
     * @param file The directory to scan
     * @throws Exception
     */
    private static void processDirectory(File file) {
        try {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                for (File child : children) {
                    processDirectory(child);
                }
            } else if (ZipEntryInputStream.isZipFile(file)) {
                try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {
                    ZipEntry entry;
                    while ((entry = zipInputStream.getNextEntry()) != null) {
                        System.err.println("Processing " + entry);
                        Set<String> unknownCommittees
                                = processSessionData.processFile(new ZipEntryInputStream(zipInputStream));
                        UNKNOWN_COMMITTEES.addAll(unknownCommittees);
                        System.err.println("Done " + entry);
                    }
                }
            } else {
                Set<String> unknownCommittees
                        = processSessionData.processFile(new FileInputStream(file));
                UNKNOWN_COMMITTEES.addAll(unknownCommittees);
            }
        } catch (Exception ex) {
            LOGGER.error("Error processing directory " + file.getAbsolutePath(), ex);
            System.exit(1);
        }
    }
}
