package at.jku.ce.airline.business;

/**
 * this class offers functionality for booking and deleting bookings
 */

import java.util.Date;
import java.util.List;

import at.jku.ce.airline.data.*;
import at.jku.ce.juddi.UddiManager;

public class BookingHandler {
	//
	private PassengerBookingRecordEntry pbre;
	private PassengerBookingRecordTransaction pbrt;
	private FlightController fc;
	private FlightHandler fh;
	private Booking current;
	private static BookingHandler instance;
	UddiManager manager = UddiManager.getInstance();

	/**
	 * constructor retrieves airline-names and associated flight-id's 
	 */
	private BookingHandler(){
	 
		fc = FlightController.getInstance();
		fh = FlightHandler.getInstance();
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
				System.out.println("single flug");
				String uuidBooking = getUuidBooking(current.getFlight1(),current.getFname(),current.getLname(),current.getId());	
				pbrt = new PassengerBookingRecordTransaction();
				pbre = new PassengerBookingRecordEntry(uuidBooking, current.getFname(), current.getLname(), current.getId());
				
				try{
					
					/*
					 * if passengerbookingrecord entry successful, call book method of involved airline
					 */
					if(pbrt.insertInto(pbre)){
						System.out.println(current.getFlight1());
						System.out.println(current.getFname());
						System.out.println(current.getLname());
						System.out.println(uuidBooking);
						System.out.println(fc.getAirlineName(current.getFlight1()));
						System.out.println(fc.getAccesspoint(fc.getAirlineName(current.getFlight1())));
						
						
						if(fc.getAccesspoint(fc.getAirlineName(current.getFlight1())).bookFlight(uuidBooking, current.getFlight1(), current.getDate())){	
							current = null;
							return uuidBooking;
						}
					}	
				}catch(Exception e){
					e.printStackTrace();
					return "Buchung fehlgeschlagen";
				}
			}else{
				System.out.println("Doppelflug");
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
					
						
						if(fc.getAccesspoint(fh.getAirlineName(current.getFlight1())).bookFlight(uuidBooking, current.getFlight1(), current.getDate()) &&
						   fc.getAccesspoint(fh.getAirlineName(current.getFlight2())).bookFlight(uuidBooking, current.getFlight2(), current.getDate())){
							current = null;
							return uuidBooking;
						}
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
		List<String> cancelParameter = pbrt.deleteFrom(uuid_booking);
		
		/*
		 * returns true, if something could be deleted
		 */
		if(cancelParameter.size() >= 1 && cancelParameter != null){
			
			/*
			 * call cancel method of involved airline(s)
			 */
			for(String s : cancelParameter){
				
				/* 
				 * no error-handling because flightbookingrecordtable entries are already deleted
				 * at this point because of "on delete cascade". however, the cancelFlight() method
				 * shouldn't feel abandoned, therefore we call it.
				 */
				try{
					fc.getAccesspoint(fc.getAirlineName(s)).cancelFlight(uuid_booking, s);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return true;
			
			/*
			 * nothing could be deleted
			 */
		}else{
			return false;
		}	
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
