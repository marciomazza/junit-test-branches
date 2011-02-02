package junittestbranches;

import org.junit.AfterClass;
import org.junit.Test;

public class NoBranchTest extends AbstractBranchRuleTest {

	@Test
	public void simpleBranch() throws Exception {

		print("A");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		// note that the test method was executed only once
		checkWalkedPaths("A");
	}

}
