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

<!-- TODO logic (depart != arrival != default) > button enable | disable -->

<div id="header"></div>

<div id="main-wrapper"> <!-- BEGIN main wrapper -->
	<form action="Flights.jsp">

	<div> <!-- BEGIN airport wrapper -->


<%
	FlightHandler handler = new FlightHandler();
	Set<Airport> ports = handler.getAirports();
	
%>

		<!-- departing airport -->
		<div class="field">
			<div>Startflughafen</div>

			<select name="departairport">
				<% for(Airport port : ports) { %>
					<option value="<%= port.getIcao() %>"><%= port.getName() %>, <%= port.getCountry() %></option>
				<% } %>
			</select>
		</div>

		<!-- arriving airport -->
		<div class="field">
			<div>Zielflughafen</div>

			<select name="arriveairport">
				<% for(Airport port : ports) { %>
					<option value="<%= port.getIcao() %>"><%= port.getName() %>, <%= port.getCountry() %></option>
				<% } %>
			</select>
		</div>

	</div> <!-- END airport wrapper -->

	<div id="submit-wrapper">
		<div class="field">
			<input type="checkbox" name="direct" value="true"> Nur Direktfl&uuml;ge
		</div>

		<div class="field">
			<input class="button" type="submit" value="Los!">
		</div>
	</div>


	</form>

</div> <!-- END main wrapper -->

</body>
</html>
