/**
 * An exception that is thrown when an invalid character is used.
 * 
 * @author Jamie Walder
 */
public class IllegalInputException extends Exception {
	public IllegalInputException(String input) {
		super("The Character "+input+" is not valid");
	} // end constructor
} // end IllegalInputException
