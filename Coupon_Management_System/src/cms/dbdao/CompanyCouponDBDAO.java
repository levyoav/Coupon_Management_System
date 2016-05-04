package cms.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import cms.dao.CompanyCouponDAO;
import cms.main.CouponSystemException;
import cms.utility.ConnectionPool;
import cms.utility.SqlTableHandler;


public class CompanyCouponDBDAO implements CompanyCouponDAO {
	//******************************************************************
	//ATTRIBUTES
	
	private final String CMPNY_CPN_TBL_NAME = "Company_Coupon_Table"; //Company coupon table name.

	//Company table columns names.
	private final String CMPNY_ID_CLMN_NAME = "Company_ID"; //Company ID column name.
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
	 * @see jbt.com.cms.dao.CompanyCouponDAO#insertRow(long, long)
	 * Inserts a new row to the Company Coupon table. Company ID into column 1 
	 * and coupon ID into column 2.
	 */
	@Override
	public void insertRow(long cmpnyId, long cpnId) throws CouponSystemException {
		//Check for valid copany ID.
		if (cmpnyId <= 0) {
			throw new CouponSystemException("ERROR: Invalid company ID value: " + cmpnyId + ". Please enter a value greater than 0.");
		}
		
		//Check for valid coupon ID.
		if (cpnId <= 0) {
			throw new CouponSystemException("ERROR: Invalid coupon ID value: " + cpnId + ". Please enter a value greater than 0.");
		}
		
		//Check if the given coupon ID already exists in the table. 
		Collection<long[]> cmpnyCpnIds = this.getAllRows(); //Getting all existing rows in the Company Coupon table.
		Iterator<long[]> idsIt  = cmpnyCpnIds.iterator();
		
		while(idsIt.hasNext()) {
			long[] idsFromTbl = idsIt.next(); //Extraction of a single row from the table containing company and coupon IDs.
			if (cpnId == idsFromTbl[1]) { //Extraction of the coupon ID from the row and comparing it to the given coupon ID.
				throw new CouponSystemException("ERROR: A coupon with the given ID already exists in the table '" + this.CMPNY_CPN_TBL_NAME 
						+ "'. Please enter a different coupon ID.");
			} 
		}
		
		//Composition of an SQL statement for inserting a row to the Company Coupon table.
		String sql = "INSERT INTO " + this.CMPNY_CPN_TBL_NAME + " VALUES(?,?)";
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();

		//Creating a prepared SQL statement object.
		try(PreparedStatement prStmnt = cnctn.prepareStatement(sql);) {
			prStmnt.setLong(1, cmpnyId); //Company ID in column 1.
			prStmnt.setLong(2, cpnId); //Coupon ID in column 2.

			prStmnt.executeUpdate(); //Execution of the SQL statement.

		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to insert new row into table '" + this.CMPNY_CPN_TBL_NAME +"'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyCouponDAO#deleteRow(java.lang.String, long)
	 * Deletes a row from the table as per a given company ID value.
	 * Invokes the deleteFromTable method from the class SqlTableHandler.
	 */
	@Override
	public void deleteRow(String clmnName, long id) throws CouponSystemException {
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
		SqlTableHandler.deleteFromTable(cnctn, this.CMPNY_CPN_TBL_NAME, clmnName, id);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyCouponDAO#deleteCoupon(long)
	 * Deletes a row from the table as per a given coupon ID value.
	 * Invokes the deleteRow method.
	 */
	@Override
	public void deleteCoupon(long cpnId) throws CouponSystemException {
		this.deleteRow(this.CPN_ID_CLMN_NAME, cpnId);		
	}

	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyCouponDAO#deleteCouponsByCompanyId(long)
	 * Deletes all rows with a matching given company ID value.
	 * Invokes the deleteRow method.
	 */
	@Override
	public void deleteCouponsByCompanyId(long cmpnyId) throws CouponSystemException {
		this.deleteRow(this.CMPNY_ID_CLMN_NAME, cmpnyId);		
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyCouponDAO#getCompanyIDByCouponID(long)
	 * Getting the company ID associated with a given coupon ID from the table.
	 * Invokes the getAllFromTableByCoulmnValue method from the SqlTableHandler class.
	 */
	@Override
	public long getCompanyIDByCouponID(long cpnId) throws CouponSystemException {
		//Check for valid coupon ID.
		if (cpnId <= 0) {
			throw new CouponSystemException("ERROR: Invalid coupon ID value: " + cpnId + ". Please enter a value greater than 0.");
		}
		
		//Converting the ID value type from long to string for simplifying the SQL commands composition. 
		String cpnIdStrng = Long.toString(cpnId);
		
		long cmpnyId = 0L; //Default ID value if no matching company ID was found.
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		//Getting all rows with a matching coupon ID - THERE CAN BE ONLY 1!
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CMPNY_CPN_TBL_NAME, this.CPN_ID_CLMN_NAME, cpnIdStrng, this.CMPNY_ID_CLMN_NAME);) {
			//If the result set is not empty, assert cmpnyId with the returned value.
			if(rsltSet.next()) {
				cmpnyId = rsltSet.getLong(1); //Company ID in column 1.
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to get company ID associated with coupon ID: " + cpnId + 
					" from table '" + this.CMPNY_CPN_TBL_NAME +"'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return cmpnyId;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyCouponDAO#getCouponsIDsByCompanyID(long)
	 * Getting all the coupon IDs associated with a given company ID from the table.
	 * Invokes the getAllFromTableByCoulmnValue method from the SqlTableHandler class.
	 */
	@Override
	public Collection<Long> getCouponsIDsByCompanyID(long cmpnyId) throws CouponSystemException {
		//Check for valid company ID.
		if (cmpnyId <= 0) {
			throw new CouponSystemException("ERROR: Invalid company ID value: " + cmpnyId + ". Please enter a value greater than 0.");
		}
		
		//Converting the ID value type from long to string for simplifying the SQL commands composition. 
		String cmpnyIdStrng = Long.toString(cmpnyId);

		Collection<Long> cpnsIds = new HashSet<>(); //Coupon IDs collection.
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		//Getting all rows with a matching company ID.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CMPNY_CPN_TBL_NAME, this.CMPNY_ID_CLMN_NAME, cmpnyIdStrng, this.CMPNY_ID_CLMN_NAME);) {
			while(rsltSet.next()) {
				cpnsIds.add(rsltSet.getLong(2)); //Coupon ID in column 2.
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to retrieve all coupons IDs associated with company ID: " + cmpnyId 
					+ " from table '" + this.CMPNY_CPN_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}

		return cpnsIds;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyCouponDAO#getAllRows()
	 * Getting all the company coupon IDs from the table.
	 * Invokes the getAllFromTableByCoulmnValue method from the SqlTableHandler class.
	 */
	@Override
	public Collection<long[]> getAllRows() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();

		Collection<long[]> cmpnysCpnsIds = new HashSet<>(); //Company coupon IDs collection.
		
		//Getting all rows from the table.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTable(cnctn, this.CMPNY_CPN_TBL_NAME, this.CMPNY_ID_CLMN_NAME);) {
			while(rsltSet.next()) {
				long cmpnyCpn[] = {rsltSet.getLong(1), rsltSet.getLong(2)}; //Company ID in column 1 and Coupon ID in column 2.
				cmpnysCpnsIds.add(cmpnyCpn);
			}			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to retrieve all companies and coupons IDs from table '" + this.CMPNY_CPN_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}

		return cmpnysCpnsIds;
	}
	
	//------------------------------------------------------------------
	//Utility methods:
	
	public void showCompanyCouponTable() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTable(cnctn, this.CMPNY_CPN_TBL_NAME, this.CMPNY_ID_CLMN_NAME);) {	
			//Printing the Company Coupon table content.
			System.out.println("Shwoing the content of '" + this.CMPNY_CPN_TBL_NAME + "':");
			
			System.out.println("-----------------------------------------"+ this.CMPNY_CPN_TBL_NAME +"----------------------------------------------");
			//Printing the Company table columns names.
			System.out.println("---------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-20.20s%-20.20s", 
								"|" + this.CMPNY_ID_CLMN_NAME, 
								"|" + this.CPN_ID_CLMN_NAME);
			System.out.print("|\n");
			System.out.println("-----------------------------------------------------------------------------------------------------------------");

			//Printing the Company table rows.
			while(rsltSet.next()) {
				System.out.printf("%-20.20s%-20.20s", 
									"|" + rsltSet.getLong(1),
									"|" + rsltSet.getLong(2));
				System.out.print("|\n");

			}
			
			System.out.println("-----------------------------------------END OF "+ this.CMPNY_CPN_TBL_NAME +"----------------------------------------------");

		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed retireieving table '" + this.CMPNY_CPN_TBL_NAME + "' from the database.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
	}
	
	//------------------------------------------------------------------
	
	public void createCompanyCouponTable() throws CouponSystemException {
		//Composition of an SQL statement for creating the Coupon table in a specified format.
		String sql = "CREATE TABLE " + this.CMPNY_CPN_TBL_NAME + "("
						+ this.CMPNY_ID_CLMN_NAME + " BIGINT,"
						+ this.CPN_ID_CLMN_NAME + " BIGINT,"
						+ "PRIMARY KEY (" 
						+ this.CMPNY_ID_CLMN_NAME + ", " 
						+ this.CPN_ID_CLMN_NAME + "))";
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		SqlTableHandler.createSqlTable(cnctn, sql, this.CMPNY_CPN_TBL_NAME);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	//------------------------------------------------------------------
	
	public void dropCompanyCouponTable() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		SqlTableHandler.dropTable(cnctn, this.CMPNY_CPN_TBL_NAME);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	
	//END OF METHODS
	//******************************************************************
	
	
	//END OF CompanyCouponDBDAO
}
