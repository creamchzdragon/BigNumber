
public class IllegalInputException extends Exception {
	public IllegalInputException(String input) {
		super("The Character "+input+"is not valid");
	}
}
