import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * A class that can contain crazy big numbers and perform basic arithmetic operations.
 * 
 * @authors Jamie Walder, Mantas Pileckis, Justin Davis
 * 
 */
public class BigNumber {
	//buffer that contains the digits of our BigNumber, 
	//The buffer is read from left to right with the first digit(10^0) being the end of the array
	//could be an linked list
	//the buffer is base 10
	//the buffer uses tens comliment for negative numbers
	//the buffer is god
	private LinkedList<Integer> buffer;

	/**
	 * Default constructor. Initially sets the number to 0.
	 */
	private BigNumber() {
		this(0);
	} //end default constructor

	/** 
	 * Creates a new big number based on a long.
	 * 
	 * @author Jamie Walder 
	 * @param num number this big number will be based off of.
	 */
	public BigNumber(long num) {
		// create new linked list to store integers of the big number
		buffer=new LinkedList<Integer>();
		// boolean to keep track of sign of the number
		boolean neg=num<0;
		// if a negative number, make it positive
		if(neg) {
			num*=-1;
		}
		// keep adding the (mod 10) of num to the front of the buffer
		// until num is less than 10
		while(num>=10) {
			buffer.addFirst((int)num%10);
			num/=10;
		}
		// add whatever value is left in num to front of buffer
		buffer.addFirst((int)num);
		// if number is negative, converts number to its tens complement form
		// then adds a 9 to the front to denote it is negative
		if(neg) {
			buffer=BigNumber.tensCompliment(this).buffer;
			buffer.addFirst(9);
		}
		//if positive, adds a 0 to the front
		else {
			buffer.addFirst(0);
		}
	} // end long constructor

	/**
	 * NOTES: FILLNUM SHOULD BE > 0 AND < 10!!!!
	 * 		  NUMBEROFDIGITS SHOULD BE >= 0!!!!
	 * 
	 * Creates a buffer with a number of digits.
	 * 
	 * @author Jamie Walder
	 * @param numberOfDigits the number of digits the number will have.
	 */
	public BigNumber(int numberOfDigits, int fillNum) {
		// assign a new LinkedList to the buffer
		buffer=new LinkedList<Integer>();
		// fill the entire length of the LinkedList with int specified in fillNum
		for(int i=0;i<numberOfDigits;i++) {
			buffer.add(fillNum);
		}
	} // end number of digits construtor

	/**
	 * Creates a big number based off a string input and then fills the buffer accordingly.
	 * 
	 * @author Jamie Walder
	 * @param number the string of the number that will be inputed.
	 * @throws IllegalInputException string contains something other than numbers between 0 and 9 (inclusive).
	 */
	public BigNumber(String number) {
		// determines whether the big number inputed is negative
		boolean neg=number.charAt(0)=='-';
		// assigns a new LinkedList to buffer
		buffer=new LinkedList<Integer>();
		// convert String input to an array of type char
		char[] digits=number.toCharArray();
		// create a number with ONE more decimal to account for negatives
		for(int i=(neg?1:0);i<digits.length;i++) {
			// if current char is a number between 0 and 9 (inclusive), add to buffer
			if(digits[i]<='9'&&digits[i]>='0') {
				buffer.add(digits[i]-'0');
			}
			// otherwise, current char is not a number between 0 and 9 (inclusive)
			// throw an exception
			else {
				// throw an exception and print the stack trace
				try {
					throw new IllegalInputException(""+digits[i]);
				} catch (IllegalInputException e) {
					e.printStackTrace();
				}
			}
		}
		// if the big number is negative, use the tens complement and add
		// a 9 to the front of the buffer
		if(neg) {
			buffer=tensCompliment(this).buffer;
			buffer.addFirst(9);
		}
		// big number is positive; add a 0 to front of the buffer
		else {
			buffer.addFirst(0);
		}
		// normalizes the new big number
		normalize();
	} //end String constructor

	/**
	 * Creates a big number based on another big number.
	 * 
	 * @author Jamie Walder
	 * @param bn big number we are using to create another big number.
	 */
	public BigNumber(BigNumber bn) {
		this.buffer=(LinkedList<Integer>)bn.buffer.clone();
	} // end big number constructor
	
