package at.jku.ce.airline.data;

import java.net.MalformedURLException;
import java.net.URL;
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
	
	
	public FlightController() {
		super();
		
		flights = new LinkedList<Flight>();
		map = new HashMap<String, String>();
		
		initialize();
	}
	
	private void initialize() {
		
		//TODO eliminate malformatted or invalid URLs
		UddiManager manager = UddiManager.getInstance();
		List<String> accessPoints = manager.getAllPublishedAccessPoints();
		accessPoints.remove("http://example.com/fail");
		accessPoints.remove("http://140.78.73.67:8080/AirlineService/services/airlineservice?wsdl");
		accessPoints.remove("http://140.78.73.67:8080/XAirline/services/AirlineServiceImplPort?wsdl");
		
		
		// add flights to local field
		for(String s : accessPoints) {
			AirlineServiceImplService service;
			try {
				service = new AirlineServiceImplService(new URL(s));
				AirlineServiceImpl port = service.getAirlineServiceImplPort();
				
				List<Flight> list = port.getFlightplan();
				
				for(Flight f : list) {
					flights.add(f);
					map.put(f.getFlightId(), port.getAirline().getName());
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
	
	public List<Flight> getFlights(Airport departure, Airport arrival) {
		List<Flight> list = new LinkedList<Flight>();
		
		for(Flight f : flights) {
			if(f.getArrivesAt().getIcao().equals(arrival.getIcao())
					&& f.getDepartesFrom().getIcao().equals(departure.getIcao()))
				list.add(f);
		}
		
		return list;
	}
	
	public List<Flight> getFlightsWithArrival(Airport port) {
		List<Flight> list = new LinkedList<Flight>();
		
		for(Flight f : flights) {
			if(f.getArrivesAt().getIcao().equals(port.getIcao()))
				list.add(f);
		}
		
		return list;
	}
	
	public List<Flight> getFlightsWithDeparture(Airport port) {
		List<Flight> list = new LinkedList<Flight>();
		
		for(Flight f : flights) {
			if(f.getDepartesFrom().getIcao().equals(port.getIcao()))
				list.add(f);
		}
		
		return list;
	}
	
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
	
}
