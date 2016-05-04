package cms.beans;

import java.util.Collection;
import java.util.HashSet;


public class Customer implements Comparable<Object> {
	//******************************************************************
	//ATTRIBUTES
	
	private long id; //Customer ID.
	private String custName; //Customer name.
	private String password; //Customer password;
	private Collection<Coupon> coupons = new HashSet<>(); //Customer list of coupons.
	
	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR
	
	public Customer() {

	}
	
	public Customer(long id, String custName, String password, Collection<Coupon> coupons) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
		this.coupons = coupons;
	}
	
	
	//END OF CTOR
	//******************************************************************

	//******************************************************************
	//METHODS
	
	//Attributes getters & setters
	//id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = this.id == 0 ? id : this.id; //For preventing changes if already set.
	}

	//custName
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = this.custName == null ? custName : this.custName; //For preventing changes if already set.
	}

	//password
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//coupons
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	//Class toString
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password="
				+ password + ", coupons=" + coupons + "]";
	}


	@Override
	public int compareTo(Object obj) {
		Customer other = (Customer) obj;

		if (this.id < other.id) {
			return -1;
		} else if (this.id > other.id) {
			return 1;
		} else {
			return 0;
		}
	}
	
	
	//END OF METHODS
	//******************************************************************

	
	//END OF Customer
}
