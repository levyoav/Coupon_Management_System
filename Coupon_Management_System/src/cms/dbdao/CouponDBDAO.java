package cms.dbdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import cms.beans.Company;
import cms.beans.Coupon;
import cms.beans.CouponType;
import cms.beans.Customer;
import cms.dao.CompanyCouponDAO;
import cms.dao.CompanyDAO;
import cms.dao.CouponDAO;
import cms.dao.CustomerCouponDAO;
import cms.dao.CustomerDAO;
import cms.main.CouponSystemException;
import cms.utility.ConnectionPool;
import cms.utility.SqlTableHandler;


public class CouponDBDAO implements CouponDAO {
	//******************************************************************
	//ATTRIBUTES
	
	private final String CPN_TBL_NAME = "Coupon_Table"; //Coupon table name.
	
	//Coupon table columns names.
	private final String ID_CLMN_NAME = "ID"; //Coupon ID column name.
	private final String TITLE_CLMN_NAME = "Title"; //Coupon title column name.
	private final String START_DATE_CLMN_NAME = "Start_Date"; //Coupon start date column name.
	private final String END_DATE_CLMN_NAME = "End_Date"; //Coupon start date column name.
	private final String AMOUNT_CLMN_NAME = "Amount"; //Coupon amount column name.
	private final String TYPE_CLMN_NAME = "Type"; //Coupon type column name.
	private final String MESSAGE_CLMN_NAME = "Message"; //Coupon message column name.
	private final String PRICE_CLMN_NAME = "Price"; //Coupon price column name.
	private final String IMAGE_CLMN_NAME = "Image"; //Coupon image column name.
	
	
	//END OF ATTRIBUTES
	//******************************************************************
	
	//******************************************************************
	//CTOR
	
	
	//END OF CTOR
	//******************************************************************
	
