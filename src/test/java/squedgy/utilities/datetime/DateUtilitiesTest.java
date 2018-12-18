package squedgy.utilities.datetime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author Squedgy
 */
public class DateUtilitiesTest {

	private static LocalDateTime time;

	public DateUtilitiesTest() {
	}
	
	@BeforeAll
	public static void setUp() { time = LocalDateTime.now(); }
	
	@Test
	public void testDateUtilitiesConvert(){
		int year = time.getYear(),
				month = time.getMonthValue(),
				day = time.getDayOfMonth(),
				hours = time.getHour(),
				minutes = time.getMinute(),
				seconds = time.getSecond();
		Assertions.assertEquals(
				LocalDateTime.of(year, month, day, hours, minutes, seconds),
				DateUtilities.localDateTime(time.format(DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss")))
		);

		Assertions.assertEquals(
				LocalDate.of(year, month, day),
				DateUtilities.localDate(time.format(DateTimeFormatter.ofPattern("uuuu/MM/dd")))
		);
	}

	public String twoDigits(int value){
		return (value > 9 ? "": "0") + value;
	}

	@Test
	public void testLocalDateFails(){
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			DateUtilities.localDate("");
		});
	}
	@Test
	public void testLocalDateTimeFails(){
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			DateUtilities.localDateTime("");
		});
	}
		
		
	@AfterAll
	public static void tearDown() {
		time = null;
	}
	
	
	
}
