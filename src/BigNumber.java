/***
 * A class that can contain crazy big numbers
 * @author Jamie Walder, Manta (insert last name), Justin Davis
 *
 */
public class BigNumber {
	//buffer that contains the digits of our BigNumber, 
	//The buffer is read from left to right with the first digit(X^0) being the end of the array
	//could be an linked list
	private int[] buffer;
	/***
	 * creates a buffer with a number of digits, fills buffer with null
	 * @param numberOfDigits the number of digits the number wil have
	 */
	public BigNumber(int numberOfDigits) {
		//adds 1 so that we can account for positive or negative
		buffer=new int[numberOfDigits+1];
	}
	/**
	 * creates a bignumber based off of a string of that number and then fills the buffer accordingly 
	 * @param number the string of the number that will be inputed
	 */
	public BigNumber(String number) {
		buffer=new int[number.length()+1];
		char[] digits=number.toCharArray();
		for(int i=0;i<digits.length;i++) {
			buffer[i]=digits[i]-'0';
		}
	}
	/** 
	 * print stuff
	 */
	public String toString() {
		String result="";
		for(int i:buffer) {
			result+=i;
		}
		return result;
	}

}
