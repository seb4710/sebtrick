package at.jku.ce.airline.data;

/**
 * class for inserting or deleting bookings in passengerbookingrecord table
 */


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import at.jku.ce.airline.business.AirlineFlights;


public class PassengerBookingRecordTransaction {

	/**
	 * inserts a booking into PassingerBookingRecord table
	 * @param pbr entry for passengerbookingrecord table
	 * @return true if entry successful, else false
	 */
	public boolean insertInto(PassengerBookingRecordEntry pbr){
	
		Connection con = null;
		Statement stm = null;
		String sqlStm = null;
		
		try{
			con = this.getDBConnection();
			
			//the sql statement
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
			
	/**
	 * deletes a booking from PassengerBookingRecord table
	 * @param uuid_booking booking we want to delete
	 * @return list of AirlineFlights containing all associated flights needed for deleting the booking in flightbookingrecord table
	 */
	public List<AirlineFlights> deleteFrom(String uuid_booking){
		Connection con = null;
		Statement stm = null;
		String sqlStm = null;
		List<AirlineFlights> cancelParameter = getCancelParameter(uuid_booking);
		
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
		
	/**
	 * retrieves flightId and airline name of flight which is to be deleted. 
	 * flightId and name are needed for airline to delete entry in flightbookingrecord table
	 * @param uuid_booking booking we want to delete
	 * @return list of AirlineFlights containing all associated flights needed for deleting the booking in flightbookingrecord table
	 */
	private List<AirlineFlights> getCancelParameter(String uuid_booking){
		Connection con = null;
		Statement stm = null;
		String sqlStm = null;
		List<AirlineFlights> cancelParameter = new ArrayList<AirlineFlights>();
		
		try{
			con = this.getDBConnection();			
			sqlStm = "select id_flight, airline from flightBookingRecord where uuid_booking = '" + uuid_booking + "';";
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sqlStm);					
			while(rs.next()){
				cancelParameter.add(new AirlineFlights(rs.getString("airline"),rs.getString("id_flight")));
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
		
		
	/**
	 * establishes a connection to the airlineDB	
	 * @return the connection
	 * @throws SQLException
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

	

