package at.jku.ce.airline.bl;

//import java.net.ConnectException;
//import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import at.jku.ce.airline.dal.FlightBookingRecordEntry;
import at.jku.ce.airline.dal.FlightBookingRecordTransaction;
import at.jku.ce.airline.dal.FlightsTableAccess;
//import at.jku.ce.juddi.*;
import at.jku.ce.airline.service.*;

public class AirlinePlatformService {

	private final String GROUP = "W3Airline";
//	private final String BUSINESSNAME = "AirlineService";
	private static Airline airline;
	private static AirlinePlatformService instance = null;
	public static List<Flight> flights;

	/*
	 * Constructor
	 */
	private AirlinePlatformService(){
	}
	
	/*
	 * Getter retrieves instance of AirlinePlatformService
	 */
	public static AirlinePlatformService getInstance(){
		if(instance == null){
			instance = new AirlinePlatformService();
			instance.initialize();
		}
		return instance;
	}
	
	/*
	 * Initializes new airline and publishes it
	 */
	private void initialize(){
		airline = new Airline();
		airline.setName(GROUP);
		
		System.out.println("retrieving flights");
		flights = FlightsTableAccess.getFlights();
		for(Flight f : flights){
			airline.getFlightPlan().add(f);
			System.out.println("adding flights");
		}
	}
	
	/*
	 * Cancel booking
	 */
	public boolean cancelBooking(String bookingUuid, String flightId){
		FlightBookingRecordTransaction fbrta = new FlightBookingRecordTransaction();
		return fbrta.deleteFrom(bookingUuid, flightId);
	}
	
	/*
	 * Perform booking
	 */
	public boolean performBooking(String bookingUuid,java.lang.String flightId,XMLGregorianCalendar flightDate){
		Flight f = getFlightById(flightId);
		XMLGregorianCalendar gc = flightDate;
		GregorianCalendar c = gc.toGregorianCalendar();
		java.util.Date d = c.getTime();
		java.sql.Date sqlDate = new java.sql.Date(d.getTime());
		FlightBookingRecordTransaction fbrta = new FlightBookingRecordTransaction();
		FlightBookingRecordEntry fbre = new FlightBookingRecordEntry(bookingUuid, f.getFlightId(), sqlDate, f.getDepartesFrom().getAirportTax(), f.getArrivesAt(). getAirportTax(), f.getStdFee());
		return fbrta.insertInto(fbre);
	}
	
	/*
	 * Getter retrieves certain flight
	 */
	public Flight getFlightById(String flightId){
		flights = FlightsTableAccess.getFlights();
		for(Flight f : flights){
			if(f.getFlightId().equals(flightId)){
				return f;
			}
		}
		System.out.println("Flight not found!");
		return null;
	}
	
		
	/*	if(publishAirline()){
			System.out.println("Mission accomplished (uddi registrierung");
		}else{
			System.out.println("Mission failed (uddi registrierung)");
		}*/
	
	/*
	 * Publishes airline at uddi server
	 */
	/*public boolean publishAirline(){
		UddiManager manager = UddiManager.getInstance();
		unPublishAirline();
		try {
			//"W3Airline", "AirlineService", "http://140.78.196.32:8080/W3Airline/services/AirlineServiceImplPort?wsdl"
			String serviceKey = manager.publish(GROUP, BUSINESSNAME, "http://w3airline.noip.me:8080/W3Airline/services/AirlineServiceImplPort?wsdl");
			System.out.print(serviceKey);
		} catch (ConnectException ce) {
			ce.printStackTrace();
			return false;
		} catch (RemoteException re) {
			re.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*
	 * Deletes registration from uddi server
	 */
	/*private void unPublishAirline(){
		UddiManager manager = UddiManager.getInstance();
		String delete = manager.deletePublishedBusinessFromUDDI(GROUP);
		System.out.println(delete);
	}
	
	/*
	 * Getter delivers airline
	 */
	public Airline getAirline(){
	/*	if(airline==null){
			initialize();
			flights = FlightsTableAccess.getFlights();
			for(Flight f : flights){
				airline.getFlightPlan().add(f);
			}
		}*/
		if(flights== null){
			instance.initialize();
		}
		return airline;
	}
}
