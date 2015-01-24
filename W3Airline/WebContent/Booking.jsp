<!DOCTYPE html>
<%@ page import = "at.jku.ce.airline.business.BookingHandler" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "javax.xml.datatype.XMLGregorianCalendar" %>
<%@ page import = "java.util.GregorianCalendar" %>
<%@ page import = "javax.xml.datatype.DatatypeFactory" %>
<%@ page import = "java.text.*" %>
<%@ page import = "at.jku.ce.airline.business.Booking" %>
<html>
<head>
<meta charset="UTF-8">
<title>Personal data</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="header"></div>

<div class="navigation"><a href="Index.jsp">W3 Flugsuchmaschine</a></div>
<div id="main-wrapper"> <!-- BEGIN main wrapper -->


	

		<form action="BookingConfirmation.jsp">
		<div class="container">
			<div class="row">
				<div class="form-descr">Vorname</div>
				<div class="form-value"><input type="text" name="firstname"></div>
			</div>

			<div class="row">
				<div class="form-descr">Nachname</div>
				<div class="form-value"><input type="text" name="lastname"></div>
			</div>

			<div class="row">
				<div class="form-descr">Reisepassnummer</div>
				<div class="form-value"><input type="text" name="identification"></div>
			</div>

			<div id="submit-wrapper">
				<div class="field right">
					<input class="button" type="submit" value="Buchen!">
				</div>
			</div>

		</form>

	<div> <!-- END booking wrapper -->


</div> <!-- END main wrapper -->
	
	<%
	
	/////dummy Datum////
	SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
	Date utilDateDeparture = sdfDate.parse("20.10.2015");
	java.util.GregorianCalendar flightDateGreg = new java.util.GregorianCalendar();
	flightDateGreg.setTime(utilDateDeparture);
	javax.xml.datatype.XMLGregorianCalendar flightDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(flightDateGreg);
	///////////////////
	
 	BookingHandler bh = BookingHandler.getInstance();
	
	bh.getCurrent().setFlight1(request.getParameter("first"));
	bh.getCurrent().setFlight2(request.getParameter("second"));
	bh.getCurrent().setDate(flightDate);
	%>
		
</body>
</html>
