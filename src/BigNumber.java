import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/***
 * A class that can contain crazy big numbers
 * @author Jamie Walder, Mantas (insert last name), Justin Davis
 *Jamie Walder
 *	• BigNumber( ) constructors
	• BigNumber add (BigNumber)
	• BigNumber subtract(BigNumber)
 */
public class BigNumber {
	//buffer that contains the digits of our BigNumber, 
	//The buffer is read from left to right with the first digit(10^0) being the end of the array
	//could be an linked list
	//the buffer is base 10
	//the buffer uses tens comliment for negative numbers
	//the buffer is god
	private LinkedList<Integer> buffer;
	//default private constructor
	private BigNumber() {
		this(0);
	}
	/**@author Jamie Walder
	 * create a new big number based on a long
	 * @param num number this big number will be based off of
	 */
	public BigNumber(long num) {
		
		buffer=new LinkedList<Integer>();
		boolean neg=num<0;
		if(neg) {
			num*=-1;
		}
		while(num>=10) {
			buffer.addFirst((int)num%10);
			num/=10;
		}
		buffer.addFirst((int)num);
		if(neg) {
			buffer=BigNumber.tensCompliment(this).buffer;
			buffer.addFirst(9);
		}
		else {
			buffer.addFirst(0);
		}
	}
	/***@author Jamie Walder
	 * creates a buffer with a number of digits, fills buffer with null
	 * @param numberOfDigits the number of digits the number will have
	 */
	public BigNumber(int numberOfDigits, int fillNum) {
		//adds 1 so that we can account for positive or negative
		buffer=new LinkedList<Integer>();
		for(int i=0;i<numberOfDigits;i++) {
			buffer.add(fillNum);
		}

	}
	/**@author Jamie Walder
	 * creates a big number based off of a string of that number and then fills the buffer accordingly 
	 * @param number the string of the number that will be inputed
	 * @throws IllegalInputException 
	 */
	public BigNumber(String number) {
		boolean neg=number.charAt(0)=='-';
		buffer=new LinkedList<Integer>();
		//create a number with ONE more decimal to account for negatives 
		char[] digits=number.toCharArray();
		for(int i=(neg?1:0);i<digits.length;i++) {
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
			buffer=tensCompliment(this).buffer;
			buffer.addFirst(9);
		}
		else {
			buffer.addFirst(0);
		}
		normalize();
	}
	public BigNumber(BigNumber bn) {
		this.buffer=(LinkedList<Integer>)bn.buffer.clone();
		
	}
	/**@author Jamie Walder
	 * converts the given number to it's tens compliment
	 * @param bn the number we want to convert to it's tens compliment
	 * @return the result of the tens compliment
	 */
	public static BigNumber tensCompliment(BigNumber bn){
		Iterator<Integer> it=bn.buffer.descendingIterator();
		LinkedList<Integer> result=new LinkedList<>();
		result.add(10-it.next());
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
			//return new BigNumber(0).add(new BigNumber().setBuffer(result));
		}
		
