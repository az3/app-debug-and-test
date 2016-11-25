package com.cagricelebi.voltdb.sample.procedure;

import org.voltdb.SQLStmt;
import org.voltdb.VoltTable;

/**
 *
 * @author cagricelebi
 */
public class EventOne extends BaseEventProcedure {

    protected static final SQLStmt simpleInsertStatement = new SQLStmt("INSERT INTO library (id) VALUES (?);");
    protected static final SQLStmt getQuery = new SQLStmt("SELECT * FROM library WHERE id=? LIMIT 1;");

    protected VoltTable currentRecord;
    protected long uid;

    public VoltTable[] run(long uid, String json) throws VoltAbortException {
        this.uid = uid;

        return new VoltTable[]{};
    }

}
