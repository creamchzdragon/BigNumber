
public class InputDriver {
	public static void main(String[] args) {
		BigNumber b=new BigNumber("500");
		for(int i=0;i<10;i++) {
			b=b.add(b);
			System.out.println(b);
		}
		
		
	}
}
