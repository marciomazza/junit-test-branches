package junittestbranches;

import org.junit.AfterClass;
import org.junit.Test;

public class BranchLongerTest extends AbstractBranchRuleTest {

	@Test
	public void simpleBranch() throws Exception {

		print("A");
		brancher.branch(new Runnable() {
			public void run() {
				print("1");
				brancher.branch(new Runnable() {
					public void run() {
						print("#");
					}
				});
				print("2");
			}
		});
		print("B");
		brancher.branch(new Runnable() {
			public void run() {
				print("3");
			}
		});
		print("C");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		checkWalkedPaths("A 1 #", "A 1 2", "A B 3", "A B C");
	}

}
