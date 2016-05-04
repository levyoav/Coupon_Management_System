package cms.facade;

import cms.main.CouponSystemException;


public interface CouponClientFacade {
	//******************************************************************
	//ATTRIBUTES

	
	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR
	

	//END OF CTOR
	//******************************************************************

	//******************************************************************
	//METHODS
	
	public boolean login(String userName, String pswd) throws CouponSystemException;
	
	
	//END OF METHODS
	//******************************************************************

	
	//END OF CouponClientFacade
}
