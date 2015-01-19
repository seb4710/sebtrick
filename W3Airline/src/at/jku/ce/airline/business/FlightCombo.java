package at.jku.ce.airline.business;

import at.jku.ce.airline.service.Airport;
import at.jku.ce.airline.service.Flight;
import at.jku.ce.airline.service.Time;

public class FlightCombo {

    private Flight flight1;
    private Flight flight2;
	
    public FlightCombo(Flight flight) {
    	this.flight1 = flight;
    	this.flight2 = null;
	}
    
    public FlightCombo(Flight flight1, Flight flight2) {
    	this.flight1 = flight1;
    	this.flight2 = flight2;
	}
    
    
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
	public float getFee() {
		
		//TODO calculate fee
		
//		return 0.0f;
		
		return 12345.6789f;
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
	public int getDuration() {
		int time = 0;
		
		int hours1;
		int hours2;
		int minutes1;
		int minutes2;
		
		minutes1 = (int) (flight1.getDepartureTime().getTimeOfDay() % 100);
		hours1 = (int) (flight1.getDepartureTime().getTimeOfDay() / 100);
		
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
	
}
