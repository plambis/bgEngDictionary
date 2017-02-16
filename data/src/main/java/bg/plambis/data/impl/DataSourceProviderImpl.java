package bg.plambis.data.impl;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bg.plambis.data.api.DataSourceProvider;

/**
 * The Class DataSourceProviderImpl.
 *
 * @author plambis
 */
public class DataSourceProviderImpl implements DataSourceProvider {

	/** The Constant LOG. */
	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(DataSourceProviderImpl.class);

	/** The db driver. */
	private final String dbDriver;

	/** The db connection string. */
	private final String dbConnectionString;

	/** The db user name. */
	private final String dbUserName;

	/** The db password. */
	private final String dbPassword;

	/** The data source. */
	private DataSource dataSource;

	/**
	 * Instantiates a new data source provider impl.
	 *
	 * @param driver
	 *            the driver
	 * @param connectionString
	 *            the connection string
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	public DataSourceProviderImpl(final String driver, final String connectionString, final String userName, final String password) {
		dbConnectionString = connectionString;
		dbUserName = userName;
		dbPassword = password;
		dbDriver = driver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.plambis.data.api.DataSourceProvider#createDataSource()
	 */
	@Override
	public DataSource createDataSource() {
		if (dataSource == null) {
			BasicDataSource newDataSource = new BasicDataSource();
			newDataSource.setDriverClassName(dbDriver);
			newDataSource.setUrl(dbConnectionString);
			newDataSource.setUsername(dbUserName);
			newDataSource.setPassword(dbPassword);
			dataSource = newDataSource;
		}
		return dataSource;
	}

}