	/**
	 * Converts the given number to it's tens complement.
	 * 
	 * @author Jamie Walder
	 * @param bn the number we want to convert to it's tens complement.
	 * @return the result of the tens complement.
	 */
	public static BigNumber tensCompliment(BigNumber bn){
		// creates an iterator that will be used to cycle through the big number
		Iterator<Integer> it=bn.buffer.descendingIterator();
		// new LinkedList to store the result of the tens complement
		LinkedList<Integer> result = new LinkedList<>();
		result.add(10-it.next());
		// 
		while(it.hasNext()) {
			result.addFirst(9-it.next());
		}
		
		if(result.getLast()==10) {
			Iterator<Integer> it2=result.descendingIterator();
			LinkedList<Integer> result2=new LinkedList<Integer>();
			result2.add(0);
			it2.next();
			int carry=1;
			while(it2.hasNext()) {
				int num=it2.next()+carry;
				if(carry>0)carry--;
				if(num>9) {
					carry++;
					num%=10;
				}
				result2.addFirst(num);
			}
			return new BigNumber().setBuffer(result2);
		}
		return new BigNumber().setBuffer(result);
	} // end tensCompliment
	
	/**
	 * Adds two big numbers and returns the result.
	 * 
	 * @author Jamie Walder
	 * @param num the number to be added to this number.
	 * @return the result of adding these two big numbers.
	 */
	public BigNumber add(BigNumber num) {
		// find the difference between the number of digits of both big numbers
		// add padding to buffer if one isn't big enough
		int diff=this.buffer.size()-num.buffer.size();
		BigNumber thisNum=this;
		thisNum=thisNum.addPadding(thisNum, 1);
		num=thisNum.addPadding(num, 1);
		// this big number has more digits - add padding to other number
		if(diff>0) {
			num=addPadding(num,diff);
		}
		// the other number has more digits - add padding to this big number
		else {
			thisNum=addPadding(thisNum,diff);
		}
		// using descending iterators to add digits from right to left
		Iterator<Integer> thisIt=thisNum.buffer.descendingIterator();
		Iterator<Integer> numIt=num.buffer.descendingIterator();
		// new LinkedList to store result of addition
		LinkedList<Integer> result=new LinkedList<Integer>();
		// variable to hold the carry
		int carry=0;
		// continue to loop while there are digits left to add
		while(thisIt.hasNext()) {
			// add the current value of digits in
			int tempR=thisIt.next()+numIt.next()+carry;
			// if the carry value is greater than 0, decrement
			if(carry>0) {
				carry--;
			}
			// if result of two digit addition is greater than 10, find the value of the result mod 10
			// and use that as the result - also, increment carry
			if(tempR>9) {
				carry++;
				tempR%=10;
			}
			// add the result of the addition to the result LinkedList
			result.addFirst(tempR);
		}
		// create a new big number and set the buffer to the result of the addition
		// normalize the new big number and return it
		BigNumber bn=new BigNumber().setBuffer(result);
		bn.normalize();
		return bn;
	} // end add
	
	/**
	 * Sets the buffer to a new buffer and returns the big number.
	 * 
	 * @author Jamie Walder
	 * @param buff the new buffer to be inserted.
	 * @return this BigNumber with the new buffer.
	 */
	private BigNumber setBuffer(LinkedList<Integer> buff) {
		this.buffer=buff;
		return this;
	} // end setBuffer
	
	/**
	 * Subtract two big numbers and return the result.
	 * 
	 * @author Jamie Walder
	 * @param num the number to be subtracted from this number.
	 * @return the result of subtraction.
	 */
	public BigNumber subtract(BigNumber num) {
		return add(tensCompliment(num));
	} // end subtract
	
