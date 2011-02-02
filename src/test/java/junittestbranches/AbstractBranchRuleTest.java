package junittestbranches;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;

public abstract class AbstractBranchRuleTest {

	private final static List<String> walkedPaths = new ArrayList<String>(5);
	private String path = "";

	@Rule
	public BranchRule brancher = new BranchRule();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		walkedPaths.clear();
	}

	@After
	public void tearDown() {
		walkedPaths.add(path.trim());
		path = "";
	}

	protected void print(String s) {
		path = path + s + " ";
	}

	protected static void checkWalkedPaths(String... expectedPaths) {
		assertEquals(Arrays.asList(expectedPaths), walkedPaths);
	}

}
