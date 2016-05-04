package cms.facade;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import cms.beans.Company;
import cms.beans.Coupon;
import cms.beans.CouponType;
import cms.dao.CompanyCouponDAO;
import cms.dao.CompanyDAO;
import cms.dao.CouponDAO;
import cms.dao.CustomerCouponDAO;
import cms.dbdao.CompanyCouponDBDAO;
import cms.dbdao.CompanyDBDAO;
import cms.dbdao.CouponDBDAO;
import cms.dbdao.CustomerCouponDBDAO;
import cms.main.CouponSystemException;

public class CompanyFacade implements CouponClientFacade {
	//******************************************************************
	//ATTRIBUTES
	
	private long cmpnyId;
	private String cmpnyName;

	private CompanyDAO cmpnyDao = new CompanyDBDAO();
	private CouponDAO cpnDao = new CouponDBDAO();
	private CompanyCouponDAO cmpnyCpnDao = new CompanyCouponDBDAO();
	private CustomerCouponDAO cstmrCpnDao = new CustomerCouponDBDAO();
	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR
	
	public CompanyFacade() {
		super();
		this.cmpnyId = 0;
		this.cmpnyName = null;
	}
	
	public CompanyFacade(long cmpnyId, String cmpnyName) {
		super();
		this.cmpnyId = cmpnyId;
		this.cmpnyName = cmpnyName;
	}
	
	
	//END OF CTOR
	//******************************************************************

	//******************************************************************
	//METHODS
	