	/**
	 * Added padding to the big number on the left side.
	 * 
	 * @author Jamie Walder
	 * @param bn1 the big number to be padded.
	 * @param num the amount of digits to bad it by.
	 * @return the new big number after padding.
	 */
	private BigNumber addPadding(BigNumber bn1, int num) {
		// if a negative number is added, make it positive
		if(num<0) {
			num*=-1;
		}
		int numAdded;
		// checks if the big number is negative - assign numAdded a value of 9
		if(bn1.buffer.get(0)>4) {
			numAdded=9;
		}
		// otherwise, big number is positive - assign numAdded a value of 0
		else {
			numAdded=0;
		}
		// add the value of numAdded (either a 0 or a 9) num times to the beginning of the buffer
		for(int i=0;i<num;i++) {
			bn1.buffer.addFirst(numAdded);	
		}
		// return the big number
		return bn1;
	} // end addPadding
	
	/** 
	 * Prints a String representation of the big number.
	 * 
	 * @author Jamie Walder
	 */
	public String toString() {
		BigNumber bn=this;
		String result="";
		// if number is negative, call tensCompliment on it and add a '-' sign to the String representation
		if(buffer.get(0)>4) {
			bn=tensCompliment(this);
			result+="-";
		}
		// iterate through the buffer and add each element to the String
		Iterator<Integer> it= bn.buffer.iterator();
		// continue to add while there are elements left to add
		while(it.hasNext()) {
			result+=it.next();
		}
		// return the String representation
		return result;
	} // end toString
	
	/**
	 * Shift the buffer left a designated number of spaces.
	 * 
	 * @author Jamie Walder
	 * @param num the number of spaces to shift the buffer.
	 */
	protected void shiftLeft(int num) {
		// adds 0s to the end of the big number based on the value of num
		for(int i=0;i<num;i++) {
			buffer.addLast(0);
		}
	} // end shiftLeft
	
	/**
	 * Shift the buffer right a designated number of spaces.
	 * 
	 * @author Jamie Walder
	 * @param num the number of spaces to shift the buffer.
	 */
	protected void shiftRight(int num) {
		int fillnum;
		// big number is negative, fillnum will be 9
		if(buffer.getFirst()>4) {
			fillnum=9;
		}
		// big number is positive, fillnum will be 0
		else {
			fillnum=0;
		}
		// add the value in fillnum to the front of the buffer and removes the last element num times
		for(int i=0;i<num;i++) {
			buffer.removeLast();
			buffer.addFirst(fillnum);
		}
	} // end shiftRight
	
	/**
	 * Multiplies two big numbers together and returns the result.
	 * Uses addition and shifting.
	 * 
	 * @author Justin Davis
	 * @param bigNumber Number we are multiplying our big number by.
	 * @return the product of two big numbers.
	 */
	public BigNumber multipy(BigNumber bigNumber) {
		BigNumber result, tempResult;
		result = tempResult = new BigNumber(0);
		// the current big number
		BigNumber thisTemp = this;
		// number we are multiplying by
		BigNumber thatTemp = bigNumber;
		// variable used to keep track of sign of the result
		boolean posResult = true;
		// one of the big numbers if negative and one is positive - result will be negative
		if ((this.sign() < 0 && bigNumber.sign() > 0) || (this.sign() > 0 && bigNumber.sign() < 0)) {
			posResult = false;
		}
		// the current big number is negative - use the tensComplement (positive form)
		if (this.sign() < 1) {
			thisTemp = tensCompliment(this);
		}
		// the number we are multiplying is negative - use the tensComplement (positive form)
		if (bigNumber.sign() < 1) {
			thatTemp = tensCompliment(bigNumber);
		}
		int size = thatTemp.numDigits();
		// loop for the number of digits in the big number we are multiplying by
		for (int i = 0; i < size; i++) {
			tempResult = new BigNumber(0);
			// gets the ith element of the big number we are multiplying by (the ith element of the buffer)
			int value = (int) thatTemp.getBuffer().get(i);
			// the value of the current element is not 0
			if (value != 0) {
				// adds the value of this big number over and over again based on the value of the value variable
				for (int j = 0; j < value; j++) {
					tempResult = tempResult.add(thisTemp);
				} // shifts the temporary result (adds 0s) based on the position of the current element
				tempResult.shiftLeft(size - (i + 1));
			}
			// adds the result and tempResult together
			result = result.add(tempResult);
		}
		// if one of the big numbers was negative, make the result negative
		if (!posResult) {
			result.negate();
		}
		// return the result of the multiplication
		return result;
	} //end multiply
	
