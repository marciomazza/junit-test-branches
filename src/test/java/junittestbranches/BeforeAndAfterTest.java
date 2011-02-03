package junittestbranches;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;

public class BeforeAndAfterTest {

	public static class BeforeAndAfter {
		private static String log;

		@Rule
		public BranchRule brancher = new BranchRule();

		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
			log += "-{";
		}

		@Before
		public void setUp() {
			log += " <";
		}

		@Test
		public void test1() throws Exception {
			log += "A";
			brancher.branch(new Runnable() {
				public void run() {
					log += "1";
				}
			});
		}

		@After
		public void tearDown() throws Exception {
			log += ">";
		}

		@AfterClass
		public static void tearDownAfterClass() throws Exception {
			log += "}-";
		}
	}

	@Test
	public void beforeWithTwoTests() {
		BeforeAndAfter.log = "";
		JUnitCore.runClasses(BeforeAndAfter.class);
		assertEquals("-{ <A1> <A>}-", BeforeAndAfter.log);
	}

}
