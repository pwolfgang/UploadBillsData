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

import edu.temple.cla.policydb.uploadbillsdata.UploadBillsData;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 * This class is a Hibernate Data Access Object for a Bill.
 *
 * @author Paul Wolfgang
 */
public class BillDAO {

    /**
     * LOGGER for error reporting
     */
    private static final Logger LOGGER = Logger.getLogger(BillDAO.class);

    /**
     * Committee codes
     */
    private static int[] ctyCodes = {
        101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113,
        114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126,
        127, 130, 201, 202, 203, 205, 208, 209, 210, 211, 212, 213, 214,
        215, 216, 217, 219, 221, 222, 224, 225, 226, 227, 228, 230};

    /**
     * Hibernate session factory
     */
    private final SessionFactory sessionFactory;
    /**
     * Hibernate session object
     */
    private Session session;
    /**
     * Hibernate transaction object
     */
    private Transaction tx;

    /**
     * Constructor used for testing.
     *
     * @param sessionFactory Mock SessionFactory object
     */
    public BillDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Construct a BillsDataDOA object that is configured from
     * the hiberante.cfg.xml file that is on the class path, but
     * change the name of the &quot;Bills_Data&quot; table to a specified
     * table name. The named table must have the same definition as
     * the &quot;Bills_Data&quot; table
     * 
     * @param tableName The table to store the Bills_Data 
     */
    public BillDAO(String tableName) {
        sessionFactory = UploadBillsData.createSessionFactory(tableName);
    }
    
    /**
     * Get the session factory
     * @return the session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Method to set one of the committee fields in a bill object
     *
     * @param bill The Bill object
     * @param ctyCode The endTransactiontee code
     * @param isPrimary True from primary endTransactiontee, false for other endTransactiontees
     * @param value The value to be set
     */
    public void setCommittee(Bill bill, int ctyCode, boolean isPrimary, Integer value) {
        String methodName;
        if (ctyCode != 300) {
            methodName = String.format("set$%3d%s", ctyCode, isPrimary ? "p" : "o");
        } else {
            methodName = "set$300";
        }
        try {
            Method method = Bill.class.getMethod(methodName, Integer.class);
            method.invoke(bill, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            String message = String.format("Error setting committee %d%s in bill %s",
                    ctyCode, isPrimary ? "p" : "o", bill.getId());
            LOGGER.error(message, ex);
        }
    }

    /**
     * Method to clear all committee fields in a Bill object
     *
     * @param bill The Bill object
     */
    public void clearAll(Bill bill) {
        for (int ctyCode : ctyCodes) {
            setCommittee(bill, ctyCode, true, 0);
            setCommittee(bill, ctyCode, false, 0);
        }
        setCommittee(bill, 300, false, 0);
    }

    /**
     * Retrieve a bill based on its unique identifier 
     * If one does not exist, a new empty one is created
     * and inserted into the database.
     *
     * @param billID The unique identifier
     * @return The Bill object
     */
    public Bill getBill(String billID) {
        beginTransaction();
        Bill bill = null;
        try {
            bill = session.get(Bill.class, billID);
            if (bill == null) {
                bill = new Bill();
                bill.setId(billID);
                session.save(bill);
            }
        } catch (HibernateException ex) {
            LOGGER.error("Error retrieving " + billID, ex);
        }
        return bill;
    }
    
    /**
     * Method to save a Bill object back to the database
     * @param bill the bills data to be saved to the db
     */
    public void save(Bill bill) {
        session.save(bill);
    }

    /**
     * Method to open a session
     */
    public void openSession() {
        if (session == null) {
            session = sessionFactory.openSession();
        }
    }
    
    /**
     * Method to begin a transaction
     */
    public void beginTransaction() {
        openSession();
        if (tx == null) {
            tx = session.beginTransaction();
        }
    }
    
    /**
     * Method to endTransaction the current transaction
     */
    public void endTransaction() {
        if (tx != null) {
            tx.commit();
            tx = null;
        }
    }
    
    /**
     * Method to close the session
     */
    public void closeSession() {
        endTransaction();
        if (session != null) {
            session.close();
            session = null;
        }
    }
    
    /**
     * Method to close the session factory
     */
    public void closeSessionFactory() {
        closeSession();
        sessionFactory.close();
    }

}
