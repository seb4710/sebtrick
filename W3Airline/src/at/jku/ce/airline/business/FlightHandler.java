package at.jku.ce.airline.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
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
	
	private FlightController controller;
	private Set<Airport> airports;
	private Map<String, Airport> airportMap;
	
	/**
	 * Constructor
	 */
	public FlightHandler() {
		super();
		controller = new FlightController();
		initialize();
	}
	
	/**
	 * Initialises the new instance:
	 * - get all airports
	 * - map all airports with its ICAOs
	 */
	private void initialize() {
		airports = controller.getAllAirports();
		airportMap = new HashMap<String, Airport>();
		
		for(Airport port : airports)
			airportMap.put(port.getIcao(), port);
	}
	
	/**
	 * 
	 * @param flightId
	 * @return
	 */
	public String getAirlineName(String flightId) {
		return controller.getAirlineName(flightId);
	}
	
	/**
	 * Returns a specific Flight by its ID
	 * @param flightId: ID of the wanted flight
	 * @return the requested flight if found - else null
	 */
	public Flight getFlight(String flightId) {
		return controller.getFlight(flightId);
	}

	/**
	 * Get all FlightCombos (direct and one-stop) for the given route - optionally sorted;
	 * @param departureIcao: ICAO of the departing airport
	 * @param arrivalIcao: ICAO of the arriving airport
	 * @param sort: optional parameter for sorting the returned list of FlightCombo
	 * @return all corresponding flights
	 */
	public List<FlightCombo> getAllFlights(String departureIcao, String arrivalIcao, String date, String time, String sort, String maxDuration, String maxPrice) {
		List<FlightCombo> list = new LinkedList<FlightCombo>();
		
		// add all direct flights 
		list.addAll(getDirectFlights(departureIcao, arrivalIcao, date, time, null, null, null));
		
		// add all flights with one stop
		list.addAll(getOneStopFlights(departureIcao, arrivalIcao, date, time));
		
		// filter list according to parameters
		if((maxDuration != null && !maxDuration.equals("")) || (maxPrice != null && !maxPrice.equals("")))
			list = filterList(list, maxDuration, maxPrice);
		
		// is null if parameter 'sort' is invalid
		Comparator<FlightCombo> comparator = getComparator(sort);
		
		// sort list according to parameter
		if(comparator != null)
			Collections.sort(list, comparator);
		
		return list;
	}
	
	/**
	 * Get all DIRECT FlightCombos (no one-stop) for the given route - optionally sorted;
	 * @param departureIcao: ICAO of the departing airport
	 * @param arrivalIcao: ICAO of the arriving airport
	 * @param sort: optional parameter for sorting the returned list of FlightCombo
	 * @return all corresponding flights
	 */
	public List<FlightCombo> getDirectFlights(String departureIcao, String arrivalIcao, String date, String time, String sort, String maxDuration, String maxPrice) {
		List<FlightCombo> flights = new LinkedList<FlightCombo>();
		List<Flight> direct = controller.getFlights(getAirport(departureIcao), getAirport(arrivalIcao));
		
		GregorianCalendar cal = Formatter.stringToCalendar(date);
		int dayConstraint = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		int timeConstraint = Formatter.stringToTime(time);
		
		for(Flight f : direct) {
			if(f.getDepartureTime().getIndexDayOfWeek() == dayConstraint &&
					timeConstraint < f.getDepartureTime().getTimeOfDay())
				flights.add(new FlightCombo(f));
		}
		
		// is null if parameter 'sort' is invalid
		Comparator<FlightCombo> comparator = getComparator(sort);
		
		// sort list according to parameter
		if(comparator != null)
			Collections.sort(flights, comparator);
		
		return flights;
	}
	
	/**
	 * Get all ONE-STOP FlightCombos (no direct) for the given route - optionally sorted;
	 * @param departureIcao: ICAO of the departing airport
	 * @param arrivalIcao: ICAO of the arriving airport
	 * @return all corresponding flights
	 */
	private List<FlightCombo> getOneStopFlights(String departureIcao, String arrivalIcao, String date, String time) {
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
	
	private List<FlightCombo> filterList(List<FlightCombo> list, String maxDuration, String maxPrice) {
		List<FlightCombo> result = new LinkedList<FlightCombo>();
		List<FlightCombo> temp = new LinkedList<FlightCombo>();
		
		boolean didDuration = false;
		boolean didPrice = false;
		
		if(maxDuration != null && !maxDuration.equals("")) {
			int duration = Integer.parseInt(maxDuration) * 100;
		
			for(FlightCombo f : list) {
				if(f.getDuration_Total() <= duration) {
					temp.add(f);
				}
			}
			
			didDuration = true;
		}
		
		if(maxPrice != null && !maxPrice.equals("")) {
			float price = Float.parseFloat(maxPrice);
			
			if(didDuration) {
				for(FlightCombo f : temp) {
					if(f.getFee_Total() <= price)
						result.add(f);
				}
			} else {
				for(FlightCombo f : list) {
					if(f.getFee_Total() <= price)
						result.add(f);
				}
			}
			
			didPrice = true;
		}	
		
		if(didPrice)
			return result;
		else if(didDuration)
			return temp;
		else
			return list;
	}

	public Airport getAirport(String icao) {
		return airportMap.get(icao);
	}
	
	public Set<Airport> getAirports() {
		return airports;
	}
	
	private Comparator<FlightCombo> getComparator(String sort) {
		if(sort == null || sort.equals("") || sort.equals("null")) {
			return null;
		} else {
			if(sort.equals("duration"))
				return new Comparator<FlightCombo>() {

					public int compare(FlightCombo o1, FlightCombo o2) {
						if(o1.getDuration_Total() > o2.getDuration_Total())
							return 1;
						if(o1.getDuration_Total() < o2.getDuration_Total())
							return -1;
						
						return 0;
					}
				};
			else if(sort.equals("fee"))
				return new Comparator<FlightCombo>() {

					public int compare(FlightCombo o1, FlightCombo o2) {
						if(o1.getFee_Total() > o2.getFee_Total())
							return 1;
						if(o1.getFee_Total() < o2.getFee_Total())
							return -1;
						
						return 0;
					}
				};
			else
				return null;
		}
	}
	
	
}
