package cms.beans;

import java.util.Date;


public class Coupon implements Comparable<Object> {
	//******************************************************************
	//ATTRIBUTES
	
	private long id; //Coupon ID.
	private String title; //Coupon title.
	private Date startDate; //Coupon start date.
	private Date endDate; //Coupon end date.
	private int amount; //Coupon amount.
	private CouponType type; //Coupon type.
	private String message; //Coupon message.
	private double price; //Coupon price.
	private String image; //Coupon image link.
	
	
	//END OF ATTRIBUTES
	//******************************************************************

	//******************************************************************
	//CTOR
	
	public Coupon() {

	}

	public Coupon(long id, String title, Date startDate, Date endDate,
			int amount, CouponType type, String message, double price,
			String image) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}


	//END OF CTOR
	//******************************************************************

	//******************************************************************
	//METHODS
	
	//Attributes getters & setters.
	//id:
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = this.id == 0 ? id : this.id; //For preventing changes if already set.
	}

	//title
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	//startDate
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	//endDate
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	//amount
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	//type
	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	//message
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	//price
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	//image
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	//Class toString.
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate="
				+ startDate + ", endDate=" + endDate + ", amount=" + amount
				+ ", type=" + type + ", message=" + message + ", price="
				+ price + ", image=" + image + "]";
	}

	@Override
	public int compareTo(Object obj) {
		Coupon other = (Coupon) obj;

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

	
	//END OF Coupon
}
