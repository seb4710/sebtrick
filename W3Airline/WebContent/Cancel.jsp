<!DOCTYPE html>
<%@ page import = "at.jku.ce.airline.business.BookingHandler" %>
<html>
<head>
<meta charset="UTF-8">
<title>Cancel your flight!</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="navigation"><a href="Index.jsp">W3 Flugsuchmaschine</a></div>

<div class="main-wrapper"> <!-- BEGIN main wrapper -->

	<!-- div class="cancel-wrapper"> <!-- BEGIN cancel wrapper -->

	<form action="CancelConfirmation.jsp">
	<div class="container">
			<div class="container">
				<div class="form-descr">Buchungsnummer</div>
				<div class="form-value"><input type="text" name="bookinguuid" required></div>
			</div>

			<div class="container">
				<div class="container">
					<input class="button" type="submit" value="Stornieren!">
				</div>
				
				<div class="container">
					<a class="link" href="Index.jsp">Zur&uuml;ck zur Suche</a>
				</div>
			</div>
			
			
	<div> <!-- END cancel wrapper -->
	</form>
	
</div> <!-- END main wrapper -->


</body>

</html>
