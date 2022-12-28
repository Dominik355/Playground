package basics.unsafe;

import java.time.Duration;
import java.time.Instant;

public class CreateInstanceWithoutConstructor {

    public static void main(String[] args) throws Exception {
        ClassWithExpensiveConstructor instance = (ClassWithExpensiveConstructor) UnsafeHolder.UNSAFE.allocateInstance(ClassWithExpensiveConstructor.class);
        System.out.println("UNSAFE RESULT: " + (instance.getValue() == 0));
    }

    static class ClassWithExpensiveConstructor {

        private final int value;

        private ClassWithExpensiveConstructor() {
            value = doExpensiveLookup();
        }

        private int doExpensiveLookup() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }

        public int getValue() {
            return value;
        }
    }

}
