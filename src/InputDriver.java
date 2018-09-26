
public class InputDriver {
	public static void main(String[] args) {
		long time=System.currentTimeMillis();
		BigNumber b=new BigNumber("0999");
		BigNumber b2=new BigNumber("-1");
		BigNumber b3=new BigNumber("-09");
		BigNumber b4=new BigNumber("-9999");
		System.out.println(b+" "+b2+" "+b3+" "+b4);
		b=b.add(b2);
		b=b.add(b3);
		System.out.println(b.toString());
		b=b.subtract(b);
		System.out.println(b.toString());
		b=b.add(b4);
		System.out.println(b.toString());
		BigNumber huge=new BigNumber(4000,4);
		BigNumber huge2=new BigNumber(4000,2);
		System.out.println(huge);
		System.out.println(huge2);
		huge2=huge2.subtract(huge);
		System.out.println(huge2);
		System.out.println(huge2.subtract(huge2));
		System.out.println((System.currentTimeMillis()-time)/1000.0);

		
	}
}
