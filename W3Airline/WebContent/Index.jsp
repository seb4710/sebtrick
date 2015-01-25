<%@page import="at.jku.ce.airline.business.FlightHandler"%>
<%@page import="at.jku.ce.airline.service.AirlineServiceImpl"%>
<%@page import="at.jku.ce.airline.service.AirlineServiceImplService"%>
<%@page import="at.jku.ce.airline.service.Airport"%>
<%@page import="at.jku.ce.airline.service.Flight"%>
<%@page import="java.util.Set" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Look for a flight!</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

<div class="navigation"><a href="Index.jsp">W3 Flugsuchmaschine</a></div>

<div class="main-wrapper"> <!-- BEGIN main wrapper -->
	<form action="Flights.jsp">

<%
	FlightHandler handler = FlightHandler.getInstance();
	//FlightHandler handler = new FlightHandler();
	Set<Airport> ports = handler.getAirports();
	
	String from = request.getParameter("departairport");
	String to = request.getParameter("arriveairport");
	String sort = request.getParameter("sort");
	String date = request.getParameter("date");
	String time = request.getParameter("time");
	
%>
		<div class="container">
			<!-- departing airport -->
			<div class="half">
				<div>Startflughafen</div>
				<div>
					<input list="departairport" name="departairport" required 
					<% if(from != null && !from.equals("") && !from.equals("null")) out.print("value=\"" + from + "\""); %> >
					<datalist id="departairport">
						<% for(Airport port : ports) { %>
							<option value="<%= port.getIcao() %>"><%= port.getCity() %>, <%= port.getCountry() %></option>
						<% } %>
					</datalist>
				</div>
			</div>
			
			<!-- arriving airport -->
			<div class="half">
				<div>Zielflughafen</div>
				<div>
					<input list="arriveairport" name="arriveairport" required
					<% if(to != null && !to.equals("") && !to.equals("null")) out.print("value=\"" + to + "\""); %> >
					<datalist id="arriveairport">
						<% for(Airport port : ports) { %>
							<option value="<%= port.getIcao() %>"><%= port.getCity() %>, <%= port.getCountry() %></option>
						<% } %>
					</datalist>
				</div>
			</div>
		</div>
		
		<div class="container">
			<div class="half">
				<div>Datum
					<input type="date" name="date" required
					<% if(date != null && !date.equals("") && !date.equals("null")) out.print("value=\"" + date + "\""); %> >
				</div>
			</div>
			
			<div class="half">
				<div>Uhrzeit				
					<input type="time" name="time" required
					<% if(time != null && !time.equals("") && !time.equals("null")) out.print("value=\"" + time + "\""); %> >
				</div>
			</div>
	
		</div> <!-- END airport wrapper -->
	
		<div class="container">
			<div class="half">
				<input type="checkbox" name="direct" value="true"> Nur Direktfl&uuml;ge
			</div>
	
			<div class="half">
				<input class="button" type="submit" value="Los!">
			</div>
		</div>

	</form>
	
	<div class="line" style="margin-top:30px; margin-bottom:30px;"></div>
	
	<div class="container">
		<div class="half">
			<div>Sie sind mit der Buchung unzufrieden?</div>
		</div>
	
		<div class="half">
			<a href="Cancel.jsp"><div class="button">Stornieren</div></a>
		</div>
	</div>

</div> <!-- END main wrapper -->

</body>
</html>