	/**
	 * @author Mantas Pileckis
	 * Inner class for Division method
	 */
	public class DivisionReturn {
		BigNumber remainder;
		BigNumber quotient;
		/**
		 * Constructor for Division Return
		 * @param remainder The remainder post division
		 * @param quotient The quotient post division
		 * @param
		 */
		public DivisionReturn(BigNumber remainder, BigNumber quotient) {
			this.remainder = remainder;
			this.quotient = quotient;
		}
		/**
		 * Method to get the remainder
		 * @return The remainder post division
		 */
		public BigNumber getMod() {
			return remainder;
		}
		/**
		 * Method to get the quotient
		 * @return the quotient post division
		 */
		public BigNumber getQuotient() {
			return quotient;
		}
	}

	/**
	 * @author Mantas Pileckis
	 * Divides two bigNumbers
	 * @param bigNumber Number we are dividing our big number by
	 * @return 
	 */
	public DivisionReturn divide(BigNumber bigNumber) {
		DivisionReturn temp = null; //temp holder for new DivisionReturn Object 
		BigNumber remainder = this; //base case for remainder being 0
		boolean negative = false; //boolean flag to check if any numbers were negative

		//Check for negative, if so negate it
		//And trigger the negative flag
		if(bigNumber.sign() == -1) {
			bigNumber.negate();
			negative = true;
		}

		//check for negative, if so negate it
		//And trigger the negative flag
		if(this.sign() == -1) {
			this.negate();
			negative = true;
		}
		//If numbers are the same, return default case
		if(this.compareTo(bigNumber) == 0) {
			temp = new DivisionReturn(new BigNumber(0), new BigNumber(1));

		}

		//if the input is larger than the bigNumber, return original value as remainder (mod) and base case for quotient
		else if (this.compareTo(bigNumber) == -1) {
			temp = new DivisionReturn(this, new BigNumber(0));
		}

		else {
			BigNumber result = new BigNumber(0); //New bigNumber object to keep track of the result
			int ammount = 0; //Value used to keep track of shifts through the iteration
			int counter = 0; //Counter to populate result set
			BigNumber original  = new BigNumber(bigNumber.toString()); // original value for number that we are dividing bigNumber by
			BigNumber tempDivisor  = new BigNumber(bigNumber.toString()); //Value that we are going to be modifying thruough each iteration

			//While current remainder is EQUAL or MORE than the original Divisor
			//AND current remainder - the original divisor doesn't return negative. -> loop because there are still numbers remaining to divide.
			while(remainder.compareTo(original) == 0 || remainder.compareTo(original) == 1 && ((new BigNumber(remainder.toString()).subtract(original)).sign() == 1) ) {

				//tempDivisor  = new BigNumber(bigNumber.toString());
				//Get the difference of number digits (remainder - divisor) to determine shift.
				ammount = (remainder.numDigits()-tempDivisor.numDigits());
				tempDivisor.shiftLeft(ammount);

				//if remainder - divisor == negative, that means we assumed that divisor is too big, decrement the shift by 1.
				if( ((new BigNumber(remainder.toString()).subtract(tempDivisor)).sign() == -1)  ) {

					//Decrement the shift by 1.
					tempDivisor  = new BigNumber(bigNumber.toString());
					ammount = (remainder.numDigits()-tempDivisor.numDigits());
					tempDivisor.shiftLeft(ammount-1);
				}
				//While its a remainder - divisor is a positive number, keep subtracting and increment the counter.
				//Use counter to populate the result after iteration is done.
				while(((new BigNumber(remainder.toString()).subtract(tempDivisor)).sign() == 1)) {

					remainder = remainder.subtract(tempDivisor);
					counter++;		
				}
				//Add the counter to the result buffer to determine the quotient step by step.
				result.buffer.add(counter);
				counter = 0;

				//If we are done, pad the result with 0's according the the number of digits that we shifted previously.
				if(remainder.compareTo(new BigNumber(0)) == -1) {
					result.shiftLeft(ammount-1);
				}
			}
			//Reset the tempDivisor
			tempDivisor  = new BigNumber(bigNumber.toString());
			// If negative flag is triggered, invert the result.
			if(negative) {
				result.negate();
			}
			//Instantiate DivisionReturn object, to store the remainder and the quotient
			temp = new DivisionReturn(remainder, result);


		}
		return temp;
	}

