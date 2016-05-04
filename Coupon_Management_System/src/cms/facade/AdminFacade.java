package cms.facade;

import java.util.Collection;
import java.util.Iterator;

import cms.beans.Company;
import cms.beans.Customer;
import cms.dao.CompanyCouponDAO;
import cms.dao.CompanyDAO;
import cms.dao.CouponDAO;
import cms.dao.CustomerCouponDAO;
import cms.dao.CustomerDAO;
import cms.dbdao.CompanyCouponDBDAO;
import cms.dbdao.CompanyDBDAO;
import cms.dbdao.CouponDBDAO;
import cms.dbdao.CustomerCouponDBDAO;
import cms.dbdao.CustomerDBDAO;
import cms.main.CouponSystemException;

public class AdminFacade implements CouponClientFacade {
	//******************************************************************
	//ATTRIBUTES
	
	private final String ADMIN_USER_NAME = "admin";
	private final String ADMIN_PSWD = "1234";

	private CompanyDAO cmpnyDao = new CompanyDBDAO();
	private CustomerDAO cstmrDao = new CustomerDBDAO();
	private CouponDAO cpnDao = new CouponDBDAO();
	private CompanyCouponDAO cmpnyCpnDao = new CompanyCouponDBDAO();
	private CustomerCouponDAO cstmrCpnDao = new CustomerCouponDBDAO();
	
	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR
	
	
	//END OF CTOR
	//******************************************************************

	//******************************************************************
	//METHODS
	
