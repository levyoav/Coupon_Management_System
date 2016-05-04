package cms.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cms.main.CouponSystemException;
import cms.utility.ConnectionPool;


public class MyRunnable implements Runnable {
	private ConnectionPool cnctnPl;
	
	public MyRunnable(ConnectionPool cnctnPl) {
		this.cnctnPl = cnctnPl;
	}
	
	

	@Override
	public void run() {
		for (int i = 1; i <=3; i++) {
		
			Connection cnctn;
			try {
				cnctn = cnctnPl.getConnection();
			
			
			//TEST
			//Composition of an SQL statement for selecting the entire content of the Company table.
			String sql = "SELECT * FROM CostumerTable";
			System.out.println(Thread.currentThread().getName());
			//Creating an SQL statement object.
			Statement stmnt = cnctn.createStatement();
				//Printing out the SQL statement.
				System.out.println("SQL: " + sql);
				//Execution of the SQL statement.
				ResultSet resSet = stmnt.executeQuery(sql);
				
				//Printing the Company table content.
				System.out.println("Shwoing the content of CostumerTable");
				
			
				
				//Printing the Company table rows.
				while(resSet.next()) {
					System.out.print(resSet.getInt(1) + "\t\t");
					System.out.print(resSet.getString(2) + "\t\t");
					System.out.println(resSet.getString(3));
					//System.out.println(resSet.getString(4));
				}
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
					
				cnctnPl.returnConnection(cnctn);
			} catch (SQLException|CouponSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//END OF TEST
			System.out.println("Thread '" + Thread.currentThread().getName() + "' has occupied a connection.");
			
			
			
			System.out.println("Thread '" + Thread.currentThread().getName() + "' has returned a connection.");
			
		}
		
		System.out.println("MSG: Thread '" + Thread.currentThread().getName() + "' has been terminated.");
	}

}
