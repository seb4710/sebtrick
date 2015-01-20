package at.jku.ce.airline.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;


public class PassengerBookingRecordTransaction {

		/*
		 * inserts a booking into PassingerBookingRecord table
		 */
		public boolean insertInto(PassengerBookingRecordEntry pbr){
		
			Connection con = null;
			Statement stm = null;
			String sqlStm = null;
			
			try{
				con = this.getDBConnection();
				
				sqlStm = "insert into passengerBookingRecord "
						+ "values ('" + pbr.getUuid_booking()  + "', '"
						+ pbr.getFirstName()  + "', '" + pbr.getLastName() + "', '"
						+ pbr.getIdCardNr() + "', '"+ pbr.getFlightSearchEngine()+"');";
		
				
				
				stm = con.createStatement();
				stm.executeUpdate(sqlStm);
			}catch(Exception ex){
				ex.printStackTrace();
				return false;
			}finally{
				try{
					if (stm != null){
						stm.close();
					}
					if (con != null){
						con.close();
					}			
				}catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}
			
		
	/*
	 * deletes a booking from PassengerBookingRecord table
	 */
	public String[] deleteFrom(String uuid_booking){
		Connection con = null;
		Statement stm = null;
		String sqlStm = null;
		String[] cancelParameter = getCancelParameter(uuid_booking);
		
		try{
			con = this.getDBConnection();			
			sqlStm = "delete from passengerBookingRecord where uuid_booking = '"+ uuid_booking + "'";
			//System.out.println(sqlStm);	
			stm = con.createStatement();
			stm.execute(sqlStm);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			try{
				if (stm != null){
					stm.close();
				}
				if (con != null){
					con.close();
				}			
			}catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return cancelParameter;
	}
		
		
	/*
	 * retrieves flightId and airline name of flight which is to be deleted. 
	 * flightId and name are needed for airline to delete entry in flightbookingrecord table
	 */
	private String[] getCancelParameter(String uuid_booking){
		Connection con = null;
		Statement stm = null;
		String sqlStm = null;
		String[] cancelParameter = new String[2];
		
		try{
			con = this.getDBConnection();			
			sqlStm = "select id_flight, airline from flightBookingRecord where uuid_booking = '" + uuid_booking + "';";
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sqlStm);					
			if(rs.next()){
				cancelParameter[0] = rs.getString("id_flight");
				cancelParameter[1] = rs.getString("airline");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			try{
				if (stm != null){
					stm.close();
				}
				if (con != null){
					con.close();
				}			
			}catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return cancelParameter;
		
	}
		
		
	/*
	 * Connection Manager
	 */	
	private Connection getDBConnection() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:mysql://140.78.73.67:3306/airlineDB", "ceue", "ceair14db");
	}
		
	/*
	 * methods for testing fancy airlineDB
	 */
		public void getSample(){
			
			Connection con = null;
			Statement stmt = null;
			String sqlStm = null;
			
			try{
			
				con = this.getDBConnection();			
				sqlStm = "select * from passengerBookingRecord where uuid_booking = '20120152000ab';";
				//System.out.println(sqlStm);	
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sqlStm);
				
			//	rs.next();
				while(rs.next()){
					System.out.println(rs.getString("uuid_booking"));
					System.out.println(rs.getString("firstname"));
					System.out.println(rs.getString("lastname"));
					System.out.println(rs.getString("idCardNr"));
					System.out.println(rs.getString("flightSearchEngine"));
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{
					if (stmt != null){
						stmt.close();
					}
					if (con != null){
						con.close();
					}			
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		/*
		 * method for testing fancy airlineDB
		 */
		public static void main(String[] args){
			PassengerBookingRecordTransaction pbrt = new PassengerBookingRecordTransaction();
			PassengerBookingRecordEntry pbr = new PassengerBookingRecordEntry("20120152000ab", "Frodo", "Beutlin", "12345");
			
			//pbrt.insertInto(pbr);
			//pbrt.getSample();
			//pbrt.deleteFrom("20120152000ab");
			//pbrt.getSample();
		}
		
}

	

