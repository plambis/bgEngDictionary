/*
 * 
 */
package bg.plambis.data.api;

import javax.sql.DataSource;

/**
 * The Interface DataSourceProvider.
 *
 * @author plambis
 */
public interface DataSourceProvider {

	/**
	 * Creates the data source.
	 *
	 * @return the data source
	 */
	DataSource createDataSource();
}
