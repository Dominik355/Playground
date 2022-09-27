package basics.lambdasAndStreams.devoxxConference.part2;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Test08_StreamingMaps {

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
            "To eat the world's due, by the grave and thee.",
            "to to thy");

    /**
     * Find the most frequently occurring words in the Sonnet
     */
    @Test
    public void streamingMaps_1() {

        Pattern pattern = Pattern.compile(("[ ,':\\-]+"));

        Map<String, Long> words =
                sonnet.stream()
                        .map(String::toLowerCase)
                        .flatMap(pattern::splitAsStream)
                        .collect(
                                Collectors.collectingAndThen(
                                        Collectors.groupingBy(
                                                word -> word,
                                                Collectors.counting()
                                        ),
                                        map -> Map.copyOf(map)
                                )
                        );

        words.forEach((letter, count) -> System.out.println(letter + " => " + count));

        Map.Entry<String, Long> mostFrequentWord =
                words.entrySet().stream() // Stream<Map.Entry<String, Long>>
                        .max(Map.Entry.comparingByValue())
                        .orElseThrow();
        System.out.println("mostFrequentWord = " + mostFrequentWord);

//       Originally we have a map with Key as a word itself and value is number of occurences.
//       Here we flip that. So key is number of occurences and value is list of all the words with that number of occurences
        Map<Long, List<String>> otherWords =
                words.entrySet().stream() // Stream<Map.Entry<String, Long>>
                        .collect(
                                Collectors.groupingBy(
                                        Map.Entry::getValue,
                                        Collectors.mapping(
                                                Map.Entry::getKey,
                                                Collectors.toList()
                                        )
                                )
                        );
        // But we can try to put it into one stream, instead of 2
        Map<Long, List<String>> otherWords2 =
                sonnet.stream()
                        .map(String::toLowerCase)
                        .flatMap(pattern::splitAsStream) // Stream<String>
                        .collect(
                                Collectors.groupingBy(
                                        word -> Long.valueOf(word.length()), // keys are length of words
                                        Collectors.mapping(Function.identity(), Collectors.toList()) // map values into a list without any change
                                )
                        );

        Map.Entry<Long, List<String>> mostSeenWords =
                otherWords.entrySet().stream() // Stream<Map.Entry<Long, List<String>>>
                        .max(Map.Entry.comparingByKey())
                        .orElseThrow();
        System.out.println("mostSeenWords = " + mostSeenWords);

        Map.Entry<Long, List<String>> mostSeenWords2 =
                otherWords2.entrySet().stream() // Stream<Map.Entry<Long, List<String>>>
                        .max(Map.Entry.comparingByKey())
                        .orElseThrow();
        System.out.println("mostSeenWords2 = " + mostSeenWords);
    }

    @Test
    public void streamingMaps_1b() {

        Pattern pattern = Pattern.compile(("[ ,':\\-]+"));

        Collector<String, ?, Map<String, Long>> collector =
                Collectors.mapping(
                        String::toLowerCase, // first map to lower case
                        Collectors.flatMapping(
                                pattern::splitAsStream, // then flatmap lines into separate words
                                Collectors.collectingAndThen( //here we have Stream<String>
                                        Collectors.groupingBy(
                                                word -> word, // group by a word itself
                                                Collectors.counting() // value is number of appearances of that word
                                        ),
                                        Map::copyOf
                                )
                        )
                ); // same as streamingMaps_1 - but flatmap is in collect section
        Map<String, Long> words =
                sonnet.stream().collect(collector);

        words.forEach((letter, count) -> System.out.println(letter + " => " + count));

        Collector<Map.Entry<String, Long>, ?, Map.Entry<String, Long>> collector1 =
                Collectors.collectingAndThen(
                        Collectors.maxBy(
                                Map.Entry.comparingByValue()
                        ),
                        Optional::orElseThrow
                );

        Map.Entry<String, Long> mostFrequentWord =
                words.entrySet().stream().collect(collector1);
        System.out.println("mostFrequentWord = " + mostFrequentWord);

//        Map<Long, List<Map.Entry<String, Long>>> otherWords =
        Map<Long, List<String>> otherWords =
                words.entrySet().stream() // Stream<Map.Entry<String, Long>>
                        .collect(
                                Collectors.groupingBy(
                                        Map.Entry::getValue,
                                        Collectors.mapping(
                                                Map.Entry::getKey,
                                                Collectors.toList()
                                        )
                                )
                        );

        Map.Entry<Long, List<String>> mostSeenWords =
                otherWords.entrySet().stream() // Stream<Map.Entry<Long, List<String>>>
                        .max(Map.Entry.comparingByKey())
                        .orElseThrow();
        System.out.println("mostSeenWords = " + mostSeenWords);
    }
}
