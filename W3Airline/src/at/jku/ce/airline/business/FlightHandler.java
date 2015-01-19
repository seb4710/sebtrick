package at.jku.ce.airline.business;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import at.jku.ce.airline.data.FlightController;
import at.jku.ce.airline.service.Airport;
import at.jku.ce.airline.service.Flight;

public class FlightHandler {

	public static long MAX_WAITING_TIME = 360;	// refers to 6 hours in minutes
	
	private static FlightHandler instance;
	
	private FlightController controller;
	private Set<Airport> airports;
	private Map<String, Airport> airportMap;
	
	private FlightHandler() {
		super();
		if(controller == null)
			controller = FlightController.getInstance();
		
		initialize();
	}
	
	public static FlightHandler getInstance() {
		if(instance == null)
			instance = new FlightHandler();
		
		return instance;
	}
	
	private void initialize() {
		airportMap = new HashMap<String, Airport>();
		
		airports = controller.getAllAirports();
		
		for(Airport port : airports)
			airportMap.put(port.getIcao(), port);
	}

	public List<FlightCombo> getFlights(String departureIcao, String arrivalIcao) {
		List<FlightCombo> list = new LinkedList<FlightCombo>();
		
		// add all direct flights 
		list.addAll(getDirectFlights(departureIcao, arrivalIcao));
		
		// add all flights with one stop
		list.addAll(getOneStopFlights(departureIcao, arrivalIcao));
		
		return list;
	}
	
	public List<FlightCombo> getDirectFlights(String departureIcao, String arrivalIcao) {
		List<FlightCombo> flights = new LinkedList<FlightCombo>();
		List<Flight> direct = controller.getFlights(getAirport(departureIcao), getAirport(arrivalIcao));
		
		for(Flight f : direct)
			flights.add(new FlightCombo(f));
		
		return flights;
	}
	
	private List<FlightCombo> getOneStopFlights(String departureIcao, String arrivalIcao) {
			List<FlightCombo> flights = new LinkedList<FlightCombo>();
			List<Flight> listOfDep = controller.getFlightsWithDeparture(getAirport(departureIcao));
			List<Flight> listOfArr = controller.getFlightsWithArrival(getAirport(arrivalIcao));
			
			for(Flight first : listOfDep) {
				for(Flight second : listOfArr) {
					long departure = Formatter.timeToMinutes(second.getDepartureTime().getTimeOfDay());
					long arrival = Formatter.timeToMinutes(first.getArrivalTime().getTimeOfDay());
					
					if(first.getArrivesAt().getIcao().equals(second.getDepartesFrom().getIcao())
							&& (departure - arrival) > 0
							&& (departure - arrival) < MAX_WAITING_TIME)
						flights.add(new FlightCombo(first, second));
				}
			}
			
			return flights;
	}

	public Airport getAirport(String icao) {
		return airportMap.get(icao);
	}
	
	public Set<Airport> getAirports() {
		return airports;
	}
	
	
}
