package basics.concurrency.java_concurrency_in_practice;

import java.time.LocalDateTime;

public class Utils {

    public static void log(Object s) {
        System.out.println("{" + LocalDateTime.now() + "][" + Thread.currentThread().getName() + "]# " + s.toString());
    }

    @FunctionalInterface
    public interface ExceptionalRunnable {
        void run() throws Exception;

        static Runnable wrap(ExceptionalRunnable er) {
            return () -> {
                try {
                    er.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
        }
    }

}
