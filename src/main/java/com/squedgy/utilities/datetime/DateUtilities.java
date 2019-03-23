
package com.squedgy.utilities.datetime;

import java.rmi.UnexpectedException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * This class is created to interact with and help with the
 * creation and modification of Different Java DateTime Objects
 * As well as formatting the Date time into strings with different kinds of formatting for them.
 * 
 * @author Squedgy
 * @version 1.0
 * @since 1.8
 */
public abstract class DateUtilities {

	private static final int year = 0, month = 1, day = 2, hours = 0, minutes = 1, seconds = 2;

	/**
	 * Returns a basic Month/Day/Year format in String form for a LocalDate object
	 * @param date the object that is being turned into a formatted String
	 * @return A formatted String in Month/Day/Year form if the parameter existed
	 * @throws IllegalArgumentException - If the argument is null throws an exception
	 */
	public static String getFormattedLocalDate(LocalDate date) throws IllegalArgumentException{
		if(date == null)
			throw new IllegalArgumentException("date cannot be null!");
		return date.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
	}
	/**
	 * Returns a String representative of a LocalDate in a format requested by the user
	 * using Java.time.LocalDate format requirements
	 * @param date The object being used to create the String being Returned
	 * @param format The Format String defining how the date should be formatted following DateTimeFormatter formats
	 * @return A formatted String representing a LocalDate or null if parameter requirements were not met
	 * @throws IllegalArgumentException - If the arguments are null or empty (if applicable) throws an exception
	 */
	public static String getFormattedLocalDate(LocalDate date, String format) throws IllegalArgumentException{
		if(date == null || format == null || format.isEmpty())
			throw new IllegalArgumentException("Date cannot be null and format cannot be null or empty!");
		return date.format(DateTimeFormatter.ofPattern(format));
	}
	/**
	 * Returns a LocalDateTime object as a formatted string following
	 * Month/Day/Year - Hours(military):Minutes:Seconds
	 * @param dateTime The object being returned as a string
	 * @return returns a formatted string following the format Month/Day/Year - Hours(Military):Minutes:Seconds
	 * @throws IllegalArgumentException - if dateTime is null
	 */
	public static String getFormattedLocalDateTime(LocalDateTime dateTime)throws IllegalArgumentException{
		if(dateTime == null){
			throw new IllegalArgumentException("Date Time cannot be null!");
		}
		return dateTime.format(DateTimeFormatter.ofPattern("M/d/yyyy - HH:mm:ss"));
	}
	/**
	 * returns a String representative of a LocalDateTime Object in a format requested by the user using 
	 * Java.time.DateTimeFormatter requirements
	 * @param dateTime LocalDateTime object being converted to a String
	 * @param format the format being requested by the user following DateTimeFormatter standards
	 * @return Formatted String following the format requested by the user
	 * @throws IllegalArgumentException if dateTime is null or format is null or empty
	 */
	public static String getFormattedLocalDateTime(LocalDateTime dateTime, String format){
		if(dateTime == null || format == null || format.isEmpty()){
			throw new IllegalArgumentException("Date Time cannot be null and format cannot be null or empty!");
		}
		return dateTime.format(DateTimeFormatter.ofPattern(format));
	}
	/**
	 * Returns a LocalDate object from an input string using the following format
	 * Year/Month/Day
	 * @param formattedString The formatted string in the format Year/Month/Day
	 * @return a LocalDate Object created from the formatted string
	 * @throws IllegalArgumentException if the string is null or empty, or contians more or less than 2 groups of forward slashes (/)
	 */
	public static LocalDate localDate(String formattedString) throws IllegalArgumentException {
		int[] yearMonthDay = getYearMonthDayOfFormattedString(formattedString);
		return LocalDate.of(yearMonthDay[0], yearMonthDay[1], yearMonthDay[2]);
	}
	/**
	 * Returns a LocalDateTime object based on a formatted String using the following format
	 * Year/Month/Day Hours(Military):Minutes:Seconds
	 * @param formattedString The formatted input following Year/Month/Day Hours(Military):Minutes:Seconds
	 * @return a LocalDateTime object from the formatted string
	 * @throws IllegalArgumentException if the any input is null or empty, if the string does not contain a space, forward slashes (/),
	 * and/or colons (:) or if the separated strings using forward slashes and colons cannot be transformed into numbers
	 */
	public static LocalDateTime localDateTime(String formattedString) throws IllegalArgumentException {
		if(formattedString == null || formattedString.isEmpty() || !formattedString.contains(" "))
			throw new IllegalArgumentException("Formatted String must not be null, empty, or without a space \" \"!");
		String[] dateAndTime = formattedString.split(" ");
		if(dateAndTime.length != 2)
			throw new IllegalArgumentException("Date and Time must be the only items seperated by a space(s)");
		int[] yearMonthDay = getYearMonthDayOfFormattedString(dateAndTime[0]);
		int[] hoursMinutesSeconds = getHoursMinutesSecondsOfFormattedString(dateAndTime[1]);
		return LocalDateTime.of(yearMonthDay[year], yearMonthDay[month], yearMonthDay[day],
				hoursMinutesSeconds[0], hoursMinutesSeconds[1], hoursMinutesSeconds[seconds]);
	}
	/**
	 * Returns the absolute difference in Seconds between the input LocalDateTime objects
	 * @param date1 the first LocalDateTime Object
	 * @param date2 The Second LocalDateTime Object
	 * @return a Long value symbolic of the absolute difference in Seconds between the input LocalDateTimes
	 */
	public static long getSecondsBetween(LocalDateTime date1, LocalDateTime date2){
		return Math.abs(date1.until(date2, ChronoUnit.SECONDS));
	}
	/**
	 * Returns the absolute difference in Seconds between the input LocalDate objects
	 * @param date1 The First LocalDate Object
	 * @param date2 The Second LocalDate Object
	 * @return a long value symbolic of the absolute difference in Seconds between the input LocalDates
	 */
	public static long getSecondsBetween(LocalDate date1, LocalDate date2){
		return Math.abs(date1.until(date2, ChronoUnit.SECONDS));
	}
	/**
	 * Returns a String relating to a Duration object created from the total amount of time in Seconds
	 * In the format [Hours] Hours [Minutes] Minutes [Seconds] Seconds
	 * @param seconds the amount of Seconds in the duration
	 * @return a String relating to a duration object created by an amount of Seconds
	 * @throws IllegalArgumentException if totalTime is less than zero
	 */
	public static String secondsToDurationString(long seconds){
		if(seconds <0)
			throw new IllegalArgumentException("Total Time must be greater than or equal to 0!");
		String ret = "";
		Duration time = Duration.ofSeconds(seconds);
		ret = time.toString().substring(2).replaceAll("H", " Hours " ).replaceAll("M", " Minutes ").replaceAll("S", " Seconds");
		
		return ret;
	}

