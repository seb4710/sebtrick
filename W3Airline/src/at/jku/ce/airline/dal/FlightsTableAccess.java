package at.jku.ce.airline.dal;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import at.jku.ce.airline.service.Airport;
import at.jku.ce.airline.service.Flight;
import at.jku.ce.airline.service.Time;

public class FlightsTableAccess {
 private static final String DB_URL = "jdbc:mysql://w3airline.noip.me:3306/Ceue";

 //  Database credentials
 private static final String USER = "ceue";
 private static final String PASS = "bCy8LuvzJzZJN4bT";
 
 private static List<Flight> flights = new ArrayList<Flight>();
 private static List<Airport> airports = new ArrayList<Airport>();
 
 public static List<Flight> getFlights() {
	 java.sql.Connection conn = null;
	 Statement stmt = null;
	 try{
	    Class.forName("com.mysql.jdbc.Driver");
	    conn = DriverManager.getConnection(DB_URL,USER,PASS);
	    stmt = (Statement) conn.createStatement();
	    String sql;
	    sql = "SELECT * from Flights";
	    ResultSet rs = stmt.executeQuery(sql);
	   
	    //creating flight and airport objects
		Airport arrivalAirport;
		Airport departureAirport;
		Flight flight;
		Time arrivalTime;
		Time departureTime;
	
	    while(rs.next()){

			arrivalAirport = new Airport();
			departureAirport = new Airport();
			flight = new Flight();
			arrivalTime = new Time();
			departureTime = new Time();
			
	    	//set icao of arrival and departure airport
	    	arrivalAirport.setIcao(rs.getString("ArrivesAt"));
	    	departureAirport.setIcao(rs.getString("DepartesFrom"));
	    	
	    	//set arrival and departure time
	    	arrivalTime.setIndexDayOfWeek(rs.getInt("ArrivalDay"));
	    	arrivalTime.setTimeOfDay(rs.getInt("ArrivalTime"));
	    	departureTime.setIndexDayOfWeek(rs.getInt("DepartureDay"));
	    	departureTime.setTimeOfDay(rs.getInt("DepartureTime"));
	    	
	    	//set airports and time
	    	flight.setArrivesAt(arrivalAirport);
	    	flight.setDepartesFrom(departureAirport);
	    	flight.setArrivalTime(arrivalTime);
	    	flight.setDepartureTime(departureTime);
	    	
	    	//set remaining flight attributes
	    	flight.setFlightId(rs.getString("FlightId"));
	    	flight.setPlaneName(rs.getString("PlaneName"));
	    	flight.setStdFee(new BigDecimal(rs.getDouble("StdFee")));
	    	
	    	//add flight to list
	    	flights.add(flight);
	    }
	    rs.close();
	    
	    sql = "SELECT * from Airports";
	    rs = null;
	    stmt = (Statement) conn.createStatement(); 
	    rs = stmt.executeQuery(sql);
	    
	    Airport airport;
	    
	    while(rs.next()){
	    	
	    	airport = new Airport();
	    	airport.setAirportTax(new BigDecimal(rs.getDouble("airportTax")));
	    	airport.setCity(rs.getString("City"));
	    	airport.setCountry(rs.getString("Country"));
	    	airport.setIcao(rs.getString("Icao"));
	    	airport.setName(rs.getString("Name"));
	    	
	    	airports.add(airport);
	    }
	    
	    for(Flight f : flights){
	    	for(Airport a : airports){
	    		if(f.getDepartesFrom().getIcao().equalsIgnoreCase(a.getIcao())){
	    			f.setDepartesFrom(a);
	    		}
	    		if(f.getArrivesAt().getIcao().equalsIgnoreCase(a.getIcao())){
	    			f.setArrivesAt(a);
	    		}
	    	}
	    }
	    
	    rs.close();
	    stmt.close();
	    conn.close();
	 }catch(SQLException se){
	    //Handle errors for JDBC
	    se.printStackTrace();
	 }catch(Exception e){
	    //Handle errors for Class.forName
	    e.printStackTrace();
	 }finally{
	    //finally block used to close resources
	    try{
	       if(stmt!=null)
	          stmt.close();
	    }catch(SQLException se2){
	    }// nothing we can do
	    try{
	       if(conn!=null)
	          conn.close();
	    }catch(SQLException se){
	       se.printStackTrace();
	    }//end finally try
	 }//end try
	 return flights;
 }
 
//testing the connection & results 
// public static void main (String[] args){
//	 FlightsTableAccess fta = new FlightsTableAccess();
//	 List<Flight> flights = fta.getFlights();
//	 
//	 for(Flight f : flights){
//		 System.out.println("---- Flight ----");
//		 System.out.println(f.getFlightId());
//		 System.out.println(f.getPlaneName());
//		 System.out.println(f.getStdFee());
//		 System.out.println("---- Arrives At ----");
//		 System.out.println(f.getArrivesAt().getCity());
//		 System.out.println(f.getArrivesAt().getCountry());
//		 System.out.println(f.getArrivesAt().getIcao());
//		 System.out.println(f.getArrivesAt().getName());
//		 System.out.println(f.getArrivesAt().getAirportTax());
//		 System.out.println("---- Departes From ----");
//		 System.out.println(f.getDepartesFrom().getCity());
//		 System.out.println(f.getDepartesFrom().getCountry());
//		 System.out.println(f.getDepartesFrom().getIcao());
//		 System.out.println(f.getDepartesFrom().getName());
//		 System.out.println(f.getDepartesFrom().getAirportTax());
//		 System.out.println("---- Arrives On ----");
//		 System.out.println(f.getArrivalTime().getIndexDayOfWeek());
//		 System.out.println(f.getArrivalTime().getTimeOfDay());
//		 System.out.println("---- Departes On ----");
//		 System.out.println(f.getDepartureTime().getIndexDayOfWeek());
//		 System.out.println(f.getDepartureTime().getTimeOfDay());
//	 }
// }
}

