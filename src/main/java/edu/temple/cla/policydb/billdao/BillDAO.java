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

import edu.temple.cla.policydb.dbutilities.ColumnMetaData;
import edu.temple.cla.policydb.dbutilities.DBUtil;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import org.apache.log4j.Logger;

/**
 *
 * @author Paul Wolfgang
 */
public class BillDAO {

    private static final Logger LOGGER = Logger.getLogger(BillDAO.class);

    private final StringJoiner valuesList;
    private List<ColumnMetaData> metadataList = null;
    private final Connection conn;
    private final String tableName;

    public BillDAO(Connection conn, String tableName) {
        this.conn = conn;
        this.tableName = tableName;
        valuesList = new StringJoiner(",\n");
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getColumns(null, "PAPolicy", tableName, null)) {
                metadataList = ColumnMetaData.getColumnMetaDataList(rs);
            }
        } catch (Exception ex) {
            LOGGER.error("Error loading metadata", ex);
            throw new RuntimeException("Error loading metadata", ex);
        }
    }

    public void updateDatabase() {
        String insert = DBUtil.buildSqlInsertStatement(tableName, metadataList);
        String query = insert + "\n" + valuesList.toString();
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (Exception ex) {
            LOGGER.error("Error updating database " + query, ex);
        }
        
    }

    public void addToValuesList(Bill bill) {
        Map<String, Object> billFieldMap = bill.buildFieldMap();
        valuesList.add(DBUtil.buildValuesList(billFieldMap, metadataList));
    }

}
