package junitbranchrule;

import java.util.LinkedHashSet;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * This is a JUnit MethodRule that allows "branching" of the execution of a test method. It supports dead end
 * branches ( through {@link #branch(Runnable)}), that cut execution at their end, and merge back ones ( through
 * {@link #branchAndMerge(Runnable)}), that continue after the branch point.
 * 
 * Branches can be nested. All branches are visited exactly once, in depth-first order.
 * 
 * @author marciomazza
 */
public class BranchRule implements MethodRule {

	/**
	 * Throwing an Exception seems to be the only control flow option we have to "cut" execution at the end of a
	 * branch. This exception is used signal this.
	 * 
	 * It is thrown in {@link #branch(Runnable)} and caught in the loop in
	 * {@link #apply(Statement, FrameworkMethod, Object)}
	 */
	private class BranchDoneSignalException extends RuntimeException {
	}

	private LinkedHashSet<Class<?>> completedBranches;
	private boolean oneBranchCompleted;

	public Statement apply(final Statement base, FrameworkMethod method, Object target) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				completedBranches = new LinkedHashSet<Class<?>>();
				while (true) {
					oneBranchCompleted = false;
					try {
						base.evaluate();
						if ( ! oneBranchCompleted) {
							break;
						}
					} catch (BranchDoneSignalException e) {
						continue;
					}
				}
			}
		};
	}

	/**
	 * Executes a {@code Runnable} as a dead end branch, i.e, that cuts execution after its completion.
	 * 
	 * <pre>
	 * public class BranchRuleTest {
	 * 
	 * 	&#064;Rule
	 * 	public BranchRule brancher = new BranchRule();
	 * 
	 * 	&#064;Test
	 * 	public void simpleBranch() throws Exception {
	 * 
	 * 		System.out.println(&quot;restart&quot;);
	 * 		brancher.branch(new Runnable() {
	 * 			public void run() {
	 * 				System.out.println(&quot;BRANCH-END&quot;);
	 * 			}
	 * 		});
	 * 		System.out.println(&quot;NORMAL-END&quot;);
	 * 	}
	 * }
	 * </pre>
	 * 
	 * The above test prints this output:
	 * 
	 * <pre>
	 * restart
	 * BRANCH-END
	 * restart
	 * NORMAL-END
	 * </pre>
	 * 
	 * @param runnable
	 *            a Runnable to be ran as a dead end branch
	 */
	public void branch(Runnable runnable) {
		run(runnable, true);
	}

	/**
	 * Executes a {@code Runnable} as a merge back branch, i.e, that continues execution after the branch point.
	 * 
	 * <pre>
	 * public class BranchRuleTest {
	 * 
	 * 	&#064;Rule
	 * 	public BranchRule brancher = new BranchRule();
	 * 
	 * 	&#064;Test
	 * 	public void simpleBranch() throws Exception {
	 * 
	 * 		System.out.println(&quot;restart&quot;);
	 * 		brancher.branchAndMerge(new Runnable() {
	 * 			public void run() {
	 * 				System.out.println(&quot;BRANCH-END&quot;);
	 * 			}
	 * 		});
	 * 		System.out.println(&quot;NORMAL-END&quot;);
	 * 	}
	 * }
	 * </pre>
	 * 
	 * The above test prints the following output. Note that the first pass prints both BRANCH-END and NORMAL-END.
	 * 
	 * <pre>
	 * restart
	 * BRANCH-END
	 * NORMAL-END
	 * restart
	 * NORMAL-END
	 * </pre>
	 * 
	 * @param runnable
	 *            a Runnable to be ran as a dead end branch
	 */
	public void branchAndMerge(Runnable runnable) {
		run(runnable, false);
	}

	private void run(Runnable runnable, boolean cut) {
		if ( ! completedBranches.contains(runnable.getClass())) {
			runnable.run();
			if ( ! oneBranchCompleted) {
				oneBranchCompleted = true;
				completedBranches.add(runnable.getClass());
			}
			if (cut) {
				throw new BranchDoneSignalException();
			}
		}
	}

}
