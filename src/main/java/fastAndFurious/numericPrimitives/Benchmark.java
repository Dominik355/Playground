package fastAndFurious.numericPrimitives;

import java.math.BigDecimal;

public class Benchmark {

    public static void benchmark(String label, Code code) {
        print(25, label);

        try {
            for (int iterations = 1; ; iterations *= 2) { // detect reasonable iteration count and warm up the code under test
                System.gc(); // clean up previous runs, so we don't benchmark their cleanup
                long previouslyUsedMemory = usedMemory();
                long start = System.nanoTime();
                code.execute(iterations);
                long duration = System.nanoTime() - start;
                long memoryUsed = usedMemory() - previouslyUsedMemory;
                // 100 mil iterations or 1 sec
                if (iterations > 100_000_000 || duration > 1 * 1E9) {
                    print(25, new BigDecimal(duration * 1000 / iterations).movePointLeft(3) + " ns / iteration");
                    print(30, new BigDecimal(memoryUsed * 1000 / iterations).movePointLeft(3) + " bytes / iteration\n");
                    return;
                }
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void print(int desiredLength, String message) {
        System.out.print(" ".repeat(Math.max(1, desiredLength - message.length())) + message);
    }

    private static long usedMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    @FunctionalInterface
    interface Code {
        /**
         * Executes the code under test.
         *
         * @param iterations
         *            number of iterations to perform
         * @return any value that requires the entire code to be executed (to
         *         prevent dead code elimination by the just in time compiler)
         * @throws Throwable
         *             if the test could not complete successfully
         */
        Object execute(int iterations);
    }

    public static void main(String[] args) {
        benchmark("long[] traversal", (iterations) -> {
            long[] array = new long[iterations];
            for (int i = 0; i < iterations; i++) {
                array[i] = i;
            }
            return array;
        });
        benchmark("int[] traversal", (iterations) -> {
            int[] array = new int[iterations];
            for (int i = 0; i < iterations; i++) {
                array[i] = i;
            }
            return array;
        });
        benchmark("short[] traversal", (iterations) -> {
            short[] array = new short[iterations];
            for (int i = 0; i < iterations; i++) {
                array[i] = (short) i;
            }
            return array;
        });
        benchmark("byte[] traversal", (iterations) -> {
            byte[] array = new byte[iterations];
            for (int i = 0; i < iterations; i++) {
                array[i] = (byte) i;
            }
            return array;
        });

        benchmark("long fields", (iterations) -> {
            class C {
                long a = 1;
                long b = 2;
            }

            C[] array = new C[iterations];
            for (int i = 0; i < iterations; i++) {
                array[i] = new C();
            }
            return array;
        });
        benchmark("int fields", (iterations) -> {
            class C {
                int a = 1;
                int b = 2;
            }

            C[] array = new C[iterations];
            for (int i = 0; i < iterations; i++) {
                array[i] = new C();
            }
            return array;
        });
        benchmark("short fields", (iterations) -> {
            class C {
                short a = 1;
                short b = 2;
            }

            C[] array = new C[iterations];
            for (int i = 0; i < iterations; i++) {
                array[i] = new C();
            }
            return array;
        });
        benchmark("byte fields", (iterations) -> {
            class C {
                byte a = 1;
                byte b = 2;
            }

            C[] array = new C[iterations];
            for (int i = 0; i < iterations; i++) {
                array[i] = new C();
            }
            return array;
        });

        benchmark("long multiplication", (iterations) -> {
            long result = 1;
            for (int i = 0; i < iterations; i++) {
                result *= 3;
            }
            return result;
        });
        benchmark("int multiplication", (iterations) -> {
            int result = 1;
            for (int i = 0; i < iterations; i++) {
                result *= 3;
            }
            return result;
        });
        benchmark("short multiplication", (iterations) -> {
            short result = 1;
            for (int i = 0; i < iterations; i++) {
                result *= 3;
            }
            return result;
        });
        benchmark("byte multiplication", (iterations) -> {
            byte result = 1;
            for (int i = 0; i < iterations; i++) {
                result *= 3;
            }
            return result;
        });
    }
}