import java.util.Iterator;
import java.util.LinkedList;

/***
 * A class that can contain crazy big numbers
 * @author Jamie Walder, Mantas Pileckis, Justin Davis
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
	private BigNumber() {}
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
		bn.clean();
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
	public BigNumber multipy(BigNumber bigNumber) {
		// TODO Auto-generated method stub
		return null;
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
		BigNumber thisTemp = this; //temp holder for the original value
		BigNumber subTemp = this; //temp holder for the original value post subtraction (while loop)
		BigNumber remainder = new BigNumber(0); //base case for remainder being 0
		BigNumber quotient = new BigNumber(1); //base case for quotient being 1
		boolean flag = true; //flag for the loop
		//if the input and the bigNumber are they same, return base case
		if(this.compareTo(bigNumber) == 0) {
			temp = new DivisionReturn(remainder, quotient);
			flag = false;
		}
		//if the input is larger than the bigNumber, return original value as remainder (mod) and base case for quotient
		else if (this.compareTo(bigNumber) == -1) {
			temp = new DivisionReturn(thisTemp, new BigNumber(0));
			flag = false;
		}
		//if the bigNumber is divisible by the input, loop until flag is triggered
		else {
			//counter for the loop
			int counter = 0;
			while(flag) {
				//reassign subTemp to original - input until flag is triggered
				subTemp = subTemp.subtract(bigNumber);
				//increment the counter 
				counter++;
				//if subTemp is finally equal to the input, return base case of remainder and the counter for quotient
				if(subTemp.compareTo(bigNumber) == 0) {
					temp = new DivisionReturn(remainder, new BigNumber(counter));
					flag = false;
				}
				//if subTemp is smaller than the input, return remainder as the input, and the counter for the quotient
				else if (subTemp.compareTo(bigNumber) == -1) {
					temp = new DivisionReturn(subTemp, new BigNumber(counter));
					flag = false;
				}
			}
		}
		//return DivisionReturn Object -> call .getMod() and getQuotient on return to get appropriate values.
		return temp;
	}

	public int numDigits() {
		return this.buffer.size();
	}

	public int compareTo(BigNumber bigNumber) {
		int result = 0;
		int thisFirstNum = this.buffer.getFirst(); //first number for this big number - indicates sign
		int thatFirstNum = bigNumber.buffer.getFirst(); //first number for bug number being compared - indicates sign
		if ((thisFirstNum > 4 && thatFirstNum > 4) || (thisFirstNum == 0 && thatFirstNum == 0)) { //the big numbers are either both positive or both negative
			int numberOfDigits = this.buffer.size(); //number of digits in this big number
			int difference = numberOfDigits - bigNumber.numDigits(); //the difference in number of digits
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
			if (thisFirstNum == 0) { //this big number is the positive number
				result = 1; //this big number is larger
			}
			else { //this big number is a negative number
				result = -1; //the big number being compared is larger
			} //end if
		} //end if
		return result;
	}


	private void clean() {
		int fillnum=buffer.getFirst()>4?9:0;
		Iterator<Integer> it=buffer.iterator();
		while(it.hasNext()&&it.next()==fillnum) {
			it.remove();
		}
		buffer.addFirst(fillnum);
	}





	public DivisionReturn fullDivide(BigNumber bigNumber) {
		DivisionReturn temp = null; //temp holder for new DivisionReturn Object 
		BigNumber thisTemp = this; //temp holder for the original value
		BigNumber subTemp = this; //temp holder for the original value post subtraction (while loop)
		BigNumber remainder = new BigNumber(0); //base case for remainder being 0
		BigNumber quotient = new BigNumber(1); //base case for quotient being 1
		boolean flag = true; //flag for the loop
		//if the input and the bigNumber are they same, return base case
		if(this.compareTo(bigNumber) == 0) {
			temp = new DivisionReturn(remainder, quotient);
			flag = false;
		}
		//if the input is larger than the bigNumber, return original value as remainder (mod) and base case for quotient
		else if (this.compareTo(bigNumber) == -1) {
			temp = new DivisionReturn(thisTemp, new BigNumber(0));
			flag = false;
		}

		else {
			BigNumber tempOriginal = this;
			BigNumber tempDivisor = bigNumber;
			BigNumber result = new BigNumber(0);
			int ammount = this.numDigits()-bigNumber.numDigits();
			tempDivisor.shiftLeft(ammount-1);
			int counter = 0;
			boolean finished = false;
			while(tempOriginal.compareTo(tempDivisor) == 1  || (tempOriginal.subtract(tempDivisor)).compareTo(tempDivisor) == -1) {
				
				System.out.println("original " +tempOriginal);
				System.out.println("subtract " +tempDivisor);


				if(tempOriginal.subtract(tempDivisor).sign() < 0) {
					
					tempDivisor.buffer.removeLast();
					tempOriginal = tempOriginal.subtract(tempDivisor);
					
				}
				else {
					tempOriginal = tempOriginal.subtract(tempDivisor);
					//counter++;
				}

				counter++;

				System.out.println("post-sub " +tempOriginal);
				System.out.println(counter);

				if (tempOriginal.compareTo(tempDivisor) == -1) {
					
					System.out.println("add the carry " + counter);
					System.out.println();
					
					result.buffer.add(counter);
					tempDivisor.buffer.removeLast();
					counter = 0;
				}

				else if(tempOriginal.compareTo(tempDivisor) == 0) {
					
					//counter = 0;
					result.buffer.add(0);
					
					System.out.println("add the carry xx " + counter);
					System.out.println("DONE!!!!!!!!!!!!!!!!");
					System.out.println();
					
				}


				System.out.println(result);
			}
			//result.shiftLeft(1);
			result.normalize();
			temp = new DivisionReturn(tempOriginal, result);


		}
		return temp;
	}



	private void normalize() {
		int fillnum=buffer.getFirst()>4?9:0;
		Iterator<Integer> it=buffer.iterator();
		while(it.hasNext()&&it.next()==fillnum) {
			it.remove();
		}
		if (fillnum == 0 && buffer.get(0) > 4) {
			buffer.addFirst(fillnum);
		}
		if (fillnum == 9 && buffer.get(0) < 5) {
			buffer.addFirst(fillnum);
		}
	}




	public LinkedList getBuffer(){
		return buffer;
	}

	public int sign() {
		int sign = 1;
		if (buffer.get(0) > 4) {
			sign = -1;
		}
		return sign;
	}


}
