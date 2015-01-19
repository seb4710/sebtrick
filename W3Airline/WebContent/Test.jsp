<%@page import="at.jku.ce.airline.business.Formatter"%>
<%@page import="at.jku.ce.airline.business.FlightHandler"%>
<%@page import="at.jku.ce.airline.bl.AirlinePlatformService"%>
<%@page import="at.jku.ce.airline.data.FlightController"%>
<%@page import="at.jku.ce.airline.service.AirlineServiceImpl"%>
<%@page import="at.jku.ce.airline.service.AirlineServiceImplService"%>
<%@page import="at.jku.ce.airline.service.Flight"%>
<%@page import="at.jku.ce.airline.service.Airport"%>
<%@page import="at.jku.ce.juddi.UddiManager"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Set" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% Set<Airport> ports = FlightHandler.getInstance().getAirports(); %>
<% List<Flight> flights = FlightController.getInstance().getFlights(); %>
	<% System.out.println("Number of ports: " + ports.size()); %>
	
	<dl>
	
	<% for(Flight a : flights) { %>
		<dt><%= a.getDepartesFrom().getCity() %> nach <%= a.getArrivesAt().getCity() %></dt>
		<dd><%= Formatter.formatTime(a.getDepartureTime().getTimeOfDay()) %> bis <%= Formatter.formatTime(a.getArrivalTime().getTimeOfDay()) %></dd>
	<% } %>
	
	</dl>

<% %>

</body>
</html>