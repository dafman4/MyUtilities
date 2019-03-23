package com.squedgy.utilities;

import java.io.InputStream;
import java.net.URL;

public interface Resources {

	public static URL getResource(String resource) {
		return Thread.currentThread().getContextClassLoader().getResource(resource);
	}

	public static InputStream getResourceAsStream(String resource) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
	}

}
