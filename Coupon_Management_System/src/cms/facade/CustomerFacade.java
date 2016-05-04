package cms.facade;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import cms.beans.Coupon;
import cms.beans.CouponType;
import cms.beans.Customer;
import cms.dao.CouponDAO;
import cms.dao.CustomerCouponDAO;
import cms.dao.CustomerDAO;
import cms.dbdao.CouponDBDAO;
import cms.dbdao.CustomerCouponDBDAO;
import cms.dbdao.CustomerDBDAO;
import cms.main.CouponSystemException;

public class CustomerFacade implements CouponClientFacade {
	//******************************************************************
	//ATTRIBUTES
	
	private long cstmrId;
	private String cstmrName;

	private CustomerDAO cstmrDao = new CustomerDBDAO();
	private CouponDAO cpnDao = new CouponDBDAO();
	private CustomerCouponDAO cstmrCpnDao = new CustomerCouponDBDAO();
	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR
	
	public CustomerFacade() {
		super();
		this.cstmrId = 0;
		this.cstmrName = "";
	}
	
	public CustomerFacade(long cstmrId, String cstmrName) {
		super();
		this.cstmrId = cstmrId;
		this.cstmrName = cstmrName;
	}
	
	
	//END OF CTOR
	//******************************************************************

	//******************************************************************
	//METHODS
	
