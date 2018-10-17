import static org.junit.jupiter.api.Assertions.*;

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
						assert(i*j==Long.parseLong(new BigNumber(i).multipy(new BigNumber(j)).toString()));
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
						assert(i/j==Long.parseLong(new BigNumber(i).altDivide(new BigNumber(j)).toString()));
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
						assert(((Long)i%j)==Long.parseLong(new BigNumber(i).mod(new BigNumber(j)).toString()));
					}
				}
	}
	
	

}
