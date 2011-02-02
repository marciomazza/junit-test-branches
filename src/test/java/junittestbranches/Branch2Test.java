package junittestbranches;

import org.junit.AfterClass;
import org.junit.Test;

public class Branch2Test extends AbstractBranchRuleTest {

	@Test
	public void abc() throws Exception {

		print("A");
		brancher.branch(new Runnable() {
			public void run() {
				print("1");
			}
		});
		print("B");
		brancher.branch(new Runnable() {
			public void run() {
				print("2");
			}
		});
		print("C");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		checkWalkedPaths("A 1", "A B 2", "A B C");
	}

}
