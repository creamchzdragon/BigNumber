import java.util.Iterator;
import java.util.LinkedList;

/***
 * A class that can contain crazy big numbers
 * @author Jamie Walder, Manta (insert last name), Justin Davis
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
	private LinkedList<Integer> buffer;
	/***
	 * creates a buffer with a number of digits, fills buffer with null
	 * @param numberOfDigits the number of digits the number will have
	 */
	private BigNumber() {}
	public BigNumber(int numberOfDigits, int fillNum) {
		//adds 1 so that we can account for positive or negative
		buffer=new LinkedList<Integer>();
		for(int i=0;i<numberOfDigits;i++) {
			buffer.add(fillNum);
		}
		
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
	public BigNumber tensCompliment(BigNumber bn){
		Iterator<Integer> it=bn.buffer.descendingIterator();
		LinkedList<Integer> result=new LinkedList<>();
		result.add(10-it.next());
		while(it.hasNext()) {
			result.addFirst(9-it.next());
		}
		return new BigNumber().setBuffer(result);
		
	}
	
	public BigNumber add(BigNumber num) {
		//add padding to buffer if one isnt big enough
		int diff=this.buffer.size()-num.buffer.size();
		BigNumber thisNum=this;
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
		
		
		return new BigNumber().setBuffer(result);
	}
	private BigNumber setBuffer(LinkedList<Integer> buff) {
		this.buffer=buff;
		return this;
	}
	public BigNumber subtract(BigNumber num) {
		return add(tensCompliment(num));
		// TODO Auto-generated method stub
		
	}
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
	/** 
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

}
