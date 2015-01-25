<!DOCTYPE html>
<%@ page import = "at.jku.ce.airline.business.BookingHandler" %>

<html>
<head>
<meta charset="UTF-8">
<title>Confirmation</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="navigation"><a href="Index.jsp">W3 Flugsuchmaschine</a></div>

	<%
		BookingHandler bh = BookingHandler.getInstance();
		
		bh.getCurrent().setFname(request.getParameter("firstname"));
		bh.getCurrent().setLname(request.getParameter("lastname"));
		bh.getCurrent().setId(request.getParameter("identification"));
		
		String uuid = bh.performBooking();
	%>

<div class="main-wrapper"> <!-- BEGIN main wrapper -->
	<div class="container">


		<% 
			if(!uuid.equals("Buchung fehlgeschlagen")){
		%> 
			<div>Ihr Flug wurde erfolgreich gebucht! Ihre Buchungsnummer lautet:</div>
			<div class="booking-number"> <%=uuid%> </div>	
			<div>Schreiben Sie sich diese Nummer auf, um Ihr Ticket abzuholen oder die Buchung zu stornieren! </div>
		
			<div class="container">
				<div class="half">
					<a class="link" href="Index.jsp">Zur&uuml;ck zur Suche</a>
				</div>
				
				<div class="half">
					<a href="Cancel.jsp"><div class="button">Stornieren</div></a>
				</div>
			</div>
			
			
			
		<% 	
			} else { 
		%>
		
			<div class="container">
				<div class="booking-number">Ihr Flug konnte leider nicht gebucht werden!</div>

				<a class="link" href="Index.jsp">Zur&uuml;ck zur Suche</a>
			</div>
		<%
			}
		%>
		 	
				
			

	</div>

</div>  <!-- END main wrapper -->


</body>
</html>