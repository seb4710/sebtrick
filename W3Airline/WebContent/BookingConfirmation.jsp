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
		
		bh.getCurrent().setFname(request.getParameter("firstname"));
		bh.getCurrent().setLname(request.getParameter("lastname"));
		bh.getCurrent().setId(request.getParameter("identification"));
		
		String uuid = bh.performBooking();
	%>

<div id="main-wrapper"> <!-- BEGIN main wrapper -->

	<div class="confirmation-wrapper"> <!-- BEGIN main wrapper -->

		<% 
			if(!uuid.equals("Buchung fehlgeschlagen")){
		%> 
			<div>Ihr Flug wurde erfolgreich gebucht! Ihre Buchungsnummer lautet:</div>
			<div class="booking-number"> <%=uuid%> </div>	
			<div>Schreiben Sie sich diese Nummer auf, um Ihr Ticket abzuholen oder die Buchung zu stornieren! </div>
		<% 	
			}else{ 
		%>
			<div>Ihr Flug wurde konnte leider nicht gebucht werden!</div>
		<%
			}
		%>
		

	<!-- display all times -->
		<div>Zur&uuml;ck zur <a href="Search.jsp">Startseite</a></div>
		<br/>
		<div>Buchung Stornieren <a href="Cancel.jsp">hier klicken!</a></div>

	</div> <!-- END confirmation wrapper -->


</div>  <!-- END main wrapper -->


</body>
</html>