package cms.test;

import java.util.Collection;
import java.util.Iterator;

import cms.beans.Company;
import cms.beans.Coupon;
import cms.beans.Customer;
import cms.dbdao.CompanyCouponDBDAO;
import cms.dbdao.CompanyDBDAO;
import cms.dbdao.CouponDBDAO;
import cms.dbdao.CustomerCouponDBDAO;
import cms.dbdao.CustomerDBDAO;
import cms.facade.AdminFacade;
import cms.main.CouponSystemException;

public class AdminFacadeTest {
	public static void main(String[] args) throws CouponSystemException {
		//-----------------------------------------------------------------------
		//Initialisation:
		
		CompanyDBDAO cmpnyDbDao = new CompanyDBDAO();
		CouponDBDAO cpnDbDao = new CouponDBDAO();
		CustomerDBDAO cstmrDbDao = new CustomerDBDAO();
		CompanyCouponDBDAO cmpnycpnDbDao = new CompanyCouponDBDAO();
		CustomerCouponDBDAO cstmrcpnDbDao = new CustomerCouponDBDAO();
		
		Collection<Company> companies = null;
		Iterator<Company> cmpnysIt  = null;
		Company cmpny = null;

		Collection<Customer> customers = null;
		Iterator<Customer> cstmrsIt  = null;
		Customer cstmr = null;
		
		Collection<Coupon> coupons = null;
		Iterator<Coupon> cpnsIt  = null;
		Coupon cpn = null;
		
//		cpnDbDao.showCouponTable();
//		cmpnyDbDao.showCompanyTable();
//		cmpnycpnDbDao.showCompanyCouponTable();
//		cstmrDbDao.showCustomerTable();
//		cstmrcpnDbDao.showCustomerCouponTable();
		
		AdminFacade admnFacade = new AdminFacade();
		
		//-----------------------------------------------------------------------
		//Test of login method:
		
		admnFacade.login("admin", "1234");
		
		//-----------------------------------------------------------------------
		//Test of get company methods:
		
//		cmpny = admnFacade.getCompany(3);
//		System.out.println(cmpny.toString());
//
//		companies = admnFacade.getAllCompanies();
//		cmpnysIt = companies.iterator();
//		
//		while(cmpnysIt.hasNext()) {
//			System.out.println(cmpnysIt.next().toString());
//		}
		
		
		//-----------------------------------------------------------------------
		//Test of remove company method:
		
//		cmpny = admnFacade.getCompany(1);
//		admnFacade.removeCompany(cmpny);
		
		//-----------------------------------------------------------------------
		//Test of update company method:

//		cmpny = admnFacade.getCompany(2);
//		cmpny.setCompName("HP");
//		cmpny.setEmail("hp@mail.com");
//		cmpny.setPassword("99999");
//		admnFacade.updateCompany(cmpny);
		
		//-----------------------------------------------------------------------
		//Test of create company method:
  
//		cmpny = null;
//		cmpny = new Company("google", "14334", "google@gamil.com");
//
//		cmpny = new Company("Facebook", "14334", "facebook@fb.com");
//		admnFacade.createCompany(cmpny);
		
		//-----------------------------------------------------------------------
		//Test of get customer methods:
				
//		cstmr = admnFacade.getCustomer(1);
//		System.out.println(cstmr.toString());
//
//		customers = admnFacade.getAllCustomers();
//		cstmrsIt = customers.iterator();
//
//		while(cstmrsIt.hasNext()) {
//			System.out.println(cstmrsIt.next().toString());
//		}
				
		//-----------------------------------------------------------------------
		//Test of remove customer method:
		
		cstmrDbDao.showCustomerTable();
		cstmrcpnDbDao.showCustomerCouponTable();
		
		cstmr = admnFacade.getCustomer(4);
		admnFacade.removeCustomer(cstmr);
		
		cstmrDbDao.showCustomerTable();
		cstmrcpnDbDao.showCustomerCouponTable();
		
		//-----------------------------------------------------------------------
		//Test of update customer method:

//		cmpnyDbDao.showCompanyTable();
//
//		cstmr = admnFacade.getCustomer(3);
//		cstmr.setCustName("felix");
//		cstmr.setPassword("4534");
//		admnFacade.updateCustomer(cstmr);
//		
//		cmpnyDbDao.showCompanyTable();

		
		//-----------------------------------------------------------------------
		//Test of create customer method:
		  
//		cstmrDbDao.showCustomerTable();
//
//		cstmr = null;
//		cstmr = new Customer("Udi", "4234gfr");
//		
//		admnFacade.createCustomer(cstmr);
//
//		cstmrDbDao.showCustomerTable();

		//-----------------------------------------------------------------------

//		cpnDbDao.showCouponTable();
//		cmpnyDbDao.showCompanyTable();
//		cmpnycpnDbDao.showCompanyCouponTable();
//		cstmrDbDao.showCustomerTable();
//		cstmrcpnDbDao.showCustomerCouponTable();
		
		//-----------------------------------------------------------------------
		System.out.println("\nEND OF TEST");
	}
}
