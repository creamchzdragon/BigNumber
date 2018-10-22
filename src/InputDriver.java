import java.util.Scanner;

public class InputDriver {
	
	public static void main(String[] args) {
		/*String[] nums= {"-1","0","1"};
		for(int i=0;i<nums.length;i++) {
			for(int j=0;j<nums.length;j++) {
				if(j==1) {
					continue;
				}
				BigNumber bn=new BigNumber(nums[i]);
				BigNumber bn2=new BigNumber(nums[j]);
				int num1=Integer.parseInt(nums[i]);
				int num2=Integer.parseInt(nums[j]);
				System.out.println(num1/num2+" "+bn.altDivide(bn2));
				
				System.out.println(num1%num2+" "+bn.mod(bn2));
			}*/
			
		Scanner scanner = new Scanner (System.in);
		BigNumber x,y;
		System.out.println ("Enter two BigNumbers, on separate lines, or hit Enter to terminate");
		String line = scanner.nextLine();

		while (line.length() > 0)
		  {	x = new BigNumber (line);
			System.out.println ("Enter a second BigNumber");
			line = scanner.nextLine();
			y = new BigNumber (line);

			System.out.println ("Sum: " + x.add(y));
			System.out.println ("Sum: " + y.add(x));
			System.out.println ("First - Second: " + x.subtract(y));
			System.out.println ("Second - First: " + y.subtract(x));
			System.out.println ("Product: " + x.multiply(y));
			System.out.println ("Product: " + y.multiply(x));
			System.out.println ("First / Second: " + x.divide(y).getQuotient());
	 		System.out.println ("Second / First: " + y.divide(x).getQuotient());
			System.out.println ("First % Second: " + x.divide(y).getMod());
			System.out.println ("Second % First: " + y.divide(x).getMod());
		   
			line = scanner.nextLine();
		   }
		System.out.println("Factors for 117852727: " + new BigNumber("117852727").factors());
		System.out.println("Factors for 2168211218041261: " + new BigNumber("2168211218041261").factors());
		}
	
		
		
	}


