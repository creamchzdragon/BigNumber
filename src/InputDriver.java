
public class InputDriver {
	public static void main(String[] args) {
		BigNumber b=new BigNumber(28);
		BigNumber a=new BigNumber(500);
		BigNumber.DivisionReturn result;
			result = a.divide(b);
			System.out.println("Mod " + result.getMod());
			System.out.println("Quotient " + result.getQuotient());
		
		
	}
}
