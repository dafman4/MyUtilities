/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davidafaris.utilities.error;
/**
 * This provides a handleable exception at runtime 
 * @author David
 */
public class HandleableException extends RuntimeException{
	private final static String ADDON = "\nThis error was handled, resuming program\n";
	
	public HandleableException(String message){
		super(message);
	}
	
	@Override
	public String getMessage(){
		return super.getMessage()+ADDON;
	}
}
