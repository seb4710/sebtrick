package at.jku.ce.airline.business;

/**
 * this class offers functionality for booking and deleting bookings
 */

import java.util.Date;
import java.util.List;
import at.jku.ce.airline.data.*;

public class BookingHandler {

	private PassengerBookingRecordEntry pbre;
	private PassengerBookingRecordTransaction pbrt;
	private static BookingHandler instance;
	private FlightController fc; 
	
	
	/**
	 * constructor retrieves airline-names and associated flight-id's 
	 */
	public BookingHandler(){
		fc = new FlightController();
	}
	
	/**
	 * creates the booking id
	 * @param flightId id of the flight(s)
	 * @param firstName first name of the passenger
	 * @param lastName last name of the passenger
	 * @param idCardNr id of the passenger
	 * @return String id of the booking
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
	public String performBooking(String flightId, String firstName, String lastName, String idCardNr, javax.xml.datatype.XMLGregorianCalendar flightDate){
		
		String uuidBooking = getUuidBooking(flightId,firstName,lastName,idCardNr);
		pbrt = new PassengerBookingRecordTransaction();
		pbre = new PassengerBookingRecordEntry(uuidBooking, firstName, lastName, idCardNr);
		
		try{
			if(pbrt.insertInto(pbre)){
				fc.getAccesspoint(fc.getAirlineName(flightId)).bookFlight(uuidBooking, flightId, flightDate);
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
	 */
	public String performBooking(String flightId1, String flightId2, String firstName, String lastName, String idCardNr, javax.xml.datatype.XMLGregorianCalendar flightDate){
		
		String uuidBooking = getUuidBooking(flightId1+flightId2,firstName,lastName,idCardNr);	
		pbrt = new PassengerBookingRecordTransaction();
		pbre = new PassengerBookingRecordEntry(uuidBooking, firstName, lastName, idCardNr);
		
		try{			
			if(pbrt.insertInto(pbre)){
				fc.getAccesspoint(fc.getAirlineName(flightId1)).bookFlight(uuidBooking, flightId1, flightDate);
				fc.getAccesspoint(fc.getAirlineName(flightId2)).bookFlight(uuidBooking, flightId2, flightDate);
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
			if(!fc.getAccesspoint(af.getAirlineName()).cancelFlight(uuid_booking, af.getFlightId())){
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
}
