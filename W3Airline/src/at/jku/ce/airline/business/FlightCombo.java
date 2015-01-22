package at.jku.ce.airline.business;

import at.jku.ce.airline.service.Airport;
import at.jku.ce.airline.service.Flight;
import at.jku.ce.airline.service.Time;

public class FlightCombo {

    private Flight flight1;
    private Flight flight2;
    
    private int duration1;
    private int duration2;
    private float fee1;
    private float fee2;
	
    /**
     * Constructor for DIRECT flight
     * @param flight: single flight
     */
    public FlightCombo(Flight flight) {
    	this.flight1 = flight;
    	this.flight2 = null;
    	
    	duration1 = calcDuration(flight1);
    	duration2 = 0;
    	fee1 = calcFee(flight1);
    	fee2 = 0.0f;
	}
    
    /**
     * Constructor for ONE-STOP flight
     * @param flight1: first flight
     * @param flight2: subsequent flight
     */
    public FlightCombo(Flight flight1, Flight flight2) {
    	this.flight1 = flight1;
    	this.flight2 = flight2;
    	
    	duration1 = calcDuration(flight1);
    	duration2 = calcDuration(flight2);
    	
    	fee1 = calcFee(flight1);
    	fee2 = calcFee(flight2);	// Service fee is just for the first Flight
	}
    
    /* Helper Methods */
    
    /**
     * Calculates the duration for the flight
     * @return duration for flight
     */
    private int calcDuration(Flight flight) {
		int time = 0;
		
		int hours1 = (int) (flight.getDepartureTime().getTimeOfDay() / 100);
		int minutes1 = (int) (flight.getDepartureTime().getTimeOfDay() % 100);
		int hours2 = (int) (flight.getArrivalTime().getTimeOfDay() / 100);
		int minutes2 = (int) (flight.getArrivalTime().getTimeOfDay() % 100);
		
		time = (hours2 -  hours1) * 100;
		
		if(minutes2 < minutes1) {
			time -= 100;
			time += (60 - minutes1);
			time += minutes2;
		} else {
			time += (minutes2 - minutes1);			
		}
		
		return time;
	}
    
    /**
     * Calculates the fee for the given flight
     * @return fee for flight
     */
	private float calcFee(Flight flight) {
		float sum = 0.0f;	// Service fee
		
		try {
			sum += flight.getStdFee().floatValue();
			sum += flight.getDepartesFrom().getAirportTax().floatValue();
			sum += flight.getArrivesAt().getAirportTax().floatValue();
		} catch(NullPointerException e) {
			System.out.println("No Airport Tax found for Flight" + flight.getFlightId());
		}
		
		return sum;
	}
    
    /* Getter Methods*/
	
	public Time getArrivalTime() {
		if(flight2 != null)
			return flight2.getArrivalTime();
		else
			return flight1.getArrivalTime();
	}
	public Airport getArrivesAt() {
		if(flight2 != null)
			return flight2.getArrivesAt();
		else
			return flight1.getArrivesAt();
	}
	public Airport getDepartesFrom() {
		return flight1.getDepartesFrom();
	}
	public Time getDepartureTime() {
		return flight1.getDepartureTime();
	}
	public int getStops() {
		return flight2 == null ? 0 : 1;
	}

	public String getFirstId() {
		return flight1.getFlightId();
	}
	public String getSecondId() {
		if(flight2 == null)
			return "";
		else
			return flight2.getFlightId();
	}
	public int getDuration_Total() {
		int time = 0;
		
		int hours1 = (int) (flight1.getDepartureTime().getTimeOfDay() / 100);
		int minutes1 = (int) (flight1.getDepartureTime().getTimeOfDay() % 100);
		int hours2;
		int minutes2;
		
		if(flight2 != null) {
			minutes2 = (int) (flight2.getArrivalTime().getTimeOfDay() % 100);
			hours2 = (int) (flight2.getArrivalTime().getTimeOfDay() / 100);
		} else {
			minutes2 = (int) (flight1.getArrivalTime().getTimeOfDay() % 100);
			hours2 = (int) (flight1.getArrivalTime().getTimeOfDay() / 100);
		}
	
		time = (hours2 -  hours1) * 100;
		
		if(minutes2 < minutes1) {
			time -= 100;
			time += (60 - minutes1);
			time += minutes2;
		} else {
			time += (minutes2 - minutes1);			
		}
		
		return time;
	}
	public int getDuration_First() {
		return duration1;
	}
	public int getDuration_Second() {
		return duration2;
	}
	public float getFee_Total() {
		return fee1 + fee2;
	}
	public float getFee_First() {
		return fee1;
	}
	public float getFee_Second() {
		return fee2;
	}
	
}
