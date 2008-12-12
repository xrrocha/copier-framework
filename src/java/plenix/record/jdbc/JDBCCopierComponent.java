package plenix.record.jdbc;

import javax.sql.DataSource;

public abstract class JDBCCopierComponent {
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
