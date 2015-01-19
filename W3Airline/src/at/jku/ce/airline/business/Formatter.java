package at.jku.ce.airline.business;

import java.text.DecimalFormat;

public class Formatter {

	public static String CURRENCY = "&euro;";
	
	public static String formatMoney(float money) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		return formatter.format(money) + CURRENCY;
	}
	
	public static long timeToMinutes(long time) {
		long unformatted;
		unformatted = (time / 100) * 60;
		unformatted += time % 100;
		
		return unformatted;
	}
	
	public static String formatTime(long time) {
		int formatTime = (int) time;
		
		int minutes = formatTime % 100;
		int hours = formatTime / 100;
		
		String s = "";
		
		if(hours < 10)
			s += "0";
		
		s += hours + ":";
		
		if(minutes < 10)
			s += "0";
		
		s += minutes;
			
		return s;
	}
	
	public static String formatDuration(int time) {
		return time / 100 + "h " + time % 100 + "min";
	}
}
