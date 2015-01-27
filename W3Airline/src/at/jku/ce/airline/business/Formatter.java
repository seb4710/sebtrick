package at.jku.ce.airline.business;

import java.text.DecimalFormat;
import java.util.GregorianCalendar;

public class Formatter {

	public static String CURRENCY = "&euro;";
	public static String DATE_SEPARATOR = "-";
	public static String TIME_SEPARATOR = ":";
	
	public static int stringToTime(String s) {
		if(s == null || s.equals("") || s.equals("null"))
			return 0;
		
		String[] time = s.split(TIME_SEPARATOR);
		
		return Integer.parseInt(time[0]) * 100 + Integer.parseInt(time[1]);
	}
	
	public static GregorianCalendar stringToCalendar(String s) {
		if(s == null || s.equals("") || s.equals("null"))
			return new GregorianCalendar();
		
		String[] date = s.split(DATE_SEPARATOR);
		
		return new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
	}
	
	public static String formatDay(int day) {
		switch(day) {
			case 1: return "Montag";
			case 2: return "Dienstag";
			case 3: return "Mittwoch";
			case 4: return "Donnerstag";
			case 5: return "Freitag";
			case 6: return "Samstag";
			default: return "Sonntag";
		}
	}
	
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
