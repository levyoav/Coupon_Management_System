package cms.utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cms.main.CouponSystemException;


//Handles generic operations on the SQL table.
public class SqlTableHandler {
		
	/**
	 * @param cnctn
	 * @param tblName
	 * @param idClmnName
	 * @return
	 * @throws CouponSystemException
	 * Generates an ID for a new row inserted to an SQL table.
	 */
	public static long generateRowId(Connection cnctn, String tblName, String idClmnName) throws CouponSystemException {
		long genId = 0;
		long rows = 0;

		try(Statement stmnt = cnctn.createStatement();) {
			String sql = "SELECT COUNT(" + idClmnName + ") FROM " + tblName;

			//Execution of the SQL statement.
			ResultSet resSet = stmnt.executeQuery(sql);
			resSet.next();
			rows = (Integer)resSet.getInt(1);

			//Setting the initial value for the generated ID to be the number of rows in the table.
			genId = rows;
			
			sql = "SELECT " + idClmnName + " FROM " + tblName;
			
			//Execution of the SQL statement.
			resSet = stmnt.executeQuery(sql);
			
			//List of all the existing row ID's in the table.
			List<Long> rowIdList = new ArrayList<Long>();
			//Going over the ID's in the table and adding each ID to the list.
			while(resSet.next()) {
				//Adding the current ID to the list.
				rowIdList.add(resSet.getLong(1));
			}
			
			if(rowIdList.isEmpty()) {
				genId = (long)1;
			} else {
				//Flag for notifying if the generated ID value exists in the table.
				boolean idExistFlag = true;
			
				while(idExistFlag) {
					//Reseting the flag after each ID search iteration in the ID list.
					idExistFlag = false;
					//Generating new ID value based on the number of rows in the table.
					genId++;
					
					for(Long id : rowIdList) {
						if (id == genId) {
							idExistFlag = true;
							break;
						}
					}
				}
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to generate ID for SQL table '" + tblName + "'.", e);
		}
		
		return(genId);	
	}
	
	//------------------------------------------------------------------

	/**
	 * @param cnctn
	 * @param tblName
	 * @param idClmnName
	 * @param id
	 * @throws CouponSystemException
	 * Deletes a row from a given SQL table as per a column name and value.
	 */
	public static void deleteFromTable(Connection cnctn, String tblName, String idClmnName, long id) throws CouponSystemException {
		try(Statement stmnt = cnctn.createStatement();) {
			//Composition of the SQL statement for row deletion.
			String sql = "DELETE FROM " + tblName + " WHERE " + idClmnName + "=" + id;
			
			//Execution of the SQL statement & storing its return value.
			stmnt.executeUpdate(sql);
			
		} catch (SQLException e) {
			throw new CouponSystemException("WARNNING: Failed to delete row with ID: " + id + " from table '" + tblName +"'.", e);
		}
	}

	//------------------------------------------------------------------
	
	/**
	 * @param cnctn
	 * @param tblName
	 * @param idClmnName
	 * @param id
	 * @param clmnName
	 * @param newVal
	 * @throws CouponSystemException
	 * Updates a row in a given SQL table in the database.
	 */
	public static void updateTable(Connection cnctn, String tblName, String idClmnName, long id, String clmnName, String newVal) throws CouponSystemException {		
		//Creating an SQL statement object:
		try(Statement stmnt = cnctn.createStatement();) {
			//Composition of the SQL statement for row update.
			String sql = "UPDATE " + tblName 
						+ " SET " + clmnName 
						+ "=" + newVal
						+ " WHERE " + idClmnName + "=" + id;
			
			//Execution of the SQL statement & storing its return value.
			stmnt.executeUpdate(sql);
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to update row with ID: " + id + " from table '" + tblName +"'.", e);
		}
	}
	
	//------------------------------------------------------------------

	/**
	 * @param cnctn
	 * @param tblName
	 * @param idClmnName
	 * @return
	 * @throws SQLException
	 * @throws CouponSystemException
	 * Returns a result set containing all the rows from an SQL table from the database.
	 */
	public static ResultSet getAllFromTable(Connection cnctn, String tblName, String idClmnName) throws SQLException,CouponSystemException {		
		//Creating an SQL statement object.
		Statement stmnt = cnctn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
		//Composition of an SQL statement for selecting all rows and returning them sorted by ascending ID values. 
		String sql = "SELECT * FROM " + tblName + " ORDER BY " + idClmnName + " ASC";
			
		//Execution of the SQL statement and returning the result set. 		
		return (stmnt.executeQuery(sql));
	}
	
	//------------------------------------------------------------------

	/**
	 * @param cnctn
	 * @param tblName
	 * @param clmnName
	 * @param clmnValue
	 * @param idClmnName
	 * @return
	 * @throws SQLException
	 * @throws CouponSystemException
	 * Returns a result set of all rows as per a column value from an SQL table.
	 */
	public static ResultSet getAllFromTableByCoulmnValue(Connection cnctn, String tblName, String clmnName, String clmnValue, String idClmnName) throws SQLException,CouponSystemException {		
		//Creating an SQL statement object.
		Statement stmnt = cnctn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
		//Composition of an SQL statement for selecting all rows.
		String sql = "SELECT * FROM " + tblName + " WHERE " + clmnName + "=" + clmnValue + " ORDER BY " + idClmnName + " ASC";
			
		//Printing out the SQL statement.
		//System.out.println("SQL: " + sql);
			
		//Execution of the SQL statement and returning the result set. 		
		return (stmnt.executeQuery(sql));
	}
	
	//------------------------------------------------------------------
	
	/**
	 * @param cnctn
	 * @param sql
	 * @param tblName
	 * @throws CouponSystemException
	 * 	Invoked from a unique table object. Creates the SQL table in the CSM data 
	 * base by receiving the SQL statement for creating the table from the 
	 * invoking unique table object. 
	 */
	public static void createSqlTable(Connection cnctn, String sql, String tblName) throws CouponSystemException {		
		//Creating an SQL statement object.
		try(Statement stmnt = cnctn.createStatement();) {
			//Execution of the SQL statement.
			stmnt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to create table '" + tblName + "' in the database.", e);
		}	
	}
	
	//------------------------------------------------------------------

	/**
	 * @param cnctn
	 * @param tblName
	 * @throws CouponSystemException
	 * Dropping the table from the CMS data base using an SQL statement.
	 */
	public static void dropTable(Connection cnctn, String tblName) throws CouponSystemException {
		//Composition of an SQL statement for dropping the table from the data base.
		String sql = "DROP TABLE " + tblName;
		
		//Creating an SQL statement object.
		try (Statement stmnt = cnctn.createStatement();) {
			//Execution of the SQL statement.
			stmnt.executeUpdate(sql);
			
		} catch (SQLException e) {
			throw new CouponSystemException("ERROR: Failed to drop table '" + tblName + "' from the database.", e);
		}
	}

}
