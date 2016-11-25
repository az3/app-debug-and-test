/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cagricelebi.voltdb.sample;

import java.util.Date;
import junit.framework.TestCase;
import org.voltdb.InProcessVoltDBServer;
import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcCallException;

/**
 *
 * @author cagricelebi
 */
public class BasicTest extends TestCase {

    public void testProcedureReturn() throws Exception {

        // Create an in-process VoltDB server instance
        InProcessVoltDBServer volt = new InProcessVoltDBServer();

        // If using Enterprise or Pro Edition, a license is required.
        // If using Community Edition, comment out the following line.
        volt.configPathToLicense("./license.xml");

        // Start the database
        volt.start();

        // Load the schema
        volt.runDDLFromPath("./ddl.sql");

        // Create a client to communicate with the database
        Client client = volt.getClient();

        // TESTS...
        // insert a row using a default procedure
        int id = 1;
        String val = "Hello VoltDB";
        Date initialDate = new Date();
        ClientResponse response = client.callProcedure("LIBRARY.insert", id, val, initialDate);
        assertEquals(response.getStatus(), ClientResponse.SUCCESS);

        // try inserting the same row, expect a unique constraint violation
        try {
            response = client.callProcedure("LIBRARY.insert", id, val, initialDate);
        } catch (ProcCallException e) {
        }

        // call the UpdateFoo procedure
        val = "Hello again";
        response = client.callProcedure("EventOne", id, val);
        assertEquals(response.getStatus(), ClientResponse.SUCCESS);
        // check that one row was updated
        assertEquals(response.getResults()[0].asScalarLong(), 1);

        // select the row and check the values
        response = client.callProcedure("LIBRARY.select", id);
        VoltTable t = response.getResults()[0];
        assertEquals(t.getRowCount(), 1);
        t.advanceRow();
        long lastUpdatedMicros = t.getTimestampAsLong("LAST_UPDATED");
        long initialDateMicros = initialDate.getTime() * 1000;
        assertTrue(lastUpdatedMicros > initialDateMicros);
        String latestVal = t.getString("VAL");
        assertEquals(latestVal, val);

        volt.shutdown();
    }
}