	/**
	 * @param cmpny
	 * @throws CouponSystemException
	 * Inserts a new company to the Company table as per a given company object.
	 * invokes the createtCompany from the CompanyDBDAO class.
	 */
	public long createCompany(Company cmpny) throws CouponSystemException {
		//Check for valid company.
		if(cmpny == null) {
			throw new CouponSystemException("ERROR: Invalid company. Please try again.");
		}
		
		//Resetting the company ID to 0 so that when passing through validity check, the ID value won't match any other ID in the Company table.
		cmpny.setId(0);  
		
		this.checkCompanyValidity(cmpny); //Checks company validity for insertion to the company table.
		
		return this.cmpnyDao.createtCompany(cmpny); //Creating a company and returning the generated ID. 
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cmpny
	 * @throws CouponSystemException
	 * Removes a company from the company table as per a given company object.
	 * Also deletes all of the coupons owned by the deleted company from all relevant tables.
	 */
	public void removeCompany(Company cmpny) throws CouponSystemException {
		//Check for valid company.
		if(cmpny == null) {
			throw new CouponSystemException("ERROR: Invalid company. Please try again.");
		}
				
		long cmpnyId = cmpny.getId();
		//Getting all the company's coupons IDs.
		Collection<Long> cpnsId = this.cmpnyCpnDao.getCouponsIDsByCompanyID(cmpnyId);
		Iterator<Long> cpnsIdIt = cpnsId.iterator();
		
		long cpnId;
		
		while(cpnsIdIt.hasNext()) {
			cpnId = cpnsIdIt.next(); //Getting a single coupon ID.
			this.cstmrCpnDao.deleteRowsByCouponID(cpnId); //Deleting a coupon and its associative customer IDs from the customer coupon table.
			this.cpnDao.removeCoupon(this.cpnDao.getCoupon(cpnId)); //deleting a coupon from the coupon table.
		}
		
		//Deleting a company and its associative customer IDs from the company coupon table.
		this.cmpnyCpnDao.deleteCouponsByCompanyId(cmpnyId);
		
		this.cmpnyDao.removeCompany(cmpny); //Deleting the company from the company table.
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cmpny
	 * @throws CouponSystemException
	 * Updates a company in the company table.
	 */
	public void updateCompany(Company cmpny) throws CouponSystemException {
		this.checkCompanyValidity(cmpny); //Checks company validity for update in the company table.

		this.cmpnyDao.updateCompany(cmpny);
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cmpnyId
	 * @return A Company object from the company table as per a company's ID.
	 * @throws CouponSystemException
	 */
	public Company getCompany(long cmpnyId) throws CouponSystemException {
		//Check for invalid company ID value.
		if(cmpnyId <= 0) {
			throw new CouponSystemException("ERROR: Invalid company ID value : " + cmpnyId
											+ " . Please enter a value greater than 0.");
		}
		
		Company cmpny = this.cmpnyDao.getCompany(cmpnyId);
		
		//Check if the returned company is null.
		if(cmpny == null) {
			throw new CouponSystemException("ERROR: No company with ID: " + cmpnyId + " was found in the database.");
		}
		
		return cmpny;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @return A collection of Company objects of all the companies from the company table.
	 * @throws CouponSystemException
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		return cmpnyDao.getAllCompanies();
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cstmr
	 * @throws CouponSystemException
	 * Inserts a new customer to the Customer table as per a given customer object.
	 */
	public long createCustomer(Customer cstmr) throws CouponSystemException {
		//Check for valid customer.
		if(cstmr == null) {
			throw new CouponSystemException("ERROR: Invalid customer. Please try again.");
		}
		
		//Resetting the customer ID to 0 so that when passing through validity check, the ID value won't match any other ID in the Customer table.
		cstmr.setId(0);  
		
		this.checkCustomerValidity(cstmr); //Checks customer validity for insertion to the customer table.
		
		return this.cstmrDao.createCustomer(cstmr); //Creating a customer and returning its generated ID. 
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cstmr
	 * @throws CouponSystemException
	 * Removes a customer from the customer table and deleted all his coupons from
	 * the customer coupon table.
	 */
	public void removeCustomer(Customer cstmr) throws CouponSystemException {
		//Check for valid customer.
		if(cstmr == null) {
			throw new CouponSystemException("ERROR: Invalid customer. Please try again.");
		}
				
		long csmtrId = cstmr.getId();
		
		this.cstmrCpnDao.deleteRowsByCustomerID(csmtrId); //Deletes all rows with the coupons owned by the customer.
		
		this.cstmrDao.removeCustomer(cstmr); //Deltes the customer from the customer table.
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cstmr
	 * @throws CouponSystemException
	 * Updates a customer in the customer table as per a given customer object.
	 */
	public void updateCustomer(Customer cstmr) throws CouponSystemException {
		this.checkCustomerValidity(cstmr); //Checks customer validity for update in the customer table.
		
		this.cstmrDao.updateCustomer(cstmr);
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cstmrId
	 * @return A Customer object from the customer table as per a given customer ID.
	 * @throws CouponSystemException
	 */
	public Customer getCustomer(long cstmrId) throws CouponSystemException {
		//Check for valid customer ID value.
		if(cstmrId <= 0) {
			throw new CouponSystemException("ERROR: Invalid customer ID value : " + cstmrId + " . Please enter a value greater than 0.");
		}
		
		Customer cstmr = this.cstmrDao.getCustomer(cstmrId);
		
		//Check if the returned company is null.
		if(cstmr == null) {
			throw new CouponSystemException("ERROR: No customer with ID: " + cstmrId + " was found in the database.");
		}
		
		return this.cstmrDao.getCustomer(cstmrId);
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @return A collection of Customer objects of all the customers in the customer table.
	 * @throws CouponSystemException
	 */
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		return this.cstmrDao.getAllCustomers();
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.facade.CouponClientFacade#login(java.lang.String, java.lang.String)
	 * Returns true if a correct password has been entered for an admin user, false otherwise. 
	 */
	public boolean login(String userName, String pswd) throws CouponSystemException {
		//Check for valid user name.
		if((userName == null) || (userName.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid user name. Please try again.");
		}
		
		//Check for valid password.
		if((pswd == null) || (pswd.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid password. Please try again.");
		}
		
		boolean accessGranted = false; //For returning boolean.
		
		if(!userName.equals(this.ADMIN_USER_NAME)) { //User name check.
			throw new CouponSystemException("ERROR: Administrator with the user name: '" + userName + "' does not exist.");
		} else if(!pswd.equals(this.ADMIN_PSWD)) { //Password check.
			throw new CouponSystemException("ERROR: Incorrect password for administrator '" + userName + "'. Please try again.");
		} else {
			accessGranted = true; //Correct password has been entered.
		}
		
		return accessGranted;
	}

	//------------------------------------------------------------------
	
	/**
	 * @param cmpny
	 * @throws CouponSystemException
	 * Checks for a company's validity for table insertion/update in terms of name and email.
	 * Throws exception if invalid.
	 */
	public void checkCompanyValidity(Company cmpny) throws CouponSystemException {
		int minNameLngth = 2; //Minimum name length.
		int minPswdLngth = 4; //Minimum password length.
		int minEmailLngth = 5; //Minimum Email length.

		//Check for valid company.
		if(cmpny == null) {
			throw new CouponSystemException("ERROR: Invalid company. Please try again.");
		//Check for valid company name.
		} else if(cmpny.getCompName() == null || cmpny.getCompName().trim().length() < minNameLngth) {
			throw new CouponSystemException("ERROR: Invalid company name. Please enter a name with at least " + minNameLngth + " characters.");
		//Check for valid company password.
		} else if(cmpny.getPassword() == null || cmpny.getPassword().trim().length() < minPswdLngth) {
			throw new CouponSystemException("ERROR: Invalid company password. Please enter a password with at least " + minPswdLngth + " characters.");
		//Check for valid company email.
		} else if(cmpny.getEmail() == null || cmpny.getEmail().trim().length() < minEmailLngth || cmpny.getEmail().contains(" ") || !cmpny.getEmail().contains("@")) {
			throw new CouponSystemException("ERROR: Invalid company Email. Please enter an Email with at least " + minEmailLngth + " characters, no spaces and a '@'.");
		}
		
		long cmpnyId = cmpny.getId();
		
		//Getting all companies in the Company table.
		Collection<Company> companies = this.getAllCompanies();
		Iterator<Company> cmpnyIt  = companies.iterator();
				
		//Getting the company name and Email for checking if one of them already exists for another company in the Company table.
		String cmpnyName = cmpny.getCompName();
		String cmpnyEmail = cmpny.getEmail();
		
		//For getting the names and Email's of the companies which are already in the Company table.
		String cmpnyFromTblName = null;
		String cmpnyFromTblEmail = null;
		long cmpnyFromTblId = 0;
		
		//Iterating through all the companies from the Company table.
		while(cmpnyIt.hasNext()) {
			Company cmpnyFromTbl = cmpnyIt.next(); //Getting a company from the Company table.
			
			//Getting the name and Email of the handled company from the Company table.
			cmpnyFromTblName = cmpnyFromTbl.getCompName();
			cmpnyFromTblEmail = cmpnyFromTbl.getEmail();
			cmpnyFromTblId = cmpnyFromTbl.getId();
			
			//Check for existing company name.
			if ((cmpnyName.equalsIgnoreCase(cmpnyFromTblName)) && (cmpnyId != cmpnyFromTblId)) {
				throw new CouponSystemException("ERROR: A company with the name '" + cmpnyFromTblName 
												+ "' already exists in the data base. " 
												+ "Please enter a company with a different name.");
			
			//Check for existing Email.
			} else if((cmpnyEmail.equalsIgnoreCase(cmpnyFromTblEmail)) && (cmpnyId != cmpnyFromTbl.getId())) {
				throw new CouponSystemException("ERROR: The Email address '" + cmpnyFromTblEmail 
												+ "' is already listed for another company in the data base. " 
												+ "Please enter a different Email address.");
			}
		}	
	}

	//------------------------------------------------------------------
	
	/**
	 * @param cstmr
	 * @throws CouponSystemException
	 * Checks for a customer's validity for table insertion/update in terms of name.
	 * Throws exception if invalid.
	 */
	public void checkCustomerValidity(Customer cstmr) throws CouponSystemException {
		int minNameLngth = 3; //Minimum name length.
		int minPswdLngth = 4; //Minimum password length.
		
		//Check for valid customer.
		if(cstmr == null) {
			throw new CouponSystemException("ERROR: Invalid customer. Please try again.");
		//Check for valid customer name.
		} else if(cstmr.getCustName() == null || cstmr.getCustName().trim().length() < minNameLngth) {
			throw new CouponSystemException("ERROR: Invalid customer name. Please enter a name with at least " + minNameLngth + " characters.");
		//Check for valid customer password.
		} else if(cstmr.getPassword() == null || cstmr.getPassword().trim().length() < minPswdLngth) {
			throw new CouponSystemException("ERROR: Invalid customer password. Please enter a password with at least " + minPswdLngth + " characters.");
		}
		
		long cstmrId = cstmr.getId();
		String cstmrName = cstmr.getCustName();
		Collection<Customer> customers = this.getAllCustomers();
		
		Iterator<Customer> cstmrIt  = customers.iterator();
		
		//Getting the name of the handled customer from the Customer table.
		Customer cstmrFromTbl = null;
		String cstmrFromTblName = null;
		long cstmrFromTblId = 0;
		
		//Iterating through all the customers from the Customer table.
		while(cstmrIt.hasNext()) {
			cstmrFromTbl = cstmrIt.next();
			cstmrFromTblName = cstmrFromTbl.getCustName();
			cstmrFromTblId = cstmrFromTbl.getId();
			
			//Check for existing customer name.
			if ((cstmrName.equalsIgnoreCase(cstmrFromTblName)) && (cstmrId != cstmrFromTblId)) {
				throw new CouponSystemException("ERROR: The user name '" + cstmrName 
												+ "' already exists for another customer in the data base. " 
												+ "Please enter a different user name.");
			}
		}
	}
	
	
	//END OF METHODS
	//******************************************************************

	
	//END OF AdminFacade
}