	/**
	 * @param cpn
	 * @throws CouponSystemException
	 * Creates a coupon in the coupon table as per a given coupon object.
	 */
	public long createCoupon(Coupon cpn) throws CouponSystemException {
		if(cpn == null) {
			throw new CouponSystemException("ERROR: Invalid coupon.");
		}
		
		//Resetting the coupon ID to 0 so that when passing through validity check, the ID value won't match any other ID in the Coupon table.
		cpn.setId(0);
		
		//Checks validity of title, expiration date range and price. Throws an exception if not valid.
		this.checkCouponValidity(cpn);
		
		int cpnAmount = cpn.getAmount();
		
		//Checking for valid amount upon coupon creation - if not greater then 0, throws an exception.
		if(cpnAmount <= 0) {
			throw new CouponSystemException("ERROR: Invalid coupon amount: " + cpnAmount 
											+ ". Please enter amount value greater than 0.");
		}
		
		long cpnId = cpnDao.createCoupon(cpn); //Inserting the coupon to the Coupon table.
		
		//Inserting a new row in the Company Coupon table associating the company with the handled coupon.
		this.cmpnyCpnDao.insertRow(this.cmpnyId, cpnId);
		
		return cpnId;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpn
	 * @throws CouponSystemException
	 * Removes a coupon from the coupon table as per a given coupon object
	 * and deletes all rows with the coupon ID from the customer coupon table
	 * and the company coupon table.
	 */
	public Coupon removeCoupon(Coupon cpn) throws CouponSystemException {
		//Check for valid coupon.
		if(cpn == null) {
			throw new CouponSystemException("ERROR: Invalid coupon.");
		}
		
		long cpnId = cpn.getId();

		checkForCouponOwnership(cpnId); //Check if the coupon is owned by the company.

		this.cpnDao.removeCoupon(cpn); //Deletes the coupon from the coupon table.
		this.cmpnyCpnDao.deleteCoupon(cpnId); //Deletes coupon from the company coupon table.
		this.cstmrCpnDao.deleteRowsByCouponID(cpnId); //Deletes coupon from the customer coupon table.
		
		return cpn;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpn
	 * @throws CouponSystemException
	 * Updated a coupon in the coupon table
	 */
	public Coupon updateCoupon(Coupon cpn) throws CouponSystemException {
		//Check for valid coupon.
		if(cpn == null) {
			throw new CouponSystemException("ERROR: Invalid coupon.");
		}
		
		checkForCouponOwnership(cpn.getId()); //Check if the coupon is owned by the company.
		
		this.checkCouponValidity(cpn);
		
		int cpnAmount = cpn.getAmount();
		
		//Checking for valid amount upon coupon update:
		if(cpnAmount < 0) {
			throw new CouponSystemException("ERROR: Invalid coupon amount value: " + cpnAmount 
											+ ". Please enter a non negative amount value.");
		}
		
		cpnDao.updateCoupon(cpn);
		
		return this.cpnDao.getCoupon(cpn.getId()); //Returns the updated coupon from the table. 
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpnId
	 * @return A Coupon object from the coupon table associates with the company as per a given coupon ID.
	 * @throws CouponSystemException
	 */
	public Coupon getCoupon(long cpnId) throws CouponSystemException {
		if(cpnId <= 0) {
			throw new CouponSystemException("ERROR: Invalid ID value: " + cpnId + ". Please enter a value greater than 0.");
		}
		
		checkForCouponOwnership(cpnId); //Check if the coupon is owned by the company.
		
		Coupon cpn = this.cpnDao.getCoupon(cpnId);
		
		//Check if the returned coupon is null.
		if (cpn == null) {
			throw new CouponSystemException("ERROR: No coupom with ID: " + cpnId + " was found in the database.");
		}
		
		return cpn;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @return A collection of Coupon object of all the coupons associated with the company.
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		Collection<Coupon> coupons = new HashSet<>();
		
		Collection<Long> cpnsIds = this.cmpnyCpnDao.getCouponsIDsByCompanyID(this.cmpnyId);
		
		if(cpnsIds.isEmpty()) {
			new CouponSystemException("MSG: No coupons associated with company '" + cmpnyName + "' were found.");
		} else {
			Iterator<Long> cpnIdIt  = cpnsIds.iterator();
			
			while(cpnIdIt.hasNext()) {
				coupons.add(this.getCoupon((long)cpnIdIt.next()));
			}
		}
				
		return coupons;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpnType
	 * @return A collection of Coupon object of all the coupons associated with the company
	 * as per a coupon type.
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByType(CouponType cpnType) throws CouponSystemException {
		Collection<Coupon> coupons = this.getAllCoupons();
		
		Iterator<Coupon> cpnsIt  = coupons.iterator();


		while(cpnsIt.hasNext()) {
			if(!cpnType.equals(cpnsIt.next().getType())) {
				cpnsIt.remove();
			}
		}

		return coupons;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpnMaxPrice
	 * @return A collection of Coupon objects of all the coupons associated with the company
	 * as per a max price.
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByMaxPrice(double cpnMaxPrice)	throws CouponSystemException {
		if(cpnMaxPrice <= 0) {
			throw new CouponSystemException("ERROR: Invalid maximum price value. Please enter a value greater then 0.");
		}
		
		Collection<Coupon> coupons = this.getAllCoupons();
		
		Iterator<Coupon> cpnsIt  = coupons.iterator();
		
		Coupon cpn = null;
		
		while(cpnsIt.hasNext()) {
			cpn = cpnsIt.next();
			if(cpnMaxPrice < cpn.getPrice()) {
				cpnsIt.remove();
			}
		}

		return coupons;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpnMinPrice
	 * @return A collection of Coupon objects of all the coupons associated with the company
	 * as per a minimum price.
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByMinPrice(double cpnMinPrice) throws CouponSystemException {
		if(cpnMinPrice < 0) {
			throw new CouponSystemException("ERROR: Invalid minimum price value. Please enter a non-negative value.");
		}
		
		Collection<Coupon> coupons = this.getAllCoupons();
		
		Iterator<Coupon> cpnsIt  = coupons.iterator();
		
		Coupon cpn = null;
		
		while(cpnsIt.hasNext()) {
			cpn = cpnsIt.next();
			if(cpnMinPrice > cpn.getPrice()) {
				cpnsIt.remove();
			}
		}

		return coupons;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpnPrice1
	 * @param cpnPrice2
	 * @return A collection of Coupon objects of all the coupons associated with the company
	 * as per a price range.
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByPriceRange(double cpnPrice1, double cpnPrice2) throws CouponSystemException {
		if((cpnPrice1 < 0) || (cpnPrice2 < 0)) {
			throw new CouponSystemException("ERROR: Invalid price range values. "
											+ "Please enter a non-negative value for minimum price "
											+ "and a value greater than 0 for maximum price.");
		}
		
		double cpnMaxPrice = 0;
		double cpnMinPrice = 0;
		
		if(cpnPrice1 >= cpnPrice2) {
			cpnMaxPrice = cpnPrice1;
			cpnMinPrice = cpnPrice2;
		} else {
			cpnMaxPrice = cpnPrice2;
			cpnMinPrice = cpnPrice1;
		}
		
		if(cpnMaxPrice <= 0) {
			throw new CouponSystemException("ERROR: Invalid maximum price value. Please enter a value greater then 0.");
		}
		

		Collection<Coupon> coupons = this.getAllCoupons();
		
		Iterator<Coupon> cpnsIt  = coupons.iterator();
		
		Coupon cpn = null;
		
		while(cpnsIt.hasNext()) {
			cpn = cpnsIt.next();
			if((cpnMaxPrice < cpn.getPrice()) || (cpnMinPrice > cpn.getPrice())) {
				cpnsIt.remove();
			}
		}

		return coupons;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpnTitle
	 * @return A Coupon object associated with the company
	 * as per a title.
	 * @throws CouponSystemException
	 */
	public Coupon getCouponByTitle(String cpnTitle) throws CouponSystemException {
		//Check for valid user name.
		if((cpnTitle == null) || (cpnTitle.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid coupon title. Please try again.");
		}
		
		Collection<Coupon> coupons = this.getAllCoupons();
		Iterator<Coupon> cpnsIt  = coupons.iterator();
		
		Coupon cpnReturned = null;
		Coupon cpnHandled = null;
		
		while(cpnsIt.hasNext()) {
			cpnHandled = cpnsIt.next();
			if(cpnTitle.equals(cpnHandled.getTitle())) {
				cpnReturned = cpnHandled;
			}
		}
		
		if(cpnReturned == null) {
			throw new CouponSystemException("MSG: No coupon with the title '" + cpnTitle 
											+ "' was found associated with the company '" + cmpnyName + "'.");
		}
		
		return cpnReturned;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpnEndDate
	 * @return A collection of Coupon objects of all the coupons associated with the company
	 * as per an expiration date.
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByExpirationDate(Date cpnEndDate) throws CouponSystemException {
		//Check for valid end date.
		if(cpnEndDate == null) {
			throw new CouponSystemException("ERROR: Invalid coupon end date. Please try again.");
		}
		
		Collection<Coupon> coupons = this.getAllCoupons();
		
		Iterator<Coupon> cpnsIt  = coupons.iterator();
		
		Coupon cpn = null;
		
		while(cpnsIt.hasNext()) {
			cpn = cpnsIt.next();
			if(cpnEndDate.before(cpn.getEndDate())) {
				cpnsIt.remove();
			}
		}

		return coupons;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpn
	 * @throws CouponSystemException
	 * Checks the handled coupon validity in terms of unique title, expiration date range and price.
	 * Throws an exception if one of these terms is not met.
	 */
	public void checkCouponValidity(Coupon cpn) throws CouponSystemException {
		//Checking for valid coupon.
		if(cpn == null) {
			throw new CouponSystemException("ERROR: Invalid coupon.");
		}
		
		int minTitleLngth = 3; //Minimum title length.
		
		long cpnId = cpn.getId();
		String cpnTitle = cpn.getTitle();
		Date cpnStartDate = cpn.getStartDate();
		Date cpnEndDate = cpn.getEndDate();
		double cpnPrice = cpn.getPrice();
		
		//Checking for invalid attributes of the coupon.
		if((cpnTitle == null) || (cpnTitle.trim().length() < minTitleLngth)) {
			throw new CouponSystemException("ERROR: Invalid coupon title. Please enter a title with at least " + minTitleLngth + " characters.");
		} else if(cpnStartDate == null) {
			throw new CouponSystemException("ERROR: Invalid coupon start date.");
		} else if(cpnEndDate == null) {
			throw new CouponSystemException("ERROR: Invalid coupon end date.");
		}
		
		//Checking if the coupon title already exists for another coupon:
		Collection<Coupon> coupons = this.cpnDao.getAllCoupons(); //Getting all the coupons in the Coupon table.
		Iterator<Coupon> cpnIt  = coupons.iterator();
		
		Coupon cpnHandled = null;
		
		while(cpnIt.hasNext()) {
			cpnHandled = cpnIt.next();
			if((cpnTitle.equals(cpnHandled.getTitle())) && (cpnId != cpnHandled.getId())) {
				//If the title already exists for another coupon, throws an exception.
				throw new CouponSystemException("ERROR: The coupon title '" + cpnTitle + "' already exists for another coupon in the database. "
												+ "Please enter a coupon with a different title.");
			}
		}
			
		//Checking for valid date range - if start date is later then end date, throws an exception.
		if(cpnStartDate.after(cpnEndDate)) {
			throw new CouponSystemException("ERROR: Invalid expiration date range: " + cpnStartDate + " until " + cpnEndDate 
											+ ". Please enter start date to be before end date.");
		}
		
		//Checking for valid coupon price - if price isn't greater then 0, throws an exception.
		if(cpnPrice <= 0) {
			throw new CouponSystemException("ERROR: Invalid coupon price: " + cpnPrice + ". Please enter price value greater than 0.");
		}
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @return The company being manifested in the facade.
	 * @throws CouponSystemException
	 */
	public Company getThisCompany() throws CouponSystemException {
		Company cmpny = cmpnyDao.getCompany(this.cmpnyId);
		return cmpny;
	}

	//------------------------------------------------------------------
	
	/**
	 * @see jbt.com.cms.facade.CouponClientFacade#login(java.lang.String, java.lang.String)
	 * Returns true if a correct password has been entered for the company user, false otherwise. 
	 */
	@Override
	public boolean login(String cmpnyName, String pswd) throws CouponSystemException {
		//Check for valid company name.
		if((cmpnyName == null) || (cmpnyName.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid company name. Please try again.");
		}
		
		//Check for valid password.
		if((pswd == null) || (pswd.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid password. Please try again.");
		}
		
		boolean accessGranted = false;
		
		Company cmpny = this.cmpnyDao.getCompany(this.cmpnyId);
		
		if(!cmpnyName.equals(this.cmpnyName)) {
			throw new CouponSystemException("ERROR: Company '" + cmpnyName + "' is not listed in the system. Please try again.");
		} else if(!pswd.equals(cmpny.getPassword())) {
			throw new CouponSystemException("ERROR: Incorrect password for company '" + cmpnyName + "'. Please try again.");
		} else {
			accessGranted = true;
		}
		
		return accessGranted;
	}
	
	//------------------------------------------------------------------
	
	//Checking if the coupon ID is associated with the company ID in the Company Coupon table.
	private void checkForCouponOwnership(long cpnId) throws CouponSystemException {
		if(this.cmpnyId != this.cmpnyCpnDao.getCompanyIDByCouponID(cpnId)) {
			throw new CouponSystemException("ERROR: The coupon with ID: " + cpnId + " is not associated with company '" + this.cmpnyName 
											+ "'. Please select a relevant coupon.");
		}
	}
	
	//END OF METHODS
	//******************************************************************

	
	//END OF CompanyFacade
}
