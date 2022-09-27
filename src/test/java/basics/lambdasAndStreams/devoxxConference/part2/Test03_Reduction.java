package basics.lambdasAndStreams.devoxxConference.part2;

import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class Test03_Reduction {

    @Test
    public void reduction_1() {
        int number = 21;

        BigInteger result = IntStream.rangeClosed(1, number)
                        .mapToObj(BigInteger::valueOf)
                        .reduce(BigInteger.ONE, BigInteger::multiply);
        assertThat(result).isEqualTo(new BigInteger("51090942171709440000"));
    }
}
