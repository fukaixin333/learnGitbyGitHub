package com.citic.server.service.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.citic.server.utils.Base64Utils;

/**
 * 
 * @author hubaiqing
 * @version 1.0
 * 
 * com.citic.server.service.base.CiticServerDriverManagerDataSource
 */

public class CiticServerDriverManagerDataSource extends DriverManagerDataSource {
	
	/**
	 * Constructor for bean-style configuration.
	 */
	public CiticServerDriverManagerDataSource() {
	}

	/**
	 * Create a new DriverManagerDataSource with the given JDBC URL,
	 * not specifying a username or password for JDBC access.
	 * @param url the JDBC URL to use for accessing the DriverManager
	 * @see java.sql.DriverManager#getConnection(String)
	 */
	public CiticServerDriverManagerDataSource(String url) {
		setUrl(url);
	}

	/**
	 * Create a new DriverManagerDataSource with the given standard
	 * DriverManager parameters.
	 * @param url the JDBC URL to use for accessing the DriverManager
	 * @param username the JDBC username to use for accessing the DriverManager
	 * @param password the JDBC password to use for accessing the DriverManager
	 * @see java.sql.DriverManager#getConnection(String, String, String)
	 */
	public CiticServerDriverManagerDataSource(String url, String username, String password) {
		
		
		String password64 = password;
		try{
			password64 = Base64Utils.decodeBase64( password ); 
			
			//System.err.println("password_64bianma====="+password64);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		setUrl(url);
		setUsername(username);
		setPassword(password64);
	}

	/**
	 * Create a new DriverManagerDataSource with the given JDBC URL,
	 * not specifying a username or password for JDBC access.
	 * @param url the JDBC URL to use for accessing the DriverManager
	 * @param conProps JDBC connection properties
	 * @see java.sql.DriverManager#getConnection(String)
	 */
	public CiticServerDriverManagerDataSource(String url, Properties conProps) {
		setUrl(url);
		
//		String password = conProps.getProperty("passowrd");
//		
//		String password64 = password;
//		try{
//			password64 = this.decodeBase64( password ); 
//			
//			System.err.println("password_64bianma====="+password64);
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		conProps.setProperty("passowrd", password64);
		
		setConnectionProperties(conProps);
	}

	/**
	 * Set the JDBC driver class name. This driver will get initialized
	 * on startup, registering itself with the JDK's DriverManager.
	 * <p><b>NOTE: DriverManagerDataSource is primarily intended for accessing
	 * <i>pre-registered</i> JDBC drivers.</b> If you need to register a new driver,
	 * consider using {@link SimpleDriverDataSource} instead. Alternatively, consider
	 * initializing the JDBC driver yourself before instantiating this DataSource.
	 * The "driverClassName" property is mainly preserved for backwards compatibility,
	 * as well as for migrating between Commons DBCP and this DataSource.
	 * @see java.sql.DriverManager#registerDriver(java.sql.Driver)
	 * @see SimpleDriverDataSource
	 */
	public void setDriverClassName(String driverClassName) {
		Assert.hasText(driverClassName, "Property 'driverClassName' must not be empty");
		String driverClassNameToUse = driverClassName.trim();
		try {
			Class.forName(driverClassNameToUse, true, ClassUtils.getDefaultClassLoader());
		}
		catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Could not load JDBC driver class [" + driverClassNameToUse + "]", ex);
		}
		if (logger.isInfoEnabled()) {
			logger.info("Loaded JDBC driver: " + driverClassNameToUse);
		}
	}


	@Override
	protected Connection getConnectionFromDriver(Properties props) throws SQLException {
		String url = getUrl();
		if (logger.isDebugEnabled()) {
			logger.debug("Creating new JDBC DriverManager Connection to [" + url + "]");
		}
		return getConnectionFromDriverManager(url, props);
	}

	/**
	 * Getting a Connection using the nasty static from DriverManager is extracted
	 * into a protected method to allow for easy unit testing.
	 * @see java.sql.DriverManager#getConnection(String, java.util.Properties)
	 * 
	 * 胡佰庆：数据库连接时，如果数据库密码需要用Base64编码加密，那么，在配置时用此类执行
	 * 
	 */
	protected Connection getConnectionFromDriverManager(String url, Properties props) throws SQLException {
		
		String password = props.getProperty("password");
		
		String password64 = password;
		try{
			password64 = Base64Utils.decodeBase64( password ); 
			
			//System.err.println("password_64bianma_conn====="+password64);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		props.setProperty("password", password64);
		
		return DriverManager.getConnection(url, props);
	}
	
    public  static void main(String args[]){
    	CiticServerDriverManagerDataSource d = new CiticServerDriverManagerDataSource();
    	Properties prop =  d.getConnectionProperties();
    	
    	try{
    		String passowrd = Base64Utils.encodeBase64("uafp");
        	
        	System.out.println("password_64bianma=["+passowrd+"]");
        	System.out.println("password_yuanshi =["+Base64Utils.decodeBase64(passowrd+"]"));
        	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	
    }
    

}
