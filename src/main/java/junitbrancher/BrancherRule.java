package junitbrancher;

import java.util.LinkedHashSet;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class BrancherRule implements MethodRule {

	private class BranchDoneSignalException extends RuntimeException {
	}

	private LinkedHashSet<Class<?>> alreadyRan = new LinkedHashSet<Class<?>>();

	public Statement apply(final Statement base, FrameworkMethod method, Object target) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				alreadyRan.clear();
				boolean onceMore = true;
				while (onceMore) {
					try {
						base.evaluate();
						onceMore = false;
					} catch (BranchDoneSignalException e) {
						// do nothing
					}
				}
			}
		};
	}

	public void branch(Runnable runnable) {
		if ( ! alreadyRan.contains(runnable.getClass())) {
			runnable.run();
			alreadyRan.add(runnable.getClass());
			throw new BranchDoneSignalException();
		}
	}

}
