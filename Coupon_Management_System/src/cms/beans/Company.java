package cms.beans;

import java.util.Collection;
import java.util.HashSet;

public class Company implements Comparable<Object> {
	//******************************************************************
	//ATTRIBUTES
	
	private long id; //Company ID.
	private String compName; //Company name.
	private String password; //Company password;
	private String email; //Company email;
	private Collection<Coupon> coupons = new HashSet<>(); //Company collection of coupons.
	
	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR
	
	public Company() {
		
	}
	
	public Company(Long id, String compName, String password, String email, Collection<Coupon> coupons) {
		super();
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
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

	//compName
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = this.compName == null ? compName : this.compName; //For preventing changes if already set.
	}

	//password
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		return "Company [id=" + id + ", compName=" + compName + ", password="
				+ password + ", email=" + email + ", coupons=" + coupons + "]";
	}

	@Override
	public int compareTo(Object obj) {
		Company other = (Company) obj;

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

	
	//END OF Company
}
