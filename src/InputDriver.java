
public class InputDriver {
	public static void main(String[] args) {

		BigNumber a=new BigNumber("123465346565445612");
		BigNumber b= new BigNumber("3");
		BigNumber.DivisionReturn result;
		

		
			result = a.fullDivide(b);
			//result = a.divide(b);
			System.out.println("Mod " + result.getMod());
		System.out.println("Quotient " + result.getQuotient());
			//System.out.print(a.add(b));
		
			//System.out.println(a.subtract(b));
		//	System.out.println(a.fullDivide(b));
		//System.out.println(a.toString());
		//System.out.println(555<<10^288);
			
		
		
	}
}
