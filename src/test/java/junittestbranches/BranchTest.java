package junittestbranches;

import org.junit.AfterClass;
import org.junit.Test;

public class BranchTest extends AbstractBranchRuleTest {

	@Test
	public void simpleBranch() throws Exception {

		print("A");
		brancher.branch(new Runnable() {
			public void run() {
				print("1");
			}
		});
		print("B");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		checkWalkedPaths("A 1", "A B");
	}

}
