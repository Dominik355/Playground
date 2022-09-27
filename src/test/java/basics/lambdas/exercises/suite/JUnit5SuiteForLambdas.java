package basics.lambdas.exercises.suite;

import basics.lambdas.exercises.A_Lambdas;
import basics.lambdas.exercises.B_Comparators;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SelectPackages("basics.lambdas.exercises")
@SelectClasses({
            A_Lambdas.class,
            B_Comparators.class
        })
@Suite
@SuiteDisplayName("Suite for lambdas and functional interfaces in general")
public class JUnit5SuiteForLambdas {
}
