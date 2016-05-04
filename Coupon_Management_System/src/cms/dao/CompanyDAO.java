package cms.dao;

import java.util.Collection;

import cms.beans.Company;
import cms.beans.Coupon;
import cms.main.CouponSystemException;

public interface CompanyDAO {
	
	public long createtCompany(Company cmpny) throws CouponSystemException;
	
	public void removeCompany(Company cmpny) throws CouponSystemException;
	
	public void updateCompany(Company cmpny) throws CouponSystemException;
	
	public Company getCompany(long id) throws CouponSystemException;
	
	public Company getCompanyByName(String cmpnyName) throws CouponSystemException;

	public Company getCompanyByCouponID(long cpnId) throws CouponSystemException;
	
	public Collection<Company> getAllCompanies() throws CouponSystemException;

	public Collection<Coupon> getCouponsByCompanyID(long cmpnyId) throws CouponSystemException;
	
	public boolean checkIfCompanyExistsInTable (long cmpnyId) throws CouponSystemException;
	
}
