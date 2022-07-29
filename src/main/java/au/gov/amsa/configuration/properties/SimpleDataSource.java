package au.gov.amsa.configuration.properties;

import java.sql.Connection;

public interface SimpleDataSource {

	Connection getConnection() throws Exception;

}
