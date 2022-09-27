package basics.lambdasAndStreams.devoxxConference.part2;

import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Test07_CascadingCollectors {

    private List<String> alphabet =
            List.of("alfa", "bravo", "charlie", "delta", "echo",
                    "foxtrot", "golf", "hotel", "india", "juliet",
                    "kilo", "lima", "mike", "november", "oscar",
                    "papa", "quebec", "romeo", "sierra", "tango",
                    "uniform", "victor", "whiskey", "x-ray", "yankee",
                    "zulu");

    private List<String> sonnet = List.of(
            "From fairest creatures we desire increase,",
            "That thereby beauty's rose might never die,",
            "But as the riper should by time decease,",
            "His tender heir might bear his memory:",
            "But thou contracted to thine own bright eyes,",
            "Feed'st thy light's flame with self-substantial fuel,",
            "Making a famine where abundance lies,",
            "Thy self thy foe, to thy sweet self too cruel:",
            "Thou that art now the world's fresh ornament,",
            "And only herald to the gaudy spring,",
            "Within thine own bud buriest thy content,",
            "And, tender churl, mak'st waste in niggarding:",
            "Pity the world, or else this glutton be,",
            "To eat the world's due, by the grave and thee.");

    private List<String> expand(String s) {
        return s.codePoints()
                .mapToObj(Character::toString)
                .filter(Predicate.not(String::isBlank)) // get rid of whitespaces
                .collect(toList());
    }

    @Test
    public void cascadingCollectors_01() {

        Map<String, Long> map =
                sonnet.stream()
                        .collect(groupingBy(line -> line.substring(0, 1),
                                Collectors.counting()));

        assertThat(map.size()).isEqualTo(8);
        assertThat(map).containsExactly(
                Map.entry("P", 1L),
                Map.entry("A", 2L),
                Map.entry("B", 2L),
                Map.entry("T", 4L),
                Map.entry("F", 2L),
                Map.entry("W", 1L),
                Map.entry("H", 1L),
                Map.entry("M", 1L)
        );
    }

    @Test
    public void cascadingCollectors_02() {

        Map<String, List<Integer>> map =
                sonnet.stream()
                        .collect(groupingBy(line -> line.substring(0, 1), mapping(String::length, toList())));

        assertThat(map.size()).isEqualTo(8);
        assertThat(map).containsExactly(
                Map.entry("P", List.of(40)),
                Map.entry("A", List.of(36, 46)),
                Map.entry("B", List.of(40, 45)),
                Map.entry("T", List.of(43, 46, 45, 46)),
                Map.entry("F", List.of(42, 53)),
                Map.entry("W", List.of(41)),
                Map.entry("H", List.of(38)),
                Map.entry("M", List.of(37))
        );
    }

    /**
     * Group the lines of the sonnet by first letter, and collect the first word of grouped lines into a set.
     */
    @Test
    public void cascadingCollectors_03() {

        // or just use 'var' - but then u have to define type in lamba parameter, so: (String line) -> ...
        Collector<String, ?, Set<String>> mapToFirstWordInASet =
                mapping(
                        line -> line.split(" +")[0],
                        toSet()
                );

        Map<String, Set<String>> map =
                sonnet.stream()
                        .collect(groupingBy(line -> line.substring(0, 1), mapToFirstWordInASet));


        assertThat(map.size()).isEqualTo(8);
        assertThat(map).containsExactly(
                Map.entry("P", Set.of("Pity")),
                Map.entry("A", Set.of("And", "And,")),
                Map.entry("B", Set.of("But")),
                Map.entry("T", Set.of("That", "Thy", "To", "Thou")),
                Map.entry("F", Set.of("Feed'st", "From")),
                Map.entry("W", Set.of("Within")),
                Map.entry("H", Set.of("His")),
                Map.entry("M", Set.of("Making"))
        );
    }

    /** filtering demonstration
     * create a map from sonet lines. Key is fiorst letter of line and value is number of lines with that starting letter
     * Filter those, so just lines containing "'s" will be taken into consideration
     */
    @Test
    public void cascadingCollectors_filtering() {

        Map<String, Long> map =
                sonnet.stream()
                        .collect(Collectors.filtering(line -> line.contains("'s"), groupingBy(line -> line.substring(0, 1), counting())));

        assertThat(map.size()).isEqualTo(3);
        assertThat(map).containsExactly(
                Map.entry("A", 1L),
                Map.entry("T", 3L),
                Map.entry("F", 1L)
        );

    }

    /**
     * takes the alphabet List, turn that into stream and take first letter of every word.
     * Then join these letters into a single String using dash ('-') as a delimiter
     * First using Collectors.joining() method
     * Then wihtout that, so pure .collect(...);
     */
    @Test
    public void cascadingCollectors_joining() {
        String resultJoining =
                alphabet.stream()
                        .map(line -> line.substring(0, 1))
                        .collect(Collectors.joining("-"));
        assertThat(resultJoining).isEqualTo("a-b-c-d-e-f-g-h-i-j-k-l-m-n-o-p-q-r-s-t-u-v-w-x-y-z");

        String resultOwnImpl =
                alphabet.stream()
                        .collect(
                                () -> new StringJoiner("-"),
                                (sj, word) -> sj.add(word.substring(0, 1)),
                                StringJoiner::merge
                        ).toString();
        assertThat(resultOwnImpl).isEqualTo("a-b-c-d-e-f-g-h-i-j-k-l-m-n-o-p-q-r-s-t-u-v-w-x-y-z");
    }

    @Test
    public void cascadingCollectors_04() {

        Map<String, Long> map =
                sonnet.stream() // stream of the lines of the sonnet
                        .flatMap(line -> expand(line).stream()) // stream of letters
                        .collect(
                                Collectors.groupingBy(
                                        Function.identity(),
                                        Collectors.counting()
                                )
                        );

        map.forEach((letter, count) -> System.out.println(letter + " => " + count));

        // now lets try to do the same but with whole words, so we can split line on a whitespace
        System.out.println("           now with a whole words");
        Map<String, Long> map2 =
                sonnet.stream() // stream of the lines of the sonnet
                        .flatMap(line -> Pattern.compile(" +").splitAsStream(line)) // stream of word
        // or ->        .flatMap(line -> Arrays.stream(line.split(" +"))) // stream of word
                        .collect(
                                Collectors.groupingBy(
                                        Function.identity(),
                                        Collectors.counting()
                                )
                        );

        map2.forEach((letter, count) -> System.out.println(letter + " => " + count));
    }

    @Test
    public void cascadingCollectors_04b() {
// same as cascadingCollectors_04 - bud flatmapping is done in collector itself
        Collector<String, ?, Map<String, Long>> collector =
                Collectors.flatMapping(
                        line -> expand(line).stream(),
                        Collectors.groupingBy(
                                letter -> letter,
                                Collectors.counting()
                        )
                );

        Map<String, Long> map =
                sonnet.stream() // stream of the lines of the sonnet
                        .collect(collector);

        map.forEach((letter, count) -> System.out.println(letter + " => " + count));
    }

    /**
     * You can not stream a map. T ostream a map, you need t oget a stream
     * of entries from its entrySet()
     */
    @Test
    public void cascadingCollectors_streamingMap() {
        // lets take a mapo from test 04
        Map<String, Long> map =
                sonnet.stream() // stream of the lines of the sonnet
                        .flatMap(line -> expand(line).stream()) // stream of letters
                        .collect(
                                Collectors.groupingBy(
                                        Function.identity(),
                                        Collectors.counting()
                                )
                        );
        map.forEach((letter, count) -> System.out.println(letter + " => " + count));
        // now create a stream of its entries
        Stream<Map.Entry<String,Long>> stream = map.entrySet().stream();

        // now for example, find a letter with a most occurences
        String letter = stream
                            .max(Map.Entry.comparingByValue())
                            .get().getKey();

        System.out.println("Letter with most occurences is : " + letter);
        assertThat(letter).isEqualTo("e");
    }
}
