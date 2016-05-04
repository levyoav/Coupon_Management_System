package cms.dao;

import java.util.Collection;

import cms.main.CouponSystemException;

public interface CompanyCouponDAO {

	public void insertRow(long cmpnyId, long cpnId) throws CouponSystemException;

	public void deleteRow(String clmnName, long id) throws CouponSystemException;

	public void deleteCoupon(long cpnId) throws CouponSystemException;

	public void deleteCouponsByCompanyId(long cmpnyId) throws CouponSystemException;

	public long getCompanyIDByCouponID(long cpnId) throws CouponSystemException;

	public Collection<Long> getCouponsIDsByCompanyID(long cmpnyId) throws CouponSystemException;

	public Collection<long[]> getAllRows() throws CouponSystemException;

}
