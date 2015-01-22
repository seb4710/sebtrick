package at.jku.ce.airline.business;

//Objekte für Liste von Airlinename + FlugId

public class AirlineFlights {
	
	private String airlineName;
	private String flightId;
	
	
	/**
	 * constructor
	 * @param airlineName name of the airline
	 * @param flightId id of the flight
	 */
	public AirlineFlights(String airlineName, String flightId){
		this.airlineName = airlineName;
		this.flightId = flightId;
	}
	
	/**
	 * getter
	 * @return name of the airline
	 */
	public String getAirlineName(){
		return airlineName;
	}
	
	/**
	 * getter
	 * @return id of the flight
	 */
	public String getFlightId(){
		return flightId;
	}

}
