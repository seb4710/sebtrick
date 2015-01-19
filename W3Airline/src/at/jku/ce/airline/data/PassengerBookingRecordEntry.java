package at.jku.ce.airline.data;


public class PassengerBookingRecordEntry {
//
	private String uuid_booking;
	private String firstName;
	private String lastName;
	private String idCardNr;
	private final String flightSearchEngine = "W3SearchEngine";
	
	public PassengerBookingRecordEntry(String uuid_booking, String firstName, String lastName, String idCardNr){
		this.uuid_booking = uuid_booking;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idCardNr = idCardNr;
	}

	public String getUuid_booking(){
		return uuid_booking;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
 		return lastName;
	}
	
	public String getIdCardNr(){
		return idCardNr;
	}
	
	public String getFlightSearchEngine(){
		return flightSearchEngine;
	}
}
