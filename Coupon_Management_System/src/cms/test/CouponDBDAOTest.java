package cms.test;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import cms.beans.Coupon;
import cms.beans.CouponType;
import cms.dbdao.CouponDBDAO;
import cms.main.CouponSystemException;


public class CouponDBDAOTest {
	public static void main(String[] args) throws CouponSystemException {
		CouponDBDAO cpnDbDao = new CouponDBDAO();
		cpnDbDao.showCouponTable();		
		
		Coupon cpn = null;

		Collection<Coupon> cpns = cpnDbDao.getAllCoupons();
		Iterator<Coupon> it  = cpns.iterator();

		while(it.hasNext()) {
			cpn = it.next(); 
			System.out.println(cpn.toString());
		}
		
		cpns = cpnDbDao.getCouponsByType(CouponType.HEALTH);
		System.out.println(cpns.toString());


		cpns = cpnDbDao.getCouponsByType(CouponType.ELECTRICITY);
		System.out.println(cpns.toString());


		cpns = cpnDbDao.getCouponsByType(CouponType.TRAVELLING);
		System.out.println(cpns.toString());
		
		cpn = cpnDbDao.getCoupon(1);
		cpn.setAmount(999);
		cpn.setPrice(0);
		cpn.setTitle("Groupon");
		cpn.setStartDate(new Date(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTimeInMillis()));
		cpn.setEndDate(new Date(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTimeInMillis()));
		cpn.setType(CouponType.CAMPING);
		cpn.setImage("picture");
		cpn.setId(2);
		cpn.setId(3);
		cpn.setMessage("blabla");
		
		cpnDbDao.updateCoupon(cpn);
		cpnDbDao.showCouponTable();		

		
		System.out.println("----------END OF CouponDBDAOTest----------");
	}
}
