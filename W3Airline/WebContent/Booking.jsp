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

<div class="navigation"><a href="Index.jsp">W3 Flugsuchmaschine</a></div>
<div id="main-wrapper"> <!-- BEGIN main wrapper -->

		<form action="BookingConfirmation.jsp">
		<div class="container">
			<div class="container">
				<div>Vorname</div>
				<div><input type="text" name="firstname" required></div>
			</div>

			<div class="container">
				<div>Nachname</div>
				<div><input type="text" name="lastname" required></div>
			</div>

			<div class="container">
				<div>Reisepassnummer</div>
				<div><input type="text" name="identification" required></div>
			</div>
			<div class="container">
				<div class="container">
					<input class="button" type="submit" value="Buchen!">
				</div>
				
				<div class="container">
					<a class="link" href="Index.jsp">Zur&uuml;ck zur Suche</a>
				</div>
			</div>
		</div> <!-- END booking wrapper -->
		</form>

</div> <!-- END main wrapper -->
	
	<%
		BookingHandler bh = BookingHandler.getInstance();
		
		/*
		 * flightDate handling
		 */
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-mm-dd");
		java.util.GregorianCalendar flightDateGreg = new java.util.GregorianCalendar();
		
		if(!request.getParameter("date").equals("")){
			String d = request.getParameter("date");
			Date utilDateDeparture = sdfDate.parse(d);
			flightDateGreg.setTime(utilDateDeparture);
			javax.xml.datatype.XMLGregorianCalendar flightDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(flightDateGreg);
			flightDate.toString(); 
			bh.getCurrent().setDate(flightDate);
		}else{
			bh.getCurrent().setDate(null);
		}
		
		bh.getCurrent().setFlight1(request.getParameter("first"));
		bh.getCurrent().setFlight2(request.getParameter("second"));
	%>
		
</body>
</html>
