import java.util.Iterator;
import java.util.LinkedList;

/***
 * A class that can contain crazy big numbers
 * @author Jamie Walder, Manta (insert last name), Justin Davis
 *
 */
public class BigNumber {
	//buffer that contains the digits of our BigNumber, 
	//The buffer is read from left to right with the first digit(X^0) being the end of the array
	//could be an linked list
	private LinkedList<Integer> buffer;
	/***
	 * creates a buffer with a number of digits, fills buffer with null
	 * @param numberOfDigits the number of digits the number wil have
	 */
	public BigNumber(int numberOfDigits) {
		//adds 1 so that we can account for positive or negative
		buffer=new LinkedList<Integer>();
		for(int i=0;i<numberOfDigits;i++) {
			buffer.add(0);
		}
		buffer.addFirst(0);
	}
	/**
	 * creates a bignumber based off of a string of that number and then fills the buffer accordingly 
	 * @param number the string of the number that will be inputed
	 * @throws IllegalInputException 
	 */
	public BigNumber(String number) {
		boolean neg=number.charAt(0)=='-';
		buffer=new LinkedList<Integer>();
		//create a number with ONE more decimal to account for negatives 
		buffer.add(0);
		char[] digits=number.toCharArray();
		for(int i=0;i<digits.length;i++) {
			if(digits[i]<='9'&&digits[i]>='0')
				buffer.add(digits[i]-'0');
			else {
				try {
					throw new IllegalInputException(""+digits[i]);
				} catch (IllegalInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
		if(neg) {
			subtract(new BigNumber(buffer.size()));
		}
	}
	private void subtract(BigNumber bigNumber) {
		// TODO Auto-generated method stub
		
	}
	/** 
	 * print stuff
	 */
	public String toString() {
		
		Iterator<Integer> it= buffer.iterator();
		String result="";
		while(it.hasNext()) {
			result+=it.next();
		}
		return result;
	}

}
