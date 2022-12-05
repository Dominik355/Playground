package basics.concurrency.java_concurrency_in_practice.chapter01;

import java.util.concurrent.ThreadLocalRandom;

import static basics.concurrency.java_concurrency_in_practice.Utils.ExceptionalRunnable.wrap;
import static basics.concurrency.java_concurrency_in_practice.Utils.log;

/**
 * When lock is object itself, it is shared between all synchronized blocks in object.
 * So no matter which synchronized method thread uses, once it captures the lock,
 * no other thread can use whatever other synchronized method/block within the same instance,
 * or class (if it's static)
 */
public class HowSynchronizedWorks {

    public static void main(String[] args) throws InterruptedException {
        StringWrapper wrapper = new StringWrapper();

        Thread t1 = new Thread(wrap(() -> doTesT(wrapper)));
        t1.setName("T1");

        t1.start();
        Thread.sleep(1000);
        doTesTReversed(wrapper);

        System.out.println("\nRESULT: '" + wrapper + "'");

        System.out.println("********************* NOW STATIC *********************");
        Thread t2 = new Thread(wrap(HowSynchronizedWorks::doTesTStatic));
        t2.setName("T2");

        t2.start();
        Thread.sleep(1000);
        doTesTReversedStatic();

        System.out.println("\nRESULT: '" + wrapper + "'");
    }

    private static void doTesT(StringWrapper wrapper) throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        log("Executing test in thread: " + Thread.currentThread().getName());
        wrapper.add(threadName);
        wrapper.addNumber(Thread.currentThread().getPriority());
        wrapper.addRandom();
        log("Everything DONE !!!");
    }

    private static void doTesTReversed(StringWrapper wrapper) throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        log("Executing test in thread: " + Thread.currentThread().getName());
        wrapper.addRandom();
        wrapper.addNumber(Thread.currentThread().getPriority());
        wrapper.add(threadName);
        log("Everything DONE !!!");
    }

    private static void doTesTStatic() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        log("Executing test in thread: " + Thread.currentThread().getName());
        StaticStringWrapper.add(threadName);
        StaticStringWrapper.addNumber(Thread.currentThread().getPriority());
        StaticStringWrapper.addRandom();
        log("Everything DONE !!!");
    }

    private static void doTesTReversedStatic() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        log("Executing test in thread: " + Thread.currentThread().getName());
        StaticStringWrapper.addRandom();
        StaticStringWrapper.addNumber(Thread.currentThread().getPriority());
        StaticStringWrapper.add(threadName);
        log("Everything DONE !!!");
    }

}

class StringWrapper {

    private String string = "start:";

    public synchronized void add(String s) throws InterruptedException {
        log("add called for : " + s);
        string += " " + s;
        Thread.sleep(2500);
        log("add done");
    }

    public synchronized void addNumber(int i) throws InterruptedException {
        log("addNumber called for : " + i);
        string += " " + i;
        Thread.sleep(2500);
        log("addNumber done");
    }

    public void addRandom() throws InterruptedException {
        int rnd = ThreadLocalRandom.current().nextInt();
        log("addRandom called for : " + rnd);
        synchronized (this) {
            string += " " + rnd;
            Thread.sleep(2500);
        }
        log("addRandom done");
    }

    @Override
    public String toString() {
        return string;
    }
}

class StaticStringWrapper {

    private static String string = "start:";

    public static synchronized void add(String s) throws InterruptedException {
        log("add called for : " + s);
        string += " " + s;
        Thread.sleep(2500);
        log("add done");
    }

    public static synchronized void addNumber(int i) throws InterruptedException {
        log("addNumber called for : " + i);
        string += " " + i;
        Thread.sleep(2500);
        log("addNumber done");
    }

    public static void addRandom() throws InterruptedException {
        int rnd = ThreadLocalRandom.current().nextInt();
        log("addRandom called for : " + rnd);
        synchronized (StaticStringWrapper.class) {
            string += " " + rnd;
            Thread.sleep(2500);
        }
        log("addRandom done");
    }

    @Override
    public String toString() {
        return string;
    }
}
