package junittestbranches;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class ExceptionHandlingTest {

	public static class ExceptionAfter {

		@Rule
		public BranchRule brancher = new BranchRule();

		@Test
		public void test1() throws Exception {
			brancher.branch(new Runnable() {
				public void run() {
					// do nothing
				}
			});
		}

		@After
		public void tearDown() throws Exception {
			throw new RuntimeException("stub");
		}
	}

	public static class ExceptionAfterClass {

		@Rule
		public BranchRule brancher = new BranchRule();

		@Test
		public void test1() throws Exception {
			brancher.branch(new Runnable() {
				public void run() {
					// do nothing
				}
			});
		}

		@AfterClass
		public static void tearDownAfterClass() throws Exception {
			throw new RuntimeException("stub");
		}

	}

	@Test
	public void beforeWithTwoTests() {
		checkOnlyRuntimeExceptionWasThrown(JUnitCore.runClasses(ExceptionAfter.class));
		checkOnlyRuntimeExceptionWasThrown(JUnitCore.runClasses(ExceptionAfterClass.class));
	}

	private void checkOnlyRuntimeExceptionWasThrown(Result result) {
		assertEquals(1, result.getFailures().size());
		Throwable exception = result.getFailures().get(0).getException();
		assertEquals(exception.getClass(), RuntimeException.class);
		assertEquals(exception.getMessage(), "stub");
	}

}
