package at.jku.ce.airline.dal;

import java.math.BigDecimal;
import java.sql.*;


public class FlightBookingRecordTransaction {

	/*
	 * inserts a booking into flightBookingRecord table
	 */
	public boolean insertInto(FlightBookingRecordEntry fbr){
	
		Connection con = null;
		Statement stm = null;
		String sqlStm = null;
		
		try{
			con = this.getDBConnection();
			
			sqlStm = "insert into flightBookingRecord "
					+ "values ('" + fbr.getUuid_booking()  + "', '"
					+ fbr.getId_flight()  + "', '" + fbr.getFlight_date() + "', '"
					+ fbr.getAirline() + "', '" + fbr.getDep_airport_tax() + "', '"
					+ fbr.getDest_airport_tax() + "', '" + fbr.getService_tax() + "', '"
					+ fbr.getAir_fare() + "');";
	
			//System.out.println(sqlStm);
			
			stm = con.createStatement();
			stm.executeUpdate(sqlStm);
		}catch(SQLException ex){
			ex.printStackTrace();
			return false;
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
	 * deletes a booking from flightBookingRecord table
	 */
	
	public boolean deleteFrom(String uuid_booking, String id_flight){
		Connection con = null;
		Statement stm = null;
		String sqlStm = null;
		
		try{
		
			con = this.getDBConnection();			
			sqlStm = "delete from flightBookingRecord where uuid_booking = '"+ uuid_booking + "' and id_flight = '" + id_flight+"'";
			//System.out.println(sqlStm);	
			stm = con.createStatement();
			stm.execute(sqlStm);
		}catch(SQLException ex){
			ex.printStackTrace();
			return false;
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
			sqlStm = "select * from flightBookingRecord where uuid_booking = '4f403dc6-89e0-45ec-ac41-5c17c3214edd';";
			//System.out.println(sqlStm);	
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlStm);
			
		//	rs.next();
			while(rs.next()){
				System.out.println(rs.getString("uuid_booking"));
				System.out.println(rs.getString("id_flight"));
				System.out.println(rs.getDate("flight_date"));
				System.out.println(rs.getBigDecimal("dep_airport_tax"));
				System.out.println(rs.getBigDecimal("dest_airport_tax"));
				System.out.println(rs.getString("airline"));
				System.out.println(rs.getBigDecimal("service_tax"));
				System.out.println(rs.getBigDecimal("air_fare"));
			}
			
		}catch(SQLException ex){
			ex.printStackTrace();
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
	
	private void insertFsm(){
		Connection con = null;
		Statement stm = null;
		String sqlStm = null;
		
		try{
			con = this.getDBConnection();
			
		sqlStm = "insert into passengerBookingRecord "
					+ "values ('w3testbooking00001', 'test', 'test', '1235', 'testengine')";

	
			System.out.println(sqlStm);
			
			stm = con.createStatement();
			stm.executeUpdate(sqlStm);
			//isInserted = true;
		}catch(SQLException ex){
			ex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
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
			}
		}
	}
	
	public static void main(String[] args){
		FlightBookingRecordTransaction fbr = new FlightBookingRecordTransaction();
	
		/*	@SuppressWarnings("deprecation")
		Date d = new Date(2015, 01, 01);

		//fbr.insertFsm();
		FlightBookingRecordEntry fbre = 
				new FlightBookingRecordEntry("w3testbooking00001", "flight00001", d, new BigDecimal(127.0), new BigDecimal(139.0), new BigDecimal("12.0"));
						//					(String uuid_booking, String id_flight, Date flight_date, BigDecimal dep_airport_tax, BigDecimal dest_airport_tax, BigDecimal air_fare)		fbr.insertInto(fbre);
		fbr.insertInto(fbre);
	*/	
		fbr.getSample();
		
	}
	
}

