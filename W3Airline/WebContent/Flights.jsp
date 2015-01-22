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
		String sort = request.getParameter("sort");
		
		FlightHandler handler = new FlightHandler();
		List<FlightCombo> flights = new LinkedList<FlightCombo>();
		
		if(from != null && to != null) {
			if(request.getParameter("direct") == null)
				flights = handler.getAllFlights(from, to, sort);
			else
				flights = handler.getDirectFlights(from, to, sort);	
		}
			

		if(flights != null && !flights.isEmpty()) { %>
		
		<div class="flight">
			<div class="third">Sortieren nach:</div>
			<div class="third"><a href="<%= "Flights.jsp?" + "departairport=" + from + "&arriveairport=" + to + "&sort=fee" %>"><div class="chose">Preis</div></a></div>
			<div class="third"><a href="<%= "Flights.jsp?" + "departairport=" + from + "&arriveairport=" + to + "&sort=duration" %>"><div class="chose">Flugzeit</div></a></div>
		</div>
		
		<div class="flight">
			<div class="price">Preis</div>
			<div class="deptime">Abflug</div>
			<div class="departure">Von</div>
			<div class="arrtime">Ankunft</div>
			<div class="arrival">Nach</div>
			<div class="duration">Dauer</div>
			<div class="stops">Stops</div>
			<div class="stops">Details</div>
		</div>
		
			<%
			for(FlightCombo combo : flights) {
				float fee = combo.getFee_Total();
				
				// Airline is to pay only one time per Airline
				if(combo.getStops() == 1 && !handler.getAirlineName(combo.getFirstId()).equals(handler.getAirlineName(combo.getSecondId())))
					fee += 20.0f;
				else
					fee += 10.0f;
				
			%>
			
		<div class="flight">
			<div class="price"><%=Formatter.formatMoney(fee) %></div>
			<div class="deptime"><%=Formatter.formatTime(combo.getDepartureTime().getTimeOfDay()) %></div>
			<div class="departure"><%=combo.getDepartesFrom().getName()%></div>
			<div class="arrtime"><%=Formatter.formatTime(combo.getArrivalTime().getTimeOfDay()) %></div>
			<div class="arrival"><%=combo.getArrivesAt().getName() %></div>
			<div class="duration"><%=Formatter.formatDuration(combo.getDuration_Total()) %></div>
			<div class="stops"><%= combo.getStops() %></div>
			<a href="Details.jsp?first=<%= combo.getFirstId() %>&second=<%= combo.getSecondId() %>"><div class="chose">Details</div></a>
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
