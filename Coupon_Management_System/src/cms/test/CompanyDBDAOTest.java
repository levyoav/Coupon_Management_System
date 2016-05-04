package cms.test;

import java.util.Collection;
import java.util.Iterator;

import cms.beans.Company;
import cms.dbdao.CompanyCouponDBDAO;
import cms.dbdao.CompanyDBDAO;
import cms.main.CouponSystemException;


public class CompanyDBDAOTest {
	public static void main(String[] args) throws CouponSystemException {

		Company cmpny;
		
		Collection<Company> companies = null;
		
		CompanyDBDAO cmpnyDbDao = new CompanyDBDAO();
		CompanyCouponDBDAO cmpcpnDbDao = new CompanyCouponDBDAO();
		
		cmpnyDbDao.showCompanyTable();
		cmpcpnDbDao.showCompanyCouponTable();

		//Re-populate the SQL tables to reset the test result.
		//Testing the company name existence check
		cmpny = new Company(0L, "teva", "1234", "teva@mail.com", null);
		try {
			cmpnyDbDao.createtCompany(cmpny);
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		}
		
		
		//Testing the Email existence check
		cmpny = new Company(0L, "Leumi", "1234", "teva@mail.com", null);
		try {
			cmpnyDbDao.createtCompany(cmpny);
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		}
		
		//Testing the company create 
		cmpny = new Company(0L, "Leumi", "1234", "leumi@mail.com",null);
		try {
			cmpnyDbDao.createtCompany(cmpny);
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			cmpny = cmpnyDbDao.getCompany(1);
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			cmpnyDbDao.removeCompany(cmpny);
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		}
		
		
		cmpny = new Company(0L, "teva", "1234", "teva@mail.com", null);
		try {
			cmpnyDbDao.createtCompany(cmpny);
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			cmpny = cmpnyDbDao.getCompany(6);
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		}
		
		cmpnyDbDao.showCompanyTable();

		cmpny.setPassword("password");

		try {
			cmpnyDbDao.updateCompany(cmpny);
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		cmpnyDbDao.showCompanyTable();

		try {
			companies = cmpnyDbDao.getAllCompanies();
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		Iterator<Company> it = companies.iterator();
		
		
		while(it.hasNext()) {
					System.out.println(it.next().toString());
		}
		
		cmpcpnDbDao.showCompanyCouponTable();
		
		
//		Coupon cpn = new Coupon();
//		
//		Collection<Coupon> coupons = new HashSet<>();
//		coupons.add(cpn);
//		
//		cmpny = new Company(0, "teva", "1234", "blavla", coupons);
//		cmpnyDbDao.createtCompany(cmpny);
//
//		cmpny = new Company(0, "apple", "454563", "app@gmail.com", coupons);
//		cmpnyDbDao.createtCompany(cmpny);
//
//		cmpny = new Company(0, "google", "1111", "google@gmail.com", coupons);
//		cmpnyDbDao.createtCompany(cmpny);
//
//		cmpnyDbDao.showCompanyTable();
//		
//		cmpnyDbDao.removeCompany(cmpnyDbDao.getCompany(3));
//		
//		cmpnyDbDao.removeCompany(cmpnyDbDao.getCompany(2));
//
//		cmpnyDbDao.showCompanyTable();
//
//		cmpny = new Company(0, "Intel", "1234", "intel@.com", coupons);
//		cmpnyDbDao.createtCompany(cmpny);
//		
//		cmpnyDbDao.showCompanyTable();
//		
//		cmpny = cmpnyDbDao.getCompany(2);
//		
//		cmpny.setPassword("12223");
//		
//		cmpnyDbDao.updateCompany(cmpny);
//
//		cmpnyDbDao.showCompanyTable();
//
//		Collection<Company> companies = cmpnyDbDao.getAllCompanies();
//		
//		Iterator<Company> it = companies.iterator();
//
//		while(it.hasNext()) {
//			System.out.println(it.next().toString());
//		}
//		
//		System.out.println(cmpnyDbDao.getCompanyByName("Intel").toString());
//		
//		cmpnyDbDao.login("ass", "1111");
//		cmpnyDbDao.login("Intel", "1111");
//		cmpnyDbDao.login("Intel", "12223");

		
		
		System.out.println("----------------END OF CompanyDBDAOTest----------------------");
	}
}