	/**
	 * Estimates the square root of a big number using the Babylonian method and returns it.
	 * 
	 * @author Mantas Pileckis
	 * @return the result of the square root estimation.
	 */
	public BigNumber squareRoot() {
		BigNumber thisNum = new BigNumber(this);
		//find square root
		BigNumber root=new BigNumber(thisNum);
		BigNumber prev=new BigNumber(thisNum);
		// number of digits is greater than 1
		if(this.numDigits()>1) {
			// shift the value of root to the right
			root.shiftRight(thisNum.numDigits()/2);
		}
		// number of digits in 1
		else {
			// finds the square root of a number between 0 and 9 (inclusive)
			//TODO - this needs to be updated!!!!!!!!
			int newRoot = (int)Math.sqrt(Integer.parseInt(root.toString()));
			root = new BigNumber("" + newRoot);
		}
		// normalize value stored in root
		root.normalize();
		// create constants
		final BigNumber two=new BigNumber("2");
		final BigNumber zero=new BigNumber("0");
		final BigNumber one=new BigNumber("1");
		// loops until the value of two successions of root estimations are the same
		while(!root.equals(prev)) {
			prev=new BigNumber(root);
			// value of root is not 0, perform estimation
			if(!root.equals(zero))
				root=root.add(thisNum.altDivide(root)).altDivide(two);
		}
		// returns the estimated square root
		return root;
	} //end squareRoot
	
	public Set<BigNumber> factors() {
		final BigNumber two=new BigNumber("2");
		final BigNumber zero=new BigNumber("0");
		final BigNumber one=new BigNumber("1");
		BigNumber thisNum = new BigNumber(this);
		BigNumber root=squareRoot();

		HashSet result=new HashSet<BigNumber>();

		while(!root.equals(zero)) {
			BigNumber[] qr=thisNum.altDivideRemainder(root);
			if(qr[1].equals(zero)&&!root.equals(one)) {
				result.add(root);
				result.add(qr[0]);
				root=root.squareRoot();


			}
			else {
				root=root.subtract(one);

			}
			System.out.println(root);

		}

		//TODO finish it 
		return result;


	}

