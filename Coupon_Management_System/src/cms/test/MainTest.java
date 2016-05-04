package cms.test;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import cms.beans.ClientType;
import cms.beans.Company;
import cms.beans.Coupon;
import cms.beans.CouponType;
import cms.beans.Customer;
import cms.dbdao.CompanyCouponDBDAO;
import cms.dbdao.CompanyDBDAO;
import cms.dbdao.CouponDBDAO;
import cms.dbdao.CustomerCouponDBDAO;
import cms.dbdao.CustomerDBDAO;
import cms.facade.AdminFacade;
import cms.facade.CompanyFacade;
import cms.facade.CustomerFacade;
import cms.main.CouponManagementSystem;
import cms.main.CouponSystemException;


public class MainTest {
	public static void main(String[] args) throws CouponSystemException {
		//******************************************************************
		//Main test initialisation:
		System.out.println(new java.util.Date());
		
		CouponManagementSystem cms = CouponManagementSystem.getInstance();
		
		AdminFacade admnFcd = new AdminFacade();
		CompanyFacade cmpnyFcd = new CompanyFacade();
		CustomerFacade cstmrFcd = new CustomerFacade();
		
		CompanyDBDAO cmpnyDbDao = new CompanyDBDAO();
		CustomerDBDAO cstmrDbDao = new CustomerDBDAO();
		CouponDBDAO cpnDbDao = new CouponDBDAO();
		CompanyCouponDBDAO cmpnyCpnDbDao = new CompanyCouponDBDAO();
		CustomerCouponDBDAO cstmrCpnDbDao = new CustomerCouponDBDAO();
		
		Collection<Company> companies = null;
		Iterator<Company> cmpnysIt  = null;
		Company cmpny = null;

		Collection<Customer> customers = null;
		Iterator<Customer> cstmrsIt  = null;
		Customer cstmr = null;
		
		Collection<Coupon> coupons = null;
		Iterator<Coupon> cpnsIt  = null;
		Coupon cpn = null;
		
		long date = 0L;
		
		
		//******************************************************************
		//AdminFacade test:
		
		System.out.println("*****************");
		System.out.println("AdminFacade test:");
		System.out.println("*****************");
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of login method:
		
		System.out.println("Test of login method:");
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: (AdminFacade)cms.login(\"21dd\", \"1234\", ClientType.ADMIN);");
		
		System.out.println("\nRESULT:");
		try {
			admnFcd = (AdminFacade)cms.login("21dd", "1234", ClientType.ADMIN);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: (AdminFacade)cms.login(\"admin\", \"1232\", ClientType.ADMIN);");
		
		System.out.println("\nRESULT:");
		try {
			admnFcd = (AdminFacade)cms.login("admin", "1232", ClientType.ADMIN);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: (AdminFacade)cms.login(\"admin\", \"1234\", ClientType.ADMIN);");
		
		System.out.println("\nRESULT:");
		try {
			admnFcd = (AdminFacade)cms.login("admin", "1234", ClientType.ADMIN);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of get company methods:
		
		System.out.println("Test of get company methods:");
		System.out.println();
		
		cmpnyDbDao.showCompanyTable();
		System.out.println();
		
		cpnDbDao.showCouponTable();
		System.out.println();
		
		cmpnyCpnDbDao.showCompanyCouponTable();
		System.out.println();
		
		cstmrCpnDbDao.showCustomerCouponTable();
		System.out.println();
		
		//Throws exception
		System.out.println("COMMAND: admnFcd.getCompany(-1);");

		System.out.println("\nRESULT:");
		try {
			cmpny = admnFcd.getCompany(-1);
			System.out.println("Result:");
			System.out.println(cmpny.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
				
				
		//Throws exception
		System.out.println("COMMAND: admnFcd.getCompany(0);");
		
		System.out.println("\nRESULT:");
		try {
			cmpny = admnFcd.getCompany(0);
			System.out.println("Result:");
			System.out.println(cmpny.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: admnFcd.getCompany(6);");
		
		System.out.println("\nRESULT:");
		try {
			cmpny = admnFcd.getCompany(6);
			System.out.println("Result:");
			System.out.println(cmpny.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
				
				
		System.out.println("COMMAND: admnFcd.getCompany(3);");
		
		System.out.println("\nRESULT:");
		try {
			cmpny = admnFcd.getCompany(3);
			System.out.println("Result:");
			System.out.println(cmpny.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();

		
		System.out.println("COMMAND: admnFcd.getAllCompanies();");
		
		System.out.println("\nRESULT:");
		try {
			companies = admnFcd.getAllCompanies();
			cmpnysIt = companies.iterator();
			while(cmpnysIt.hasNext()) {
				System.out.println(cmpnysIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
						
		
		//-----------------------------------------------------------------------
		//Test of remove company method:
		
		System.out.println("Test of remove company methods:");
		System.out.println();

		System.out.println("COMMAND: admnFcd.removeCompany(admnFcd.getCompany(1));");
		
		System.out.println("\nRESULT:");
		try {
			admnFcd.removeCompany(admnFcd.getCompany(1));
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		cmpnyDbDao.showCompanyTable();
		System.out.println();
		
		cmpnyCpnDbDao.showCompanyCouponTable();
		System.out.println();
		
		cpnDbDao.showCouponTable();
		System.out.println();
		
		cstmrCpnDbDao.showCustomerCouponTable();
		System.out.println();
		
		//Throws exception
		System.out.println("COMMAND: admnFcd.removeCompany(admnFcd.getCompany(1));");
		
		System.out.println("\nRESULT:");
		try {
			admnFcd.removeCompany(admnFcd.getCompany(1));
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of update company method:
		
		System.out.println("Test of update company methods:");
		System.out.println();
		
		
		//Throws exception
		cmpny = admnFcd.getCompany(2);
		cmpny.setId(3L);
		cmpny.setCompName("HP");
		cmpny.setEmail("google@gamil.com");
		cmpny.setPassword("jobs");
		
		System.out.println("COMMAND: \n"
							+ "cmpny = admnFcd.getCompany(2);\n"
							+ "cmpny.setId(3);\n"
							+ "cmpny.setCompName(\"HP\");\n"
							+ "cmpny.setEmail(\"google@gamil.com\");\n"
							+ "cmpny.setPassword(\"jobs\");\n"
							+ "admnFcd.updateCompany(cmpny);");
		
		System.out.println("\nRESULT:");
		try {
			admnFcd.updateCompany(cmpny);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		cmpny = admnFcd.getCompany(2);
		cmpny.setId(3L);
		cmpny.setCompName("HP");
		cmpny.setEmail("apple@apple.com");
		cmpny.setPassword("jobs");
		
		System.out.println("COMMAND: \n"
							+ "cmpny = admnFcd.getCompany(2);\n"
							+ "cmpny.setId(3);\n"
							+ "cmpny.setCompName(\"HP\");\n"
							+ "cmpny.setEmail(\"apple@apple.com\");\n"
							+ "cmpny.setPassword(\"jobs\");\n"
							+ "admnFcd.updateCompany(cmpny);");
		
		System.out.println("\nRESULT:");
		try {
			admnFcd.updateCompany(cmpny);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		cmpnyDbDao.showCompanyTable();
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of create company method:
		
		System.out.println("Test of create company methods:");
		System.out.println();
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "cmpny = null;\n"
				+ "admnFcd.createCompany(cmpny);");
		
		cmpny = null;
		
		System.out.println("\nRESULT:");
		try {
			admnFcd.createCompany(cmpny);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "cmpny = new Company(\"google\", \"14334\", \"google@gamil.com\");\n"
				+ "admnFcd.createCompany(cmpny);");
		
		cmpny = new Company(0L, "google", "14334", "google@gamil.com", null);
		
		System.out.println("\nRESULT:");
		try {
			admnFcd.createCompany(cmpny);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "cmpny = new Company(\"Facebook\", \"14334\", \"google@gamil.com\");\n"
				+ "admnFcd.createCompany(cmpny);");
		
		cmpny = new Company(0L, "Facebook", "14334", "google@gamil.com", null);

		System.out.println("\nRESULT:");
		try {
			admnFcd.createCompany(cmpny);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "cmpny = new Company(\"Facebook\", \"14334\", \"facebook@fb.com\");\n"
				+ "admnFcd.createCompany(cmpny);");
		
		cmpny = new Company(0L, "Facebook", "14334", "facebook@fb.com", null);

		System.out.println("\nRESULT:");
		try {
			admnFcd.createCompany(cmpny);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		cmpnyDbDao.showCompanyTable();
		System.out.println();

		
		
		//-----------------------------------------------------------------------
		//Test of get customer methods:
		
		System.out.println("Test of get customer methods:");
		System.out.println();
		
		cstmrDbDao.showCustomerTable();
		System.out.println();
		cpnDbDao.showCouponTable();
		System.out.println();
		cstmrCpnDbDao.showCustomerCouponTable();
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
							+ "cstmr = admnFcd.getCustomer(-1);");

		System.out.println("\nRESULT:");
		try {
			cstmr = admnFcd.getCustomer(-1);
			System.out.println(cstmr.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
							+ "cstmr = admnFcd.getCustomer(0);");
		
		System.out.println("\nRESULT:");
		try {
			cstmr = admnFcd.getCustomer(0);
			System.out.println(cstmr.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
							+ "cstmr = admnFcd.getCustomer(30);");

		System.out.println("\nRESULT:");
		try {
			cstmr = admnFcd.getCustomer(30);
			System.out.println(cstmr.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "cstmr = admnFcd.getCustomer(1);");

		System.out.println("\nRESULT:");
		try {
			cstmr = admnFcd.getCustomer(1);
			System.out.println(cstmr.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "admnFcd.getAllCustomers();");

		System.out.println("\nRESULT:");
		try {
			customers = admnFcd.getAllCustomers();
			cstmrsIt = customers.iterator();
			while(cstmrsIt.hasNext()) {
				System.out.println(cstmrsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of remove customer method:
		
		System.out.println("Test of remove customer methods:");
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
							+ "admnFcd.removeCustomer(admnFcd.getCustomer(4));");
		
		System.out.println("\nRESULT:");
		try {
			admnFcd.removeCustomer(admnFcd.getCustomer(4));
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		cstmrDbDao.showCustomerTable();
		System.out.println();
		cstmrCpnDbDao.showCustomerCouponTable();
		System.out.println();
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "admnFcd.removeCustomer(admnFcd.getCustomer(4));");

		System.out.println("\nRESULT:");
		try {
			admnFcd.removeCustomer(admnFcd.getCustomer(4));
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of update customer method:
		
		System.out.println("Test of update customer methods:");
		System.out.println();

		cstmrDbDao.showCustomerTable();
		System.out.println();
		
		cstmr = admnFcd.getCustomer(3);
		cstmr.setId(9L);
		cstmr.setCustName("Felix");
		cstmr.setPassword("1234");

		System.out.println("COMMAND: \n"
							+ "cstmr = admnFcd.getCustomer(3);\n"
							+ "cstmr.setId(9);\n"
							+ "cstmr.setCustName(\"Felix\");\n"
							+ "cstmr.setPassword(\"1234\");");

		System.out.println("\nRESULT:");

		try {
			admnFcd.updateCustomer(cstmr);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		cstmrDbDao.showCustomerTable();
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of create customer method:
		
		System.out.println("Test of create customer methods:");
		System.out.println();

		//Throws exception:
		System.out.println("COMMAND: \n"
							+ "cstmr = null;\n"
							+ "admnFcd.createCustomer(cstmr);");

		cstmr = null;

		System.out.println("\nRESULT:");
		try {
			admnFcd.createCustomer(cstmr);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "cstmr = new Customer(\"Ami\", \"4234gfr\");\n"
				+ "admnFcd.createCustomer(cstmr);");

		cstmr = new Customer(0L, "Ami", "4234gfr", null);

		System.out.println("\nRESULT:");
		try {
			admnFcd.createCustomer(cstmr);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();



		System.out.println("COMMAND: \n"
				+ "cstmr = new Customer(\"Udi\", \"4234gfr\");\n"
				+ "admnFcd.createCustomer(cstmr);");

		cstmr = new Customer(0L, "Udi", "4234gfr", null);

		System.out.println("\nRESULT:");
		try {
			admnFcd.createCustomer(cstmr);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		cstmrDbDao.showCustomerTable();
		System.out.println();
		
		
		//******************************************************************
		//CompanyFacade test:
		
		System.out.println("*******************");
		System.out.println("CompanyFacade test:");
		System.out.println("*******************");
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of login method:
		
		System.out.println("Test of login method:");
		System.out.println();

		cmpnyDbDao.showCompanyTable();
		System.out.println();
		cpnDbDao.showCouponTable();
		System.out.println();
		cmpnyCpnDbDao.showCompanyCouponTable();
		System.out.println();

		
		//Throws exception
		System.out.println("COMMAND: \n"
							+ "cmpnyFcd = (CompanyFacade)cms.login(\"21dd\", \"1234\", ClientType.COMPANY);");
		
		System.out.println("\nRESULT:");
		try {
			cmpnyFcd = (CompanyFacade)cms.login("21dd", "1234", ClientType.COMPANY);
			System.out.println(cmpnyFcd.getThisCompany().toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cmpnyFcd = (CompanyFacade)cms.login(\"Apple\", \"1234\", ClientType.COMPANY);");

		System.out.println("\nRESULT:");
		try {
			cmpnyFcd = (CompanyFacade)cms.login("Apple", "1234", ClientType.COMPANY);
			System.out.println(cmpnyFcd.getThisCompany().toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();

		
		System.out.println("COMMAND: \n"
				+ "cmpnyFcd = (CompanyFacade)cms.login(\"Apple\", \"jobs\", ClientType.COMPANY);");

		System.out.println("\nRESULT:");
		try {
			cmpnyFcd = (CompanyFacade)cms.login("Apple", "jobs", ClientType.COMPANY);
			System.out.println(cmpnyFcd.getThisCompany().toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of get methods:
		
		System.out.println("Test of get methods:");
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
							+ "cpn = cmpnyFcd.getCoupon(-1);");

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCoupon(-1);
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCoupon(0);");

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCoupon(0);
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();


		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCoupon(39);");

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCoupon(39);
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();

		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCoupon(4);");

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCoupon(4);
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCoupon(7);");

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCoupon(7);
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCoupon(6);");

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCoupon(6);
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getAllCoupons();");

		System.out.println("\nRESULT:");
		try {
			coupons = cmpnyFcd.getAllCoupons();
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByType(CouponType.CAMPING);");

		System.out.println("\nRESULT:");
		try {
			coupons = cmpnyFcd.getCouponsByType(CouponType.CAMPING);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByType(CouponType.RESTAURANTS);");

		System.out.println("\nRESULT:");
		try {
			coupons = cmpnyFcd.getCouponsByType(CouponType.RESTAURANTS);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByMaxPrice(-80);");

		System.out.println("\nRESULT:");
		try {
		coupons = cmpnyFcd.getCouponsByMaxPrice(-80);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByMaxPrice(0);");

		System.out.println("\nRESULT:");
		try {
			coupons = cmpnyFcd.getCouponsByMaxPrice(0);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
				
				
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByMaxPrice(249.99);");

		System.out.println("\nRESULT:");
		try {
		coupons = cmpnyFcd.getCouponsByMaxPrice(249.99);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByMaxPrice(150);");

		System.out.println("\nRESULT:");
		try {
		coupons = cmpnyFcd.getCouponsByMaxPrice(150);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByMaxPrice(10);");

		System.out.println("\nRESULT:");
		try {
		coupons = cmpnyFcd.getCouponsByMaxPrice(10);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByMinPrice(-45);");

		System.out.println("\nRESULT:");
		try {
			coupons = cmpnyFcd.getCouponsByMinPrice(-45);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
				
		
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByMinPrice(0);");

		System.out.println("\nRESULT:");
		try {
			coupons = cmpnyFcd.getCouponsByMinPrice(0);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByMinPrice(150);");

		System.out.println("\nRESULT:");
		try {
			coupons = cmpnyFcd.getCouponsByMinPrice(150);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
				
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByMinPrice(1500);");

		System.out.println("\nRESULT:");
		try {
			coupons = cmpnyFcd.getCouponsByMinPrice(1500);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByPriceRange(10, -1);");

		System.out.println("\nRESULT:");
		try {
			coupons = cmpnyFcd.getCouponsByPriceRange(10, -1);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByPriceRange(0, 0);");

		System.out.println("\nRESULT:");
		try {
			coupons = cmpnyFcd.getCouponsByPriceRange(0, 0);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "coupons = cmpnyFcd.getCouponsByPriceRange(150, 0);");

		System.out.println("\nRESULT:");
		try {
			coupons = cmpnyFcd.getCouponsByPriceRange(150, 0);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCouponByTitle(\"SuperPharm\");");

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCouponByTitle("SuperPharm");
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCouponByTitle(\"Buy 1 get 1 free\");");

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCouponByTitle("Buy 1 get 1 free");
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
				
				
		System.out.println("COMMAND: \n"
				+ "long date = new GregorianCalendar(2016, Calendar.JANUARY, 1).getTimeInMillis();\n"
				+ "coupons = cmpnyFcd.getCouponsByExpirationDate(new Date(date));");

		System.out.println("\nRESULT:");
		try {
			date = new GregorianCalendar(2016, Calendar.JANUARY, 1).getTimeInMillis();
			coupons = cmpnyFcd.getCouponsByExpirationDate(new Date(date));
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "date = new GregorianCalendar(2016, Calendar.AUGUST, 1).getTimeInMillis();\n"
				+ "coupons = cmpnyFcd.getCouponsByExpirationDate(new Date(date));");

		System.out.println("\nRESULT:");
		try {
			date = new GregorianCalendar(2016, Calendar.AUGUST, 1).getTimeInMillis();
			coupons = cmpnyFcd.getCouponsByExpirationDate(new Date(date));
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "long date = new GregorianCalendar(2017, Calendar.JUNE, 21).getTimeInMillis();\n"
				+ "coupons = cmpnyFcd.getCouponsByExpirationDate(new Date(date));");

		System.out.println("\nRESULT:");
		try {
			date = new GregorianCalendar(2017, Calendar.JUNE, 21).getTimeInMillis();
			coupons = cmpnyFcd.getCouponsByExpirationDate(new Date(date));
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				System.out.println(cpnsIt.next().toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of method updateCoupon:
		
		System.out.println("Test of updateCoupon methods:");
		System.out.println();
		
			
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCoupon(6);\n"
				+ "cpn.setId(30);\n"
				+ "cpn.setTitle(\"Buy 1 get 1 free\");\n"
				+ "cmpnyFcd.updateCoupon(cpn);");

		

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCoupon(6);
			cpn.setId(30L);
			cpn.setTitle("Buy 1 get 1 free");
			cmpnyFcd.updateCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCoupon(6);\n"
				+ "cpn.setId(30);\n"
				+ "cpn.setTitle(\"Everything must go!\");\n"
				+ "long date = new GregorianCalendar(2017, Calendar.JUNE, 21).getTimeInMillis();\n"
				+ "cpn.setStartDate(new Date(date));\n"
				+ "date = new GregorianCalendar(2016, Calendar.JUNE, 21).getTimeInMillis();\n"
				+ "cpn.setEndDate(new Date(date));\n"
				+ "cmpnyFcd.updateCoupon(cpn);");

		

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCoupon(6);
			cpn.setId(30L);
			cpn.setTitle("Everything must go!");
			date = new GregorianCalendar(2017, Calendar.JUNE, 21).getTimeInMillis();
			cpn.setStartDate(new Date(date));
			date = new GregorianCalendar(2016, Calendar.JUNE, 21).getTimeInMillis();
			cpn.setEndDate(new Date(date));
			cmpnyFcd.updateCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();

		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCoupon(6);\n"
				+ "cpn.setId(30);\n"
				+ "cpn.setTitle(\"Everything must go!\");\n"
				+ "date = new GregorianCalendar(2016, Calendar.JUNE, 21).getTimeInMillis();\n"
				+ "cpn.setStartDate(new Date(date));\n"
				+ "date = new GregorianCalendar(2017, Calendar.JUNE, 21).getTimeInMillis();\n"
				+ "cpn.setEndDate(new Date(date));\n"
				+ "cpn.setAmount(-20);\n"
				+ "cmpnyFcd.updateCoupon(cpn);");

		
		

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCoupon(6);
			cpn.setId(30L);
			cpn.setTitle("Everything must go!");
			date = new GregorianCalendar(2016, Calendar.JUNE, 21).getTimeInMillis();
			cpn.setStartDate(new Date(date));
			date = new GregorianCalendar(2017, Calendar.JUNE, 21).getTimeInMillis();
			cpn.setEndDate(new Date(date));
			cpn.setAmount(-20);
			cmpnyFcd.updateCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCoupon(6);\n"
				+ "cpn.setId(30);\n"
				+ "cpn.setTitle(\"Everything must go!\");\n"
				+ "date = new GregorianCalendar(2016, Calendar.JUNE, 21).getTimeInMillis();\n"
				+ "cpn.setStartDate(new Date(date));\n"
				+ "date = new GregorianCalendar(2017, Calendar.JUNE, 21).getTimeInMillis();\n"
				+ "cpn.setEndDate(new Date(date));\n"
				+ "cpn.setAmount(20);\n"
				+ "cpn.setType(CouponType.FOOD);\n"
				+ "cpn.setMessage(\"Open all week\");\n"
				+ "cpn.setPrice(0L);\n"
				+ "cmpnyFcd.updateCoupon(cpn);");

		

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCoupon(6);
			cpn.setId(30L);
			cpn.setTitle("Everything must go!");
			date = new GregorianCalendar(2016, Calendar.JUNE, 21).getTimeInMillis();
			cpn.setStartDate(new Date(date));
			date = new GregorianCalendar(2017, Calendar.JUNE, 21).getTimeInMillis();
			cpn.setEndDate(new Date(date));
			cpn.setAmount(20);
			cpn.setType(CouponType.FOOD);
			cpn.setMessage("Open all week");
			cpn.setPrice(0L);
			cmpnyFcd.updateCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "cpn = cmpnyFcd.getCoupon(6);\n"
				+ "cpn.setId(30);\n"
				+ "cpn.setTitle(\"Everything must go!\");\n"
				+ "date = new GregorianCalendar(2016, Calendar.JUNE, 21).getTimeInMillis();\n"
				+ "cpn.setStartDate(new Date(date));\n"
				+ "date = new GregorianCalendar(2017, Calendar.JUNE, 21).getTimeInMillis();\n"
				+ "cpn.setEndDate(new Date(date));\n"
				+ "cpn.setAmount(20);\n"
				+ "cpn.setType(CouponType.FOOD);\n"
				+ "cpn.setMessage(\"Open all week\");\n"
				+ "cpn.setPrice(50L);\n"
				+ "cpn.setImage(\"photo\");\n"
				+ "cmpnyFcd.updateCoupon(cpn);");

		

		System.out.println("\nRESULT:");
		try {
			cpn = cmpnyFcd.getCoupon(6);
			cpn.setId(30L);
			cpn.setTitle("Everything must go!");
			date = new GregorianCalendar(2016, Calendar.JUNE, 21).getTimeInMillis();
			cpn.setStartDate(new Date(date));
			date = new GregorianCalendar(2017, Calendar.JUNE, 21).getTimeInMillis();
			cpn.setEndDate(new Date(date));
			cpn.setAmount(20);
			cpn.setType(CouponType.FOOD);
			cpn.setMessage("Open all week");
			cpn.setPrice(50L);
			cpn.setImage("photo");
			cmpnyFcd.updateCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();

		
		cpnDbDao.showCouponTable();
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of method removeCoupon:
		
		System.out.println("Test of method removeCoupon:");
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cmpnyFcd.removeCoupon(cmpnyFcd.getCoupon(1));");

		System.out.println("\nRESULT:");
		try {
			cmpnyFcd.removeCoupon(cmpnyFcd.getCoupon(1));
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "cmpnyFcd.removeCoupon(cmpnyFcd.getCoupon(9));");

		System.out.println("\nRESULT:");
		try {
			cmpnyFcd.removeCoupon(cmpnyFcd.getCoupon(9));
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		cpnDbDao.showCouponTable();
		System.out.println();
		cmpnyCpnDbDao.showCompanyCouponTable();
		System.out.println();
		cstmrCpnDbDao.showCustomerCouponTable();
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of method createCoupon:
		
		System.out.println("Test of method createCoupon:");
		System.out.println();
		
		cpn = null;
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = null;\n"
				+ "cmpnyFcd.createCoupon(cpn);");

		System.out.println("\nRESULT:");
		try {
			cmpnyFcd.createCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		cpn = new Coupon();

		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn.setId(30);\n"
				+ "cpn.setTitle(\"Sale\");\n"
				+ "date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();\n"
				+ "cpn.setStartDate(new Date(date));\n"
				+ "date = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();\n"
				+ "cpn.setEndDate(new Date(date));\n"
				+ "cmpnyFcd.createCoupon(cpn);");
				

		
		System.out.println("\nRESULT:");
		try {
			cpn.setId(30L);
			cpn.setTitle("Sale");
			date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();
			cpn.setStartDate(new Date(date));
			date = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();
			cpn.setEndDate(new Date(date));
			cmpnyFcd.createCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn.setId(30);\n"
				+ "cpn.setTitle(\"Dinner for 2\");\n"
				+ "date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();\n"
				+ "cpn.setStartDate(new Date(date));\n"
				+ "date = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();\n"
				+ "cpn.setEndDate(new Date(date));\n"
				+ "cmpnyFcd.createCoupon(cpn);");
				

		
		System.out.println("\nRESULT:");
		try {
			cpn.setId(30L);
			cpn.setTitle("Dinner for 2");
			date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();
			cpn.setStartDate(new Date(date));
			date = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();
			cpn.setEndDate(new Date(date));
			cmpnyFcd.createCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn.setId(30);\n"
				+ "cpn.setTitle(\"Dinner for 2\");\n"
				+ "date = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();\n"
				+ "cpn.setStartDate(new Date(date));\n"
				+ "date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();\n"
				+ "cpn.setEndDate(new Date(date));\n"
				+ "cpn.setPrice(0);\n"
				+ "cmpnyFcd.createCoupon(cpn);");
		
		
		System.out.println("\nRESULT:");
		try {
			cpn.setId(30L);
			cpn.setTitle("Dinner for 2");
			date = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();
			cpn.setStartDate(new Date(date));
			date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();
			cpn.setEndDate(new Date(date));
			cpn.setPrice(0);
			cmpnyFcd.createCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();

		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn.setId(30);\n"
				+ "cpn.setTitle(\"Dinner for 2\");\n"
				+ "date = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();\n"
				+ "cpn.setStartDate(new Date(date));\n"
				+ "date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();\n"
				+ "cpn.setEndDate(new Date(date));\n"
				+ "cpn.setPrice(50D);\n"
				+ "cpn.setAmount(0);\n"
				+ "cmpnyFcd.createCoupon(cpn);");
		
		
		System.out.println("\nRESULT:");
		try {
			cpn.setId(30L);
			cpn.setTitle("Dinner for 2");
			date = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();
			cpn.setStartDate(new Date(date));
			date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();
			cpn.setEndDate(new Date(date));
			cpn.setPrice(50D);
			cpn.setAmount(0);
			cmpnyFcd.createCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();

		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn.setId(30);\n"
				+ "cpn.setTitle(\"Dinner for 2\");\n"
				+ "date = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();\n"
				+ "cpn.setStartDate(new Date(date));\n"
				+ "date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();\n"
				+ "cpn.setEndDate(new Date(date));\n"
				+ "cpn.setPrice(50D);\n"
				+ "cpn.setAmount(100);\n"
				+ "cpn.setType(CouponType.FOOD);\n"
				+ "cpn.setMessage(\"Hello\");\n"
				+ "cpn.setImage(\"photo.jpg\");\n"
				+ "cmpnyFcd.createCoupon(cpn);");
		
		
		System.out.println("\nRESULT:");
		try {
			cpn.setId(30L);
			cpn.setTitle("Dinner for 2");
			date = new GregorianCalendar(2015, Calendar.NOVEMBER, 26).getTimeInMillis();
			cpn.setStartDate(new Date(date));
			date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();
			cpn.setEndDate(new Date(date));
			cpn.setPrice(50D);
			cpn.setAmount(100);
			cpn.setType(CouponType.FOOD);
			cpn.setMessage("Hello");
			cpn.setImage("photo.jpg");
			cmpnyFcd.createCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		

		cpnDbDao.showCouponTable();
		System.out.println();
		cmpnyCpnDbDao.showCompanyCouponTable();
		System.out.println();
		
		
		//******************************************************************
		//CustomerFacade test:
		
		System.out.println("********************");
		System.out.println("CustomerFacade test:");
		System.out.println("********************");
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of login method:
		
		System.out.println("Test of login method:");
		System.out.println();

		cstmrDbDao.showCustomerTable();
		System.out.println();
		cpnDbDao.showCouponTable();
		System.out.println();
		cstmrCpnDbDao.showCustomerCouponTable();
		System.out.println();

		
		//Throws exception
		System.out.println("COMMAND: \n"
							+ "cstmrFcd = (CompanyFacade)cms.login(\"Haim\", \"1111\", ClientType.CUSTOMER);");
		
		System.out.println("\nRESULT:");
		try {
			cstmrFcd = (CustomerFacade)cms.login("Haim", "1111", ClientType.CUSTOMER);
			System.out.println(cstmrFcd.getThisCustomer().toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
							+ "cstmrFcd = (CompanyFacade)cms.login(\"Avi\", \"1111\", ClientType.CUSTOMER);");
		
		System.out.println("\nRESULT:");
		try {
			cstmrFcd = (CustomerFacade)cms.login("Avi", "1111", ClientType.CUSTOMER);
			System.out.println(cstmrFcd.getThisCustomer().toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		

		System.out.println("COMMAND: \n"
							+ "cstmrFcd = (CompanyFacade)cms.login(\"Avi\", \"1234\", ClientType.CUSTOMER);");
		
		System.out.println("\nRESULT:");
		try {
			cstmrFcd = (CustomerFacade)cms.login("Avi", "1234", ClientType.CUSTOMER);
			System.out.println(cstmrFcd.getThisCustomer().toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of get methods:
		
		System.out.println("Test of get methods:");
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
							+ "System.out.println(cstmrFcd.getThisCustomer().toString());");

		System.out.println("\nRESULT:");
		try {
			System.out.println(cstmrFcd.getThisCustomer().toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
						+ "coupons = cstmrFcd.getAllPurchasedCoupons();");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCoupons();
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByType(CouponType.HEALTH);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByType(CouponType.HEALTH);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();

		
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByType(CouponType.ELECTRICITY);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByType(CouponType.ELECTRICITY);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throw exception:
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByMaxPrice(-100);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByMaxPrice(-100);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throw exception:
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByMaxPrice(0);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByMaxPrice(0);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throw exception:
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByMaxPrice(20);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByMaxPrice(20);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByMaxPrice(100);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByMaxPrice(100);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();

				
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByMaxPrice(300);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByMaxPrice(300);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByMinPrice(-1);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByMinPrice(-1);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByMinPrice(1000);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByMinPrice(1000);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByMinPrice(100);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByMinPrice(100);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByMinPrice(0);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByMinPrice(0);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(-10, -100);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(-10, -100);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(0, 0);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(0, 0);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(0, 10);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(0, 10);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception:
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(2000, 1000);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(2000, 1000);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(100, 50);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(100, 50);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(0, 1000);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByPriceRange(0, 1000);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cstmrFcd.getPurchasedCouponByTitle(\"\");");

		System.out.println("\nRESULT:");
		try {
			cpn = cstmrFcd.getPurchasedCouponByTitle("");
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cstmrFcd.getPurchasedCouponByTitle(null);");

		System.out.println("\nRESULT:");
		try {
			cpn = cstmrFcd.getPurchasedCouponByTitle(null);
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
				
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cstmrFcd.getPurchasedCouponByTitle(\"blabla\");");

		System.out.println("\nRESULT:");
		try {
			cpn = cstmrFcd.getPurchasedCouponByTitle("blabla");
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cstmrFcd.getPurchasedCouponByTitle(\"DutyFree\");");

		System.out.println("\nRESULT:");
		try {
			cpn = cstmrFcd.getPurchasedCouponByTitle("DutyFree");
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
				
		
		System.out.println("COMMAND: \n"
				+ "cpn = cstmrFcd.getPurchasedCouponByTitle(\"SuperPharm\");");

		System.out.println("\nRESULT:");
		try {
			cpn = cstmrFcd.getPurchasedCouponByTitle("SuperPharm");
			System.out.println(cpn.toString());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByExpirationDate(null);");

		System.out.println("\nRESULT:");
		try {
			coupons = cstmrFcd.getAllPurchasedCouponsByExpirationDate(null);
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "date = new GregorianCalendar(2014, Calendar.JUNE, 21).getTimeInMillis();\n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByExpirationDate(new Date(date));");

		System.out.println("\nRESULT:");
		try {
			date = new GregorianCalendar(2014, Calendar.JUNE, 21).getTimeInMillis();
			coupons = cstmrFcd.getAllPurchasedCouponsByExpirationDate(new Date(date));
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "date = new GregorianCalendar(2020, Calendar.JUNE, 21).getTimeInMillis();\n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByExpirationDate(new Date(date));");

		System.out.println("\nRESULT:");
		try {
			date = new GregorianCalendar(2020, Calendar.JUNE, 21).getTimeInMillis();
			coupons = cstmrFcd.getAllPurchasedCouponsByExpirationDate(new Date(date));
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();\n"
				+ "coupons = cstmrFcd.getAllPurchasedCouponsByExpirationDate(new Date(date));");

		System.out.println("\nRESULT:");
		try {
			date = new GregorianCalendar(2016, Calendar.NOVEMBER, 26).getTimeInMillis();
			coupons = cstmrFcd.getAllPurchasedCouponsByExpirationDate(new Date(date));
			cpnsIt  = coupons.iterator();
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				System.out.println(cpn.toString());
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//-----------------------------------------------------------------------
		//Test of purchaseCoupon method:

		System.out.println("Test of purchaseCoupon method:");
		System.out.println();
		
		cpnDbDao.showCouponTable();
		System.out.println();
		cstmrCpnDbDao.showCustomerCouponTable();
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = null;\n"
				+ "cstmrFcd.purchaseCoupon(cpn);");

		System.out.println("\nRESULT:");
		try {
			cpn = null;
			cstmrFcd.purchaseCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = new Coupon();\n"
				+ "cstmrFcd.purchaseCoupon(cpn);");

		System.out.println("\nRESULT:");
		try {
			cpn = new Coupon();
			cstmrFcd.purchaseCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cpnDbDao.getCoupon(2);\n"
				+ "cstmrFcd.purchaseCoupon(cpn);");

		System.out.println("\nRESULT:");
		try {
			cpn = cpnDbDao.getCoupon(2);
			cstmrFcd.purchaseCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cpnDbDao.getCoupon(3);\n"
				+ "cstmrFcd.purchaseCoupon(cpn);");

		System.out.println("\nRESULT:");
		try {
			cpn = cpnDbDao.getCoupon(3);
			cstmrFcd.purchaseCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		//Throws exception
		System.out.println("COMMAND: \n"
				+ "cpn = cpnDbDao.getCoupon(5);\n"
				+ "cstmrFcd.purchaseCoupon(cpn);");

		System.out.println("\nRESULT:");
		try {
			cpn = cpnDbDao.getCoupon(5);
			cstmrFcd.purchaseCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		System.out.println("COMMAND: \n"
				+ "cpn = cpnDbDao.getCoupon(4);\n"
				+ "cstmrFcd.purchaseCoupon(cpn);");

		System.out.println("\nRESULT:");
		try {
			cpn = cpnDbDao.getCoupon(4);
			cstmrFcd.purchaseCoupon(cpn);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println();
		
		
		cpnDbDao.showCouponTable();
		System.out.println();
		cstmrCpnDbDao.showCustomerCouponTable();
		System.out.println();
		
				
		
//		System.out.println("COMMAND: \n"
//				+ "");
//
//		System.out.println("\nRESULT:");
//		try {
//
//		} catch (CouponSystemException e) {
//			System.out.println(e.getMessage());
//		}		
//		System.out.println();
		
		cms.shutdown();
		
		//******************************************************************
		System.out.println("\n*****************");
		System.out.println("END OF TEST");
		System.out.println("*****************");
	}
}
