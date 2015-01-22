package at.jku.ce.airline.business;

/**
 * this class offers functionality for booking and deleting bookings
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import at.jku.ce.airline.data.*;
import at.jku.ce.airline.service.Airline;
import at.jku.ce.airline.service.AirlineServiceImpl;
import at.jku.ce.airline.service.Flight;


public class BookingHandler {

	private PassengerBookingRecordEntry pbre;
	private PassengerBookingRecordTransaction pbrt;
	private static AirlineHandler ah;
	private static BookingHandler instance;
	private static List<AirlineFlights> flights;
	
	
	/**
	 * constructor retrieves airline-names and associated flight-id's 
	 */
	public BookingHandler(){
		ah = AirlineHandler.getInstance();
		ah = new AirlineHandler();
		List<AirlineServiceImpl> ports = ah.getPorts();
		flights = new ArrayList<AirlineFlights>();
		
		for(AirlineServiceImpl asi : ports){
			List<Flight> tempFlight = asi.getFlightplan();
			String al = asi.getAirline().getName();
			for(Flight f : tempFlight){
				flights.add(new AirlineFlights(al, f.getFlightId()));
			}
		}
	}
	
	/*////////ab in das booking jsp damit!!
	 * creates the uuid of a certain booking
	 */
	private String getUuidBooking(String flightId, String firstName, String lastName, String idCardNr){
		Date date = new Date();
		date.getTime();
		
		int hash = (flightId+firstName+lastName+idCardNr+date).hashCode();
		return "w3-se-"+hash;
	}
	
	/**
	 * books a single flight
	 * @param uuidBooking id of the booking which is to be performed
	 * @param flightId id of the flight
	 * @param firstName first name of the passenger
	 * @param lastName last name of the passenger
	 * @param idCardNr identification of the passenger 
	 * @param flightDate date of the flight
	 * @return uuidBooking id of the booking // ev weg damit
	 */
	public String performBooking(String uuidBooking, String flightId, String firstName, String lastName, String idCardNr, javax.xml.datatype.XMLGregorianCalendar flightDate){
		
		/////uuidBooking muss im jsp erzeugt werden, da sonst keine kombination von flügen mit gleicher buchungsuuid möglich ist
		//String uuidBooking = getUuidBooking(flightId,firstName,lastName,idCardNr);
		
		pbrt = new PassengerBookingRecordTransaction();
		pbre = new PassengerBookingRecordEntry(uuidBooking, firstName, lastName, idCardNr);
		String airlineName = "";
		
		for(AirlineFlights af : flights){
			if(af.getFlightId().equals(flightId)){
				airlineName = af.getAirlineName();
			}
		}
		
		try{
			AirlineServiceImpl service = ah.getAirlineServiceImpl(airlineName);
			if(pbrt.insertInto(pbre)){
				service.bookFlight(uuidBooking, flightId, flightDate);
			}	
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return uuidBooking;
	}
	
	/**
	 * books a combined flight
	 * @param uuidBooking id of the booking which is to be performed
	 * @param flightId1 id of the first flight
	 * @param flightId2 id of the second flight
	 * @param firstName first name of the passenger
	 * @param lastName last name of the passenger
	 * @param idCardNr identification of the passenger 
	 * @param flightDate date of the flight
	 * @return uuidBooking id of the booking // ev weg damit
	 */
	public String performBooking(String uuidBooking, String flightId1, String flightId2, String firstName, String lastName, String idCardNr, javax.xml.datatype.XMLGregorianCalendar flightDate){
		
		/////uuidBooking muss im jsp erzeugt werden, da sonst keine kombination von flügen mit gleicher buchungsuuid möglich ist
		//String uuidBooking = getUuidBooking(flightId,firstName,lastName,idCardNr);
	
		pbrt = new PassengerBookingRecordTransaction();
		pbre = new PassengerBookingRecordEntry(uuidBooking, firstName, lastName, idCardNr);
		
		String airlineName1 = "";
		String airlineName2 = "";
		
		for(AirlineFlights af : flights){
			if(af.getFlightId().equals(flightId1)){
				airlineName1 = af.getAirlineName();
			}
			
			if(af.getFlightId().equals(flightId2)){
				airlineName2 = af.getAirlineName();
			}
		}
		
		try{
			AirlineServiceImpl service = ah.getAirlineServiceImpl(airlineName1);
			
			if(pbrt.insertInto(pbre)){
				service.bookFlight(uuidBooking, flightId1, flightDate);
				service = ah.getAirlineServiceImpl(airlineName2);
				service.bookFlight(uuidBooking, flightId2, flightDate);
			}	
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return uuidBooking;
	}
			
	/**
	 * cancels a certain booking
	 * @param uuid_booking id of the booking which will be deleted
	 * @return true if cancel successful, else false
	 */
	public boolean performCancelFlight(String uuid_booking){
		pbrt = new PassengerBookingRecordTransaction();
		
		//deletes entry in passengerbookingrecord table
		List<AirlineFlights> cancelParameter = pbrt.deleteFrom(uuid_booking);
		
		for(AirlineFlights af : cancelParameter){
			
			//calls cancelFlight procedure of airline
			if(!ah.getAirlineServiceImpl(af.getAirlineName()).cancelFlight(uuid_booking, af.getFlightId())){
				return false;
			}	
		}
		return true;
	}
	
	/**
	 * static getter returns BookingHandler instance
	 * @return instance of BookingHandler
	 */
	public static BookingHandler getInstance(){
		if(instance == null){
			instance = new BookingHandler();
		}
		return instance;
	}

	
	
	
	/*
	 * test methods
	 */
	public static void main(String[] args){
		BookingHandler bh = BookingHandler.getInstance();
		/*Airline a = new Airline();
		a.setName("W3Airline");
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(2015, 01, 01);
		
		XMLGregorianCalendar calendar;
		try {
			calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
			String uuid = bh.performBooking(a, "1-W3F1-ELLX-LROP", "patrick", "kien", "123", calendar);
			System.out.println("booking performed");
			bh.performCancelFlight(uuid);
			System.out.println("cancel performed");
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		for(AirlineFlights af : flights){
			System.out.println(af.getAirlineName() +" " + af.getFlightId() );
		}
		
		
	}
}
