package cms.main;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import cms.beans.Coupon;
import cms.dao.CompanyCouponDAO;
import cms.dao.CouponDAO;
import cms.dao.CustomerCouponDAO;
import cms.dbdao.CompanyCouponDBDAO;
import cms.dbdao.CouponDBDAO;
import cms.dbdao.CustomerCouponDBDAO;

public class DailyCouponExpirationTask implements Runnable {
	//******************************************************************
	//ATTRIBUTES
	
	private boolean quit = false;
	private final long ITERATION_CYCLE_TIME = 86400000L; //Iteration period of 24h represented in msec.
	//For testing - uncomment the following 2 lines as well as commenting out the line above. 
//	private final long ITERATION_CYCLE_TIME = 2000L; //Iterate every 2 sec instead of every 24h.
//	private final long MILI_SEC_IN_MONTH = 2592000000L; //A month period represented in msec.
	
	private long elapsedPeriod = 0L; //For capturing the elapsed period from the thread start up. 
	private Date currentTime = null; //For representing the current time in each iteration.

	private CouponDAO cpnDbDao = new CouponDBDAO();
	private CompanyCouponDAO cmpnycpnDbDao = new CompanyCouponDBDAO();
	private CustomerCouponDAO cstmrcpnDbDao = new CustomerCouponDBDAO();

	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR

	
	//END OF CTOR
	//******************************************************************

	//******************************************************************
	//METHODS
	
	@Override
	public void run() {
		long timeAtStartUp = (new Date()).getTime();
		long iterationCount = 0L;
		
		while(!quit) {
			//Capturing the elapsed period from thread start up for the current time calculation.
			elapsedPeriod = this.ITERATION_CYCLE_TIME*iterationCount; 
			//For testing - uncomment the following line as well as commenting out the line above. 
//			elapsedPeriod = this.MILI_SEC_IN_MONTH*iterationCount; //Simulation of a month period for each iteration.
			
			currentTime = new Date(timeAtStartUp + elapsedPeriod);
//			System.out.println(currentTime);
			
			Collection<Coupon> coupons = null;
			
			try {
				coupons = this.cpnDbDao.getAllCoupons();
			} catch (CouponSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Iterator<Coupon> cpnsIt = coupons.iterator();
			
			Coupon cpn = null;
			while(cpnsIt.hasNext()) {
				cpn = cpnsIt.next();
				 if(currentTime.after(cpn.getEndDate())) {
					 long cpnId = cpn.getId();
//					 System.out.println("Deleting coupon: " + cpnId + " - expierd at " + cpn.getEndDate());

					 try {
						this.cpnDbDao.removeCoupon(cpn);
						this.cmpnycpnDbDao.deleteCoupon(cpnId);
						this.cstmrcpnDbDao.deleteRowsByCouponID(cpnId);
					} catch (CouponSystemException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				 }
			}
			
			try {
				Thread.sleep(this.ITERATION_CYCLE_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			iterationCount++;
		}
	}
	
	//------------------------------------------------------------------
	
	public void stopTask() {
		this.quit = true;
	}
	
	
	//END OF METHODS
	//******************************************************************
	
	
	//END OF DailyCouponExpirationTask
}
