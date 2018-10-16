
public class InputDriver {
	public static void main(String[] args) {
		//BigNumber b=new BigNumber("-200");
		//for(int i=0;i<10;i++) {
		//	b=b.add(b);
		//	System.out.println(b);
		//}
		//System.out.println(b.toString());
		
		//BigNumber a = new BigNumber("12");
		//System.out.println(a.toString());
		//System.out.println(a.equals(b));
		//BigNumber c = new BigNumber("-450");
		//System.out.println(c);
		
		//a.negate();
		//System.out.println(a);
		
		//c.negate();
		//System.out.println(c);
		
		BigNumber ab=new BigNumber("1500");
		BigNumber ac= new BigNumber("11");
		BigNumber.DivisionReturn result;



		result = ab.divide(ac);


		System.out.println("Mod " + result.getMod());
		System.out.println("Quotient " + result.getQuotient());

	}
}