	private void normalize() {
		int fillnum=buffer.getFirst()>4?9:0;
		Iterator<Integer> it=buffer.iterator();
		while(buffer.size()>1&&it.hasNext()&&it.next()==fillnum) {
			it.remove();
		}
		if (fillnum == 0 && buffer.get(0) > 4) {
			buffer.addFirst(fillnum);
		}
		if (fillnum == 9 && buffer.get(0) < 5) {
			buffer.addFirst(fillnum);
		}
	}
	/**
	 * @author Justin Davis
	 * Compares two big numbers to see if they are equal
	 * @param bigNumber the big number being compared to this number
	 * @return a boolean whether or not two big numbers are equal - true if equal, false if not
	 */
	public boolean equals(BigNumber bigNumber) {
		boolean equal = true;
		if(this.compareTo(bigNumber) != 0) { //utilizes compareTo method
			equal = false; //not equal - set to false
		}
		return equal;
	}
	/**
	 * @author Justin Davis
	 * Compares two big numbers together
	 * @param bigNumber the big number to be compared to this number
	 * @return -1 if this big number is less than the number being compared, 0 if both numbers are equal, or 1 
	 * if this number is larger than the number being compared
	 */
	public int compareTo(BigNumber bigNumber) {
		int result = 0;
		//System.out.println("this: " + this);
		//System.out.println("that: " + bigNumber);
		if ((bigNumber.sign() == this.sign()) || (bigNumber.sign() == this.sign())) { //the big numbers are either both positive or both negative
			int numberOfDigits = this.buffer.size(); //number of digits in this big number
			int difference = numberOfDigits - bigNumber.numDigits(); //the difference in number of digits
			//System.out.println("difference: " + difference);
			if (difference > 0) { //this big number has more digits
				result = 1; //this big number is larger
			}
			else if (difference < 0) { //big number being compared has more digits
				result = -1; //big number being compared is larger
			}
			else { //both big numbers have the same amount of digits - must now compare individual digits
				boolean equal = true; //keeps track if the big numbers are equal - initially set to true
				int index = 0;
				while (index < numberOfDigits && equal) {
					if (this.buffer.get(index) != bigNumber.buffer.get(index)) {
						equal = false; //found two digits that are not the same - the big numbers are not equal
						if (this.buffer.get(index) > bigNumber.buffer.get(index)) { //this big number digit is larger
							result = 1; //this big number is larger
						}
						else { //the big number being compared has a larger digit
							result = -1; //the big number being compared is larger
						} //end if
					} //end if
					index++;
				}
			} //end if
		}
		else { //the big numbers do not have the same sign
			if (this.sign() == 1) { //this big number is the positive number
				result = 1; //this big number is larger
			}
			else { //this big number is a negative number
				result = -1; //the big number being compared is larger
			} //end if
		} //end if
		return result;
	}
	/**
	 * @author Justin Davis
	 * Negates our big number - if positive becomes negative; if negative becomes positive
	 */
	public void negate() {
		if (sign() > 0) {
			buffer = tensCompliment(this).buffer;
			//buffer.addFirst(9);
		}
		else {
			buffer = tensCompliment(this).buffer;
		}
	}
	/**
	 * @author Justin Davis
	 * @return an int representing the sign of the big number; +1 if positive, -1 if negative
	 */
	public int sign() {
		int sign = 1;
		if (buffer.get(0) > 4) {
			sign = -1;
		}
		return sign;
	}
	/**
	 * @author Justin Davis
	 * Returns the number of digits in the big number
	 * @return the number of digits in our big number
	 */
	public int numDigits() {
		return this.buffer.size();
	}
	public String toString2() {
		return buffer.toString();
	}
	public LinkedList getBuffer(){
		return buffer;
	}
	private BigNumber[] altDivideRemainder(BigNumber bn) {
		BigNumber thisNum=new BigNumber(this);//copy this number
		BigNumber otherNum=new BigNumber(bn);//copy the number coming in
		BigNumber qoutient=new BigNumber();//create a resulting qoutient 

		if(otherNum.sign()==-1) {//flip the other number if it is negative
			otherNum.buffer.addFirst(9);
			otherNum=tensCompliment(otherNum);
			otherNum.normalize();
		}
		if(thisNum.sign()==-1) {//flip this number if it is negative
			thisNum.buffer.addFirst(9);
			thisNum=tensCompliment(thisNum);
			thisNum.normalize();
		}
		int shift=thisNum.numDigits()-1;//create a shift factor to track how far somethign should shift
		while(shift>=0) {//keep shifting until wee are left with the initial number
			BigNumber tempDiv=new BigNumber(otherNum);//create a temp variable based on the other number and shift it by the shift amount
			tempDiv.shiftLeft(shift);
			tempDiv.normalize();
			int count=0;//keep a count for how many time you subtract from the initial number
			while(thisNum.compareTo(tempDiv)>=0) {//keep subtracting until our tempDiv is greater than our current number
				thisNum=thisNum.subtract(tempDiv);
				count++;
			}
			qoutient.buffer.addLast(count);//push the count into the qoutient buffer
			shift--;
		}
		BigNumber[] nums=new BigNumber[2];//create array to return both the qoutient and remainder
		if(this.sign()*bn.sign()==-1) {//get the final sign
			qoutient=tensCompliment(qoutient);
		}
		if(this.sign()==-1) {
			thisNum=tensCompliment(thisNum);
		}
		qoutient.normalize();
		nums[0]=qoutient;
		nums[1]=thisNum;
		return nums;
	}
	public BigNumber mod(BigNumber bn) {
		return altDivideRemainder(bn)[1];
	}
	public BigNumber altDivide(BigNumber bn) {
		return altDivideRemainder(bn)[0];
	}

}
