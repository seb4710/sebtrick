<!DOCTYPE html>
<%@ page import = "at.jku.ce.airline.business.BookingHandler" %>
<html>
<head>
<meta charset="UTF-8">
<title>Confirmation</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="header"></div>

	<%
	BookingHandler bh = BookingHandler.getInstance();	
	Boolean success = bh.performCancelFlight(request.getParameter("bookinguuid"));
	%>

    <div id="main-wrapper"> <!-- BEGIN main wrapper -->

	<div class="confirmation-wrapper"> <!-- BEGIN main wrapper -->
	
	<% 
		if(!success){ 
	%>
		<div>Hoppala, dieser Flug konnte nicht storniert werden.</div>
	<% 
		}else{
	%>		
		<div>Ihr Flug wurde erfolgreich storniert! Ihre Buchungsnummer lautete:</div>
		<div class="booking-number"> 
	<% out.print(request.getParameter("bookinguuid"));%>
	<%}%>
		</div>


	<div>Zur&uuml;ck zur <a href="Search.jsp">Startseite</a></div>

	</div> <!-- END confirmation wrapper -->


</div>  <!-- END main wrapper -->


</body>
</html>