package cms.test;


import cms.main.DailyCouponExpirationTask;

public class DailyCouponExpirationTaskTest {
	public static void main(String[] args) throws InterruptedException {
			
		DailyCouponExpirationTask dcxt = new DailyCouponExpirationTask();
		//instantiating the object as a Thread 
		Thread thrd = new Thread(dcxt, "thrd");
		
		thrd.start();
		
		Thread.sleep(10000);
		
		dcxt.stopTask();
		
	}
}
