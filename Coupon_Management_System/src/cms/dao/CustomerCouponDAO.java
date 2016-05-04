package cms.dao;

import java.util.Collection;

import cms.main.CouponSystemException;

public interface CustomerCouponDAO {
	
	public void insertRow(long cstmrId, long cpnId) throws CouponSystemException;
	
	public void deleteRowsByCulomnValue(String clmnName, long id) throws CouponSystemException;
	
	public void deleteRowsByCouponID(long cpnId) throws CouponSystemException;
	
	public void deleteRowsByCustomerID(long cstmrId) throws CouponSystemException;
	
	public Collection<Long> getCustomersIDsByCouponID(long cpnId) throws CouponSystemException;
	
	public Collection<Long> getCouponsIDsByCustomerID(long cstmrId) throws CouponSystemException;
	
	public Collection<long[]> getAllRows() throws CouponSystemException;
	
}
