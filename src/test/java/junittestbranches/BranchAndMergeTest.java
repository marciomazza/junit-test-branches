package junittestbranches;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * Tests for {@link BranchRule} involving merge.
 * 
 * @author marciomazza
 * 
 */
public class BranchAndMergeTest {

	public static class OneBranchAndMerge {

		// Note that it continues after 1, because of the "merge"
		private static final String EXPECTED_LOG = " A1B AB";

		private static String log = "";

		@Rule
		public BranchRule brancher = new BranchRule();

		@Test
		public void test() throws Exception {
			log += " A";
			brancher.branchAndMerge(new Runnable() {
				public void run() {
					log += "1";
				}
			});
			log += "B";
		}
	}

	public static class TwoBranchAndMerges {

		// Note that it continues after 1 and 2, because of the "merge"
		private static final String EXPECTED_LOG = " A1B2C AB2C ABC";
		private static String log = "";

		@Rule
		public BranchRule brancher = new BranchRule();

		@Test
		public void test() throws Exception {
			log += " A";
			brancher.branchAndMerge(new Runnable() {
				public void run() {
					log += "1";
				}
			});
			log += "B";
			brancher.branchAndMerge(new Runnable() {
				public void run() {
					log += "2";
				}
			});
			log += "C";
		}
	}

	public static class MoreBranchAndMerges {

		// Note that it continues after # and 3, because of the "merge"
		private static final String EXPECTED_LOG = " A1#2 A12 AB3C ABC";
		private static String log = "";

		@Rule
		public BranchRule brancher = new BranchRule();

		@Test
		public void test() throws Exception {
			log += " A";
			brancher.branch(new Runnable() {
				public void run() {
					log += "1";
					brancher.branchAndMerge(new Runnable() {
						public void run() {
							log += "#";
						}
					});
					log += "2";
				}
			});
			log += "B";
			brancher.branchAndMerge(new Runnable() {
				public void run() {
					log += "3";
				}
			});
			log += "C";
		}
	}

	@Test
	public void test() {
		OneBranchAndMerge.log = TwoBranchAndMerges.log = MoreBranchAndMerges.log = "";
		JUnitCore.runClasses(OneBranchAndMerge.class, TwoBranchAndMerges.class, MoreBranchAndMerges.class);
		assertEquals(OneBranchAndMerge.EXPECTED_LOG, OneBranchAndMerge.log);
		assertEquals(TwoBranchAndMerges.EXPECTED_LOG, TwoBranchAndMerges.log);
		assertEquals(MoreBranchAndMerges.EXPECTED_LOG, MoreBranchAndMerges.log);
	}
}
