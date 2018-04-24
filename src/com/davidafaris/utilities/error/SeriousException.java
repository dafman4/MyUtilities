/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This is a serious exception at runtime that cannot be easily fixed, and requires a restart or shutdown
 */
package com.davidafaris.utilities.error;

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
