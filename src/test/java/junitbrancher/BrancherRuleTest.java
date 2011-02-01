package junitbrancher;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class BrancherRuleTest {

	private static StringWriter out;
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
		printer.println("---");
	}

	@Test
	public void branchMe() throws Exception {

		printer.println("restart");

		brancher.branch(new Runnable() {
			public void run() {
				printer.println("111");
				brancher.branch(new Runnable() {
					public void run() {
						printer.println("AAAA");
					}
				});
				printer.println("111 - ordinary end");
			}
		});
		brancher.branch(new Runnable() {
			public void run() {
				printer.println("222");
			}
		});
		printer.println("end");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		StringWriter expected = new StringWriter();
		PrintWriter p = new PrintWriter(expected);
		p.println("---");
		p.println("restart");
		p.println("111");
		p.println("AAAA");
		p.println("---");
		p.println("restart");
		p.println("111");
		p.println("111 - ordinary end");
		p.println("---");
		p.println("restart");
		p.println("222");
		p.println("---");
		p.println("restart");
		p.println("end");
		assertEquals(expected.toString(), out.toString());
	}

}
