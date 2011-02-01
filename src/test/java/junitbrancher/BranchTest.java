package junitbrancher;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.AfterClass;
import org.junit.Test;

public class BranchTest extends AbstractBrancherRuleTest {

	@Test
	public void simpleBranch() throws Exception {

		print("restart");
		brancher.branch(new Runnable() {
			public void run() {
				print("111");
			}
		});
		print("end");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		StringWriter expected = new StringWriter();
		PrintWriter p = new PrintWriter(expected);
		p.println("---");
		p.println("restart");
		p.println("111");
		p.println("---");
		p.println("restart");
		p.println("end");
		assertEquals(expected.toString(), out.toString());
	}

}
