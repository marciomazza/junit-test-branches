package junitbrancher;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;

public abstract class AbstractBrancherRuleTest {

	protected static StringWriter out;
	private static PrintWriter printer;

	@Rule
	public BrancherRule brancher = new BrancherRule();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		out = new StringWriter();
		printer = new PrintWriter(out);
	}

	@Before
	public void setUp() throws Exception {
		print("---");
	}

	protected void print(String s) {
		printer.println(s);
	}

}
