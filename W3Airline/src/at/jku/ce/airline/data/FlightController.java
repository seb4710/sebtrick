package at.jku.ce.airline.data;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import at.jku.ce.airline.service.AirlineServiceImpl;
import at.jku.ce.airline.service.AirlineServiceImplService;
import at.jku.ce.airline.service.Airport;
import at.jku.ce.airline.service.Flight;
import at.jku.ce.juddi.UddiManager;

public class FlightController {
	
	private List<Flight> flights;
	private Map<String, String> map;
	private Map<String, AirlineServiceImpl> accesspoints;
	private static FlightController instance;
	
	private FlightController() {
		super();
		
		flights = new LinkedList<Flight>();
		map = new HashMap<String, String>();
		accesspoints = new HashMap<String, AirlineServiceImpl>();
		
		initialize();
	}
	
	public static FlightController getInstance(){
		if(instance == null){
			instance = new FlightController();
		}
		return instance;
	}
	
	/**
	 * do necessary initialisation of local attributes
	 */
	private void initialize() {
		
		UddiManager manager = UddiManager.getInstance();		
		List<String> accessPoints = manager.getAllPublishedAccessPoints();
		
		// add flights to local field
		for(String s : accessPoints) {
			AirlineServiceImplService service;
			try {
				
				//url validation
				if(s.contains("wsdl") && s.contains("http://")){
				
					service = new AirlineServiceImplService(new URL(s));
					AirlineServiceImpl port = service.getAirlineServiceImplPort();
				
					accesspoints.put(port.getAirline().getName(), port);
				
					
					List<Flight> list = port.getFlightplan();
				
					for(Flight f : list) {
						flights.add(f);
						map.put(f.getFlightId(), port.getAirline().getName());
					}
				}
			} catch (MalformedURLException e) {
				System.out.println("failed to parse URL: '" + s + "'");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public String getAirlineName(String flightId) {
		return map.get(flightId);
	}
	
	public Flight getFlight(String flightId) {
		Flight flight = null;
		
		for(Flight f : flights) {
			if(f.getFlightId().equals(flightId))
				flight = f;
		}
			
		return flight;
	}
	
	public List<Flight> getFlights() {
		return flights;
	}
	
	/**
	 * Return all Flights with passed departure and arrival
	 * @param departure
	 * @param arrival
	 * @return List of Flights according to parameters
	 */
	public List<Flight> getFlights(Airport departure, Airport arrival) {
		List<Flight> list = new LinkedList<Flight>();
		
		for(Flight f : flights) {
			if(f.getArrivesAt().getIcao().equals(arrival.getIcao())
					&& f.getDepartesFrom().getIcao().equals(departure.getIcao()))
				list.add(f);
		}
		
		return list;
	}
	
	/**
	 * Return Flights with passed Airport as arrival
	 * @param port
	 * @return List of appropriate Flights
	 */
	public List<Flight> getFlightsWithArrival(Airport port) {
		List<Flight> list = new LinkedList<Flight>();
		
		for(Flight f : flights) {
			if(f.getArrivesAt().getIcao().equals(port.getIcao()))
				list.add(f);
		}
		
		return list;
	}
	
	/**
	 * Return Flights with passed Airport as departure
	 * @param port
	 * @return List of appropriate Flights
	 */
	public List<Flight> getFlightsWithDeparture(Airport port) {
		List<Flight> list = new LinkedList<Flight>();
		
		for(Flight f : flights) {
			if(f.getDepartesFrom().getIcao().equals(port.getIcao()))
				list.add(f);
		}
		
		return list;
	}
	
	/**
	 * Return all Flights of registered Airlines
	 * @return Set of all Airports
	 */
	public Set<Airport> getAllAirports() {
		Set<Airport> ports = new HashSet<Airport>();
		Set<String> icaos = new HashSet<String>();
		
		// add departure and arrival to set if not already present
		for(Flight f : flights) {
			if(!icaos.contains(f.getArrivesAt().getIcao())) {
				icaos.add(f.getArrivesAt().getIcao());
				ports.add(f.getArrivesAt());
			}
			if(!icaos.contains(f.getDepartesFrom().getIcao())) {
				icaos.add(f.getDepartesFrom().getIcao());
				ports.add(f.getDepartesFrom());
			}
		}
		
		return ports;		
	}
	
	/**
	 * 
	 * @param airlineName name of airline we are looking for
	 * @return accesspoint of airline we are looking for
	 */
	public AirlineServiceImpl getAccesspoint(String airlineName){
		return accesspoints.get(airlineName);
	}
	

	
}
