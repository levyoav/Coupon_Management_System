package cms.test;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import cms.beans.Coupon;
import cms.dbdao.CouponDBDAO;
import cms.dbdao.CustomerCouponDBDAO;
import cms.dbdao.CustomerDBDAO;
import cms.facade.CustomerFacade;
import cms.main.CouponSystemException;

public class CustomerFacadeTest {
	public static void main(String[] args) throws CouponSystemException {
		CustomerDBDAO cstmrDbDao = new CustomerDBDAO();
		CouponDBDAO cpnDbDao = new CouponDBDAO();
		CustomerCouponDBDAO cstmrcpnDbDao = new CustomerCouponDBDAO();
		
		Collection<Coupon> coupons = null;
		Coupon cpn = null;
	    
		cstmrDbDao.showCustomerTable();
		cpnDbDao.showCouponTable();
		cstmrcpnDbDao.showCustomerCouponTable();
		
		CustomerFacade cstmrFcd = new CustomerFacade(6, "Yoav");
		
		coupons = cstmrFcd.getAllPurchasedCoupons();
		Iterator<Coupon> cpnsIt  = coupons.iterator();
		
		
		while(cpnsIt.hasNext()) {
			cpn = cpnsIt.next();
			System.out.println(cpn.toString());
		}
		
		System.out.println("Result:");
		
//		coupons = cstmrFcd.getAllPurchasedCouponsByType(CouponType.HEALTH);
//		coupons = cstmrFcd.getAllPurchasedCouponsByMaxPrice(250);
//		coupons = cstmrFcd.getAllPurchasedCouponsByMinPrice(1000);
//		coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(100, 100);
		cpn = cstmrFcd.getPurchasedCouponByTitle("Rent a car");
		System.out.println(cpn.toString());
		long date = new GregorianCalendar(2016, Calendar.JUNE, 21).getTimeInMillis();
		System.out.println(new Date(date));
		coupons = cstmrFcd.getAllPurchasedCouponsByExpirationDate(new Date(date));

		
		cpnsIt  = coupons.iterator();

		while(cpnsIt.hasNext()) {
			cpn = cpnsIt.next();
			System.out.println(cpn.toString());
		}
		
		try {
			cpn = cpnDbDao.getCoupon(7);
			System.out.println(cpn.toString());
			cpnDbDao.removeCoupon(cpn);
			//cpn = null;
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			cstmrFcd.purchaseCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		
		cpnDbDao.showCouponTable();
		cstmrcpnDbDao.showCustomerCouponTable();
		
		System.out.println("END OF TEST");
	}

}
