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

<div class="navigation"><a href="Search.jsp">W3 Flugsuchmaschine</a></div>

<div class="main-wrapper"> <!-- BEGIN main wrapper -->

		<div class="header">
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
		
		FlightHandler handler = FlightHandler.getInstance();
		List<FlightCombo> flights = new LinkedList<FlightCombo>();
		
		if(from != null && to != null) {
			if(request.getParameter("direct") == null)
				flights = handler.getAllFlights(from, to, date, time, sort, maxDuration, maxPrice);
			else
				flights = handler.getDirectFlights(from, to, date, time, sort, maxDuration, maxPrice);	
		}
			

		if(flights != null && !flights.isEmpty()) { %>
		
		<div class="flight-list">
		<form action="Flights.jsp?" method="GET">
		
		<div class="flight">
			<div class="container">
				<div class="half">
					<div class="inline">Datum</div>
					
					<div class="inline">
						<input type="date" name="date" id="date" value="<%= date %>">
					</div>
				</div>
				
				<div class="half">
					<div class="inline">Uhrzeit</div>
					
					<div class="inline">
						<input type="time" name="time" id="time" value="<%= time %>">
					</div>
				</div>
			</div>
			
			<div class="container">
				
				
				<div class="half">
					<div>Zeit-Limit
						<input class="input-field" type="text" id="rangeDuration" value="<% if(maxDuration == null || maxDuration.equals("") || maxDuration.equals("null")) out.print("0"); else out.print(maxDuration); %> h"/>
					</div>
					<div>
						<input type="range" min="0" max="24" value="<% if(maxDuration == null || maxDuration.equals("") || maxDuration.equals("null")) out.print("0"); else out.print(maxDuration); %>" step="1" onchange="showDuration(this.value)" />
					</div>				
				</div>
				
				<div class="half">
					<div>Preis-Limit
						<input class="input-field" type="text" id="rangePrice" value="<% if(maxPrice == null || maxPrice.equals("") || maxPrice.equals("null")) out.print("0"); else out.print(maxPrice); %> EUR" />
					</div>
					<div>
						<input type="range" min="0" max="5000" value="<% if(maxPrice == null || maxPrice.equals("") || maxPrice.equals("null")) out.print("0"); else out.print(maxPrice); %>" step="5" onchange="showPrice(this.value)" />
					</div>
				</div>
				
				<script type="text/javascript">
					function showDuration(newValue) {
						document.getElementById("rangeDuration").value=newValue + " h";
						document.getElementById("durationfield").value=newValue;
					}
					function showPrice(newValue) {
						document.getElementById("rangePrice").value=newValue + " EUR";
						document.getElementById("pricefield").value=newValue;
					}
				</script>
				
				<div style="display: none;">
					<input type="text" name="departairport" value="<%= from %>">
					<input type="text" name="arriveairport" value="<%= to %>">
					<input type="text" id="durationfield" name="maxduration" value="<%= maxDuration %>">
					<input type="text" id="pricefield" name="maxprice" value="<%= maxPrice %>">
					<input type="text" name="date" value="<%= request.getParameter("date") %>" />
					<input type="text" name="time" value="<%= request.getParameter("time") %>" />
				</div>
			</div>
			
			<div class="container">
				
				<div class="half">
					<div>Sortieren nach:
						<select name="sort">
							<option value=""></option>
							<option value="fee" <% if(sort != null && sort.equals("fee")) { out.print("selected=\"selected\""); } %>>Preis</option>
							<option value="duration" <% if(sort != null && sort.equals("duration")) { %> selected="selected" <% } %> >Flugzeit</option>
						</select>
					</div>
					
				</div>
				
				<div class="half">
					<div>
						<input type="checkbox" name="direct" value="true" <% if(request.getParameter("direct") != null && request.getParameter("direct").equals("true")) out.print("checked"); %> > Nur Direktfl&uuml;ge
					</div>
				</div>
			</div>
			
			<div class="container">
				<input class="button" type="submit" value="filtern">
			</div>
		</div>
		</form>

		<div class="flight">
			<div class="small">Preis</div>
			<div class="small">Abflug</div>
			<div class="big">Von</div>
			<div class="small">Ankunft</div>
			<div class="big">Nach</div>
			<div class="small">Dauer</div>
			<div class="small">Stops</div>
			<div class="small">Details</div>
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
				<div class="small"><%=Formatter.formatMoney(fee) %></div>
				<div class="small"><%=Formatter.formatTime(combo.getDepartureTime().getTimeOfDay()) %></div>
				<div class="big"><%=combo.getDepartesFrom().getName()%></div>
				<div class="small"><%=Formatter.formatTime(combo.getArrivalTime().getTimeOfDay()) %></div>
				<div class="big"><%=combo.getArrivesAt().getName() %></div>
				<div class="small"><%=Formatter.formatDuration(combo.getDuration_Total()) %></div>
				<div class="small"><%= combo.getStops() %></div>
				<a href="Details.jsp?first=<%= combo.getFirstId() %>&second=<%= combo.getSecondId() %>&date=<%= date %>"><div class="button small">Details</div></a>
			</div>
		<%
				
			} // END FOR LOOP
			
		} else {
		%>
		<div class="container">
			Es konnten leider keine passenden Fl&uuml;ge gefunden werden!
			<p>
				<a class="link" href="Search.jsp">Zur&uuml;ck zur Suche</a>
			</p>	
		</div>
		<%
		}
		%>
	</div> <!-- END flight list -->
</div> <!-- END main wrapper -->

</body>
</html>
