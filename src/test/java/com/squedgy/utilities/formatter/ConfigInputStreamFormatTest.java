package com.squedgy.utilities.formatter;

import com.squedgy.utilities.writer.FileWriter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.squedgy.utilities.Resources.getResourceAsStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

public class ConfigInputStreamFormatTest {

	private static final Logger log = getLogger(ConfigInputStreamFormatTest.class);

	@Test
	public void reads() {

		ConfigInputStreamFormat format = new ConfigInputStreamFormat();

		Map<String,String> props = format.decode(getResourceAsStream("test.properties"));
		log.debug("props: " + props);
		assertNotNull(props.get("test"));

	}

	@Test
	public void writes() throws IOException {

		ConfigInputStreamFormat format = new ConfigInputStreamFormat();
		final String file = "settings.properties";

		FileWriter<Map<String,String>> w = new FileWriter<>( file, format, false);
		Map<String,String> props = new HashMap<>();
		props.put("test", "test");

		w.write(props);

		assertEquals(props, format.decode(new FileInputStream(new File(file))));

	}

}
