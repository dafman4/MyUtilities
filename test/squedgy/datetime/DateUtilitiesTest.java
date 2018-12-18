package com.davidafaris.datetime;

import com.davidafaris.utilities.datetime.DateUtilities;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class DateUtilitiesTest {
	private DateUtilities du;
	private LocalDateTime time;
	public DateUtilitiesTest() {
	}
	
	@Before
	public void setUp() {
		du = DateUtilities.getInstance();
		time = LocalDateTime.now();
	}
	
	@Test
	public void testDateUtilitiesConvert(){
		du.getLocalDateTimeFromFormattedString(time.format(DateTimeFormatter.ofPattern("uuuu/MM/ee HH:mm:ss")));
		du.getLocalDateFromFormattedString(time.format(DateTimeFormatter.ofPattern("uuuu/MM/ee")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetLocalDateFromFormattedStringFail(){
		du.getLocalDateFromFormattedString("");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testGetLocalDateTimeFromFormattedStringFail(){
		du.getLocalDateTimeFromFormattedString("");
	}
		
		
	@After
	public void tearDown() {
		du = null;
		time = null;
	}
	
	
	
}
