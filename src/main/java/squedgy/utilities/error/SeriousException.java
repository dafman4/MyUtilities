/**
 * 
 */
<<<<<<< HEAD:src/com/davidafaris/utilities/error/SeriousException.java
package com.davidafaris.utilities.error;
/**
 * This is a serious exception at runtime that cannot be easily fixed, and requires a restart or shutdown
 * @author David
=======
package squedgy.utilities.error;
/**
 * This is a serious exception at runtime that cannot be easily fixed, and requires a restart or shutdown
 * @author Squedgy
>>>>>>> f3c143ac94da942eb7f112eb2af2c6a8f9b9628e:src/main/java/squedgy/utilities/error/SeriousException.java
 */
public class SeriousException extends RuntimeException{
	private final static String ADDON = "\nTHIS IS A SERIOUS EXCEPTION, shutting down";
	
	public SeriousException(String message){
		super(message);
	}
	
	@Override
	public String getMessage(){
		return super.getMessage() + ADDON;
	}
}
