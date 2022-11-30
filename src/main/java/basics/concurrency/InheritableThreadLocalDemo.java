package basics.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class InheritableThreadLocalDemo {

    public static void main(String[] args) {

        final AtomicInteger childCount = new AtomicInteger();

        final InheritableThreadLocal<String> local = new InheritableThreadLocal<String>() {
            public String childValue(String parentValue) {
                return parentValue + ".child-" + (childCount.incrementAndGet());
            }
        };

        final Runnable child = () -> System.out.println("Child Thread Value: " + local.get());

        final Runnable parent = () -> {
            // The thread local value is associated with the thread that is running this block of
            // code.
            local.set("p1");
            System.out.println("Thread id:" + local.get());

            Thread c1 = new Thread(child);
            c1.start();

            Thread c2 = new Thread(child);
            c2.start();
        };

        final Thread parentThread = new Thread(parent);
        parentThread.run();
    }
}