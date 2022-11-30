package effectivejava.chapter7_lambdas_streams.item48_Use_caution_when_making_streams_parallel;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

public class ParallelPrimeCounting {
    // Prime-counting stream pipeline - parallel version (Page 225)
    static long pi(long n) {
        return LongStream.rangeClosed(2, n)
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(50))
                .count();
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        System.out.println(pi(10_000_000));
        System.out.println("took: " + Duration.between(start, Instant.now()));
    }
}
