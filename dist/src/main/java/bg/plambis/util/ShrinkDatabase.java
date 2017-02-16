package bg.plambis.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import bg.plambis.data.api.DataSourceProvider;
import bg.plambis.data.impl.DataSourceProviderImpl;

public class ShrinkDatabase {
	private static final Logger LOG = Logger.getLogger(ShrinkDatabase.class.getName());
	private static final String DB_DRIVER_STRING = "org.h2.Driver";
	private static final String EXT_DB_URL_STRING_PREF = "jdbc:h2:";
	private static final String EXT_DB_URL_STRING_SUFIX = "/plambis";
	private static final String EXT_DB_USER_NAME = "plambis";
	private static final String EXT_DB_PASSWORD = "plambis";

	public static void main(String[] args) throws SQLException {
		LOG.info("Shrink Database start DB URL: " + args[0]);
		testConnection(DB_DRIVER_STRING,EXT_DB_URL_STRING_PREF + args[0] + EXT_DB_URL_STRING_SUFIX,EXT_DB_USER_NAME,EXT_DB_PASSWORD);
		LOG.info("Shrink Database end");
	}

	private static void testConnection(String dbDriver, String dbUrl, String dbUserName, String dbPassword) throws SQLException {
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
