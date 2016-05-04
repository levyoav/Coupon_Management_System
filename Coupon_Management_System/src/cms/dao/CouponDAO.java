package cms.dao;

import java.util.Collection;

import cms.beans.Coupon;
import cms.beans.CouponType;
import cms.main.CouponSystemException;

public interface CouponDAO {
	
	public long createCoupon(Coupon cpn) throws CouponSystemException; //Returns the created coupon's ID.
	
	public void removeCoupon(Coupon cpn) throws CouponSystemException;
	
	public void updateCoupon(Coupon cpn) throws CouponSystemException;
	
	public Coupon getCoupon(long cpnId) throws CouponSystemException;
	
	public Coupon getCouponsByTitle(String cpnTtl) throws CouponSystemException;

	public Collection<Coupon> getAllCoupons() throws CouponSystemException;
	
	public Collection<Coupon> getCouponsByType(CouponType cpnTyp) throws CouponSystemException;
		
	public Collection<Coupon> getCouponsByCompanyID(long cmpnyId) throws CouponSystemException;
	
	public Collection<Coupon> getCouponsByCustomerID(long cstmrId) throws CouponSystemException;
	
	public Collection<Coupon> getCouponsByCompanyName(String cmpnyName) throws CouponSystemException;
	
	public Collection<Coupon> getCouponsByCustomerName(String cstmrName) throws CouponSystemException;
	
	public void decrementCouponAmount(Coupon cpn) throws CouponSystemException;
	
	public boolean checkIfCouponExists (Coupon cpn) throws CouponSystemException;
}
