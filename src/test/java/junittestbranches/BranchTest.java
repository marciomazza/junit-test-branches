package junittestbranches;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * Tests for {@link BranchRule}.
 * 
 * @author marciomazza
 * 
 */
public class BranchTest {

	public static class NoBranch {
		private static final String EXPECTED_LOG = " A";
		private static String log = "";

		@Rule
		public BranchRule brancher = new BranchRule();

		@Test
		public void test() throws Exception {
			log += " A";
		}
	}

	public static class OneBranch {
		private static final String EXPECTED_LOG = " A1 AB";
		private static String log = "";

		@Rule
		public BranchRule brancher = new BranchRule();

		@Test
		public void test() throws Exception {
			log += " A";
			brancher.branch(new Runnable() {
				public void run() {
					log += "1";
				}
			});
			log += "B";
		}
	}

	public static class TwoBranches {
		private static final String EXPECTED_LOG = " A1 AB2 ABC";
		private static String log = "";

		@Rule
		public BranchRule brancher = new BranchRule();

		@Test
		public void test() throws Exception {
			log += " A";
			brancher.branch(new Runnable() {
				public void run() {
					log += "1";
				}
			});
			log += "B";
			brancher.branch(new Runnable() {
				public void run() {
					log += "2";
				}
			});
			log += "C";
		}
	}

	public static class MoreBranches {
		private static final String EXPECTED_LOG = " A1# A12 AB3 ABC";
		private static String log = "";

		@Rule
		public BranchRule brancher = new BranchRule();

		@Test
		public void test() throws Exception {
			log += " A";
			brancher.branch(new Runnable() {
				public void run() {
					log += "1";
					brancher.branch(new Runnable() {
						public void run() {
							log += "#";
						}
					});
					log += "2";
				}
			});
			log += "B";
			brancher.branch(new Runnable() {
				public void run() {
					log += "3";
				}
			});
			log += "C";
		}
	}

	@Test
	public void test() {
		NoBranch.log = OneBranch.log = TwoBranches.log = MoreBranches.log = "";
		JUnitCore.runClasses(NoBranch.class, OneBranch.class, TwoBranches.class, MoreBranches.class);
		assertEquals(NoBranch.EXPECTED_LOG, NoBranch.log);
		assertEquals(OneBranch.EXPECTED_LOG, OneBranch.log);
		assertEquals(TwoBranches.EXPECTED_LOG, TwoBranches.log);
		assertEquals(MoreBranches.EXPECTED_LOG, MoreBranches.log);
	}
}
