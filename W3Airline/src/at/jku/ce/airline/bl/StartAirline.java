package at.jku.ce.airline.bl;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class StartAirline extends HttpServlet {
	
	public void init() throws ServletException {	
		AirlinePlatformService.getInstance();
	}
}
