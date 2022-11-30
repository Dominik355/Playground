package effectivejava.chapter6.item39_Prefer_annotations_to_naming_patterns.markerannotation;

// Program to process marker annotations (Page 182)

import java.lang.reflect.*;

public class RunTests {
    public static void main(String[] args) throws Exception {
        args = new String[]{"effectivejava.chapter6.item39_Prefer_annotations_to_naming_patterns.markerannotation.Sample"};
        System.out.println("\n");

        int tests = 0;
        int passed = 0;
        Class<?> testClass = Class.forName(args[0]);
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    // non-static methods will throw NPE, becasue there is no instance to be called on
                    m.invoke(null);
                    passed++;
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(m + " failed: " + exc);
                } catch (Exception exc) {
                    System.out.println("Invalid @Test: " + m);
                }
            }
        }
        System.out.printf("Passed: %d, \nFailed: %d%n",
                passed, tests - passed);
    }
}
