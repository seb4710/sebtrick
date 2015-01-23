package at.jku.ce.airline.business;

public class Booking {

	private String flight1;
	private String flight2;
	private String fname;
	private String lname;
	private String id;
	private javax.xml.datatype.XMLGregorianCalendar date;
	
	public Booking(){	
	}
	
	/*
	 * setters
	 */
	
	public void setFlight1(String flight1){
		this.flight1 = flight1;
	}
	
	public void setFlight2(String flight2){
		this.flight2 = flight2;
	}
	
	public void setFname(String fname){
		this.fname = fname;
	}
	
	public void setLname(String lname){
		this.lname = lname;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setDate(javax.xml.datatype.XMLGregorianCalendar date){
		this.date = date;
	}
	
	/*
	 * getters
	 */
	
	public String getFname(){
		return fname;
	}
	
	public String getLname(){
		return lname;
	}

	public String getId(){
		return id;
	}
	public String getFlight1(){
		return flight1;
	}
	public String getFlight2(){
		return flight2;
	}
	
	public javax.xml.datatype.XMLGregorianCalendar getDate(){
		return date;
	}
	
	public boolean bookingPossible(){
		if(flight1.length() < 1 || fname.length() < 1 || lname.length() < 1 || id.length() < 1 || date == null){
			return false;
		}else{
			return true;
		}
	}
}

