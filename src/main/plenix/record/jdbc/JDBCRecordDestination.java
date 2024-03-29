package plenix.record.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import plenix.copier.destination.Destination;
import plenix.record.Record;

public class JDBCRecordDestination extends JDBCCopierComponent implements Destination<Record> {
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(JDBCRecordDestination.class.getName());
    
	private String[] fieldNames;

	private int batchSize = 1;
	private boolean commit = true;

	private int rowCount = 0;
	private PreparedStatement statement;
	
	public void open() throws Exception {
		Connection connection = getDataSource().getConnection();
		statement = connection.prepareStatement(getSqlText());
	}

	public void put(Record record) throws Exception {
	    try {
	        ResultSetMetaData metaData = statement.getMetaData();
	        for (int i = 0; i < fieldNames.length; i++) {
	            Object object = record.getField(fieldNames[i]);
	            if (object != null) {
	                statement.setObject(i + 1, object);
	            } else {
	                if (metaData != null) {
	                    statement.setNull(i + 1, metaData.getColumnType(i + 1));
	                } else {
	                    statement.setObject(i + 1, null);
	                }
	            }
	        }

	        statement.addBatch();
	        if (++rowCount % batchSize == 0) {
	            if (logger.isLoggable(Level.FINE)) logger.info("Batch execution point reached: " + rowCount);
	            statement.executeBatch();
	            if (commit) {
	                statement.getConnection().commit();
	            }
	        }
	    } catch (SQLException e) {
            e.printStackTrace();
            if (e.getNextException() != null) {
                e.getNextException().printStackTrace();
            }
	    }
	}

	public void close() throws Exception {
		statement.executeBatch();
		Connection connection = statement.getConnection();
		connection.commit();
		statement.close();
		connection.close();
	}
		
	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public boolean isCommit() {
		return commit;
	}

	public void setCommit(boolean commit) {
		this.commit = commit;
	}

	public String[] getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}
}
