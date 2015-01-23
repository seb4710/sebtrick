<%@page import="at.jku.ce.airline.business.Formatter"%>
<%@page import="at.jku.ce.airline.service.Flight"%>
<%@page import="at.jku.ce.airline.business.FlightCombo"%>
<%@page import="at.jku.ce.airline.business.FlightHandler"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Flight Details</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

<div class="navigation"><a href="Search.jsp">W3 Flugsuchmaschine</a></div>

<div class="main-wrapper"> <!-- BEGIN main wrapper -->

	<div class="header">
		&Uuml;bersicht
	</div>
	
	<div class="flight-list">
			

<%
float fee = 10.0f;

String flightId1 = request.getParameter("first");
String flightId2 = request.getParameter("second");

Flight first = null;
Flight second = null;
FlightCombo combo = null;

String bookingURL = "";

FlightHandler handler = new FlightHandler();

if(flightId1 != null && !flightId1.equals("")) {
	bookingURL += "first=" + flightId1;
	first = handler.getFlight(flightId1);
}

if(flightId2 != null && !flightId2.equals("")) {
	bookingURL += "&second=" + flightId2;
	second = handler.getFlight(flightId2);
	combo = new FlightCombo(first, second);
} else {
	combo = new FlightCombo(first);
}

//TODO date
bookingURL += "&date=";
	
%>
		<div class="flight left">
			<div class="line">
				<div class="half">Von</div>
				<div class="inline"><%=first.getDepartesFrom().getName()%></div>
			</div>
			<div class="line">
				<div class="half">Abflug</div>
				<div class="inline"><%= Formatter.formatDay(first.getDepartureTime().getIndexDayOfWeek()) %>, <%= Formatter.formatTime(first.getDepartureTime().getTimeOfDay()) %></div>
			</div>
			<div class="line">
				<div class="half">Nach</div>
				<div class="inline"><%=first.getArrivesAt().getName() %></div>
			</div>
			<div class="line">
				<div class="half">Ankunft</div>
				<div class="inline"><%= Formatter.formatDay(first.getDepartureTime().getIndexDayOfWeek()) %>, <%=Formatter.formatTime(first.getArrivalTime().getTimeOfDay()) %></div>
			</div>
			<div class="line last">
				<div class="half">Dauer</div>
				<div class="inline"><%=Formatter.formatDuration(combo.getDuration_First()) %></div>
			</div>
		</div>
		
		<div class="flight left">
			<div class="line">
				<div class="half">Taxen '<%= handler.getAirlineName(flightId1) %>' : </div>
				<div class="inline"><%=Formatter.formatMoney(fee) %></div>
			</div>
			<div class="line">
				<div class="half">Taxen ' <%= first.getDepartesFrom().getName()%>' : </div>
				<div class="inline"><%=Formatter.formatMoney(first.getDepartesFrom().getAirportTax().floatValue()) %></div>
			</div>
			<div class="line">
				<div class="half">Taxen ' <%= first.getArrivesAt().getName()%>' : </div>
				<div class="inline"><%=Formatter.formatMoney(first.getArrivesAt().getAirportTax().floatValue()) %></div>
			</div>
			<div class="line">
				<div class="half">Fluggeb&uuml;hren ' <%= first.getFlightId()%>' : </div>
				<div class="inline"><%=Formatter.formatMoney(first.getStdFee().floatValue()) %></div>
			</div>
			<div class="line last">
				<div class="half"><b>Summe : </b></div>
				<% float feeFirst = fee + first.getDepartesFrom().getAirportTax().floatValue() + first.getArrivesAt().getAirportTax().floatValue() + first.getStdFee().floatValue(); %>
				<div class="inline"><b><%=Formatter.formatMoney(feeFirst) %></b></div>
			</div>
		</div>
		
<%
	if(second != null) {
%>

		<div class="flight left">
			<div class="line">
				<div class="half">Von</div>
				<div class="inline"><%=second.getDepartesFrom().getName()%></div>
			</div>
			<div class="line">
				<div class="half">Abflug</div>
				<div class="inline"><%= Formatter.formatDay(second.getDepartureTime().getIndexDayOfWeek()) %>, <%=Formatter.formatTime(second.getDepartureTime().getTimeOfDay()) %></div>
			</div>
			<div class="line">
				<div class="half">Nach</div>
				<div class="inline"><%=second.getArrivesAt().getName() %></div>
			</div>
			<div class="line">
				<div class="half">Ankunft</div>
				<div class="inline"><%= Formatter.formatDay(second.getDepartureTime().getIndexDayOfWeek()) %>, <%=Formatter.formatTime(second.getArrivalTime().getTimeOfDay()) %></div>
			</div>
			<div class="line last">
				<div class="half">Dauer</div>
				<div class="inline"><%=Formatter.formatDuration(combo.getDuration_Second()) %></div>
			</div>
		</div>
		
		<div class="flight left">
		
		<%
			boolean twoAirlines = !handler.getAirlineName(combo.getFirstId()).equals(handler.getAirlineName(combo.getSecondId()));
			
		// Airline is to pay only one time per Airline
			if(twoAirlines) {
		%>
			<div class="line">
				<div class="half">Taxen '<%= handler.getAirlineName(flightId2) %>' : </div>
				<div class="inline"><%=Formatter.formatMoney(fee) %></div>
			</div>
		<% } %>
		
			<div class="line">
				<div class="half">Taxen ' <%= second.getDepartesFrom().getName()%>' : </div>
				<div class="inline"><%=Formatter.formatMoney(second.getDepartesFrom().getAirportTax().floatValue()) %></div>
			</div>
			<div class="line">
				<div class="half">Taxen ' <%= second.getArrivesAt().getName()%>' : </div>
				<div class="inline"><%=Formatter.formatMoney(second.getArrivesAt().getAirportTax().floatValue()) %></div>
			</div>
			<div class="line">
				<div class="half">Fluggeb&uuml;hren ' <%= second.getFlightId()%>' : </div>
				<div class="inline"><%=Formatter.formatMoney(second.getStdFee().floatValue()) %></div>
			</div>
			<div class="line last">
				<div class="half"><b>Summe : </b></div>
				<%
					float feeSecond = second.getDepartesFrom().getAirportTax().floatValue() + second.getArrivesAt().getAirportTax().floatValue() + second.getStdFee().floatValue();
					if(twoAirlines)
						feeSecond += fee;
				%>
				<div class="inline"><b><%=Formatter.formatMoney(feeSecond) %></b></div>
			</div>
		</div>
		


<% } %>

		<div class="flight left">
			<div class="line last">
				<div class="half">
					<b>Gesamtsumme</b>
				</div>
				
				<div class="inline">
					<b><%= Formatter.formatMoney(combo.getFee_Total()) %> &euro;</b>
				</div>
				
			</div>
			
			<div class="container" style="margin-top: 40px; margin-left: 40px;">
				<a href="Booking.jsp?<%= bookingURL %>"><div class="button">buchen</div></a>
			</div>
		</div>

	</div>  <!-- END flight list -->
	
</div> <!-- END main wrapper -->

</body>
</html>