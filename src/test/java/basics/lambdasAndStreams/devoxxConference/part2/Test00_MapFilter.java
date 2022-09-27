package basics.lambdasAndStreams.devoxxConference.part2;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class Test00_MapFilter {

    private List<String> alphabet =
            List.of("alfa", "bravo", "charlie", "delta", "echo",
                    "foxtrot", "golf", "hotel", "india", "juliet",
                    "kilo", "lima", "mike", "november", "oscar",
                    "papa", "quebec", "romeo", "sierra", "tango",
                    "uniform", "victor", "whiskey", "x-ray", "yankee",
                    "zulu");

    /**
     * Prints only words, which length is equal to 6
     */
    @Test
    public void mapFilter_1() {
        alphabet.stream()
                .map(String::toUpperCase)
                .filter(word -> word.length() == 6)
                .forEach(System.out::println);
    }

    @Test
    public void mapFilter_2() {
        BigInteger result = LongStream.rangeClosed(1, 21)
                .mapToObj(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply);


        assertThat(result).isEqualTo(new BigInteger("51090942171709440000"));
    }
}
