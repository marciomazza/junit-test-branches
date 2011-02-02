# JUnit Test Branches

This project provides a simple way to express and execute different branches in a single JUnit test method.
It supports dead end branches (that cut execution at their end), and merge back branches (that continue after the branch point).

This is implemented in a rather simple way by [BranchRule](https://github.com/marciomazza/junit-test-branches/blob/master/src/main/java/junittestbranches/BranchRule.java), a [JUnit method rule](http://kentbeck.github.com/junit/javadoc/latest/org/junit/rules/MethodRule.html).

Branches are defined by passing `Runnable` arguments to the rule methods `branch` and `branchAndMerge`. They can be nested and are visited exactly once, in depth-first order.

The iteration is done by executing the method just as many times as needed to visit each branch. Each execution respects the normal JUnit decorations, including `@Before`, `@After` and other `@Rule`s.

## Example
### Dead end branch

In the following, the test method will be executed twice:
1. first entering the branch and terminating
2. then ignoring it and reaching the method's end.

	public class BranchRuleTest {

		@Rule
		public BranchRule brancher = new BranchRule();
	
		@Test
		public void simpleBranch() throws Exception {

			System.out.println("restart");
			brancher.branch(new Runnable() {
				public void run() {
					System.out.println("BRANCH-END");
				}
			});
			System.out.println("NORMAL-END");
		}
	}

It will print

	restart
	BRANCH-END
	restart
	NORMAL-END

### Merge back

If we change, in the previous example, the call from

			brancher.branch(new Runnable() { ...

to

			brancher.branchAndMerge(new Runnable() { ...

the execution of the branch continues after the `Runnable` block is done.

It will then print

    restart
    BRANCH-END
    NORMAL-END
    restart
    NORMAL-END

Now both executions reach the method's end.

### More examples

Check the [project tests](https://github.com/marciomazza/junit-test-branches/tree/master/src/test/java/junittestbranches) for more examples.

## Installation

This a Maven Project. Install it the [usual way](http://maven.apache.org/plugins/maven-install-plugin/).

## Issues

Please file issues in the standard [Issues Section](https://github.com/marciomazza/junit-test-branches/issues) of the project.

## License

All the work is licensed under Apache 2.0 license, available at
http://www.apache.org/licenses/LICENSE-2.0.
