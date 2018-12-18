/**
 * 
 */
package squedgy.utilities.error;
/**
 * This is a serious exception at runtime that cannot be easily fixed, and requires a restart or shutdown
 * @author Squedgy
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
