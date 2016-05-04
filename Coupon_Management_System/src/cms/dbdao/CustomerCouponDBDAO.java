package cms.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import cms.dao.CustomerCouponDAO;
import cms.main.CouponSystemException;
import cms.utility.ConnectionPool;
import cms.utility.SqlTableHandler;


public class CustomerCouponDBDAO implements CustomerCouponDAO {
	//******************************************************************
	//ATTRIBUTES
	
	private final String CSTMR_CPN_TBL_NAME = "Customer_Coupon_Table"; //Customer coupon table name.
	
	//Customer Coupon table columns names.
	private final String CSTMR_ID_CLMN_NAME = "Customer_ID"; //Customer ID column name.
	private final String CPN_ID_CLMN_NAME = "Coupon_ID"; //Coupon ID column name.

	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR

	
	//END OF CTOR	
	//******************************************************************

	//******************************************************************
	//METHODS

	/**
	 * @see jbt.com.cms.dao.CustomerCouponDAO#insertRow(long, long)
	 * Inserts a new row to the Customer Coupon table. Customer ID into column 1 
	 * and coupon ID into column 2.
	 */
	@Override
	public void insertRow(long cstmrId, long cpnId) throws CouponSystemException {
		//Check for valid customer ID.
		if (cstmrId <= 0) {
			throw new CouponSystemException("ERROR: Invalid customer ID value: " + cstmrId + ". Please enter a value greater than 0.");
		}
		
		//Check for valid coupon ID.
		if (cpnId <= 0) {
			throw new CouponSystemException("ERROR: Invalid coupon ID value: " + cpnId + ". Please enter a value greater than 0.");
		}
		
		//Check if the given coupon ID already exists in the table. 
		Collection<long[]> cstmrCpn = this.getAllRows(); //Getting all existing rows in the Customer Coupon table.
		Iterator<long[]> it  = cstmrCpn.iterator();
				
		while(it.hasNext()) {
			long[] cstmrCpnFromTbl = it.next(); //Extraction of a single row from the table containing customer and coupon IDs.
			if (cstmrId == cstmrCpnFromTbl[0] && cpnId == cstmrCpnFromTbl[1]) { //Comparing the rows customer and coupon id to the given ones.
				throw new CouponSystemException("ERROR: A coupon with the given ID already exists in the table '" + this.CSTMR_CPN_TBL_NAME 
												+ "'. Please enter a different name coupon ID.");
			} 
		}
		
		//Composition of an SQL statement for inserting a row to the Customer table.
		String sql = "INSERT INTO " + this.CSTMR_CPN_TBL_NAME + " VALUES(?,?)";
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();

		//Creating a prepared SQL statement object.
		try(PreparedStatement prStmnt = cnctn.prepareStatement(sql);) {
			prStmnt.setLong(1, cstmrId); //Customer ID in column 1.
			prStmnt.setLong(2, cpnId); //Coupon ID in column 2.
			
			prStmnt.executeUpdate(); //Execution of the SQL statement.
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to insert new row into table '" + this.CSTMR_CPN_TBL_NAME +"'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerCouponDAO#deleteRowsByCulomnValue(java.lang.String, long)
	 * Deletes a row from the table as per a given column name and ID value.
	 * Invokes the deleteFromTable method from the class SqlTableHandler.
	 */
	@Override
	public void deleteRowsByCulomnValue(String clmnName, long id) throws CouponSystemException {
		//Check for invalid column name.
		if((clmnName == null) || (clmnName.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid column name. Please try again.");
		}
		
		//Check for valid ID.
		if (id <= 0) {
			throw new CouponSystemException("ERROR: Invalid ID value: " + id + ". Please enter a value greater than 0.");
		}
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
			
		//Invoking the 'deleteFromTable' method.
		SqlTableHandler.deleteFromTable(cnctn, this.CSTMR_CPN_TBL_NAME, clmnName, id);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerCouponDAO#deleteRowsByCouponID(long)
	 * Deletes a row from the table as per a given coupon ID value.
	 * Invokes the deleteFromTable method from the class SqlTableHandler.
	 */
	@Override
	public void deleteRowsByCouponID(long cpnId) throws CouponSystemException {
		this.deleteRowsByCulomnValue(this.CPN_ID_CLMN_NAME, cpnId);				
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerCouponDAO#deleteRowsByCustomerID(long)
	 * Deletes a row from the table as per a given customer ID value.
	 * Invokes the deleteFromTable method from the class SqlTableHandler.
	 */
	@Override
	public void deleteRowsByCustomerID(long cstmrId) throws CouponSystemException {
		this.deleteRowsByCulomnValue(this.CSTMR_ID_CLMN_NAME, cstmrId);				
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerCouponDAO#getCustomersIDsByCouponID(long)
	 * Getting all the customers IDs associated with a given coupon ID from the table.
	 * Invokes the getAllFromTableByCoulmnValue method from the SqlTableHandler class.
	 */
	@Override
	public Collection<Long> getCustomersIDsByCouponID(long cpnId) throws CouponSystemException {
		//Check for valid coupon ID.
		if (cpnId <= 0) {
			throw new CouponSystemException("ERROR: Invalid coupon ID value: " + cpnId + ". Please enter a value greater than 0.");
		}
		
		//Converting the ID value type from long to string for simplifying the SQL commands composition. 
		String cpnIdStrng = Long.toString(cpnId);
		
		Collection<Long> cstmrIds = new HashSet<>(); //Customer IDs collection.
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		//Getting all rows with a matching coupon ID.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CSTMR_CPN_TBL_NAME, this.CPN_ID_CLMN_NAME, cpnIdStrng, this.CSTMR_ID_CLMN_NAME);) {
			while(rsltSet.next()) {
				cstmrIds.add(rsltSet.getLong(1)); //Customer ID in column 1.
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to get all customers IDs associated with coupon ID: " + cpnId 
											+ " from table '" + this.CSTMR_CPN_TBL_NAME +"'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return cstmrIds;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerCouponDAO#getCouponsIDsByCustomerID(long)
	 * Getting all the coupon IDs associated with a given customer ID from the table.
	 * Invokes the getAllFromTableByCoulmnValue method from the SqlTableHandler class.
	 */
	@Override
	public Collection<Long> getCouponsIDsByCustomerID(long cstmrId) throws CouponSystemException {
		//Check for valid customer ID.
		if (cstmrId <= 0) {
			throw new CouponSystemException("ERROR: Invalid customer ID value: " + cstmrId + ". Please enter a value greater than 0.");
		}
		
		//Converting the ID value type from long to string for simplifying the SQL commands composition. 
		String cstmrIdStrng = Long.toString(cstmrId);

		Collection<Long> cpnsIds = new HashSet<>(); //Coupon IDs collection.
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection(); 
		
		//Getting all rows with a matching company ID.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CSTMR_CPN_TBL_NAME, this.CSTMR_ID_CLMN_NAME, cstmrIdStrng, this.CSTMR_ID_CLMN_NAME);) {
			while(rsltSet.next()) {
				cpnsIds.add(rsltSet.getLong(2)); //Coupon ID in column 2.
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to get all coupons IDs associated with customer ID: " + cstmrId 
											+ " from table '" + this.CSTMR_CPN_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}

		return cpnsIds;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerCouponDAO#getAllRows()
	 * Getting all the customer coupon IDs from the table.
	 * Invokes the getAllFromTableByCoulmnValue method from the SqlTableHandler class.
	 */
	@Override
	public Collection<long[]> getAllRows() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		Collection<long[]> cstmrsCpnsIds = new HashSet<>(); //Customer coupon IDs collection.
		
		//Getting all rows from the table.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTable(cnctn, this.CSTMR_CPN_TBL_NAME, this.CSTMR_ID_CLMN_NAME);) {
			while(rsltSet.next()) {
				long cstmrCpn[] = {rsltSet.getLong(1), rsltSet.getLong(2)}; //Customer ID in column 1 and Coupon ID in column 2.
				cstmrsCpnsIds.add(cstmrCpn);
			}			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to retrieve all customers and coupons IDs from table '" + this.CSTMR_CPN_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return cstmrsCpnsIds;
	}
	
	//------------------------------------------------------------------
	//Utility methods:
	
	public void showCustomerCouponTable() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTable(cnctn, this.CSTMR_CPN_TBL_NAME, this.CSTMR_ID_CLMN_NAME);) {	
			//Printing the Customer Coupon table content.
			System.out.println("Shwoing the content of '" + this.CSTMR_CPN_TBL_NAME + "':");
			
			System.out.println("-----------------------------------------"+ this.CSTMR_CPN_TBL_NAME +"----------------------------------------------");
			//Printing the Customer table columns names.
			System.out.println("---------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-20.20s%-20.20s", 
								"|" + this.CSTMR_ID_CLMN_NAME,
								"|" + this.CPN_ID_CLMN_NAME);
			System.out.print("|\n");

			System.out.println("-----------------------------------------------------------------------------------------------------------------");

			//Printing the Customer table rows.
			while(rsltSet.next()) {
				System.out.printf("%-20.20s%-20.20s", 
									"|" + rsltSet.getLong(1),
									"|" + rsltSet.getLong(2));
				System.out.print("|\n");
			}
			
			System.out.println("-----------------------------------------END OF "+ this.CSTMR_CPN_TBL_NAME +"----------------------------------------------");

		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed retireieving table '" + this.CSTMR_CPN_TBL_NAME + "' from the database.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
	}
	
	//------------------------------------------------------------------
	
	public void createCustomerCouponTable() throws CouponSystemException {
		//Composition of an SQL statement for creating the Coupon table in a specified format.
		String sql = "CREATE TABLE " + this.CSTMR_CPN_TBL_NAME + "("
						+ this.CSTMR_ID_CLMN_NAME + " BIGINT,"
						+ this.CPN_ID_CLMN_NAME + " BIGINT,"
						+ "PRIMARY KEY (" 
						+ this.CSTMR_ID_CLMN_NAME + ", " 
						+ this.CPN_ID_CLMN_NAME + "))";
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		SqlTableHandler.createSqlTable(cnctn, sql, this.CSTMR_CPN_TBL_NAME);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	//------------------------------------------------------------------
	
	public void dropCustomerCouponTable() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		SqlTableHandler.dropTable(cnctn, this.CSTMR_CPN_TBL_NAME);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	
	//END OF METHODS
	//******************************************************************
	
	
	//END OF CustomerCouponDBDAO
}
