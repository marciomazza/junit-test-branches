package junitbranchrule;

import java.io.PrintWriter;
import java.io.StringWriter;

import junitbranchrule.BranchRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;

public abstract class AbstractBranchRuleTest {

	protected static StringWriter out;
	private static PrintWriter printer;

	@Rule
	public BranchRule brancher = new BranchRule();

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
