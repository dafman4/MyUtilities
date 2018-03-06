
package com.davidafaris.utilities.datetime;

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
 * @author David A. Faris, dfaris@my.wctc.edu
 * @version 1.0
 * @since 1.8
 */
public final class DateUtilities {
	private static DateUtilities instance;
	
	private DateUtilities(){
	}
	/**
	 * A Method that returns an instance of DateUtilities and creates one if none exists.
	 * @return An Instance of this class
	 */
	public static DateUtilities getInstance(){
		return instance == null ? new DateUtilities() : instance;
	}
	/**
	 * Returns a basic Month/Day/Year format in String form for a LocalDate object
	 * @param time the object that is being turned into a formatted String
	 * @return A formatted String in Month/Day/Year form if the parameter existed
	 * @throws IllegalArgumentException - If the argument is null throws an exception
	 */
	public String getFormattedLocalDate(LocalDate time) throws IllegalArgumentException{
		if(time == null)
			throw new IllegalArgumentException("Time cannot be null!");
		return time.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
	}
	/**
	 * Returns a String representative of a LocalDate in a format requested by the user
	 * using Java.time.LocalDate format requirements
	 * @param time The object being used to create the String being Returned
	 * @param format The Format String defining how the date should be formatted following DateTimeFormatter formats
	 * @return A formatted String representing a LocalDate or null if parameter requirements were not met
	 * @throws IllegalArgumentException - If the arguments are null or empty (if applicable) throws an exception
	 */
	public String getFormattedLocalDate(LocalDate time, String format) throws IllegalArgumentException{
		if(time == null || format == null || format.isEmpty())
			throw new IllegalArgumentException("Time cannot be null and format cannot be null or empty!");
		return time.format(DateTimeFormatter.ofPattern(format));
	}
	/**
	 * Returns a LocalDateTime object as a formatted string following
	 * Month/Day/Year - Hours(military):Minutes:Seconds
	 * @param dateTime The object being returned as a string
	 * @return returns a formatted string following the format Month/Day/Year - Hours(Military):Minutes:Seconds
	 * @throws IllegalArgumentException - if dateTime is null
	 */
	public String getFormattedLocalDateTime(LocalDateTime dateTime)throws IllegalArgumentException{
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
	public String getFormattedLocalDateTime(LocalDateTime dateTime, String format){
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
	 * @throws IllegalArgumentException if the string is null or empty, or contain EXACTLY 2 groups of forward slashes (/)
	 */
	public LocalDate getLocalDateFromFormattedString(String formattedString) throws IllegalArgumentException, Exception{
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
	public LocalDateTime getLocalDateTimeFromFormattedString(String formattedString) throws IllegalArgumentException, Exception{
		if(formattedString == null || formattedString.isEmpty() || !formattedString.contains(" "))
			throw new IllegalArgumentException("Formatted String must not be null, empty, or without a space \" \"!");
		String[] dateAndTime = formattedString.split(" ");
		if(dateAndTime.length != 2)
			throw new IllegalArgumentException("Date and Time must seperated by a space(s)");
		int[] yearMonthDay = getYearMonthDayOfFormattedString(dateAndTime[0]);
		int[] hoursMinutesSeconds = getHoursMinutesSecondsOfFormattedString(dateAndTime[1]);
		return LocalDateTime.of(yearMonthDay[0], yearMonthDay[1], yearMonthDay[2],
				hoursMinutesSeconds[0], hoursMinutesSeconds[1], hoursMinutesSeconds[2]);
	}
	/**
	 * Returns the absolute difference in Seconds between the input LocalDateTime objects
	 * @param date1 the first LocalDateTime Object
	 * @param date2 The Second LocalDateTime Object
	 * @return a Long value symbolic of the absolute difference in Seconds between the input LocalDateTimes
	 */
	public long getSecondsDifferenceBetweenLocalDateTimes(LocalDateTime date1, LocalDateTime date2){
		return Math.abs(date1.until(date2, ChronoUnit.SECONDS));
	}
	/**
	 * Returns the absolute difference in Seconds between the input LocalDate objects
	 * @param date1 The First LocalDate Object
	 * @param date2 The Second LocalDate Object
	 * @return a long value symbolic of the absolute difference in Seconds between the input LocalDates
	 */
	public long getSecondsDifferenceBetweenLocalDates(LocalDate date1, LocalDate date2){
		return Math.abs(date1.until(date2, ChronoUnit.SECONDS));
	}
	/**
	 * Returns a String relating to a Duration object created from the total amount of time in Seconds
	 * In the format [Hours] Hours [Minutes] Minutes [Seconds] Seconds
	 * @param totalTime the amount of Seconds in the duration
	 * @return a String relating to a duration object created by an amount of Seconds
	 * @throws IllegalArgumentException if totalTime is less than zero
	 */
	public String getDurationAsStringOfTimeFromSeconds(long totalTime){
		if(totalTime <0)
			throw new IllegalArgumentException("Total Time must be greater than or equal to 0!");
		String ret = "";
		Duration time = Duration.ofSeconds(totalTime);
		ret = time.toString().substring(2).replaceAll("H", " Hours " ).replaceAll("M", " Minutes ").replaceAll("S", " Seconds");
		
		return ret;
	}
	/**
	 * Creates and returns a Duration Object representative of a total time in Seconds
	 * @param totalTime The amount of time being converted to a Duration object
	 * @return A Duration object with a duration equal to totalTime
	 */
	public Duration getDurationOfTimeFromSeconds(long totalTime){
		return Duration.ofSeconds(totalTime);
	}

	/**
	 * Creates a Duration Object with a duration equal to the input durations
	 * @param durations an input amount of durations (comma separated, array, or list)
	 * @return a Duration Object with a duration equal to all input durations
	 */
	public Duration addDurationsToNewDuration(Duration... durations){
		long totalSeconds = 0;
		for(Duration d: durations)
			totalSeconds += d.get(ChronoUnit.SECONDS);
		return Duration.ofSeconds(totalSeconds);
	}
	
	/**
	 * gets the minutes that exist within a given duration
	 * @param d the duration being converted
	 * @return the amount of minutes within the duration
	 */
	public long getMinutesWithinDuration(Duration d){
		return d.getSeconds()/60;
	}
	
	private int[] getHoursMinutesSecondsOfFormattedString(String s){
		int[] ret = new int[3];
		if(s == null || s.isEmpty()){
			throw new IllegalArgumentException("Formatted Time String must not be null or empty!");
		}
		if(!s.contains(":"))
			throw new IllegalArgumentException("Formatted Time String must contain exactly 2 colons");
		String[] hoursMinutesSeconds = s.split(":");
		if(s.length() != 3)
			throw new IllegalArgumentException("Formatted Time String must contain exactly 2 colons");
		for(String s2 : hoursMinutesSeconds)
			if(!this.checkIfStringIsInteger(s2))
				throw new IllegalArgumentException("All parts of the string must be integers");
		ret[0] = Integer.valueOf(hoursMinutesSeconds[0]);
		ret[1] = Integer.valueOf(hoursMinutesSeconds[1]);
		ret[2] = Integer.valueOf(hoursMinutesSeconds[2]);
		return ret;
	}
	
	private int[] getYearMonthDayOfFormattedString(String s) {
		if(s == null || s.isEmpty())
			throw new IllegalArgumentException("Formatted String cannot be null or empty!");
		if(!s.contains("/"))
			throw new IllegalArgumentException("Formatted String must contain forward slashes (/)");
		String[] parts = s.split("/+");
		if(parts.length != 3)
			throw new IllegalArgumentException("Formatted String must contain exactly 2 groups of forward slashes (/)");
		
		int[] ret = new int[3];
		for(String part : parts)
			if(!checkIfStringIsInteger(part))
				throw new IllegalArgumentException();
		ret[0] = Integer.valueOf(parts[0]);
		ret[1] = Integer.valueOf(parts[1]);
		ret[2] = Integer.valueOf(parts[2]);
		return ret;
	}
	
	private boolean checkIfStringIsInteger(String s){
		try{
			Integer.valueOf(s);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
}
