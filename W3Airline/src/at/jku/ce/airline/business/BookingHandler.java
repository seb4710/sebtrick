package at.jku.ce.airline.business;

/**
 * this class offers functionality for booking and deleting bookings
 */

import java.util.Date;
import java.util.List;
import at.jku.ce.airline.data.*;

public class BookingHandler {
	//
	private PassengerBookingRecordEntry pbre;
	private PassengerBookingRecordTransaction pbrt;
	private FlightController fc;
	private Booking current;
	private static BookingHandler instance;


	/**
	 * constructor retrieves airline-names and associated flight-id's 
	 */
	private BookingHandler(){
		 
		
		 
		fc = FlightController.getInstance();
		
		
		//fc = new FlightController(); //use as non-singleton
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
	 * books a flight based on the attributes of Booking object current
	 * @return uuidBooking id of the booking
	 */
	public String performBooking(){
		
		/*
		 * Booking object is valid & not null
		 */
		if(current.bookingPossible() && current != null){
			
			/*
			 * booking a single flight
			 */
			if(current.getFlight2() == null){
				String uuidBooking = getUuidBooking(current.getFlight1(),current.getFname(),current.getLname(),current.getId());	
				pbrt = new PassengerBookingRecordTransaction();
				pbre = new PassengerBookingRecordEntry(uuidBooking, current.getFname(), current.getLname(), current.getId());
				try{
					
					/*
					 * if passengerbookingrecord entry successful, call book method of involved airline
					 */
					if(pbrt.insertInto(pbre)){
						fc.getAccesspoint(fc.getAirlineName(current.getFlight1())).bookFlight(uuidBooking, current.getFlight1(), current.getDate());	
						current = null;
						return uuidBooking;
					}	
				}catch(Exception e){
					e.printStackTrace();
					return "Buchung fehlgeschlagen";
				}
			}else{
				
				/*
				 * booking a combined flight
				 */
				String uuidBooking = getUuidBooking(current.getFlight1()+current.getFlight2(),current.getFname(),current.getLname(),current.getId());	
				pbrt = new PassengerBookingRecordTransaction();
				pbre = new PassengerBookingRecordEntry(uuidBooking, current.getFname(), current.getLname(), current.getId());
				
				try{
					
					/*
					 * if passengerbookingrecord entry successful, call book methods of involved airlines
					 */
					if(pbrt.insertInto(pbre)){
						fc.getAccesspoint(fc.getAirlineName(current.getFlight1())).bookFlight(uuidBooking, current.getFlight1(), current.getDate());
						fc.getAccesspoint(fc.getAirlineName(current.getFlight2())).bookFlight(uuidBooking, current.getFlight2(), current.getDate());
						current = null;
						return uuidBooking;
					}	
				}catch(Exception e){
					e.printStackTrace();
					return "Buchung fehlgeschlagen";
				}
			}
		}
		return "Buchung fehlgeschlagen";	
	}
			
	/**
	 * cancels a certain booking
	 * @param uuid_booking id of the booking which will be deleted
	 * @return true if cancel successful, else false
	 */
	public boolean performCancelFlight(String uuid_booking){
		
		pbrt = new PassengerBookingRecordTransaction();
		
		/*
		 * deletes the entry in passengerbookingrecord table
		 */
		List<AirlineFlights> cancelParameter = pbrt.deleteFrom(uuid_booking);
		
		/*
		 * return false if cancel not successful
		 */
		if(cancelParameter.size() < 1){
			return false;
		}
		
		/*
		 * call cancel method of involved airline(s)
		 */
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
	
	/**
	 * @return Booking current instance
	 */
	public Booking getCurrent(){
		if(current == null){
			current = new Booking();
		}
		return current;
	}
}
