
public class InputDriver {
	public static void main(String[] args) {

		BigNumber a=new BigNumber("1123313");
		BigNumber b= new BigNumber("-11");
		BigNumber.DivisionReturn result;



		result = a.divide(b);
		
		//BigNumber factor = new BigNumber("117852727");
		
		//System.out.println(factor.factor(factor));

		System.out.println("Mod " + result.getMod());
		System.out.println("Quotient " + result.getQuotient());




	}
}
