package junittestbranches;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This test shows that the a single branch is equivalent to the pattern {@code @Before} + two {@code @Test}
 * methods.
 * 
 * @author marciomazza
 * 
 */
public class BeforeWithTwoTestsEquivalentToBranchTest {

	public static class BeforeWithTwoTests {
		private static String log;

		@Before
		public void setUp() {
			log += " A";
		}

		@Test
		public void test1() throws Exception {
			log += "1";
		}

		@Test
		public void test2() throws Exception {
			log += "2";
		}
	}

	public static class Branch {
		private static String log;

		@Rule
		public BranchRule brancher = new BranchRule();

		@Test
		public void test() throws Exception {

			log += " A";
			brancher.branch(new Runnable() {
				public void run() {
					log += "1";
				}
			});
			log += "2";
		}
	}

	@Test
	public void beforeWithTwoTests() {
		BeforeWithTwoTests.log = Branch.log = "";
		JUnitCore.runClasses(BeforeWithTwoTests.class, Branch.class);
		assertEquals(" A1 A2", BeforeWithTwoTests.log);
		assertEquals(BeforeWithTwoTests.log, Branch.log);
	}

}
