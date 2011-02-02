package junittestbranches;

import org.junit.AfterClass;
import org.junit.Test;

public class BranchAndMergeLongerTest extends AbstractBranchRuleTest {

	@Test
	public void simpleBranch() throws Exception {

		print("A");
		brancher.branch(new Runnable() {
			public void run() {
				print("1");
				brancher.branchAndMerge(new Runnable() {
					public void run() {
						print("#");
					}
				});
				print("2");
			}
		});
		print("B");
		brancher.branchAndMerge(new Runnable() {
			public void run() {
				print("3");
			}
		});
		print("C");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		// Note that it continues after # and 3, because of the "merge"
		checkWalkedPaths("A 1 # 2", "A 1 2", "A B 3 C", "A B C");
	}

}
