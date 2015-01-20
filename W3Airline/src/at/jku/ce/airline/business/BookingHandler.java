package at.jku.ce.airline.business;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import at.jku.ce.airline.data.*;
import at.jku.ce.airline.service.Airline;
import at.jku.ce.airline.service.AirlineServiceImpl;


public class BookingHandler {

	private PassengerBookingRecordEntry pbre;
	private PassengerBookingRecordTransaction pbrt;
	private AirlineHandler ah;
	private static BookingHandler instance;
	
	public BookingHandler(){
		ah = AirlineHandler.getInstance();
	}
	
	/*
	 * books a flight
	 */
	public String performBooking(Airline airline, String flightId, String firstName, String lastName, String idCardNr, javax.xml.datatype.XMLGregorianCalendar flightDate){
		pbrt = new PassengerBookingRecordTransaction();
		String uuidBooking = getUuidBooking(flightId,firstName,lastName,idCardNr);
		
		pbre = new PassengerBookingRecordEntry(uuidBooking, firstName, lastName, idCardNr);
		
		AirlineServiceImpl service = ah.getAirlineServiceImpl(airline.getName());
		
		//actual booking
		if(pbrt.insertInto(pbre)){
			try{
				service.bookFlight(uuidBooking, flightId, flightDate);
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}	
		}
		return uuidBooking;
	}
	
	
	
	/*
	 * cancels a certain flight/booking
	 */
	public boolean performCancelFlight(String uuid_booking){
		pbrt = new PassengerBookingRecordTransaction();
		//storniert in passengerbookingrecord
		String[] cancelParameter = pbrt.deleteFrom(uuid_booking);
		
		if(cancelParameter[0] != null){
			//ruft storno methode der airline auf
			ah.getAirlineServiceImpl(cancelParameter[1]).cancelFlight(uuid_booking, cancelParameter[0]);
			return true;
		}else{
			return false;
		}	
	}
	
	/*
	 * creates the uuid of a certain booking
	 */
	private String getUuidBooking(String flightId, String firstName, String lastName, String idCardNr){
		Date date = new Date();
		date.getTime();
		
		int hash = (flightId+firstName+lastName+idCardNr+date).hashCode();
		return "w3-se-"+hash;
	}
	
	/*
	 * 
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
		Airline a = new Airline();
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
		}
		
		
		
	}
}
