package cms.dao;

import java.util.Collection;

import cms.beans.Coupon;
import cms.beans.Customer;
import cms.main.CouponSystemException;

public interface CustomerDAO {
	
	public long createCustomer(Customer cstmr) throws CouponSystemException;
	
	public Customer removeCustomer(Customer cstmr) throws CouponSystemException;
	
	public void updateCustomer(Customer cstmr) throws CouponSystemException;
	
	public Customer getCustomer(long cstmrId) throws CouponSystemException;
	
	public Customer getCustomerByName(String cstmrName) throws CouponSystemException;
	
	public Collection<Customer> getAllCustomers() throws CouponSystemException;

	public Collection<Coupon> getCouponsByCustomerID(long cstmrId) throws CouponSystemException;
	
}
