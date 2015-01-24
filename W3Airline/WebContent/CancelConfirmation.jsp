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
	Boolean success = false;
	if(request.getParameter("bookinguuid") != null){
		 success = bh.performCancelFlight(request.getParameter("bookinguuid"));
	}
	%>

    <div id="main-wrapper"> <!-- BEGIN main wrapper -->

	
	<div class="container">
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

	</div> <!-- END confirmation wrapper -->


</div>  <!-- END main wrapper -->


</body>
</html>