		return new BigNumber().setBuffer(result);

	}
	/**@author Jamie Walder
	 * adds two big numbers
	 * @param num the number to be added to this number
	 * @return the result of adding these two number
	 */
	public BigNumber add(BigNumber num) {
		//add padding to buffer if one isnt big enough

		int diff=this.buffer.size()-num.buffer.size();
		BigNumber thisNum=this;
		thisNum=thisNum.addPadding(thisNum, 1);
		num=thisNum.addPadding(num, 1);
		if(diff>0) {
			num=addPadding(num,diff);
		}
		else {
			thisNum=addPadding(thisNum,diff);
		}

		Iterator<Integer> thisIt=thisNum.buffer.descendingIterator();
		Iterator<Integer> numIt=num.buffer.descendingIterator();
		LinkedList<Integer> result=new LinkedList<Integer>();
		int carry=0;
		while(thisIt.hasNext()) {

			int tempR=thisIt.next()+numIt.next()+carry;
			if(carry>0) {
				carry--;
			}
			if(tempR>9) {
				carry++;
				tempR%=10;
			}

			result.addFirst(tempR);


		}

		BigNumber bn=new BigNumber().setBuffer(result);
		bn.normalize();
		return bn;
	}
	/**@author Jamie Walder
	 * sets the buffer to a new buffer
	 * @param buff the new buffer to be inserted
	 * @return this BigNumber with the new buffer
	 */
	private BigNumber setBuffer(LinkedList<Integer> buff) {
		this.buffer=buff;
		return this;
	}
	/**@author Jamie Walder
	 * subtract two big numbers
	 * @param num the number to be subtracted from this number
	 * @return the result of subtraction
	 */
	public BigNumber subtract(BigNumber num) {
		return add(tensCompliment(num));


	}
	/**@author Jamie Walder
	 * added padding to the big number on the left side
	 * @param bn1 the bignumber to be padded
	 * @param num the amount of digits to bad it by
	 * @return the new big number after padding
	 */
	private BigNumber addPadding(BigNumber bn1,int num) {
		if(num<0) {
			num*=-1;
		}
		int numAdded;
		if(bn1.buffer.get(0)>4) {
			numAdded=9;
		}
		else {
			numAdded=0;
		}
		for(int i=0;i<num;i++) {
			bn1.buffer.addFirst(numAdded);	
		}

		return bn1;
	}
	/** @author Jamie Walder
	 * print stuff
	 */
	public String toString() {
		BigNumber bn=this;
		String result="";
		if(buffer.get(0)>4) {
			bn=tensCompliment(this);
			result+="-";
		}
		Iterator<Integer> it= bn.buffer.iterator();

		while(it.hasNext()) {
			result+=it.next();
		}
		return result;
	}
	/**@author Jamie Walder
	 * shift the buffer left
	 * @param num the number of spaces to shift the buffer
	 */
	protected void shiftLeft(int num) {
		for(int i=0;i<num;i++) {
			buffer.addLast(0);
		}
	}
	/**@author Jamie Walder
	 * shift the buffer right
	 * @param num the number of spaces to shift the buffer
	 */
	protected void shiftRight(int num) {
		int fillnum;
		if(buffer.getFirst()>4) {
			fillnum=9;
		}
		else {
			fillnum=0;
		}
		for(int i=0;i<num;i++) {
			buffer.removeLast();
			buffer.addFirst(fillnum);
		}
	}
	/**
	 * @author Justin Davis
	 * Multiplies two big numbers together
	 * @param bigNumber Number we are multiplying our big number by
	 * @return the product of two big numbers
	 */
	public BigNumber multipy(BigNumber bigNumber) {
		BigNumber result, tempResult;
		result = tempResult = new BigNumber(0);
		BigNumber thisTemp = this;
		BigNumber thatTemp = bigNumber;
		boolean posResult = true;
		if ((this.sign() < 0 && bigNumber.sign() > 0) || (this.sign() > 0 && bigNumber.sign() < 0)) {
			posResult = false;
		}
		if (this.sign() < 1) {
			thisTemp = tensCompliment(this);
		}
		if (bigNumber.sign() < 1) {
			thatTemp = tensCompliment(bigNumber);
		}
		int size = thatTemp.numDigits();
		for (int i = 0; i < size; i++) {
			tempResult = new BigNumber(0);
			int value = (int) thatTemp.getBuffer().get(i);
			if (value != 0) {
				for (int j = 0; j < value; j++) {
					tempResult = tempResult.add(thisTemp);
				}
				tempResult.shiftLeft(size - (i + 1));
			}
			result = result.add(tempResult);
		}
		if (!posResult) {
			result.negate();
		}
		return result;
	}
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
	 * Efficiency black hole...
	 * @author Mantas Pileckis
	 */

	public Set<BigNumber> factors() {
		BigNumber result = new BigNumber(this);
		//find square root
		BigNumber root=new BigNumber(result);
		BigNumber prev=new BigNumber(result);
		root.shiftRight(result.numDigits()/2);
		while(!root.equals(prev)) {
			prev=new BigNumber(root);
			root=root.add(result.altDivide(root)).altDivide(new BigNumber("2"));
		}
		//TODO finish it 
		return new HashSet<BigNumber>();
		
		
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
