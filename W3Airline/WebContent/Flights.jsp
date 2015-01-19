<!DOCTYPE html>
<%@page import="java.util.LinkedList"%>
<%@page import="at.jku.ce.airline.business.Formatter"%>
<%@page import="at.jku.ce.airline.business.FlightCombo"%>
<%@page import="java.util.List"%>
<%@page import="at.jku.ce.airline.business.FlightHandler"%>
<html>
<head>
<meta charset="UTF-8">
<title>Available flights</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

<div id="header"></div>

<div id="main-wrapper"> <!-- BEGIN main wrapper -->

	<div id="flight-list"> <!-- BEGIN flight list -->

		<div id="flight-header">
			Gefundene Fl&uuml;ge
		</div>
		
<%
		String from = request.getParameter("departairport");
		String to = request.getParameter("arriveairport");
		List<FlightCombo> flights = new LinkedList<FlightCombo>();

		if(from != null && to != null) {
			if(request.getParameter("direct") == null)
				flights = FlightHandler.getInstance().getFlights(from, to);
			else
				flights = FlightHandler.getInstance().getDirectFlights(from, to);			
		}
			

		if(flights != null && !flights.isEmpty()) {
			for(FlightCombo combo : flights) {
		%>
		<div class="flight">
			<div class="price"><%=Formatter.formatMoney(combo.getFee()) %></div>
			<div class="deptime"><%=Formatter.formatTime(combo.getDepartureTime().getTimeOfDay()) %></div>
			<div class="departure"><%=combo.getDepartesFrom().getName()%></div>
			<div class="arrtime"><%=Formatter.formatTime(combo.getArrivalTime().getTimeOfDay()) %></div>
			<div class="arrival"><%=combo.getArrivesAt().getName() %></div>
			<div class="duration"><%=Formatter.formatDuration(combo.getDuration()) %></div>
			<div class="stops"><%= combo.getStops() %></div>
			<a href="Booking.jsp?first=<%= combo.getFirstId() %>&second=<%= combo.getSecondId() %>"><div class="chose">Buchen</div></a>
		</div>
<%
	}
} else {
%>
		<div>Es konnten leider keine passenden Fl&uuml;ge gefunden werden!</div>
<%
}
%>

	</div>  <!-- END flight list -->

</div> <!-- END main wrapper -->

</body>
</html>
