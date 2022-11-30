package basics.concurrency.java_concurrency_in_practice.chapter01;

public class Examples {
    static class UnsafeSequence {
        private int value;

        public int getNext() {
            return value++;
        }
    }

    static class SafeSequence {
        private int value;

        public synchronized int getNext() {
            return value++;
        }
    }
}
