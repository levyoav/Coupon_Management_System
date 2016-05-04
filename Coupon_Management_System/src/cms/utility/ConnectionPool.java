package cms.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cms.main.CouponSystemException;


public class ConnectionPool {
	//******************************************************************
	//ATTRIBUTES
	
	private final String CLSS_NAME = this.getClass().getName(); //Getting this class name for more coherent messages.
	
	private final int CNCTN_PL_CAP = 5; //Number of connection in the connection pool.
	private final String DRVR_NAME = "org.apache.derby.jdbc.ClientDriver"; //JDBC driver name.
	private final String DB_NAME = "cmsdb"; //Name of the data base.
	private final String DB_URL = "jdbc:derby://localhost:1527/" + this.DB_NAME; //Connection path to the data base.
		
	private Set<Connection> cnctnsStack; //Stack of Connection objects reference for distribution.
	private Set<Connection> cnctns; //List of Connection objects reference for to be used when terminating all connections.


	private static ConnectionPool instance = null;
	
	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR
	
	private ConnectionPool() throws CouponSystemException {
		//Loading of the JDBC driver.
		try {
			Class.forName(this.DRVR_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		this.cnctnsStack = new HashSet<>();
		this.cnctns = new HashSet<>();

		//Iterations for establishing each connection according to the designated number of connections in the pool.
		for (int i = 1; i <= this.CNCTN_PL_CAP; i++) {
			//Attempting to connect to the data base.
			try {
				Connection cnctn = DriverManager.getConnection(this.DB_URL);
				
				//Adding the established connection to the connection pool.
				this.cnctnsStack.add(cnctn);
				this.cnctns.add(cnctn);
			//Failed to establish a connection.
			} catch(SQLException e) {
				throw new CouponSystemException("WARNNING: Failed to establish connetion #" + i + ".", e);
			}
		}
	}
	
	
	//END OF CTOR
	//******************************************************************

	//******************************************************************
	//METHODS
	
	public static ConnectionPool getInstance() throws CouponSystemException {
		if (instance == null) {
			instance = new ConnectionPool() ;
		}
		
		return instance;
	}
	
	
	//Returns a connection from the connection pool. 
	public synchronized Connection getConnection() throws CouponSystemException {
		//Checking if there are any available connection in the connection pool.
		while (this.cnctnsStack.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new CouponSystemException("ERROR: Failed to get a connection (from within: " 
						+ this.CLSS_NAME + ").", e);
				//e.printStackTrace();
			}
		}
		
		//Iterator on the connection pool.
		Iterator<Connection> it  = this.cnctnsStack.iterator();

		//Gets the next available connection in the connection pool by assigning a new reference to it.
		Connection cnctn = it.next();
				
		//Removes the handled connection from the connection pool.
		it.remove();

		return cnctn;
	}
	
	
	//Returns the given connection to the connection pool.
	public synchronized void returnConnection(Connection cnctn) {
		//Adding the handled connection to the connection pool.
		this.cnctnsStack.add(cnctn);
		//Notifies the other threads that are pending for an available connection. 
		notifyAll();
	}
	
	
	//Terminates all the connection to the data base in the connection pool.
	public void closeAllConnections() throws CouponSystemException {
		//Going over all the connections and terminates them.			
		for(Connection cnctn : this.cnctns) {
			try {
				cnctn.close();
			} catch (SQLException e) {
				throw new CouponSystemException("ERROR: Failed to close a connection (from within: " 
												+ this.CLSS_NAME + ").", e);
			}
			
		}
	}
	
	
	//END OF METHODS
	//******************************************************************


	//END OF ConnectionPool
}
