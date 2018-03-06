package com.davidafaris.datetime;

import com.davidafaris.utilities.datetime.DateUtilities;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
	
	public DateUtilitiesTest() {
	}
	
	@Before
	public void setUp() {
		du = DateUtilities.getInstance();
	}
	
	@After
	public void tearDown() {
		du = null;
	}
	
	
	
}
