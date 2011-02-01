package junitbranchrule;

import java.util.LinkedHashSet;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class BranchRule implements MethodRule {

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

	public void branch(Runnable runnable) {
		run(runnable, true);
	}

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
