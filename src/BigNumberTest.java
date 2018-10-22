import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BigNumberTest {
	BigNumber bn1,bn2;

	
	
	@Test
	void testAdd() {
		//test add
				for(long i=-500;i<=500;i++) {
					for(long j=-500;j<=500;j++) {
						
						assert(i+j==Long.parseLong(new BigNumber(i).add(new BigNumber(j)).toString()));
					}
				}
	}
	@Test
	void testSub() {
		//test subtract
				for(long i=-500;i<500;i++) {
					for(long j=-500;j<500;j++) {
						assert(i-j==Long.parseLong(new BigNumber(i).subtract(new BigNumber(j)).toString()));
					}
				}
	}
	@Test
	void testMult() {
		//test subtract
				for(long i=-500;i<500;i++) {
					for(long j=-500;j<500;j++) {
						assert(i*j==Long.parseLong(new BigNumber(i).multiply(new BigNumber(j)).toString()));
					}
				}
	}
	@Test
	void testDiv() {
		//test subtract
				for(long i=-500;i<=500;i++) {
					for(long j=-500;j<=500;j++) {
						if(j==0) {
							continue;
						}
						System.out.println("div i:"+i+" j:"+j);
						assert(i/j==Long.parseLong(new BigNumber(i).divide(new BigNumber(j)).getQuotient().toString()));
					}
				}
	}
	@Test
	void testMod() {
		//test subtract
				for(long i=-500;i<500;i++) {
					for(long j=-500;j<500;j++) {
						if(j==0) {
							continue;
						}
						System.out.println("mod i:"+i+" j:"+j);
						assert(((Long)i%j)==Long.parseLong(new BigNumber(i).divide(new BigNumber(j)).getMod().toString()));
					}
				}
	}
	@Test
	void testRandos() {
		Random r=new Random();
		
		for(int i=0;i<1000;i++) {
			long long1=r.nextInt();
			long long2=r.nextInt();
			BigNumber num1=new BigNumber(long1);
			BigNumber num2=new BigNumber(long2);
			System.out.println(long1+" "+long2);
			assert(long1+long2==Long.parseLong(num1.add(num2).toString()));
			assert(long1-long2==Long.parseLong(num1.subtract(num2).toString()));
			assert(long1*long2==Long.parseLong(num1.multiply(num2).toString()));
			assert(long1/long2==Long.parseLong(num1.divide(num2).getQuotient().toString()));
			assert(long1%long2==Long.parseLong(num1.divide(num2).getMod().toString()));
		}
	}
	
	

}
