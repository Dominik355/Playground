package fastAndFurious.unsafeAndReflection;

import basics.unsafe.UnsafeHolder;

import java.time.Duration;
import java.time.Instant;

/**
 * WITH constructor:    PT0.000236874S - doesn't call constructor
 * WITHOUT constructor: PT0.000963614S - calls constructor
 * WITH reflection:     PT0.000119142S - calls constructor
 *
 * So, when to use unsafe ? if construcotr is too expensive, and we dont need that.
 */
public class ObjectInitialization {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        System.out.println("WITH constructor: " + testConst());
        System.out.println("WITHOUT constructor: " + testNoConst());
        System.out.println("WITH reflection: " + testRefl());
    }

    private static Duration testConst() {
        Instant start = Instant.now();
        for (int i = 0; i < 1E1; i++) {
            A obj = new A();
            obj.a();
        }
        return Duration.between(start, Instant.now());
    }

    private static Duration testNoConst() throws InstantiationException {
        Instant start = Instant.now();
        for (int i = 0; i < 1E1; i++) {
            A obj = (A) UnsafeHolder.UNSAFE.allocateInstance(A.class);
            obj.a();
        }
        return Duration.between(start, Instant.now());
    }

    private static Duration testRefl() throws IllegalAccessException, InstantiationException {
        Instant start = Instant.now();
        for (int i = 0; i < 1E1; i++) {
            A obj = A.class.newInstance();
            obj.a();
        }
        return Duration.between(start, Instant.now());
    }

    static class A {
        private long a; // not initialized value

        public A() {
            this.a = 1; // initialization
        }

        public long a() {
            return this.a;
        }
    }


}
