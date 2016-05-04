package cms.test;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cms.beans.Coupon;
import cms.beans.CouponType;

public class CouponTest {
	public static void main(String[] args) {
		
		final String CPN_TBL_NAME = "Coupon_Table"; //Coupon table name.
		final String ID_CLMN_NAME = "ID"; //Coupon ID column name.
		final String TITLE_CLMN_NAME = "Title"; //Coupon title column name.
		final String START_DATE_CLMN_NAME = "Start_Date"; //Coupon start date column name.
		final String END_DATE_CLMN_NAME = "End_Date"; //Coupon start date column name.
		final String AMOUNT_CLMN_NAME = "Amount"; //Coupon amount column name.
		final String TYPE_CLMN_NAME = "Type"; //Coupon type column name.
		final String MESSAGE_CLMN_NAME = "Message"; //Coupon message column name.
		final String PRICE_CLMN_NAME = "Price"; //Coupon price column name.
		final String IMAGE_CLMN_NAME = "Image"; //Coupon image column name.
		
		final String clmnsNames[] = {TITLE_CLMN_NAME, START_DATE_CLMN_NAME,
								END_DATE_CLMN_NAME, AMOUNT_CLMN_NAME,
								TYPE_CLMN_NAME, MESSAGE_CLMN_NAME,
								PRICE_CLMN_NAME, IMAGE_CLMN_NAME};
		
		long sd = (new GregorianCalendar(2015, Calendar.OCTOBER, 10)).getTimeInMillis();
		long ed = (new GregorianCalendar(2016, Calendar.OCTOBER, 10)).getTimeInMillis();
		Date startDate = new Date(sd);
		Date endDate = new Date(ed);
		Coupon cpn = new Coupon(0, "bla", startDate, endDate, 6, CouponType.CAMPING, "hi", 300, "rrse");
		System.out.println(cpn.toString());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		String dateString = dateFormat.format(startDate);
		
		System.out.println(dateString);
		
		
		String cpnVals[] = {cpn.getTitle(), //Coupon title value. 
				dateFormat.format(cpn.getStartDate()), //Getting the start date as a string
				dateFormat.format(cpn.getEndDate()),
				Integer.toString(cpn.getAmount()), 
				cpn.getType().toString(),
				cpn.getMessage(), 
				Double.toString(cpn.getPrice()), 
				cpn.getImage()};
		
		long id = 1234;
		for (int i = 0; i < cpnVals.length; i++) {
			//Composition of the SQL statement for row update.
			String sql = "UPDATE " + CPN_TBL_NAME 
						+ " SET " + clmnsNames[i] 
						+ "='" + cpnVals[i]
						+ "' WHERE " + ID_CLMN_NAME + "=" + id;
		
			//Printing out the SQL statement.
			System.out.println("SQL: " + sql);

		}
		
	}

}
