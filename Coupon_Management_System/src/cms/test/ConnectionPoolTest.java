package cms.test;

import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import cms.main.CouponSystemException;
import cms.utility.ConnectionPool;


public class ConnectionPoolTest {
	public static void main(String[] args) throws CouponSystemException {
		
		int thrdAmount = 7; //amount of threads that are pending connection.
		

	
		
		ConnectionPool cnctnPl = ConnectionPool.getInstance();
		
		
//		try {
//			cnctnPl = 
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//Connection cnctn1 = cnctnPl.getConnection();
		
		MyRunnable r = new MyRunnable(cnctnPl);
		
		Set<Thread> thrds = new HashSet<>();
		
		for (int i = 1; i <= thrdAmount; i++) {
			thrds.add(new Thread(r, "t"+ i));
		}
		
		for (Thread thrd : thrds) {
			thrd.start();
		}
		
//		try(Statement stmnt = cnctn1.createStatement();) {
//			stmnt.execute(arg0);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//int count = thrdAmount;
		int count = thrdAmount;
		while(count != 0) {
			count = thrdAmount;
			for (Thread thrd : thrds) {
				if(thrd.getState() == State.TERMINATED) {
					//System.out.println("Thread: '" + thrd.getName() + "' has been terminated.");
					//thrds.remove(thrd);
					count--;
				}
			}
		}
		
		System.out.println("MSG: All threads have been terminated.");
		
		
		cnctnPl.closeAllConnections();
		

	}
	
	public void showCompanyTable(Connection cnctn) {
		//Composition of an SQL statement for selecting the entire content of the Company table.
		String sql = "SELECT * FROM CostumerTable";
		
		//Creating an SQL statement object.
		try(Statement stmnt = cnctn.createStatement();) {
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
				System.out.print(resSet.getString(3) + "\t\t");
				System.out.println(resSet.getString(4));
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
