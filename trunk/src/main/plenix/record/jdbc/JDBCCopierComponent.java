package plenix.record.jdbc;

import javax.sql.DataSource;

public abstract class JDBCCopierComponent {
	private DataSource dataSource;
	private String sqlText;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }
}
