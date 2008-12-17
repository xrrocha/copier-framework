package plenix.record.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import plenix.copier.source.Source;
import plenix.record.Record;

public class JDBCRecordSource extends JDBCCopierComponent implements Source<Record> {
	private ResultSet resultSet;
	
	public void open() throws Exception {
		Connection connection = getDataSource().getConnection();
		Statement statement = connection.createStatement();
		resultSet = statement.executeQuery(getSqlText());
	}

	public Record get() throws Exception {
		if (resultSet.next()) {
			ResultSetMetaData metaData = resultSet.getMetaData();
			Record record = new Record();
			int columnCount = metaData.getColumnCount();
			for (int i = 0; i < columnCount; i++) {
				record.setField(metaData.getColumnLabel(i + 1), resultSet.getObject(i + 1));
			}
			return record;
		} else {
			return null;
		}
	}

	public void close() throws Exception {
		Statement statement = resultSet.getStatement();
		Connection connection = statement.getConnection();
		resultSet.close();
		statement.close();
		connection.close();
	}
}