	//******************************************************************
	//METHODS
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CouponDAO#createCoupon(jbt.com.cms.beans.Coupon)
	 * Inserts a new coupon to the coupon table by a given coupon object. 
	 * Returns the inserted coupon's ID as in the table for the Company Coupon table.
	 */
	@Override
	public long createCoupon(Coupon cpn) throws CouponSystemException {
		//Check for valid coupon object.
		if(cpn == null) {
			throw new CouponSystemException("ERROR: Invalid coupon.");
		}
		
		//Composition of an SQL statement for inserting a row to the Coupon table.
		String sql = "INSERT INTO " + this.CPN_TBL_NAME + " VALUES(?,?,?,?,?,?,?,?,?)";
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		long cpnId = 0; //For returning the generated ID of the coupon. 
		
		//Creating a prepared SQL statement object.
		try(PreparedStatement prStmnt = cnctn.prepareStatement(sql);) {
			//Generating new coupon ID:
			cpnId = SqlTableHandler.generateRowId(cnctn, this.CPN_TBL_NAME, this.ID_CLMN_NAME);
			//Setting the values from the given object into the SQL table.
			prStmnt.setLong(1, cpnId); 
			prStmnt.setString(2, cpn.getTitle()); //Coupon title in column 2.
			prStmnt.setDate(3, (Date) cpn.getStartDate()); //Coupon start date in column 3.
			prStmnt.setDate(4, (Date) cpn.getEndDate()); //Coupon end date in column 4.
			prStmnt.setInt(5, cpn.getAmount()); //Coupon amount in column 5.
			prStmnt.setString(6, cpn.getType().toString()); //Coupon type in column 6.
			prStmnt.setString(7, cpn.getMessage()); //Coupon start message in column 7.
			prStmnt.setFloat(8, (float)cpn.getPrice()); //Coupon price in column 8.
			prStmnt.setString(9, cpn.getImage()); //Coupon image in column 8.
			
			prStmnt.executeUpdate(); //Execution of the SQL statement.
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to insert new row into table '" + this.CPN_TBL_NAME +"'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return cpnId;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CouponDAO#removeCoupon(jbt.com.cms.beans.Coupon)
	 * Deletes a coupon from the coupon table as per a given coupon object.
	 */
	@Override
	public void removeCoupon(Coupon cpn) throws CouponSystemException {
		//Check for valid coupon object.
		if(cpn == null) {
			throw new CouponSystemException("ERROR: Invalid coupon.");
		}
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
			
		//Invoking the 'deleteFromTable' method.
		SqlTableHandler.deleteFromTable(cnctn, this.CPN_TBL_NAME, this.ID_CLMN_NAME, cpn.getId());
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CouponDAO#updateCoupon(jbt.com.cms.beans.Coupon)
	 * Updates a coupon's values from the coupon table as per a given coupon object.
	 */
	@Override
	public void updateCoupon(Coupon cpn) throws CouponSystemException {
		//Check for valid coupon object.
		if(cpn == null) {
			throw new CouponSystemException("ERROR: Invalid coupon.");
		}
		
		long cpnId = cpn.getId(); //Getting the coupon's ID.
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		//Composition of an SQL statement for updating a coupon in the Coupon table.
		String sql = "UPDATE " + this.CPN_TBL_NAME + " SET " 
						+ this.TITLE_CLMN_NAME + "=?, "
						+ this.START_DATE_CLMN_NAME + "=?, "
						+ this.END_DATE_CLMN_NAME + "=?, "
						+ this.AMOUNT_CLMN_NAME + "=?, "
						+ this.TYPE_CLMN_NAME + "=?, "
						+ this.MESSAGE_CLMN_NAME + "=?, "
						+ this.PRICE_CLMN_NAME + "=?, "
						+ this.IMAGE_CLMN_NAME + "=? "				
						+ " WHERE " + this.ID_CLMN_NAME + "=" + cpnId;
		
		//Creating a prepared SQL statement object.
		try(PreparedStatement prStmnt = cnctn.prepareStatement(sql);) {	
			//Setting the values from the given object into the SQL table.
			prStmnt.setString(1, cpn.getTitle()); //Coupon title in column 2.
			prStmnt.setDate(2, (Date) cpn.getStartDate()); //Coupon start date in column 3.
			prStmnt.setDate(3, (Date) cpn.getEndDate()); //Coupon end date in column 4.
			prStmnt.setInt(4, cpn.getAmount()); //Coupon amount in column 5.
			prStmnt.setString(5, cpn.getType().toString()); //Coupon type in column 6.
			prStmnt.setString(6, cpn.getMessage()); //Coupon start message in column 7.
			prStmnt.setFloat(7, (float)cpn.getPrice()); //Coupon price in column 8.
			prStmnt.setString(8, cpn.getImage()); //Coupon image in column 9.
			
			prStmnt.executeUpdate(); //Execution of the SQL statement.

		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to update coupon with ID:" + cpnId + " in table '" + this.CPN_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
	}

	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CouponDAO#getCoupon(long)
	 * Returns a coupon object from the coupon table as per a given coupon ID.
	 */
	@Override
	public Coupon getCoupon(long cpnId) throws CouponSystemException {
		//Check for valid coupon ID.
		if (cpnId <= 0) {
			throw new CouponSystemException("ERROR: Invalid coupon ID value: " + cpnId + ". Please enter a value greater than 0.");
		}
		
		//Converting the ID value type from long to string for simplifying the SQL commands composition. 
		String idStrng = Long.toString(cpnId);
		
		Coupon cpn = null; //For returning a coupon object.
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		//Get a coupon from the coupon table with a matching ID to the given ID.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CPN_TBL_NAME, this.ID_CLMN_NAME, idStrng, this.ID_CLMN_NAME);) {
			//Check if the result set is not empty.
			if(rsltSet.next()) {
				cpn = new Coupon();
				cpn.setId(cpnId);
				cpn.setTitle(rsltSet.getString(2)); //Title.
				cpn.setStartDate(rsltSet.getDate(3)); //Start date.
				cpn.setEndDate(rsltSet.getDate(4)); //End date
				cpn.setAmount(rsltSet.getInt(5)); //Amount.
				cpn.setType(CouponType.valueOf(rsltSet.getString(6))); //Type.
				cpn.setMessage(rsltSet.getString(7)); //Message.
				cpn.setPrice(rsltSet.getFloat(8)); //Price.
				cpn.setImage(rsltSet.getString(9)); //Image.
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to get coupon with ID: " + cpnId + " from table '" + this.CPN_TBL_NAME +"'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return cpn;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CouponDAO#getCouponsByTitle(java.lang.String)
	 * Returns a coupon object from the coupon table as per a given coupon title.
	 */
	@Override
	public Coupon getCouponsByTitle(String cpnTtl) throws CouponSystemException {
		//Check for invalid coupon title.
		if((cpnTtl == null) || (cpnTtl.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid coupon title. Please try again.");
		}
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();

		Coupon cpn = null; //For returning a coupon object.
		
		//Revising the coupon title string value for simplifying the SQL commands composition. 
		String cpnTtlStrng = "'" + cpnTtl.toString() + "'";
		
		//Getting a coupon from the coupon table with a matching title to the given title.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CPN_TBL_NAME, this.TITLE_CLMN_NAME, cpnTtlStrng, this.ID_CLMN_NAME);) {
			while(rsltSet.next()) {
				cpn = new Coupon();
				cpn.setId(rsltSet.getLong(1));
				cpn.setTitle(rsltSet.getString(2)); //Title.
				cpn.setStartDate(rsltSet.getDate(3)); //Start date.
				cpn.setEndDate(rsltSet.getDate(4)); //End date
				cpn.setAmount(rsltSet.getInt(5)); //Amount.
				cpn.setType(CouponType.valueOf(rsltSet.getString(6))); //Type.
				cpn.setMessage(rsltSet.getString(7)); //Message.
				cpn.setPrice(rsltSet.getFloat(8)); //Price.
				cpn.setImage(rsltSet.getString(9)); //Image.
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to retrieve coupon with the title '" + cpnTtlStrng + "' from table '" + this.CPN_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return cpn;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CouponDAO#getAllCoupons()
	 * Returns a collection of all the coupons in the coupon table.
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();

		Collection<Coupon> coupons = new HashSet<>(); //For returning a collection of coupons.
		
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTable(cnctn, this.CPN_TBL_NAME, this.ID_CLMN_NAME);) {
			while(rsltSet.next()) {
				Coupon cpn = new Coupon();
				cpn.setId(rsltSet.getLong(1));
				cpn.setTitle(rsltSet.getString(2)); //Title.
				cpn.setStartDate(rsltSet.getDate(3)); //Start date.
				cpn.setEndDate(rsltSet.getDate(4)); //End date
				cpn.setAmount(rsltSet.getInt(5)); //Amount.
				cpn.setType(CouponType.valueOf(rsltSet.getString(6))); //Type.
				cpn.setMessage(rsltSet.getString(7)); //Message.
				cpn.setPrice(rsltSet.getFloat(8)); //Price.
				cpn.setImage(rsltSet.getString(9)); //Image.
				coupons.add(cpn);
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to retrieve all coupons from table '" + this.CPN_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}

		return coupons;
	}
	
	//------------------------------------------------------------------

	/**
	 * @see jbt.com.cms.dao.CouponDAO#getCouponsByType(jbt.com.cms.beans.CouponType)
	 * Returns a collection of coupons objects from the coupon table as per a given coupon type.
	 */
	@Override
	public Collection<Coupon> getCouponsByType(CouponType cpnTyp) throws CouponSystemException {
		//Check for invalid coupon type.
		if((cpnTyp == null) || (cpnTyp.equals(""))) {
			throw new CouponSystemException("ERROR: Invalid coupon title. Please try again.");
		}
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		Collection<Coupon> coupons = new HashSet<>(); //For returning a collection of coupons.
		
		//Revising the coupon type string value for simplifying the SQL commands composition. 
		String cpnTypStrng = "'" + cpnTyp.toString() + "'";
		
		//Getting a coupon from the coupon table with a matching type to the given type.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CPN_TBL_NAME, this.TYPE_CLMN_NAME, cpnTypStrng, this.ID_CLMN_NAME);) {
			while(rsltSet.next()) {
				Coupon cpn = new Coupon();
				cpn.setId(rsltSet.getLong(1));
				cpn.setTitle(rsltSet.getString(2)); //Title.
				cpn.setStartDate(rsltSet.getDate(3)); //Start date.
				cpn.setEndDate(rsltSet.getDate(4)); //End date
				cpn.setAmount(rsltSet.getInt(5)); //Amount.
				cpn.setType(CouponType.valueOf(rsltSet.getString(6))); //Type.
				cpn.setMessage(rsltSet.getString(7)); //Message.
				cpn.setPrice(rsltSet.getFloat(8)); //Price.
				cpn.setImage(rsltSet.getString(9)); //Image.
				coupons.add(cpn);
			}
		
		} catch (SQLException e) {
			throw new CouponSystemException("WARNNING: Failed to retrieve coupons of the type '" + cpnTypStrng + "' from table '" + this.CPN_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return coupons;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CouponDAO#getCouponsByCompanyID(long)
	 * Returning a collection of coupons associated with a certain company as per the company's ID.
	 * Invokes the getCouponsIDsByCompanyID method from the CompanyCouponDAO class.
	 */
	@Override
	public Collection<Coupon> getCouponsByCompanyID(long cmpnyId) throws CouponSystemException {	
		//Check for valid company ID.
		if (cmpnyId <= 0) {
			throw new CouponSystemException("ERROR: Invalid company ID value: " + cmpnyId + ". Please enter a value greater than 0.");
		}
		
		Collection<Coupon> coupons = new HashSet<>(); //For returning a collection of coupons.
		
		CompanyCouponDAO cmpnyCpnDao = new CompanyCouponDBDAO(); //For getting the coupon ID from the Company Coupon table.
		
		//Getting all the coupons IDs associated wit the given company ID.
		Collection<Long> cpnsIds = cmpnyCpnDao.getCouponsIDsByCompanyID(cmpnyId);
		
		Iterator<Long> idsIt  = cpnsIds.iterator();
		
		while(idsIt.hasNext()) {
			coupons.add(this.getCoupon((long)idsIt.next())); //Adding a matching coupon to the collection.
		}
		
		return coupons;
	}
	
	//------------------------------------------------------------------

	/**
	 * @see jbt.com.cms.dao.CouponDAO#getCouponsByCustomerID(long)
	 * Returns a collection of coupons associated with a certain customers as per the customers's ID.
	 * Invokes the getCouponsIDsByCustomerID method from the CustomerCouponDAO class.
	 */
	@Override
	public Collection<Coupon> getCouponsByCustomerID(long cstmrId) throws CouponSystemException {
		//Check for valid customer ID.
		if (cstmrId <= 0) {
			throw new CouponSystemException("ERROR: Invalid customer ID value: " + cstmrId + ". Please enter a value greater than 0.");
		}
		
		Collection<Coupon> coupons = new HashSet<>(); //For returning a collection of coupons.
		
		CustomerCouponDAO cstmrCpnDao = new CustomerCouponDBDAO(); //For getting the coupon ID from the Customer Coupon table.
		
		//Getting all the coupons IDs associated wit the given customer ID.
		Collection<Long> cpnsIds = cstmrCpnDao.getCouponsIDsByCustomerID(cstmrId);
		
		Iterator<Long> it  = cpnsIds.iterator();
		
		while(it.hasNext()) {
			coupons.add(this.getCoupon((long)it.next()));
		}
		
		return coupons;
	}

	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CouponDAO#getCouponsByCompanyName(java.lang.String)
	 * Returns a collection of coupons associated with a certain company as per the company's name.
	 * Invokes the getCompanyByName method from the CompanyDAO class.
	 */
	@Override
	public Collection<Coupon> getCouponsByCompanyName(String cmpnyName) throws CouponSystemException {
		//Check for invalid company name.
		if((cmpnyName == null) || (cmpnyName.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid company name. Please try again.");
		}
		
		Collection<Coupon> coupons = new HashSet<>(); //For returning a collection of coupons.

		CompanyDAO cmpnyDao = new CompanyDBDAO(); //For getting the company from the company table.
		
		Company cmpny = cmpnyDao.getCompanyByName(cmpnyName);
		
		Long cmpnyId = cmpny.getId(); //Getting the company ID.
		
		coupons = this.getCouponsByCompanyID(cmpnyId); //Getting a collection of coupons by a company ID.
		
		return coupons;
	}
	
	//------------------------------------------------------------------

	/**
	 * @see jbt.com.cms.dao.CouponDAO#getCouponsByCustomerName(java.lang.String)
	 * Returns a collection of coupons associated with a certain customer as per the customer's name.
	 * Invokes the getCustomerByName method from the CustomerDAO class.
	 */
	@Override
	public Collection<Coupon> getCouponsByCustomerName(String cstmrName) throws CouponSystemException {
		//Check for invalid customer name.
		if((cstmrName == null) || (cstmrName.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid customer name. Please try again.");
		}
		
		Collection<Coupon> coupons = new HashSet<>(); //For returning a collection of coupons.

		CustomerDAO cstmrDao = new CustomerDBDAO(); //For getting the customer from the customer table.
		
		Customer cstmr = cstmrDao.getCustomerByName(cstmrName); //Getting the customer by the cusotmer's name
		
		Long cstmrId = cstmr.getId(); //Getting the customer's ID.
		
		coupons = this.getCouponsByCustomerID(cstmrId); //Getting a collection of coupons by a customer ID.
		
		return coupons;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CouponDAO#decrementCouponAmount(jbt.com.cms.beans.Coupon)
	 * Decrements a coupon's amount not below 0. 
	 */
	@Override
	public void decrementCouponAmount(Coupon cpn) throws CouponSystemException {
		//Check for valid coupon object.
		if(cpn == null) {
			throw new CouponSystemException("ERROR: Invalid coupon.");
		}
		
		long cpnId = cpn.getId(); //Getting the coupon ID.
		int cpnAmnt = cpn.getAmount(); //Getting the coupon amount.
		
		//Check if the coupon's amount is 0.
		if (cpnAmnt == 0) {
			throw new CouponSystemException("ERROR: The coupon '" + cpn.getTitle() + "' is out of stock. please buy another coupon.");
		} else {
			cpnAmnt--; //Decrementing the coupon's amount by 1.
			
			//Getting a connection reference from the connection pool.
			ConnectionPool cnctnPl =  ConnectionPool.getInstance();
			Connection cnctn = cnctnPl.getConnection();
			
			//Composition of an SQL statement for updating the coupon's amount in the coupon table.
			String sql = "UPDATE " + this.CPN_TBL_NAME + " SET " 
							+ this.AMOUNT_CLMN_NAME + "=?"
							+ " WHERE " + this.ID_CLMN_NAME + "=" + cpnId;
			
			//Creating a prepared SQL statement object.
			try(PreparedStatement prStmnt = cnctn.prepareStatement(sql);) {
				prStmnt.setInt(1, cpnAmnt); //Coupon amount in column 5.
				prStmnt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
			}
		}
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CouponDAO#checkIfCouponExists(jbt.com.cms.beans.Coupon)
	 * Returns true if a coupon with the given ID exists, false otherwise.
	 * Invokes the getCoupon method.
	 */
	@Override
	public boolean checkIfCouponExists (Coupon cpn) throws CouponSystemException {
		//Check for valid coupon object.
		if(cpn == null) {
			throw new CouponSystemException("ERROR: Invalid coupon.");
		}
		
		boolean existFlag = false;
		
		//Converting the ID value type from long to string for simplifying the SQL commands composition. 
		String cpnIdStrng = Long.toString(cpn.getId());
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		//Getting coupon from the coupon table by the coupon's ID.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CPN_TBL_NAME, this.ID_CLMN_NAME, cpnIdStrng, this.ID_CLMN_NAME);) {
			if(rsltSet.next()) {
				existFlag = true;
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to get coupon with ID: " + cpnIdStrng + " from table '" + this.CPN_TBL_NAME +"'." ,e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return existFlag;
	}
	
	//------------------------------------------------------------------
	//Utility methods
	
	public void showCouponTable() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		

		try(ResultSet rsltSet = SqlTableHandler.getAllFromTable(cnctn, this.CPN_TBL_NAME, this.ID_CLMN_NAME);) {	
			//Printing the Coupon table content.
			System.out.println("Shwoing the content of table '" + this.CPN_TBL_NAME + "'.");
			
			System.out.println("-----------------------------------------"+ this.CPN_TBL_NAME +"----------------------------------------------");
			//Printing the Coupon table columns names.
			System.out.print("---------------------------------------------------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------------------------------------------------");
			//System.out.println("|"
			System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s",
								"|" + this.ID_CLMN_NAME, 
								"|" + this.TITLE_CLMN_NAME, 
								"|" + this.START_DATE_CLMN_NAME,
								"|" + this.END_DATE_CLMN_NAME,
								"|" + this.AMOUNT_CLMN_NAME,
								"|" + this.TYPE_CLMN_NAME,
								"|" + this.MESSAGE_CLMN_NAME,
								"|" + this.PRICE_CLMN_NAME,
								"|" + this.IMAGE_CLMN_NAME);
			System.out.print("|\n");
			System.out.print("---------------------------------------------------------------------------------------------------------------");
			System.out.println("-----------------------------------------------------------------------------------------------------------------");
			
			//Printing the Coupon table rows.
			while(rsltSet.next()) {
				System.out.printf("%-20.20s%-20.20s%-20.20s%-20.20s%-20.20s%-20.20s%-20.20s%-20.20s%-20.20s",
									"|" + rsltSet.getInt(1), //ID.
									"|" + rsltSet.getString(2), //Title.
									"|" + rsltSet.getDate(3), //Start date.
									"|" + rsltSet.getDate(4), //End date
									"|" + rsltSet.getInt(5), //Amount.
									"|" + rsltSet.getString(6), //Type.
									"|" + rsltSet.getString(7), //Message.
									"|" + rsltSet.getFloat(8), //Price.
									"|" + rsltSet.getString(9)); //Image.
				System.out.print("|\n");
			}
			
			System.out.println("-----------------------------------------END OF "+ this.CPN_TBL_NAME +"----------------------------------------------");

		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed retireieving table '" + this.CPN_TBL_NAME + "' from the database.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
	}
	
	//------------------------------------------------------------------
	
	public void createCouponTable() throws CouponSystemException {
		//Composition of an SQL statement for creating the Coupon table in a specified format.
		String sql = "CREATE TABLE " + this.CPN_TBL_NAME + "("
						+ this.ID_CLMN_NAME + " BIGINT PRIMARY KEY,"
						+ this.TITLE_CLMN_NAME + " VARCHAR(255),"
						+ this.START_DATE_CLMN_NAME + " DATE,"
						+ this.END_DATE_CLMN_NAME + " DATE,"
						+ this.AMOUNT_CLMN_NAME + " INT,"
						+ this.TYPE_CLMN_NAME + " VARCHAR(255),"
						+ this.MESSAGE_CLMN_NAME + " VARCHAR(255),"
						+ this.PRICE_CLMN_NAME + " FLOAT,"
						+ this.IMAGE_CLMN_NAME + " VARCHAR(255)"
						+ ")";
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		SqlTableHandler.createSqlTable(cnctn, sql, this.CPN_TBL_NAME);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	//------------------------------------------------------------------
	
	public void dropCouponTable() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		SqlTableHandler.dropTable(cnctn, this.CPN_TBL_NAME);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	
	//END OF METHODS
	//******************************************************************
	
	
	//END OF CouponDBDAO
}
