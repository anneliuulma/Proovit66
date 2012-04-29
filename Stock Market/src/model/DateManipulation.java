package model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateManipulation {

	public static String dateToString(Date date) {
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.clear();
		gc.setTime(date);
		int day = gc.get(Calendar.DAY_OF_MONTH);
		String dayString = new Integer(day).toString();
		dayString = dayString.length()<2?"0"+dayString:dayString;
		int month = gc.get(Calendar.MONTH)+1;
		String monthString = new Integer(month).toString();
		monthString = monthString.length()<2?"0"+monthString:monthString;
		int year = gc.get(Calendar.YEAR);
		String yearString = new Integer(year).toString();
		while (yearString.length() < 4) {
			yearString = "0"+yearString;
		}
		return dayString+"."+monthString+"."+yearString;
	}
	
	public static Date stringToDate(String dateString) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.clear();
		gc.setLenient(false);
		int year, month, day;
		try {
			day = new Integer(dateString.substring(0,2)).intValue();
			month = new Integer(dateString.substring(3,5)).intValue();
			year = new Integer(dateString.substring(6,10)).intValue();
		} catch (NumberFormatException e1) {
			return null;
		}
		try {
			gc.set(year,month-1,day);
			return gc.getTime();
		} catch (RuntimeException e) {
			return null;
		}
	}
	
	public static Date addDaysToDate(Date date, int days) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.clear();
		gc.setTime(date);
		gc.add(Calendar.DAY_OF_MONTH,days);
		date = gc.getTime();
		return date;
	}
	
	public static boolean dateValid(String dateString) {
		if (dateString == null) {
			return false;
		}
		if (dateString.length() != 10) {
			return false;
		}
		GregorianCalendar gc=new GregorianCalendar();
		gc.clear();
		gc.setLenient(false);
		int year, month, day;
		try {
			day = new Integer(dateString.substring(0,2)).intValue();
			month = new Integer(dateString.substring(3,5)).intValue();
			year = new Integer(dateString.substring(6,10)).intValue();
		} catch (NumberFormatException e1) {
			return false;
		}
		try {
			gc.set(year,month-1, day);
			gc.getTime();
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}
}