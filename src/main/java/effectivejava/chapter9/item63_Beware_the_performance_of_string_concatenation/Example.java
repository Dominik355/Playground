package effectivejava.chapter9.item63_Beware_the_performance_of_string_concatenation;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class Example {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        IntStream.range(0, 973)
                .forEach(i -> list.add("cmrp874hjv"));

        Instant start1 = Instant.now();
        concat(list);
        System.out.println("concat: " + Duration.between(start1, Instant.now()));

        Instant start2 = Instant.now();
        stringB(list);
        System.out.println("stringbuilder: " + Duration.between(start2, Instant.now()));

        Instant start3 = Instant.now();
        stringBCapacity(list);
        System.out.println("stringbuilder predefined size: " + Duration.between(start3, Instant.now()));

        Instant start4 = Instant.now();
        stringB(list);
        System.out.println("stringJoiner: " + Duration.between(start4, Instant.now()));
    }

    // Inappropriate use of string concatenation - Performs poorly!
    public static String concat(List<String> col) {
        String result = "";
            for (int i = 0; i < col.size(); i++)
                result += col.get(i);// String concatenation
        return result;
    }

    public static String stringB(List<String> col) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < col.size(); i++)
            b.append(col.get(i));
        return b.toString();
    }

    public static String stringBCapacity(List<String> col) {
        StringBuilder b = new StringBuilder(col.size() * 10);
        for (int i = 0; i < col.size(); i++)
            b.append(col.get(i));
        return b.toString();
    }

    public static String stringJ(List<String> col) {
        StringJoiner joiner = new StringJoiner("");
        for (int i = 0; i < col.size(); i ++) {
            joiner.add(col.get(i));
        }
        return joiner.toString();
    }

}