	/**
	 * Creates a Duration Object with a duration equal to the input durations
	 * @param durations an input amount of durations (comma separated, array, or list)
	 * @return a Duration Object with a duration equal to all input durations
	 */
	public static Duration sumOfDurations(Duration... durations){
		long totalSeconds = 0;
		for(Duration d: durations)
			totalSeconds += d.get(ChronoUnit.SECONDS);
		return Duration.ofSeconds(totalSeconds);
	}
	
	private static int[] getHoursMinutesSecondsOfFormattedString(String s) throws IllegalArgumentException {
		int[] ret = new int[3];
		if(s == null || s.isEmpty()){
			throw new IllegalArgumentException("Formatted Time String must not be null or empty!");
		}
		if(!s.contains(":"))
			throw new IllegalArgumentException("Formatted Time String must contain exactly 2 colons");
		return splitIntString(s, ":+", 3);
	}
	
	private static int[] getYearMonthDayOfFormattedString(String s) throws IllegalArgumentException {
		if(s == null || s.isEmpty())
			throw new IllegalArgumentException("Formatted String cannot be null or empty!");
		if(!s.contains("/"))
			throw new IllegalArgumentException("Formatted String must contain forward slashes (/)");
		return splitIntString(s, "/+", 3);
	}

	private static int[] splitIntString(String s, String regex, int length) throws RuntimeException {
		String[] parts = s.split(regex);
		if(parts.length != length){
			throw new RuntimeException("String \"" + s + "\" split by '" + regex + "' is not " + length + "long");
		}
		int[] ret = new int[length];
		for(String part : parts) if(notInteger(part)) throw new RuntimeException("All parts \"" + s + "\" split by \"" + regex + "\" are not integers");
		ret[0] = Integer.valueOf(parts[0]);
		ret[1] = Integer.valueOf(parts[1]);
		ret[2] = Integer.valueOf(parts[2]);
		return ret;
	}
	
	private static boolean notInteger(String s){
		try{
			Integer.valueOf(s);
			return false;
		}catch(NumberFormatException e){
			return true;
		}
	}
	
}
