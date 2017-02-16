package bg.plambis.data.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import bg.plambis.data.api.DataSourceProvider;

public class DataSourceProviderImplTest {
	private static final String DB_DRIVER_STRING = "org.h2.Driver";
	private static final String DB_URL_STRING = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static final String DB_USER_NAME = "test";
	private static final String DB_PASSWORD = "test";

	private static final String EXT_DB_URL_STRING = "jdbc:h2:./plambis/plambis";
	private static final String EXT_DB_USER_NAME = "plambis";
	private static final String EXT_DB_PASSWORD = "plambis";

	@Test
	public void testDataSourceProviderInMemoryDB() throws SQLException {
		testConnection(DB_DRIVER_STRING, DB_URL_STRING, DB_USER_NAME, DB_PASSWORD);
	}

	@Test
	public void testDataSourceProviderWithExistingDB() throws SQLException {
		testConnection(DB_DRIVER_STRING, EXT_DB_URL_STRING, EXT_DB_USER_NAME, EXT_DB_PASSWORD);
	}

	private void testConnection(String dbDriver, String dbUrl, String dbUserName, String dbPassword) throws SQLException {
		DataSourceProvider dsProvider = new DataSourceProviderImpl(dbDriver, dbUrl, dbUserName, dbPassword);
		DataSource dataSource = dsProvider.createDataSource();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("shutdown compact");
			ps.execute();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
