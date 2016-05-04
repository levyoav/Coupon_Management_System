package cms.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import cms.beans.Coupon;
import cms.beans.Customer;
import cms.dao.CouponDAO;
import cms.dao.CustomerDAO;
import cms.main.CouponSystemException;
import cms.utility.ConnectionPool;
import cms.utility.SqlTableHandler;

public class CustomerDBDAO implements CustomerDAO {
	//******************************************************************
	//ATTRIBUTES
	
	private final String CSTMR_TBL_NAME = "Customer_Table"; //Customer table name.

	//customer table columns names.
	private final String ID_CLMN_NAME = "ID"; //Customer ID column name.
	private final String CSTMR_NM_CLMN_NAME = "Customer_Name"; //Customer name column name.
	private final String PSWD_CLMN_NAME = "Password"; //Customer password column name.
	
	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR

	
	//END OF CTOR	
	//******************************************************************

	//******************************************************************
	//METHODS
	
	/**
	 * @see jbt.com.cms.dao.CustomerDAO#createCustomer(jbt.com.cms.beans.Customer)
	 * Inserting a new customer into the Customer table as per a given company object.
	 */
	@Override
	public long createCustomer(Customer cstmr) throws CouponSystemException {
		//Check for valid customer.
		if(cstmr == null) {
			throw new CouponSystemException("ERROR: Invalid customer.");
		}
		
		long cstmrId = 0; //For returning the generated ID of the customer. 

		//Check if the given customer name already exist.
		Collection<Customer> customers = this.getAllCustomers(); //Getting all customers in the Customer table.
		Iterator<Customer> it  = customers.iterator();
		
		//Iterating through all the customers from the Customer table.
		while(it.hasNext()) {
			Customer cstmrFromTbl = it.next();
			if (cstmr.getCustName().equals(cstmrFromTbl.getCustName())) {
				System.out.println("ERROR: A customer with the name '" +cstmr.getCustName() + "' already exists in the table '" + this.CSTMR_TBL_NAME 
									+ "'. Please enter a company with a different name.");
			}
		}
		
		//Composition of an SQL statement for inserting a row to the Company table.
		String sql = "INSERT INTO " + this.CSTMR_TBL_NAME + " VALUES(?,?,?)";

		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		//Creating a prepared SQL statement object.
		try(PreparedStatement prStmnt = cnctn.prepareStatement(sql);) {
			cstmrId = SqlTableHandler.generateRowId(cnctn, this.CSTMR_TBL_NAME, this.ID_CLMN_NAME);

			prStmnt.setLong(1, cstmrId); //Customer ID in column 1.
			prStmnt.setString(2, cstmr.getCustName()); //Customer name in column 2.
			prStmnt.setString(3, cstmr.getPassword()); //Customer password in column 3.

			prStmnt.executeUpdate(); //Execution of the SQL statement.

		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to insert new row into table '" + this.CSTMR_TBL_NAME +"'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return cstmrId;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerDAO#removeCustomer(jbt.com.cms.beans.Customer)
	 * Deletes a customer from the Customer table as per a given customer object.
	 * Invokes the deleteFromTable method from the class SqlTableHandler.
	 */
	@Override
	public Customer removeCustomer(Customer cstmr) throws CouponSystemException {
		//Check for valid customer.
		if(cstmr == null) {
			throw new CouponSystemException("ERROR: Invalid customer.");
		}
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
			
		//Invoking the 'deleteFromTable' method.
		SqlTableHandler.deleteFromTable(cnctn, this.CSTMR_TBL_NAME, this.ID_CLMN_NAME, cstmr.getId());
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		
		return cstmr;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerDAO#updateCustomer(jbt.com.cms.beans.Customer)
	 * Updates a customer's password in the Customer table.
	 */
	@Override
	public void updateCustomer(Customer cstmr) throws CouponSystemException {
		//Check for valid customer.
		if(cstmr == null) {
			throw new CouponSystemException("ERROR: Invalid customer.");
		}
		
		long cstmrId = cstmr.getId();
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		//Composition of an SQL statement for updating a company in the Company table.
		String sql = "UPDATE " + this.CSTMR_TBL_NAME + " SET " 
				+ this.PSWD_CLMN_NAME + "=? "
				+ " WHERE " + this.ID_CLMN_NAME + "=" + cstmrId;

		//Creating a prepared SQL statement object.
		try(PreparedStatement prStmnt = cnctn.prepareStatement(sql);) {	
			//Setting the values from the given object into the SQL table.
			prStmnt.setString(1, cstmr.getPassword()); //Password in column 3.

			prStmnt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to update customer with ID:" + cstmrId + " in table '" + this.CSTMR_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerDAO#getCustomer(long)
	 * Returns a customer object from the Customer table as per a given customer ID.
	 */
	@Override
	public Customer getCustomer(long cstmrId) throws CouponSystemException {
		//Check for valid customer ID value.
		if(cstmrId <= 0) {
			throw new CouponSystemException("ERROR: Invalid customer ID value : " + cstmrId + " . Please enter a value greater than 0.");
		}
		
		//Converting the ID value type from long to string for simplifying the SQL commands composition. 
		String idStrng = Long.toString(cstmrId);
		
		Customer cstmr = null; //For returning a customer object.
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		//Getting the customer from the Customer table as per the given customer ID.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CSTMR_TBL_NAME, this.ID_CLMN_NAME, idStrng, this.ID_CLMN_NAME);) {
			//Check if the result set is not empty.
			if(rsltSet.next()) {
				cstmr = new Customer();
				cstmr.setId(cstmrId); //ID.
				cstmr.setCustName(rsltSet.getString(2)); //Company name.
				cstmr.setPassword(rsltSet.getString(3)); //Password.
				cstmr.setCoupons(this.getCouponsByCustomerID(cstmrId)); //Coupons.
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to get customer with ID: " + cstmrId + " from table '" + this.CSTMR_TBL_NAME +"'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return cstmr;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerDAO#getCustomerByName(java.lang.String)
	 * Returns a customer object from the Customer table as per a given customer name.
	 */
	@Override
	public Customer getCustomerByName(String cstmrName) throws CouponSystemException {
		//Check for invalid customer name.
		if((cstmrName == null) || (cstmrName.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid customer name. Please try again.");
		}
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		Customer cstmr = null; //For returning a customer object.
		
		//Revising the customer name string value for simplifying the SQL commands composition. 
		String cstmrNameSql = "'" + cstmrName + "'";
		
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CSTMR_TBL_NAME, this.CSTMR_NM_CLMN_NAME, cstmrNameSql, this.ID_CLMN_NAME);) {
			//Check if the result set is not empty.
			if(rsltSet.next()) {
				cstmr = new Customer();
				cstmr.setId(rsltSet.getLong(1)); //ID.
				cstmr.setCustName(cstmrName); //Customer name.
				cstmr.setPassword(rsltSet.getString(3)); //Password.
				cstmr.setCoupons(this.getCouponsByCustomerID(rsltSet.getLong(1))); //Coupons.
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("WARNNING: Failed to get customer with the name '" + cstmrName + "' from table '" + this.CSTMR_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}

		return cstmr;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerDAO#getAllCustomers()
	 * Returning a collection of all the customers in the customer table.
	 */
	@Override
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();

		Collection<Customer> customers = new HashSet<>(); //For returning a customer collection.
		
		Customer cstmr = null;
		
		//Getting all customers from the Customer table.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTable(cnctn, this.CSTMR_TBL_NAME, this.ID_CLMN_NAME);) {
			while(rsltSet.next()) {
				cstmr = new Customer();
				cstmr.setId(rsltSet.getLong(1)); //ID.
				cstmr.setCustName(rsltSet.getString(2)); //Customer name.
				cstmr.setPassword(rsltSet.getString(3)); //Password.
				cstmr.setCoupons(this.getCouponsByCustomerID(rsltSet.getLong(1))); //Coupons.
				
				customers.add(cstmr);
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to get all customers from table '" + this.CSTMR_TBL_NAME + "'", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}

		return customers;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CustomerDAO#getCouponsByCustomerID(long)
	 * Returning a collection of all the coupons associated with a customer as per its given ID.
	 * Invokes the getCouponsByCompanyID method from the CouponDAO.
	 */
	@Override
	public Collection<Coupon> getCouponsByCustomerID(long cstmrId) throws CouponSystemException {
		//Check for valid customer ID value.
		if(cstmrId <= 0) {
			throw new CouponSystemException("ERROR: Invalid customer ID value : " + cstmrId + " . Please enter a value greater than 0.");
		}
		
		CouponDAO cpnDao = new CouponDBDAO(); //For getting all coupons associated with the handled company
		
		Collection<Coupon> coupons = cpnDao.getCouponsByCustomerID(cstmrId);
		
		return coupons;
	}
	
	//------------------------------------------------------------------
	//Utility methods:
	
	public void showCustomerTable() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTable(cnctn, this.CSTMR_TBL_NAME, this.ID_CLMN_NAME);) {	
			//Printing the Customer table content.
			System.out.println("Shwoing the content of '" + this.CSTMR_TBL_NAME + "':");
			
			System.out.println("-----------------------------------------"+ this.CSTMR_TBL_NAME +"----------------------------------------------");
			//Printing the Customer table columns names.
			System.out.println("---------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-20.20s%-20.20s%-20.20s", 
								"|" + this.ID_CLMN_NAME,
								"|" + this.CSTMR_NM_CLMN_NAME, 
								"|" + this.PSWD_CLMN_NAME);
			System.out.print("|\n");
			System.out.println("-----------------------------------------------------------------------------------------------------------------");

			//Printing the Customer table rows.
			while(rsltSet.next()) {
				System.out.printf("%-20.20s%-20.20s%-20.20s", 
									"|" + rsltSet.getInt(1),
									"|" + rsltSet.getString(2),
									"|" + rsltSet.getString(3));
				System.out.print("|\n");
			}
			System.out.println("-----------------------------------------END OF "+ this.CSTMR_TBL_NAME +"----------------------------------------------");

			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed retireieving  table '" + this.CSTMR_TBL_NAME + "' from the database.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
	}
	
	//------------------------------------------------------------------
	
	public void createCustomerTable() throws CouponSystemException {
		//Composition of an SQL statement for creating the Coupon table in a specified format.
		String sql = "CREATE TABLE " + this.CSTMR_TBL_NAME + "("
						+ this.ID_CLMN_NAME + " BIGINT PRIMARY KEY,"
						+ this.CSTMR_NM_CLMN_NAME + " VARCHAR(255),"
						+ this.PSWD_CLMN_NAME + " VARCHAR(255)"
						+ ")";
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		SqlTableHandler.createSqlTable(cnctn, sql, this.CSTMR_TBL_NAME);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	//------------------------------------------------------------------
	
	public void dropCustomerTable() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		SqlTableHandler.dropTable(cnctn, this.CSTMR_TBL_NAME);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	//------------------------------------------------------------------
	
	public String getCSTMR_TBL_NAME() {
		return CSTMR_TBL_NAME;
	}
	
	
	//END OF METHODS
	//******************************************************************

	
	//END OF CustomerDBDAO
}
