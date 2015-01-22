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
<title>Insert title here</title>
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
String flightId1 = request.getParameter("first");
String flightId2 = request.getParameter("second");

Flight first = null;
Flight second = null;
FlightCombo combo = null;

FlightHandler handler = new FlightHandler();

if(flightId1 != null)
	first = handler.getFlight(flightId1);

if(flightId2 != null && !flightId2.equals("")) {
	second = handler.getFlight(flightId2);
	combo = new FlightCombo(first, second);
} else {
	combo = new FlightCombo(first);
}
	

%>
		<div class="flight">
			<div class="price">Preis</div>
			<div class="deptime">Abflug</div>
			<div class="departure">Von</div>
			<div class="arrtime">Ankunft</div>
			<div class="arrival">Nach</div>
			<div class="duration">Dauer</div>
		</div>
		
		<div class="flight">
			<div class="price"><%=Formatter.formatMoney(first.getStdFee().floatValue()) %></div>
			<div class="deptime"><%=Formatter.formatTime(first.getDepartureTime().getTimeOfDay()) %></div>
			<div class="departure"><%=first.getDepartesFrom().getName()%></div>
			<div class="arrtime"><%=Formatter.formatTime(first.getArrivalTime().getTimeOfDay()) %></div>
			<div class="arrival"><%=first.getArrivesAt().getName() %></div>
			<div class="duration"><%=Formatter.formatDuration(combo.getDuration_First()) %></div>
		</div>
		
		<div class="flight">
			<div class="form-descr">Taxen '<%= handler.getAirlineName(flightId1) %>' : </div>
			<div class="form-field"><%=Formatter.formatMoney(10.0f) %></div>
			
			<div class="form-descr">Taxen ' <%= first.getArrivesAt().getName()%>' : </div>
			<div class="form-field"><%=Formatter.formatMoney(first.getDepartesFrom().getAirportTax().floatValue()) %></div>
			
			<div class="form-descr">Taxen ' <%= first.getArrivesAt().getName()%>' : </div>
			<div class="form-field"><%=Formatter.formatMoney(first.getArrivesAt().getAirportTax().floatValue()) %></div>
			
			<div class="form-descr">Fluggeb&uuml;hren ' <%= first.getFlightId()%>' : </div>
			<div class="form-field"><%=Formatter.formatMoney(first.getStdFee().floatValue()) %></div>
		</div>
		
<% if(second != null) {

	float fee = 10.0f;
	
	// Airline is to pay only one time per Airline
	if(handler.getAirlineName(combo.getFirstId()).equals(handler.getAirlineName(combo.getSecondId())))
		fee = 0.0f;
%>

		<div class="flight">
			<div class="price"><%=Formatter.formatMoney(second.getStdFee().floatValue()) %></div>
			<div class="deptime"><%=Formatter.formatTime(second.getDepartureTime().getTimeOfDay()) %></div>
			<div class="departure"><%=second.getDepartesFrom().getName()%></div>
			<div class="arrtime"><%=Formatter.formatTime(second.getArrivalTime().getTimeOfDay()) %></div>
			<div class="arrival"><%=second.getArrivesAt().getName() %></div>
			<div class="duration"><%=Formatter.formatDuration(combo.getDuration_Second()) %></div>
		</div>
		
		<div class="flight">
			<div class="form-descr">Taxen '<%= handler.getAirlineName(flightId2) %>' : </div>
			<div class="form-field"><%=Formatter.formatMoney(fee) %></div>
			
			<div class="form-descr">Taxen ' <%= second.getArrivesAt().getName()%>' : </div>
			<div class="form-field"><%=Formatter.formatMoney(second.getDepartesFrom().getAirportTax().floatValue()) %></div>
			
			<div class="form-descr">Taxen ' <%= second.getArrivesAt().getName()%>' : </div>
			<div class="form-field"><%=Formatter.formatMoney(second.getArrivesAt().getAirportTax().floatValue()) %></div>
			
			<div class="form-descr">Fluggeb&uuml;hren ' <%= second.getFlightId()%>' : </div>
			<div class="form-field"><%=Formatter.formatMoney(second.getStdFee().floatValue()) %></div>
		</div>
		
<% } %>



<%-- TODO: show respective flight details of the passed flightcombo --%>

<%-- TODO: implement link to booking --%>

	</div>  <!-- END flight list -->
	
</div> <!-- END main wrapper -->

</body>
</html>