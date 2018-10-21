
public class InputDriver {
	
	public static void main(String[] args) {
		String[] nums= {"-1","0","1"};
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
			}
			
			
		}
	
		BigNumber a = new BigNumber("44");
		System.out.println(a.factors());
		
	}

}
