package basics.unsafe;

import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://blogs.oracle.com/javamagazine/post/the-unsafe-class-unsafe-at-any-speed
 *
 * Accessing Hardware CPU Features
 *
 * Let’s examine a classic use of Unsafe for the use of the hardware feature known as “compare and swap” or CAS.
 * This capability is present on virtually all modern CPUs, but famously is not a part of Java’s memory model.
 */
public class CompareAndSwap {

    /**
     * In my first example, I’ll stick to the rules and use synchronization, which forms a part of Java’s memory model:
     */
    public static class SynchronizedCounter implements Counter {
        private volatile int i = 0;

        public synchronized int increment() {
            return i = i + 1;
        }

        public synchronized int get() {
            return i;
        }
    }

    public static class AtomicCounter implements Counter {
        private AtomicInteger i = new AtomicInteger();

        public int increment() {
            return i.incrementAndGet();
        }

        public int get() {
            return i.get();
        }
    }


    /**
     * Now, let’s compare that to the AtomicCounter Unsafe implementation, which contains
     * significantly more boilerplate, due to having to access the Unsafe class reflectively:
     *
     * First of all, it computes a pointer offset (the offset where the field value lives relative to the start of AtomicCounter objects).
     * There is no sequence of JVM bytecode instructions that can provide this; only native code that directly accesses the JVM’s internal data structures can do it.
     * Next, it directly accesses memory via pointer offset, not by field indirection. Finally, it chooses the memory access mode
     * (in this case, volatile) in which to do so—instead of having that be determined by how the variable is declared.
     */
    public static class AtomicUnsafeCounter implements Counter {
        private static final Unsafe unsafe;
        private static final long valueOffset;

        private volatile int value = 0;

        static {
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                unsafe = (Unsafe) f.get(null);
                valueOffset = unsafe.objectFieldOffset(AtomicUnsafeCounter.class.getDeclaredField("value"));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public int increment() {
            return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
        }

        @Override
        public int get() {
            return value;
        }
    }

    /**
     * Steps:
     *
     * Use a Lookup object to obtain a VarHandle for the appropriate field
     * Cache the VarHandle
     * Use the VarHandle to access the field, using volatile memory semantics
     */
    public static class AtomicVHCounter implements Counter {
        private volatile int value = 0;
        private static final VarHandle vh;

        static {
            try {
                MethodHandles.Lookup l = MethodHandles.lookup();
                vh = l.findVarHandle(AtomicVHCounter.class, "value", int.class);
            } catch (ReflectiveOperationException e) {
                throw new Error(e);
            }
        }

        @Override
        public int increment() {
            int i;
            do {
                i = (int) vh.getVolatile(this);
            } while (!vh.compareAndSet(this, i, i + 1));

            return i;
        }

        @Override
        public int get() {
            return value;
        }
    }

    public static void main(String[] args) {
        System.out.println("Atomic: " + test(new AtomicCounter()));
        System.out.println("Unsafe atomic: " + test(new AtomicUnsafeCounter()));
        System.out.println("Synchronized : " + test(new SynchronizedCounter()));
        System.out.println("VarHandle    : " + test(new AtomicVHCounter()));
    }

    private static Duration test(Counter counter) {
        Instant start = Instant.now();
        for (int i = 0; i < 1E9; i++) {
            counter.increment();
        }
        return Duration.between(start, Instant.now());
    }

}


interface Counter {
    public int increment();
    public int get();
}