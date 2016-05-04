package cms.utility;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import cms.beans.Company;
import cms.beans.Coupon;
import cms.beans.CouponType;
import cms.beans.Customer;
import cms.dbdao.CompanyCouponDBDAO;
import cms.dbdao.CompanyDBDAO;
import cms.dbdao.CouponDBDAO;
import cms.dbdao.CustomerCouponDBDAO;
import cms.dbdao.CustomerDBDAO;
import cms.main.CouponSystemException;

public class SQLPopulator {
	public static void main(String[] args) throws CouponSystemException {
		//******************************************************************
		CouponDBDAO cpnDbDao = new CouponDBDAO();
		CompanyDBDAO cmpnyDbDao = new CompanyDBDAO();
		CompanyCouponDBDAO cmpcpndbdao = new CompanyCouponDBDAO();
		CustomerDBDAO cstmrDbDao = new CustomerDBDAO();
		CustomerCouponDBDAO cstcpndbdao = new CustomerCouponDBDAO();
		
		//Dropping all tables
		cpnDbDao.dropCouponTable();
		cmpnyDbDao.dropCompanyTable();
		cstmrDbDao.dropCustomerTable();
		cmpcpndbdao.dropCompanyCouponTable();
		cstcpndbdao.dropCustomerCouponTable();

		//Creating all tables
		cpnDbDao.createCouponTable();
		cmpnyDbDao.createCompanyTable();
		cmpcpndbdao.createCompanyCouponTable();
		cstmrDbDao.createCustomerTable();
		cstcpndbdao.createCustomerCouponTable();

		
		//******************************************************************
		//Coupon table population

		String[] title = {"Groupon", "Discount", "DutyFree", "SuperPharm", "Sale", "Free",
						"Spa", "2 for 1", "Buy 1 get 1 free", "Shopping", "Rent a car", "Free entrance"};
		long[] sd = {
				(new GregorianCalendar(2015, Calendar.OCTOBER, 21)).getTimeInMillis(),
				(new GregorianCalendar(2015, Calendar.NOVEMBER, 26)).getTimeInMillis(),
				(new GregorianCalendar(2015, Calendar.DECEMBER, 1)).getTimeInMillis(),
				(new GregorianCalendar(2015, Calendar.OCTOBER, 10)).getTimeInMillis(),
				(new GregorianCalendar(2015, Calendar.NOVEMBER, 1)).getTimeInMillis(),
				(new GregorianCalendar(2015, Calendar.OCTOBER, 1)).getTimeInMillis(),
				(new GregorianCalendar(2015, Calendar.OCTOBER, 1)).getTimeInMillis(),
				(new GregorianCalendar(2015, Calendar.OCTOBER, 1)).getTimeInMillis(),
				(new GregorianCalendar(2015, Calendar.MARCH, 1)).getTimeInMillis(),
				(new GregorianCalendar(2015, Calendar.AUGUST, 1)).getTimeInMillis(),
				(new GregorianCalendar(2015, Calendar.JULY, 1)).getTimeInMillis(),
				(new GregorianCalendar(2015, Calendar.JANUARY, 1)).getTimeInMillis()
				};
		
		long[] ed = {
				(new GregorianCalendar(2016, Calendar.OCTOBER, 21)).getTimeInMillis(),
				(new GregorianCalendar(2016, Calendar.NOVEMBER, 26)).getTimeInMillis(),
				(new GregorianCalendar(2016, Calendar.DECEMBER, 1)).getTimeInMillis(),
				(new GregorianCalendar(2016, Calendar.OCTOBER, 10)).getTimeInMillis(),
				(new GregorianCalendar(2016, Calendar.NOVEMBER, 1)).getTimeInMillis(),
				(new GregorianCalendar(2016, Calendar.OCTOBER, 1)).getTimeInMillis(),
				(new GregorianCalendar(2016, Calendar.OCTOBER, 1)).getTimeInMillis(),
				(new GregorianCalendar(2016, Calendar.OCTOBER, 1)).getTimeInMillis(),
				(new GregorianCalendar(2016, Calendar.MARCH, 1)).getTimeInMillis(),
				(new GregorianCalendar(2016, Calendar.AUGUST, 1)).getTimeInMillis(),
				(new GregorianCalendar(2016, Calendar.JULY, 1)).getTimeInMillis(),
				(new GregorianCalendar(2016, Calendar.JANUARY, 1)).getTimeInMillis()
				};
		
		int[] amount = {20, 10, 3, 100, 50, 20, 20, 30, 5, 40, 100, 200};
		CouponType[] type = {CouponType.ELECTRICITY, CouponType.CAMPING, CouponType.FOOD, CouponType.SPORTS, CouponType.HEALTH, CouponType.RESTAURANTS,
							CouponType.TRAVELLING, CouponType.ELECTRICITY, CouponType.CAMPING, CouponType.FOOD, CouponType.SPORTS, CouponType.HEALTH};
		String[] message = {"All half price!", "Tell a friend", "Like us on facebook", "Hello", "Dont miss!", "Thank you for buying",
							"50% off!", "Visit our website", "Come all", "Open all week", "Bring a friend", "Free parking"};
		double[] price = {200.00, 100.00, 50.00, 100.00, 250.00, 200.00, 100.00, 50.00, 100.00, 250.00, 50.00, 120.00};
		String[] image = {"http//:facebook.com", "image.jpg", "Photo", "picture", "tmona", "http//:facebook.com", 
							"image.jpg", "Photo", "picture", "tmona", "blavbla", "snapshot"};
		
		Coupon cpn = null;
		
		for (int i = 0; i < title.length; i++) {
			cpn = new Coupon(0L, title[i], new Date(sd[i]), new Date(ed[i]), amount[i], type[i], message[i], price[i], image[i]);
			cpnDbDao.createCoupon(cpn);
		}
		
		
		
		//******************************************************************
		//Company table population.
						
		String[] cmpnyName = {"Teva", "Apple", "Google", "Intel"};
		String[] cmpnyPswd = {"1234", "abcd", "0000", "ck39f"};
		String[] email = {"teva@mail.com", "apple@mail.com", "google@gamil.com", "intel@mail.com"};

		Company cmpny;

		for (int i = 0; i < cmpnyName.length; i++) {
			cmpny = new Company(0L, cmpnyName[i], cmpnyPswd[i], email[i], null);
			cmpnyDbDao.createtCompany(cmpny);
		}
		
		
		//******************************************************************
		//Company Coupon table population
						
		Collection<Company> companies = cmpnyDbDao.getAllCompanies();
				
		Iterator<Company> cmpIt = companies.iterator();
		
		//Collection<Long> cmpIds = new HashSet<>();
		long [] cmpIds = new long[companies.size()];
		int l = 0;
		while(cmpIt.hasNext()) {
			cmpIds[l] = cmpIt.next().getId();
			l++;
		}
		
		Collection<Coupon> coupons = cpnDbDao.getAllCoupons();
		
		Iterator<Coupon> cpnIt = coupons.iterator();
		
		//Collection<Long> cmpIds = new HashSet<>();
		long [] cpnIds = new long[coupons.size()];
		l = 0;
		while(cpnIt.hasNext()) {
			cpnIds[l] = cpnIt.next().getId();
			l++;
		}
		
		cmpcpndbdao.insertRow(1, 2);
		cmpcpndbdao.insertRow(1, 7);
		cmpcpndbdao.insertRow(1, 10);
		cmpcpndbdao.insertRow(2, 6);
		cmpcpndbdao.insertRow(2, 9);
		cmpcpndbdao.insertRow(2, 11);
		cmpcpndbdao.insertRow(3, 3);
		cmpcpndbdao.insertRow(3, 5);
		cmpcpndbdao.insertRow(3, 8);
		cmpcpndbdao.insertRow(4, 1);
		cmpcpndbdao.insertRow(4, 4);
		cmpcpndbdao.insertRow(4, 12);

		
//		for (int i = 0; i < cmpIds.length; i++) {
//			for (int j = 0; j < (cpnIds.length/cmpIds.length); j++) {
//				boolean flag = true;
//				while (flag) {
//					try {
//						cmpcpndbdao.insertRow(cmpIds[i], ((int)(Math.random()*(cpnIds.length)) + 1));
//						//cmpcpndbdao.insertRow(cmpIds[i], 5);
//						flag = false;
//					} catch (CouponSystemException e){
//						System.out.println(e.getMessage());
//					}
//				}
//			}
//		}
		
		
		//******************************************************************
		//Customer table population
		
		String[] cstmrName = {
							"Yossi", "Eli", "Avi", "Moshe", "Beny",
							"Yoav", "David", "Asi", "Tal", "Itik",
							"Yaniv", "Ami", "Eldad", "Baruch", "Zion",
							"Dan", "Hagar", "Hila", "Yaron", "Meital"
							};
		
		String[] cstmrPswd = {
				"vdfas", "45tgw", "43erg", "jydgd", "abcd",
				"vbgtj", "sdfraj", "feauh", "83fhv", "askdv",
				"nvfjdw", "freerf", "efaaa", "3fj2", "4gf43q",
				"cvdsf", "39vje", "3aouv", "3fahha", "3aw3ha"
		};
		
//		String cstmrPswd;
//		
//		String[] chr = {
//				"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
//				"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
//				"0","1","2","3","4","5","6","7","8","9"
//				};
	
		Customer cstmr;

		for (int i = 0; i < cstmrName.length; i++) {			
			cstmr = new Customer(0L, cstmrName[i], cstmrPswd[i], null);
			cstmrDbDao.createCustomer(cstmr);
		}
		
		
//		for (int i = 0; i < cstmrName.length; i++) {
//			cstmrPswd = "";
//			int k;
//			for (int j = 0; j < 8; j++) {
//				k = (int) (Math.random()*(chr.length));
//				cstmrPswd = concat(cstmrPswd, chr[k]);
//			}
//			
//			cstmr = new Customer(cstmrName[i], cstmrPswd.toString());
//			cstmrDbDao.createCustomer(cstmr);
//		}
		
		//******************************************************************
		//Customer Coupon table population
						
		Collection<Customer> customers = cstmrDbDao.getAllCustomers();
				
		Iterator<Customer> cstIt = customers.iterator();
		
		//Collection<Long> cmpIds = new HashSet<>();
		long [] cstIds = new long[customers.size()];
		int m = 0;
		while(cstIt.hasNext()) {
			cstIds[m] = cstIt.next().getId();
			m++;
		}
		
//		Collection<Coupon> coupons = cpnDbDao.getAllCoupons();
//		
//		Iterator<Coupon> cpnIt = coupons.iterator();
//		
//		long [] cpnIds = new long[coupons.size()];
//		l = 0;
//		while(cpnIt.hasNext()) {
//			cpnIds[l] = cpnIt.next().getId();
//			l++;
//		}
		
//		for (int i = 0; i < cmpIds.length; i++) {
//			for (int j = 0; j < cpnIds.length; j++) {
//				System.out.println(cmpIds[i] + " " + cpnIds[j]);
//			}
//		}
		
		
		long[] cstmrClmn = {1,	1,	1,	1,	1,	
							2,	2,	2,	2,	2,	
							3,	3,	3,	3,	3,	
							4,	4,	4,	4,	4,	
							5,	5,	5,	5,	5,	
							6,	6,	6,	6,	
							7,	7,	7,	7,	7,	
							8,	8,	8,	8,	8,	
							9,	9,	9,	9,	
							10,	10,	10,	
							11,	11,	11,	11,	
							12,	12,	12,	12,	
							13,	13,	13,	13,	13,	
							14,	14,	14,	14,	
							15,	15,	15,	15,	
							16,	16,	16,	16,	16,	
							17,	17,	
							18,	18,	18,	
							19,	19,	19,	19,	
							20,	20,	20,	20};
		
		long[] cpnClmn = {2, 7,	9, 10, 12, 1,
						3,	4,	7,	11,	2,	3,	
						5,	9,	10,	1,	2,	7,	
						8,	10,	2,	4,	8,	11,	
						12,	2,	3,	10,	11,	8,	
						9,	10,	11,	12,	1,	2,	
						5,	9,	10,	1,	2,	4,	
						9,	1,	5,	6,	1,	5,	
						10,	11,	1,	2,	11,	12,	
						1,	4,	5,	8,	10,	1,	
						6,	7,	12,	1,	5,	6,	
						12,	2,	4,	7,	8,	11,	
						1,	2,	5,	6,	8,	4,	
						5,	10,	11,	5,	7,	11,	12};

		System.out.println("Coupon table before coupon sale:");
		cpnDbDao.showCouponTable();
		
		int amnt;
		int cnt = 0;
		
		for (int i = 0; i < cstmrClmn.length; i++) {
			cstcpndbdao.insertRow(cstmrClmn[i], cpnClmn[i]);
			cpn = cpnDbDao.getCoupon(cpnClmn[i]);
			amnt = cpn.getAmount();
			amnt--;
			cpn.setAmount(amnt);
			cpnDbDao.updateCoupon(cpn);
			cnt++;
		}

//		Arrays.sort(cstIds);
//		long id = 0;
//		int amnt;
//		int cnt = 0;
//		int maxCpnPrCstmr = 5;
//		for (int i = 0; i < cstIds.length; i++) {
//			for (int j = 0; j < maxCpnPrCstmr ; j++) {
//				boolean flag = true;
//				while (flag) {
//					try {
//						id = ((int)(Math.random()*(cpnIds.length)) + 1);
//						cpn = cpnDbDao.getCoupon(id);
//						
//						amnt = cpn.getAmount();
//
//						if (amnt == 0) {
//							System.out.println("ERROR: The coupon '" + cpn.getTitle() + "' is out of stock. please buy another coupon.");
//							flag = false;
//						} else {
//							amnt--;
//							cstcpndbdao.insertRow(cstIds[i], id);
//							cnt++;
//						//cpnDbDao.showCouponTable();
//
//							cpn = cpnDbDao.getCoupon(id);
////						System.out.println(cpn.getAmount());
////						cpnDbDao.decrementCouponAmount(cpn);
////						cpnDbDao.showCouponTable();
//
//						//System.out.println(cpn.toString());
//							cpn.setAmount(amnt);
//						//System.out.println(cpn.toString());
//							cpnDbDao.updateCoupon(cpn);
//							flag = false;
//						}
//					} catch (CouponSystemException e){
//						//System.out.println(e.getMessage());
//						//e.printStackTrace();
//					}
//					
//				}
//			}
//		}
		
		System.out.println("Coupon table after selling " + cnt + " coupons:");
		cpnDbDao.showCouponTable();
//		System.out.println("total sold: " + cnt);

		cmpnyDbDao.showCompanyTable();
		cmpcpndbdao.showCompanyCouponTable();

		cstmrDbDao.showCustomerTable();
		cstcpndbdao.showCustomerCouponTable();
		
//		int sum = 0;
//		for (int i = 0; i < amount.length; i++) {
////			System.out.println(amount[i]);
//			sum += amount[i];
//		}
		
//		System.out.println(sum);
		
//		System.out.println(cpnClmn.length);
//		System.out.println(cstmrClmn.length);

	}
	
//	public static String concat(String a, String b) {
//		return a + b;
//	}
}
