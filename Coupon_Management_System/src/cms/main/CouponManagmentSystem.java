package cms.main;

import cms.beans.ClientType;
import cms.beans.Company;
import cms.beans.Customer;
import cms.dbdao.CompanyDBDAO;
import cms.dbdao.CustomerDBDAO;
import cms.facade.AdminFacade;
import cms.facade.CompanyFacade;
import cms.facade.CouponClientFacade;
import cms.facade.CustomerFacade;
import cms.utility.ConnectionPool;


public class CouponManagmentSystem {
	//******************************************************************
	//ATTRIBUTES
	
	private static CouponManagmentSystem instance = null;
	DailyCouponExpirationTask dlycCpnXprTsk = new DailyCouponExpirationTask();
	Thread dcxtThread = new Thread(dlycCpnXprTsk, "dcxtThread");
	ConnectionPool cnctnPl =  ConnectionPool.getInstance();
	
	CompanyDBDAO cmpnyDbDao = new CompanyDBDAO();
	CustomerDBDAO cstmrDbDao = new CustomerDBDAO();
	
	//END OF ATTRIBUTES
	//******************************************************************
	
	//******************************************************************
	//CTOR
	
	private CouponManagmentSystem() throws CouponSystemException {
		super();
//		dcxtThread.start();
	}
	
	//------------------------------------------------------------------
	
	public static CouponManagmentSystem getInstance() throws CouponSystemException {
		if (instance == null) {
			instance = new CouponManagmentSystem() ;
		}
		
		return instance;
	}
	
	
	//END OF CTOR
	//******************************************************************
	
	//******************************************************************
	//METHODS
	
	public CouponClientFacade login(String userName, String pswd, ClientType clntTyp) throws CouponSystemException {
		
		CouponClientFacade cpnClntFcd = null;
		
		switch (clntTyp) {
			case ADMIN:
				AdminFacade admnFacade = new AdminFacade();
				if(admnFacade.login(userName, pswd)) {
					cpnClntFcd = admnFacade;
				}
				
				break;
				
			case COMPANY:
				Company cmpny = this.cmpnyDbDao.getCompanyByName(userName);
				
				if (cmpny == null) {
					throw new CouponSystemException("ERROR: No company with the name '" + userName +"' is listed in the database. "
													+ "Please enter a valid company name.");				}
				
				CompanyFacade cmpnyFacade = new CompanyFacade(cmpny.getId(), cmpny.getCompName());
				if(cmpnyFacade.login(userName, pswd)) {
					cpnClntFcd = cmpnyFacade;
				}
				
				break;
				
			case CUSTOMER:
				Customer cstmr = this.cstmrDbDao.getCustomerByName(userName);
				
				if(cstmr == null) {
					throw new CouponSystemException("ERROR: No customer with the user name '" + userName +"' is listed in the database. "
													+ "Please enter a valid customer user name.");
				}
				
				CustomerFacade cstmrFacade = new CustomerFacade(cstmr.getId(), cstmr.getCustName());
				if(cstmrFacade.login(userName, pswd)) {
					cpnClntFcd = cstmrFacade;
				}
				
				break;
				
			default:
				admnFacade = new AdminFacade();
				if(admnFacade.login(userName, pswd)) {
					cpnClntFcd = admnFacade;
				}
				
				break;
		} 
		
		return cpnClntFcd;
	}
	
	
	//------------------------------------------------------------------
	
	public void shutdown() throws CouponSystemException{
		dlycCpnXprTsk.stopTask();
		cnctnPl.closeAllConnections();
	}
	
	
	//END OF METHODS
	//******************************************************************
	
	
	//END OF CouponManagmentSystem
}
