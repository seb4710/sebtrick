package at.jku.ce.airline.dal;

import java.math.BigDecimal;
import java.sql.Date;

public class FlightBookingRecordEntry {
	
	private String uuid_booking;
	private String id_flight;
	private Date flight_date;
	private String airline = "W3Airline";
	private BigDecimal dep_airport_tax;
	private BigDecimal dest_airport_tax;
	private BigDecimal service_tax;
	private BigDecimal air_fare;
	
	public FlightBookingRecordEntry(String uuid_booking, String id_flight, java.sql.Date flight_date, BigDecimal dep_airport_tax, BigDecimal dest_airport_tax, BigDecimal air_fare){
		this.uuid_booking = uuid_booking;
		this.id_flight = id_flight;
		this.flight_date = flight_date;
		this.dep_airport_tax = dep_airport_tax;
		this.dest_airport_tax = dest_airport_tax;
		this.service_tax = new BigDecimal(10);
		this.air_fare = air_fare;
	}

	public String getUuid_booking(){
		return uuid_booking;
	}
	
	public String getId_flight(){
		return id_flight;
	}
	
	public Date getFlight_date(){
 		return flight_date;
	}
	
	public String getAirline(){
		return airline;
	}
	
	public BigDecimal getDep_airport_tax(){
		return dep_airport_tax;
	}
	
	public BigDecimal getDest_airport_tax(){
		return dest_airport_tax;
	}
	
	public BigDecimal getService_tax(){
		return service_tax;
	}
	
	public BigDecimal getAir_fare(){
		return air_fare;
	}
	
}