	/**
	 * @param cpn
	 * @throws CouponSystemException
	 * Purchases coupon as per a given coupon object.
	 */
	public void purchaseCoupon(Coupon cpn) throws CouponSystemException {
		if(cpn == null) {
			throw new CouponSystemException("ERROR: Invalid coupon.");
		}
		
		if (!cpnDao.checkIfCouponExists(cpn)) {
			throw new CouponSystemException("ERROR: The coupon you are attempting to purchase is not listed in the data base. "
											+ "Please try to purchase another coupon.");
		}
		
		//Checking if the coupon is in stock.
		int cpnAmount = cpn.getAmount();
		
		if (cpnAmount == 0) {
			throw new CouponSystemException("Sorry, this coupon is out of stock. Please buy another coupon.");
		}
		
		//Check if the coupon has expired.
		Date cpnEndDate = cpn.getEndDate();
		Date crntDate = new Date();

		if (crntDate.after(cpnEndDate)) {
			throw new CouponSystemException("Sorry, this coupon is expired. Please buy another coupon.");
		}
		
		//Check if the coupon has already been purchased by the customer.
		long cpnId = cpn.getId();

		Collection<Long> allCpnsIds = cstmrCpnDao.getCouponsIDsByCustomerID(this.cstmrId);
		Iterator<Long> cpnIdIt  = allCpnsIds.iterator();
		
		while(cpnIdIt.hasNext()) {
			if(cpnId == cpnIdIt.next()) {
				throw new CouponSystemException("ERROR: You have already purchased this coupon. Please buy another coupon.");
			}
		}

		//Making the purchase.
		cstmrCpnDao.insertRow(this.cstmrId, cpnId); //Updates the Customer Coupon table. 
		cpnDao.decrementCouponAmount(cpn); //Decrements the amount of the purchased coupon.
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @return
	 * @throws CouponSystemException
	 * Returns a collection of Coupon objects of all the coupons purchased by this customer.
	 */
	public Collection<Coupon> getAllPurchasedCoupons() throws CouponSystemException {
		Collection<Coupon> coupons = this.cpnDao.getCouponsByCustomerID(this.cstmrId);
		
		return coupons;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpnTyp
	 * @return
	 * @throws CouponSystemException
	 * Returns a collection of Coupon objects of all the coupons purchased by this customer
	 * with a matching type.
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType cpnTyp) throws CouponSystemException {
		if(cpnTyp == null) {
			throw new CouponSystemException("ERROR: Invalid coupon type. Please try again.");
		}
		
		Collection<Coupon> coupons = this.getAllPurchasedCoupons();
		
		Iterator<Coupon> cpnsIt  = coupons.iterator();
		
		Coupon cpn = null;
		
		while(cpnsIt.hasNext()) {
			cpn = cpnsIt.next();
			if(cpnTyp != cpn.getType()) {
				cpnsIt.remove();
			}
		}

		return coupons;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpnMaxPrice
	 * @return
	 * @throws CouponSystemException
	 * Returns a collection of Coupon objects of all the coupons purchased by this customer
	 * with price lower than the given maximum price.
	 */
	public Collection<Coupon> getAllPurchasedCouponsByMaxPrice(double cpnMaxPrice) throws CouponSystemException {
		if(cpnMaxPrice <= 0) {
			throw new CouponSystemException("ERROR: Invalid maximum price value: " + cpnMaxPrice 
											+ ". Please enter a value greater then 0.");
		}
		
		Collection<Coupon> coupons = this.getAllPurchasedCoupons();
		
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
	 * @return
	 * @throws CouponSystemException
	 * Returns a collection of Coupon objects of all the coupons purchased by this customer
	 * with price higher than the given minimum price.
	 */
	public Collection<Coupon> getAllPurchasedCouponsByMinPrice(double cpnMinPrice) throws CouponSystemException {
		if(cpnMinPrice < 0) {
			throw new CouponSystemException("ERROR: Invalid minimum price value: " + cpnMinPrice
											+ " . Please enter a non-negative value.");
		}
		
		Collection<Coupon> coupons = this.getAllPurchasedCoupons();
		
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
	 * @return
	 * @throws CouponSystemException
	 * Returns a collection of Coupon objects of all the coupons purchased by this customer
	 * with a price in a given price range.
	 */
	public Collection<Coupon> getAllPurchasedCouponsByPriceRange(double cpnPrice1, double cpnPrice2) throws CouponSystemException {
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
			throw new CouponSystemException("ERROR: Invalid maximum price value: " + cpnMaxPrice 
											+ ". Please enter a value greater then 0.");
		} else if(cpnMinPrice < 0) {
			throw new CouponSystemException("ERROR: Invalid minimum price value : " + cpnMinPrice
											+ ". Please enter a non-negative value.");
		}
		
		
		Collection<Coupon> coupons = this.getAllPurchasedCoupons();
		
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
	 * @param cpnTtl
	 * @return
	 * @throws CouponSystemException
	 * Returns a Coupon object with a matching title.
	 */
	public Coupon getPurchasedCouponByTitle(String cpnTtl) throws CouponSystemException {
		if((cpnTtl == null) || (cpnTtl.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid coupon title. Please try again.");
		}
		
		if(this.cpnDao.getCouponsByTitle(cpnTtl) == null) {
			throw new CouponSystemException("ERROR: No coupon with title '" + cpnTtl
												+ "' is listed in the database.");
		}
		
		
		Collection<Coupon> coupons = this.getAllPurchasedCoupons();
		
		Iterator<Coupon> cpnsIt  = coupons.iterator();
		
		Coupon cpnReturned = null;
		Coupon cpnHandled = null;
		
		while(cpnsIt.hasNext()) {
			cpnHandled = cpnsIt.next();
			if(cpnTtl.equals(cpnHandled.getTitle())) {
				cpnReturned = cpnHandled;
			}
		}
		
		if(cpnReturned == null) {
			throw new CouponSystemException("MSG: Customer '" + this.cstmrName 
											+ "' does not own a coupons with the title '" + cpnTtl + "'.");
		}
		
		return cpnReturned;
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cpnEndDate
	 * @return
	 * @throws CouponSystemException
	 * Returns a collection of Coupon objects of all the coupons purchased by this customer that 
	 * are expired before a given date.

	 */
	public Collection<Coupon> getAllPurchasedCouponsByExpirationDate(Date cpnEndDate) throws CouponSystemException {
		if(cpnEndDate == null) {
			throw new CouponSystemException("ERROR: Invalid coupon expiration date. Please try again.");
		}
		
		Collection<Coupon> coupons = this.getAllPurchasedCoupons();
		
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
	 * @return
	 * @throws CouponSystemException
	 * Returns a Customer object of this customer.
	 */
	public Customer getThisCustomer() throws CouponSystemException {
		Customer cstmr = cstmrDao.getCustomer(this.cstmrId);
		return cstmr;
	}

	//------------------------------------------------------------------
	
	/**
	 * @see cms.facade.CouponClientFacade#login(java.lang.String, java.lang.String)
	 * Returns true if the given password matches this customer's password. 
	 */
	@Override
	public boolean login(String cstmrName, String pswd) throws CouponSystemException {
		//Check for valid customer name.
		if((cstmrName == null) || (cstmrName.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid customer name. Please try again.");
		}
		
		//Check for valid password.
		if((pswd == null) || (pswd.trim().isEmpty())) {
			throw new CouponSystemException("ERROR: Invalid password. Please try again.");
		}
		
		boolean accessGranted = false;

		Customer cstmr = this.cstmrDao.getCustomer(this.cstmrId);
		
		if(!cstmrName.equals(this.cstmrName)) {
			throw new CouponSystemException("ERROR: Customer user name '" + cstmrName + "' is not listed in the system. Please try again.");
		} else if(!pswd.equals(cstmr.getPassword())) {
			throw new CouponSystemException("ERROR: Incorrect password for customer '" + cstmrName + "'. Please try again.");
		} else {
			accessGranted = true;
		}
		
		return accessGranted;
	}
	
	
	//END OF METHODS
	//******************************************************************

	
	//END OF CustomerFacade
}
