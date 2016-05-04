package cms.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import cms.beans.Company;
import cms.beans.Coupon;
import cms.dao.CompanyCouponDAO;
import cms.dao.CompanyDAO;
import cms.dao.CouponDAO;
import cms.main.CouponSystemException;
import cms.utility.ConnectionPool;
import cms.utility.SqlTableHandler;


public class CompanyDBDAO implements CompanyDAO {
	//******************************************************************
	//ATTRIBUTES
	
	private final String CMPNY_TBL_NAME = "Company_Table"; //Company table name.

	//Company table columns names.
	private final String ID_CLMN_NAME = "ID"; //Company ID column name.
	private final String CMPNY_NM_CLMN_NAME = "Company_Name"; //Company name column name.
	private final String PSWD_CLMN_NAME = "Password"; //Company password column name.
	private final String EML_CLMN_NAME = "Email"; //Company email column name.

	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR

	
	//END OF CTOR	
	//******************************************************************

	//******************************************************************
	//METHODS

	/**
	 * @see jbt.com.cms.dao.CompanyDAO#createtCompany(jbt.com.cms.beans.Company)
	 * Inserting a new company into the Company table as per a given company object.
	 */
	@Override
	public long createtCompany(Company cmpny) throws CouponSystemException {
		//Check for valid company.
		if(cmpny == null) {
			throw new CouponSystemException("ERROR: Invalid company.");
		}
		
		long cmpnyId = 0L; //For returning the generated ID of the company. 

		//Check if given company name and Email already exist.
		Collection<Company> companies = this.getAllCompanies(); //Getting all companies in the Company table.
		Iterator<Company> it  = companies.iterator();
				
		//Getting the company name and Email for checking if one of them already exists for another company in the Company table.
		String cmpnyName = cmpny.getCompName();
		String cmpnyEmail = cmpny.getEmail();
		
		//For getting the names and Email's of the companies which are already in the Company table.
		String cmpnyFromTblName = null;
		String cmpnyFromTblEmail = null;
		
		//Iterating through all the companies from the Company table.
		while(it.hasNext()) {
			Company cmpnyFromTbl = it.next(); //Getting a company from the Company table.
			
			//Getting the name and Email of the handled company from the Company table.
			cmpnyFromTblName = cmpnyFromTbl.getCompName();
			cmpnyFromTblEmail = cmpnyFromTbl.getEmail();
			
			if (cmpnyName.equalsIgnoreCase(cmpnyFromTblName)) {
				throw new CouponSystemException("ERROR: A company with the name '" + cmpnyFromTblName 
												+ "' already exists in the table '" + this.CMPNY_TBL_NAME + "'. " 
												+ "Please enter a company with a different name.");
				
			} else if(cmpnyEmail.equalsIgnoreCase(cmpnyFromTblEmail)) {
				throw new CouponSystemException("ERROR: The Email address '" + cmpnyFromTblEmail 
												+ "' already exists in the table '" + this.CMPNY_TBL_NAME + "' for another company. " 
												+ "Please enter a different Email address.");
			}
		}
		
		//Composition of an SQL statement for inserting a row to the Company table.
		String sql = "INSERT INTO " + this.CMPNY_TBL_NAME + " VALUES(?,?,?,?)";
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();

		//Creating a prepared SQL statement object.
		try(PreparedStatement prStmnt = cnctn.prepareStatement(sql);) {
			//Generating new company ID:
			cmpnyId = SqlTableHandler.generateRowId(cnctn, this.CMPNY_TBL_NAME, this.ID_CLMN_NAME);
			
			prStmnt.setLong(1, cmpnyId); //Company ID in column 1.
			prStmnt.setString(2, cmpny.getCompName()); //Company name in column 2.
			prStmnt.setString(3, cmpny.getPassword()); //Company password in column 3.
			prStmnt.setString(4, cmpny.getEmail()); //Company email in column 4.
			
			//Execution of the SQL statement & storing its return value.
			prStmnt.executeUpdate();
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to insert new company into table '" + this.CMPNY_TBL_NAME +"'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return cmpnyId;
	}

	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyDAO#removeCompany(jbt.com.cms.beans.Company)
	 * Deletes a company from the Company table as per a given company object.
	 * Invokes the deleteFromTable method from the class SqlTableHandler.
	 */
	@Override
	public void removeCompany(Company cmpny) throws CouponSystemException {
		//Check for valid company.
		if(cmpny == null) {
			throw new CouponSystemException("ERROR: Invalid company.");
		}
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		//Invoking the 'deleteFromTable' method.
		SqlTableHandler.deleteFromTable(cnctn, this.CMPNY_TBL_NAME, this.ID_CLMN_NAME, cmpny.getId());
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyDAO#updateCompany(jbt.com.cms.beans.Company)
	 * Updates a company's password and/or Email in the Company table.
	 */
	@Override
	public void updateCompany(Company cmpny) throws CouponSystemException {
		//Check for valid company.
		if(cmpny == null) {
			throw new CouponSystemException("ERROR: Invalid company.");
		}
		
		long cmpnyId = cmpny.getId();
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();

		//Composition of an SQL statement for updating a company in the Company table.
		String sql = "UPDATE " + this.CMPNY_TBL_NAME + " SET " 
				+ this.PSWD_CLMN_NAME + "=?, "
				+ this.EML_CLMN_NAME + "=? "	
				+ " WHERE " + this.ID_CLMN_NAME + "=" + cmpnyId;

		//Creating a prepared SQL statement object.
		try(PreparedStatement prStmnt = cnctn.prepareStatement(sql);) {	
			//Setting the values from the given object into the SQL table.
			prStmnt.setString(1, cmpny.getPassword()); //Password in column 3.
			prStmnt.setString(2, cmpny.getEmail()); //Email in column 4.

			prStmnt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to update company with ID:" + cmpnyId + " in table '" + this.CMPNY_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyDAO#getCompany(long)
	 * Returns a company object from the Company table as per a given company ID.
	 */
	@Override
	public Company getCompany(long cmpnyId) throws CouponSystemException {
		//Check for valid company ID value.
		if(cmpnyId <= 0) {
			throw new CouponSystemException("ERROR: Invalid company ID value : " + cmpnyId + " . Please enter a value greater than 0.");
		}
		
		//Converting the ID value type from long to string for simplifying the SQL commands composition. 
		String idStrng = Long.toString(cmpnyId);
		
		Company cmpny = null; //For returning a company object.
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		//Getting the company from the Company table as per the given company ID.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CMPNY_TBL_NAME, this.ID_CLMN_NAME, idStrng, this.ID_CLMN_NAME);) {
			//Check if the result set is not empty.
			if(rsltSet.next()) {
				cmpny = new Company();
				cmpny.setId(cmpnyId); //ID.
				cmpny.setCompName(rsltSet.getString(2)); //Company name.
				cmpny.setPassword(rsltSet.getString(3)); //Password.
				cmpny.setEmail(rsltSet.getString(4)); //Email.
				cmpny.setCoupons(this.getCouponsByCompanyID(cmpnyId)); //Coupons.
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to get company with ID: " + cmpnyId + " from table '" + this.CMPNY_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
		
		return cmpny;
	}
	
	//------------------------------------------------------------------

	/**
	 * @see jbt.com.cms.dao.CompanyDAO#getCompanyByName(java.lang.String)
	 * Returns a company object from the Company table as per a given company name.
	 */
	@Override
	public Company getCompanyByName(String cmpnyName) throws CouponSystemException {
		//Check for invalid company name.
		if((cmpnyName == null) || (cmpnyName.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid company name. Please try again.");
		}
		
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		Company cmpny = null; //For returning a company object.

		//Revising the company name string value for simplifying the SQL commands composition. 
		String cmpnyNameSql = "'" + cmpnyName + "'";
		
		//Getting the company from the Company table as per the given company name.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTableByCoulmnValue(cnctn, this.CMPNY_TBL_NAME, this.CMPNY_NM_CLMN_NAME, cmpnyNameSql, this.ID_CLMN_NAME);) {
			//Check if the result set is not empty.
			if(rsltSet.next()) {
				cmpny = new Company();
				cmpny.setId(rsltSet.getLong(1)); //ID.
				cmpny.setCompName(cmpnyName); //Company name.
				cmpny.setPassword(rsltSet.getString(3)); //Password.
				cmpny.setEmail(rsltSet.getString(4)); //Email.
				cmpny.setCoupons(this.getCouponsByCompanyID(rsltSet.getLong(1))); //Coupons.
			}
		
		} catch (SQLException e) {
			throw new CouponSystemException("WARNNING: Failed to retrieve a comany with the name '" + cmpnyName + "' from table '" + this.CMPNY_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}

		return cmpny;
	}

	//------------------------------------------------------------------

	/**
	 * @see jbt.com.cms.dao.CompanyDAO#getCompanyByCouponID(long)
	 * Returning a company object as per a given coupon ID.
	 * Invokes the getCompanyIDByCouponID method from the CompanyCouponDAO.
	 */
	@Override
	public Company getCompanyByCouponID(long cpnId) throws CouponSystemException {
		//Check for valid coupon ID value.
		if(cpnId <= 0) {
			throw new CouponSystemException("ERROR: Invalid coupon ID value : " + cpnId + " . Please enter a value greater than 0.");
		}
		
		Company cmpny = null; //For returning a company object.
		
		CompanyCouponDAO cmpnyCpnDao = new CompanyCouponDBDAO(); //For getting the company ID from the Company Coupon table.
		
		long cmpnyId = cmpnyCpnDao.getCompanyIDByCouponID(cpnId);
		
		cmpny = this.getCompany(cmpnyId);

		return cmpny;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyDAO#getAllCompanies()
	 * Returning a collection of all the companies in the company table.
	 */
	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();

		Collection<Company> companies = new HashSet<>(); //For returning a company collection.
				
		Company cmpny = null;
		
		//Getting all companies from the Company table.
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTable(cnctn, this.CMPNY_TBL_NAME, this.ID_CLMN_NAME);) {
			while(rsltSet.next()) {
				cmpny = new Company();
				cmpny.setId((Long)rsltSet.getLong(1)); //ID.
				cmpny.setCompName(rsltSet.getString(2)); //Company name.
				cmpny.setPassword(rsltSet.getString(3)); //Password.
				cmpny.setEmail(rsltSet.getString(4)); //Email.
				cmpny.setCoupons(this.getCouponsByCompanyID(rsltSet.getLong(1))); //Coupons.

				companies.add(cmpny);
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to retrieve all companies from table '" + this.CMPNY_TBL_NAME + "'.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}

		return companies;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyDAO#getCouponsByCompanyID(long)
	 * Returning a collection of all the coupons associated with a company as per its given ID.
	 * Invokes the getCouponsByCompanyID method from the CouponDAO.
	 */
	@Override
	public Collection<Coupon> getCouponsByCompanyID(long cmpnyId) throws CouponSystemException {
		//Check for valid company ID value.
		if(cmpnyId <= 0) {
			throw new CouponSystemException("ERROR: Invalid company ID value : " + cmpnyId + " . Please enter a value greater than 0.");
		}
				
		CouponDAO cpnDao = new CouponDBDAO(); //For getting all coupons associated with the company ID.
		
		Collection<Coupon> coupons = cpnDao.getCouponsByCompanyID(cmpnyId);
		
		return coupons;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.dao.CompanyDAO#checkIfCompanyExistsInTable(long)
	 * Returns true if a company with the given ID exists, false otherwise.
	 * Invokes the getCompany method.
	 */
	@Override
	public boolean checkIfCompanyExistsInTable (long cmpnyId) throws CouponSystemException {
		if (this.getCompany(cmpnyId) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	//------------------------------------------------------------------
	//Utility Methods:
	
	public void showCompanyTable() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		try(ResultSet rsltSet = SqlTableHandler.getAllFromTable(cnctn, this.CMPNY_TBL_NAME, this.ID_CLMN_NAME);) {	
			//Printing the Company table content.
			System.out.println("Shwoing the content of '" + this.CMPNY_TBL_NAME + "':");
			
			System.out.println("------------------------"+ this.CMPNY_TBL_NAME +"-----------------------------------------");
			//Printing the Company table columns names.
			System.out.println("------------------------------------------------------------------------------------------");
			System.out.printf("%-20s%-20s%-20s%-20s", 
								"|" + this.ID_CLMN_NAME, 
								"|" + this.CMPNY_NM_CLMN_NAME, 
								"|" + this.PSWD_CLMN_NAME,
								"|" + this.EML_CLMN_NAME);
			System.out.print("|\n");
			System.out.println("-----------------------------------------------------------------------------------------------------------------");

			//Printing the Company table rows.
			while(rsltSet.next()) {
				System.out.printf("%-20s%-20s%-20s%-20s",
									"|" + rsltSet.getLong(1),
									"|" + rsltSet.getString(2),
									"|" + rsltSet.getString(3),
									"|" + rsltSet.getString(4) );
				System.out.print("|\n");
			}
			
			System.out.println("-----------------------------------------END OF "+ this.CMPNY_TBL_NAME +"----------------------------------------------");

		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed retireieving table '" + this.CMPNY_TBL_NAME + "' from the database.", e);
		} finally {
			cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
		}
	}
	
	//------------------------------------------------------------------
	
	public void createCompanyTable() throws CouponSystemException {
		//Composition of an SQL statement for creating the Coupon table in a specified format.
		String sql = "CREATE TABLE " + this.CMPNY_TBL_NAME + "("
						+ this.ID_CLMN_NAME + " BIGINT PRIMARY KEY,"
						+ this.CMPNY_NM_CLMN_NAME + " VARCHAR(255),"
						+ this.PSWD_CLMN_NAME + " VARCHAR(255),"
						+ this.EML_CLMN_NAME + " VARCHAR(255))";
				
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		SqlTableHandler.createSqlTable(cnctn, sql, this.CMPNY_TBL_NAME);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	//------------------------------------------------------------------

	public void dropCompanyTable() throws CouponSystemException {
		//Getting a connection reference from the connection pool.
		ConnectionPool cnctnPl =  ConnectionPool.getInstance();
		Connection cnctn = cnctnPl.getConnection();
		
		SqlTableHandler.dropTable(cnctn, this.CMPNY_TBL_NAME);
		
		cnctnPl.returnConnection(cnctn); //Returning the connection reference to the connection pool.
	}
	
	//------------------------------------------------------------------
	
	public String getCMPNY_TBL_NAME() {
		return this.CMPNY_TBL_NAME;
	}
	
	
	//END OF METHODS
	//******************************************************************

	
	//END OF CompanyDBDAO
}