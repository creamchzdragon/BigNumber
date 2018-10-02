
public class InputDriver {
	public static void main(String[] args) {
		BigNumber b=new BigNumber("500");
		for(int i=0;i<10;i++) {
			b=b.add(b);
			System.out.println(b);
		}
		
		BigNumber a = new BigNumber("-450");
		System.out.println(a);
		BigNumber c = new BigNumber("-550");
		System.out.println(c);
		System.out.println(a.toString().compareTo(c.toString()));
	}
}
