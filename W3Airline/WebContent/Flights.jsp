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
		
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		
		String maxDuration = request.getParameter("maxduration");
		String maxPrice = request.getParameter("maxprice");
		
		FlightHandler handler = new FlightHandler();
		List<FlightCombo> flights = new LinkedList<FlightCombo>();
		
		if(from != null && to != null) {
			if(request.getParameter("direct") == null)
				flights = handler.getAllFlights(from, to, date, time, sort, maxDuration, maxPrice);
			else
				flights = handler.getDirectFlights(from, to, date, time, sort, maxDuration, maxPrice);	
		}
			

		if(flights != null && !flights.isEmpty()) { %>
		
		<form action="Flights.jsp?" method="GET">
		
		<div class="flight">
		
			<div class="field">
				<div class="form-descr">Datum</div>
				
				<div class="form-field">
					<input type="date" name="date" id="date" value="<%= date %>">
				</div>
			</div>
			
			<div class="field">
				<div class="form-descr">Uhrzeit</div>
				
				<div class="form-field">
					<input type="time" name="time" id="time" value="<%= time %>">
				</div>
			</div>
		
		</div>
		
		<div class="flight">
			
			<div class="third">
				<div>Zeit-Limit</div>
				<input type="range" min="0" max="24" value="0" step="1" onchange="showDuration(this.value)" />
				<div id="rangeDuration">0</div>
			</div>
			
			<div class="third">
				<div>Preis-Limit</div>
				<input type="range" min="0" max="5000" value="0" step="5" onchange="showPrice(this.value)" />
				<div id="rangePrice">0</div>
			</div>
			
			<script type="text/javascript">
				function showDuration(newValue) {
					document.getElementById("rangeDuration").innerHTML=newValue + "h";
					document.getElementById("durationfield").value=newValue;
				}
				function showPrice(newValue) {
					document.getElementById("rangePrice").innerHTML=newValue + "&euro;";
					document.getElementById("pricefield").value=newValue;
				}
			</script>
			
			<div style="display: none;">
				<input type="text" name="departairport" value="<%= from %>">
				<input type="text" name="arriveairport" value="<%= to %>">
				<input type="text" name="sort" value="<%= sort %>">
				<input type="text" id="durationfield" name="maxduration" value="">
				<input type="text" id="pricefield" name="maxprice" value="">
			</div>
			
			<div class="third">
				<input class="button" type="submit" value="filtern">
			</div>
			
		</div>
		</form>

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
				
			} // END FOR LOOP
			
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
