package basics.concurrency.java_concurrency_in_practice.chapter01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static basics.concurrency.java_concurrency_in_practice.Utils.log;

public class Chapter01 {
    public static void main(String[] args) {
        Examples.UnsafeSequence unsafeSeq = new Examples.UnsafeSequence();
        parallelize(unsafeSeq::getNext);

        // probably won't be 10000! (not thread-safe)
        log(unsafeSeq.getNext());

        Examples.SafeSequence safeSeq = new Examples.SafeSequence();
        parallelize(safeSeq::getNext);

        // should be 10000 (thread-safe)
        log(safeSeq.getNext());
    }

    public static void parallelize(Runnable r) {
        ExecutorService svc = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            svc.execute(r);
        }

        svc.shutdown();
        try {
            svc.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}


