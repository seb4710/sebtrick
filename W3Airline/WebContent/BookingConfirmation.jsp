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
<div class="navigation"><a href="Index.jsp">W3 Flugsuchmaschine</a></div>

	<%
		BookingHandler bh = BookingHandler.getInstance();
		
		bh.getCurrent().setFname(request.getParameter("firstname"));
		bh.getCurrent().setLname(request.getParameter("lastname"));
		bh.getCurrent().setId(request.getParameter("identification"));
		
		String uuid = bh.performBooking();
	%>

<div id="main-wrapper"> <!-- BEGIN main wrapper -->
	<div class="container">


		<% 
			if(!uuid.equals("Buchung fehlgeschlagen")){
		%> 
			<div>Ihr Flug wurde erfolgreich gebucht! Ihre Buchungsnummer lautet:</div>
			<div class="booking-number"> <%=uuid%> </div>	
			<div>Schreiben Sie sich diese Nummer auf, um Ihr Ticket abzuholen oder die Buchung zu stornieren! </div>
		<% 	
			}else{ 
		%>
			<div>Ihr Flug konnte leider nicht gebucht werden!</div>
		<%
			}
		%>
		
		
	
		 	
				<a href="Cancel.jsp"><div class="button">Stornieren</div></a>
			

	</div>

</div>  <!-- END main wrapper -->


</body>
</html>