package com.squedgy.utilities.interfaces;

import com.squedgy.utilities.formatter.ConfigInputStreamFormat;

public interface Formatters {

	public static ConfigInputStreamFormat config() { return  new ConfigInputStreamFormat(); }

}
