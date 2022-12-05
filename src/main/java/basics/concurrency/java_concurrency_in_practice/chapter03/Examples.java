package basics.concurrency.java_concurrency_in_practice.chapter03;

public class Examples {
    public static class NoVisibility {
        private boolean ready;
        private int number;

        public void run() {
            while (!ready) {}
            if (number != 42) {
                System.out.println("Got the wrong number! Expected: 42, Actual: " + number);
            } else {
                System.out.println("great, it was 42");
            }
        }

        public void initialize() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            number = 42;
            ready = true;
        }
    }

    public static class NoVisibilitySynchronized {
        private boolean ready;
        private int number;

        public synchronized void run() {
            while (!ready) {}
            if (number != 42) {
                System.out.println("Got the wrong number! Expected: 42, Actual: " + number);
            } else {
                System.out.println("great, it was 42");
            }
        }

        public synchronized void initialize() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            number = 42;
            ready = true;
        }
    }

    public static class NoVisibilityVolatile {
        private volatile boolean ready;
        private volatile int number;

        public void run() {
            while (!ready) {}
            if (number != 42) {
                System.out.println("Got the wrong number! Expected: 42, Actual: " + number);
            } else {
                System.out.println("great, it was 42");
            }
        }

        public void initialize() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            number = 42;
            ready = true;
        }
    }
}
