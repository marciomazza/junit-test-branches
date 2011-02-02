package junittestbranches;

import org.junit.AfterClass;
import org.junit.Test;

public class BranchAndMergeTest extends AbstractBranchRuleTest {

	@Test
	public void simpleBranch() throws Exception {

		print("A");
		brancher.branchAndMerge(new Runnable() {
			public void run() {
				print("1");
			}
		});
		print("B");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		// Note that it continues after 1, because of the "merge"
		checkWalkedPaths("A 1 B", "A B");
	}

}
