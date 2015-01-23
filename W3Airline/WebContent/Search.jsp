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

<div class="navigation"><a href="Search.jsp">W3 Flugsuchmaschine</a></div>

<div class="main-wrapper"> <!-- BEGIN main wrapper -->
	<form action="Flights.jsp">

	<!-- BEGIN airport wrapper -->


<%
	FlightHandler handler = new FlightHandler();
	Set<Airport> ports = handler.getAirports();
	
%>
		<div class="container">
			<!-- departing airport -->
			<div class="half">
				<div>Startflughafen</div>
				<div>
					<input list="departairport" name="departairport" required>
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
					<input list="arriveairport" name="arriveairport" required>
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
					<input type="date" name="date" required>
				</div>
			</div>
			
			<div class="half">
				<div>Uhrzeit				
					<input type="time" name="time" required>
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

</div> <!-- END main wrapper -->

</body>
</html>
