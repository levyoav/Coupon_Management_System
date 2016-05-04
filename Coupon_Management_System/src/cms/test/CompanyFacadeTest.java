package cms.test;

import java.util.Collection;

import cms.beans.Coupon;
import cms.dbdao.CompanyCouponDBDAO;
import cms.dbdao.CompanyDBDAO;
import cms.dbdao.CouponDBDAO;
import cms.dbdao.CustomerCouponDBDAO;
import cms.main.CouponSystemException;

public class CompanyFacadeTest {
	public static void main(String[] args) throws CouponSystemException  {
		//-----------------------------------------------------------------------
		//Initialisation:
		
		CompanyDBDAO cmpnyDbDao = new CompanyDBDAO();
		CouponDBDAO cpnDbDao = new CouponDBDAO();
		CompanyCouponDBDAO cmpnycpnDbDao = new CompanyCouponDBDAO();
		CustomerCouponDBDAO cstmrcpnDbDao = new CustomerCouponDBDAO();
		
		Collection<Coupon> coupons = null;
		Coupon cpn = null;
		
//		cmpnyDbDao.showCompanyTable();
		cpnDbDao.showCouponTable();
		cmpnycpnDbDao.showCompanyCouponTable();
		cstmrcpnDbDao.showCustomerCouponTable();
		
//		//Dummy facade
//		CompanyFacade cmpnyFacade = new CompanyFacade(2, "Apple");
//		
//		//-----------------------------------------------------------------------
//		//Test of get methods:
//		
//		coupons = cmpnyFacade.getAllCoupons();
//		Iterator<Coupon> cpnsIt  = coupons.iterator();
//		
//		
//		while(cpnsIt.hasNext()) {
//			System.out.println(cpnsIt.next().toString());
//		}
//		
//		System.out.println("Result:");
//		
////		coupons = cmpnyFacade.getCouponsByType(CouponType.CAMPING);
////		coupons = cmpnyFacade.getCouponsByMaxPrice(249.99);
////		coupons = cmpnyFacade.getCouponsByMinPrice(1200);
////		coupons = cmpnyFacade.getCouponsByPriceRange(0, 1000);
////		System.out.println(cmpnyFacade.getCouponByTitle("SuperPharm").toString());
////		long date = new GregorianCalendar(2013, Calendar.JUNE, 21).getTimeInMillis();
////		System.out.println(new Date(date));
////		coupons = cmpnyFacade.getCouponsByExpirationDate(new Date(date));
//
//		
////		cpnsIt  = coupons.iterator();
////
////		while(cpnsIt.hasNext()) {
////			cpn = cpnsIt.next();
////			System.out.println(cpn.toString());
////		}
//		
//		
//		//-----------------------------------------------------------------------
//		//Test of method updateCoupon:
//		
////		cpn = cmpnyFacade.getCoupon(2);
////		System.out.println(cpn.toString());
//		
////		cpn.setAmount(20);
////		cpn.setPrice(0.01);
////		
////		long date = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();
////		System.out.println(new Date(date));
////		cpn.setEndDate(new Date(date));
////		cpn.setAmount(100);
//		
////		cmpnyFacade.updateCoupon(cpn);
//		
////		cpnDbDao.showCouponTable();
//		
//		
//		//-----------------------------------------------------------------------
//		//Test of method removeCoupon:
//		
////		cpn = cmpnyFacade.getCoupon(1);
////		System.out.println(cpn.toString());
////		
////		cmpnyFacade.removeCoupon(cpn);
////		
////		cpnDbDao.showCouponTable();
////		cmpnycpnDbDao.showCompanyCouponTable();
////		cstmrcpnDbDao.showCustomerCouponTable();
//		
//		//-----------------------------------------------------------------------
//		//Test of method createCoupon:
//		
//		long id = 1;
//		String title = "blavbl";
//		long sd = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();
//		long ed = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();
//		Date startDate = new Date(sd);
//		Date endDate = new Date(ed);
//		int amount = 40;
//		CouponType type = (CouponType.FOOD);
//		String message = "Hello";
//		double price = 20;
//		String image = "image";
//		
//		cpn = new Coupon(id, title, startDate, endDate, amount, type, message, price, image);
//		System.out.println(cpn.toString());
//		
//		cmpnyFacade.createCoupon(cpn);
//		
//		cpnDbDao.showCouponTable();
//		cmpnycpnDbDao.showCompanyCouponTable();
		
		
		//-----------------------------------------------------------------------
		System.out.println("END OF TEST");
	}
}
