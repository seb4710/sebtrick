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

<div id="header"></div>

<div id="main-wrapper"> <!-- BEGIN main wrapper -->

	<div id="flight-list"> <!-- BEGIN flight list -->
	
			<div id="flight-header">
				&Uuml;bersicht
			</div>
			

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
		<div class="line">
			<div>
				<div class="form-descr">Von</div>
				<div class="form-field"><%=first.getDepartesFrom().getName()%></div>
			</div>
			<div>
				<div class="form-descr">Abflug</div>
				<div class="form-field"><%=Formatter.formatTime(first.getDepartureTime().getTimeOfDay()) %></div>
			</div>
			<div>
				<div class="form-descr">Nach</div>
				<div class="form-field"><%=first.getArrivesAt().getName() %></div>
			</div>
			<div>
				<div class="form-descr">Ankunft</div>
				<div class="form-field"><%=Formatter.formatTime(first.getArrivalTime().getTimeOfDay()) %></div>
			</div>
			<div>
				<div class="form-descr">Dauer</div>
				<div class="form-field"><%=Formatter.formatDuration(combo.getDuration_First()) %></div>
			</div>
		</div>
		
		<div class="line">
			<div>
				<div class="form-descr">Taxen '<%= handler.getAirlineName(flightId1) %>' : </div>
				<div class="form-field"><%=Formatter.formatMoney(fee) %></div>
			</div>
			<div>
				<div class="form-descr">Taxen ' <%= first.getDepartesFrom().getName()%>' : </div>
				<div class="form-field"><%=Formatter.formatMoney(first.getDepartesFrom().getAirportTax().floatValue()) %></div>
			</div>
			<div>
				<div class="form-descr">Taxen ' <%= first.getArrivesAt().getName()%>' : </div>
				<div class="form-field"><%=Formatter.formatMoney(first.getArrivesAt().getAirportTax().floatValue()) %></div>
			</div>
			<div>
				<div class="form-descr">Fluggeb&uuml;hren ' <%= first.getFlightId()%>' : </div>
				<div class="form-field"><%=Formatter.formatMoney(first.getStdFee().floatValue()) %></div>
			</div>
			<div>
				<div class="form-descr"><b>Summe : </b></div>
				<% float feeFirst = fee + first.getDepartesFrom().getAirportTax().floatValue() + first.getArrivesAt().getAirportTax().floatValue() + first.getStdFee().floatValue(); %>
				<div class="form-field"><b><%=Formatter.formatMoney(feeFirst) %></b></div>
			</div>
		</div>
		
<%
	if(second != null) {
%>

		<div class="line">
			<div>
				<div class="form-descr">Von</div>
				<div class="form-field"><%=second.getDepartesFrom().getName()%></div>
			</div>
			<div>
				<div class="form-descr">Abflug</div>
				<div class="form-field"><%=Formatter.formatTime(second.getDepartureTime().getTimeOfDay()) %></div>
			</div>
			<div>
				<div class="form-descr">Nach</div>
				<div class="form-field"><%=second.getArrivesAt().getName() %></div>
			</div>
			<div>
				<div class="form-descr">Ankunft</div>
				<div class="form-field"><%=Formatter.formatTime(second.getArrivalTime().getTimeOfDay()) %></div>
			</div>
			<div>
				<div class="form-descr">Dauer</div>
				<div class="form-field"><%=Formatter.formatDuration(combo.getDuration_Second()) %></div>
			</div>
		</div>
		
		<div class="line">
		
		<%
			boolean twoAirlines = !handler.getAirlineName(combo.getFirstId()).equals(handler.getAirlineName(combo.getSecondId()));
			
		// Airline is to pay only one time per Airline
			if(twoAirlines) {
		%>
			<div>
				<div class="form-descr">Taxen '<%= handler.getAirlineName(flightId2) %>' : </div>
				<div class="form-field"><%=Formatter.formatMoney(fee) %></div>
			</div>
		<% } %>
		
			<div>
				<div class="form-descr">Taxen ' <%= second.getDepartesFrom().getName()%>' : </div>
				<div class="form-field"><%=Formatter.formatMoney(second.getDepartesFrom().getAirportTax().floatValue()) %></div>
			</div>
			<div>
				<div class="form-descr">Taxen ' <%= second.getArrivesAt().getName()%>' : </div>
				<div class="form-field"><%=Formatter.formatMoney(second.getArrivesAt().getAirportTax().floatValue()) %></div>
			</div>
			<div>
				<div class="form-descr">Fluggeb&uuml;hren ' <%= second.getFlightId()%>' : </div>
				<div class="form-field"><%=Formatter.formatMoney(second.getStdFee().floatValue()) %></div>
			</div>
			<div>
				<div class="form-descr"><b>Summe : </b></div>
				<%
					float feeSecond = second.getDepartesFrom().getAirportTax().floatValue() + second.getArrivesAt().getAirportTax().floatValue() + second.getStdFee().floatValue();
					if(twoAirlines)
						feeSecond += fee;
				%>
				<div class="form-field"><b><%=Formatter.formatMoney(feeSecond) %></b></div>
			</div>
		</div>
		
		<div class="line">
			<div class="field right">
				<a href="Booking.jsp?<%= bookingURL %>"><div class="chose">zur Buchung</div></a>
			</div>
		</div>

<% } %>



<%-- TODO: show respective flight details of the passed flightcombo --%>

<%-- TODO: implement link to booking --%>

	</div>  <!-- END flight list -->
	
</div> <!-- END main wrapper -->

</body>
</html>