package at.jku.ce.airline.data;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import at.jku.ce.airline.service.Airline;
import at.jku.ce.airline.service.AirlineServiceImpl;
import at.jku.ce.airline.service.AirlineServiceImplService;
import at.jku.ce.juddi.UddiManager;

public class AirlineHandler {
	
	private static ArrayList<AirlineServiceImpl> ports;
	private static AirlineHandler instance;
	private static final QName SERVICE_NAME = new QName("http://service.airline.ce.jku.at/", "AirlineServiceImplService");
	
	public AirlineHandler() {
		super();
		initialize();
	}
	
	private void initialize() {
		
		UddiManager manager = UddiManager.getInstance();
		List<String> accessPoints = manager.getAllPublishedAccessPoints();
		ports = new ArrayList<AirlineServiceImpl>();
		AirlineServiceImpl port;
		
		//TODO eliminate malformatted or invalid URLs		
		accessPoints.remove("http://example.com/fail");
		accessPoints.remove("http://140.78.73.67:8080/AirlineService/services/airlineservice?wsdl");
		accessPoints.remove("http://140.78.73.67:8080/XAirline/services/AirlineServiceImplPort?wsdl");
		accessPoints.remove("http://140.78.196.22:8080/AirlineService/services/AirlineServiceImplPort?wsdl");

		for(String s : accessPoints) {
			try {
				
				AirlineServiceImplService ss = new AirlineServiceImplService(new URL(s), SERVICE_NAME);
            	port = ss.getAirlineServiceImplPort();
				ports.add(port);
		
			} catch (MalformedURLException e) {
				System.out.println("failed to parse URL: '" + s + "'");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * returns port of given airline
	 */
	public AirlineServiceImpl getAirlineServiceImpl(String airline){
		Airline a;
		for(AirlineServiceImpl p : ports){
			a = p.getAirline();		
			if(a.getName().equals(airline) && a.getName() != null){
				return p;
			}
		}	
		return null;
	}
	
	/*
	 * getter returns instance of type AirlineHandler
	 */
	public static AirlineHandler getInstance(){
		if(instance == null){
			instance = new AirlineHandler();
		}
		return instance;	
	}
	
	/*
	 * test method
	 */
	public static void main(String[]args){
		AirlineHandler ah = AirlineHandler.getInstance();
		Airline a1 = new Airline();
		a1.setName("W3Airline");
		System.out.println(ah.getAirlineServiceImpl(a1.getName()).getAirline().getName());
	}
	
}
