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
	Boolean success = false;
	if(request.getParameter("bookinguuid") != null){
		 success= false;
		 success = bh.performCancelFlight(request.getParameter("bookinguuid"));
	}
	%>

    <div class="main-wrapper"> <!-- BEGIN main wrapper -->
		<div class="container">
	<% 
		if(!success){ 
	%>
			<div>Hoppala, dieser Flug konnte nicht storniert werden.</div>
			
			<div class="container">
				<div class="half">
					<a class="link" href="Index.jsp">Zur&uuml;ck zur Suche</a>
				</div>
				
				<div class="half">
					<a href="Cancel.jsp"><div class="button">Stornieren</div></a>
				</div>
			</div>
			
	<% 
		}else{
	%>		
			<div>Ihr Flug wurde erfolgreich storniert! Ihre Buchungsnummer lautete:</div>
			<div class="booking-number"> 
	<%
		out.print(request.getParameter("bookinguuid"));
	%>
			</div>
			
			<div class="container">
				<a class="link" href="Index.jsp">Zur&uuml;ck zur Suche</a>
			</div>
	<%
		}
	%>

	
		</div> <!-- END confirmation wrapper -->
</div>  <!-- END main wrapper -->


</body>
</html>
