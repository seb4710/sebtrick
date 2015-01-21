package at.jku.ce.airline.data;

/**
 * objects of this class represent an entry in passengerbookingrecord table
 */

public class PassengerBookingRecordEntry {

	private String uuid_booking;
	private String firstName;
	private String lastName;
	private String idCardNr;
	private final String flightSearchEngine = "W3SearchEngine";
	
	
	/**
	 * 
	 * @param uuid_booking id of the booking
	 * @param firstName first name of the passenger
	 * @param lastName last name of the passenger
	 * @param idCardNr id of the passenger
	 */
	public PassengerBookingRecordEntry(String uuid_booking, String firstName, String lastName, String idCardNr){
		this.uuid_booking = uuid_booking;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idCardNr = idCardNr;
	}

	/**
	 * 
	 * @return id of the booking
	 */
	public String getUuid_booking(){
		return uuid_booking;
	}
	
	/**
	 * 
	 * @return first name of the passenger
	 */
	public String getFirstName(){
		return firstName;
	}
	
	/**
	 * 
	 * @return last name of the passenger
	 */
	public String getLastName(){
 		return lastName;
	}
	/**
	 * 
	 * @return id of the passenger
	 */
	public String getIdCardNr(){
		return idCardNr;
	}
	
	/**
	 * 
	 * @return name of the search engine used for the booking
	 */
	public String getFlightSearchEngine(){
		return flightSearchEngine;
	}
}
