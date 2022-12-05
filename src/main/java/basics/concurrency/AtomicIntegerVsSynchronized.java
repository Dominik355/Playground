package basics.concurrency;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerVsSynchronized {

    static final Object LOCK1 = new Object();
    static final Object LOCK2 = new Object();
    static int i1 = 0;
    static int i2 = 0;
    static AtomicInteger ai1 = new AtomicInteger();
    static AtomicInteger ai2 = new AtomicInteger();

    static void refresh() {
        i1 = i2 = 0;
        ai1 = new AtomicInteger();
        ai2 = new AtomicInteger();
    }

    public static void main(String... args) throws IOException, InterruptedException {
        for(int i=0; i<10; i++) {
            testSyncIntSingleThread();
            testAtomicIntSingleThread();
            refresh();
        }

        System.out.println("\n*********** NOW MULTIPLE THREADS ***********");
        int threadNum = Runtime.getRuntime().availableProcessors() / 2;
        System.out.println("using " + threadNum + " threads\n");
        for(int i=0; i<10; i++) {
            testSyncIntMultipleThreads(threadNum);
            testAtomicIntMultipleThreads(threadNum);
            refresh();
        }
    }

    private static void testSyncIntSingleThread() {
        long start = System.nanoTime();
        int runs = 10_000_000;
        int increment = 2;
        for(int i=0; i<runs; i+=increment) {
            synchronized (LOCK1) {
                i1++;
            }
            synchronized (LOCK2) {
                i2++;
            }
        }
        long time = System.nanoTime() - start;
        assertEquals(runs / increment, i1);
        assertEquals(runs / increment, i2);
        System.out.printf("sync + incr:     Each increment took an average of %.1f ns%n", (double) time/runs);
    }

    private static void testAtomicIntSingleThread() {
        long start = System.nanoTime();
        int runs = 10_000_000;
        int increment = 2;
        for(int i=0;i< runs;i+=increment) {
            ai1.incrementAndGet();
            ai2.incrementAndGet();
        }
        long time = System.nanoTime() - start;
        assertEquals(runs / increment, ai1.get());
        assertEquals(runs / increment, ai2.get());
        System.out.printf("incrementAndGet: Each increment took an average of %.1f ns%n", (double) time/runs);

    }

    private static void testSyncIntMultipleThreads(int threadNum) throws InterruptedException {
        long start = System.nanoTime();
        int runs = 1_000_000;
        int increment = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        for(int i = 0; i < threadNum; i++) {
            executorService.submit(() -> {
                for(int j = 0; j < runs; j += increment) {
                    synchronized (LOCK1) {
                        i1++;
                    }
                    synchronized (LOCK2) {
                        i2++;
                    }
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        long time = System.nanoTime() - start;
        assertEquals((runs * threadNum) / increment, i1);
        assertEquals((runs * threadNum)  / increment, i2);
        System.out.printf("sync + incr:     Each increment took an average of %.1f ns%n", (double) time/runs);
    }

    private static void testAtomicIntMultipleThreads(int threadNum) throws InterruptedException {
        long start = System.nanoTime();
        int runs = 1_000_000;
        int increment = 2;

        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        for(int i = 0; i < threadNum; i++) {
            executorService.submit(() -> {
                for(int j = 0; j < runs; j += increment) {
                    ai1.incrementAndGet();
                    ai2.incrementAndGet();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        long time = System.nanoTime() - start;
        assertEquals((runs * threadNum)  / increment, ai1.get());
        assertEquals((runs * threadNum)  / increment, ai2.get());
        System.out.printf("incrementAndGet: Each increment took an average of %.1f ns%n", (double) time/runs);
    }

    private static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new RuntimeException("Expected: " + expected + ", actual: " + actual);
        }
    }

}
