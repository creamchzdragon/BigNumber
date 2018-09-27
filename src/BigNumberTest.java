import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BigNumberTest {
	BigNumber bn1,bn2;

	
	@Test
	void test() {
		testAdd();
		testSub();
		
	}
	@Test
	void testAdd() {
		//test add
				for(int i=-500;i<500;i++) {
					for(int j=-500;i<500;i++) {
						assert(i+j==Long.parseLong(new BigNumber(i).add(new BigNumber(j)).toString()));
					}
				}
	}
	@Test
	void testSub() {
		//test subtract
				for(int i=-500;i<500;i++) {
					for(int j=-500;i<500;i++) {
						assert(i-j==Long.parseLong(new BigNumber(i).subtract(new BigNumber(j)).toString()));
					}
				}
	}
	@Test
	void testMult() {
		//test subtract
				for(int i=-500;i<500;i++) {
					for(int j=-500;i<500;i++) {
						assert(i*j==Long.parseLong(new BigNumber(i).multipy(new BigNumber(j)).toString()));
					}
				}
	}
	@Test
	void testDiv() {
		//test subtract
				for(int i=-500;i<500;i++) {
					for(int j=-500;i<500;i++) {
						assert(i/j==Long.parseLong(new BigNumber(i).divid(new BigNumber(j)).toString()));
					}
				}
	}
	@Test
	void testMod() {
		//test subtract
				for(int i=-500;i<500;i++) {
					for(int j=-500;i<500;i++) {
						assert(i%j==Long.parseLong(new BigNumber(i).mod(new BigNumber(j)).toString()));
					}
				}
	}
	
	

}
