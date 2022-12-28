import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Testing {

    public static void main(String[] args) {
        int one = 0x0000008;
        int two = 0x0000010;
        System.out.println(one & two);
    }

}