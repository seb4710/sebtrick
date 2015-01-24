<!DOCTYPE html>
<%@ page import = "at.jku.ce.airline.business.BookingHandler" %>
<html>
<head>
<meta charset="UTF-8">
<title>Cancel your flight!</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="header"></div>

<div class="navigation"><a href="Index.jsp">W3 Flugsuchmaschine</a></div>

<div id="main-wrapper"> <!-- BEGIN main wrapper -->

	<!-- div class="cancel-wrapper"> <!-- BEGIN cancel wrapper -->

	<form action="CancelConfirmation.jsp">
	<div class="container">
			<div class="row">
				<div class="form-descr">Buchungsnummer</div>
				<div class="form-value"><input type="text" name="bookinguuid"></div>
			</div>

			<div id="cancel-wrapper">
				<div class="field right">
					<input class="button" type="submit" value="Stornieren!">
				</div>
			</div>
			</form>
			
	<div> <!-- END cancel wrapper -->

</div> <!-- END main wrapper -->


</body>

</html>
