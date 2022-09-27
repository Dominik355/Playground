package basics.lambdas.exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import basics.lambdas.exercises.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Map.entry;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This set of exercises covers advanced stream operations,
 * including grouping collectors, composition of collectors,
 * and customized collectors.
 */
public class F_AdvancedStreams {

    /**
     * Categorize the words from the text file into a map, where the map's key
     * is the length of each word, and the value corresponding to a key is a
     * list of words of that length. Don't bother with uniqueness or lower-
     * casing the words. As before, use the BufferedReader variable named
     * "reader" that has been set up for you to read from the text file, and
     * use SPLIT_PATTERN for splitting the line into words.
     *
     * @throws IOException
     */
    @Test 
    public void f1_mapLengthToWordList() throws IOException {
        Map<Integer, List<String>> result =
                reader.lines()
                    .flatMap(SPLIT_PATTERN::splitAsStream)
                    .collect(groupingBy((String::length), mapping(identity(), toList())));
// this is equal -> .collect(groupingBy((String::length))); because that mapping is default already


        assertEquals(10, result.get(7).size());
        assertEquals(Set.of("beauty's", "increase", "ornament"), new HashSet<>(result.get(8)));
        assertEquals(Set.of("abundance", "creatures"), new HashSet<>(result.get(9)));
        assertEquals(Set.of("contracted", "niggarding"), new HashSet<>(result.get(10)));
        assertEquals(List.of("substantial"), result.get(11));
        assertFalse(result.containsKey(12));
    }

    /**
     * Categorize the words from the text file into a map, where the map's key
     * is the length of each word, and the value corresponding to a key is a
     * count of words of that length. Don't bother with uniqueness or lower-
     * casing the words. This is the same as the previous exercise except
     * the map values are the count of words instead of a list of words.
     *
     * @throws IOException
     */
    @Test 
    public void f2_mapLengthToWordCount() throws IOException {
        Map<Integer, Long> result =
                reader.lines()
                        .flatMap(SPLIT_PATTERN::splitAsStream)
                        .collect(groupingBy(String::length, Collectors.counting()));

        assertEquals(Map.ofEntries(entry( 1,  1L),
                                   entry( 2, 11L),
                                   entry( 3, 28L),
                                   entry( 4, 21L),
                                   entry( 5, 16L),
                                   entry( 6, 12L),
                                   entry( 7, 10L),
                                   entry( 8,  3L),
                                   entry( 9,  2L),
                                   entry(10,  2L),
                                   entry(11,  1L)),
                     result);
    }

    /**
     * Gather the words from the text file into a map, accumulating a count of
     * the number of occurrences of each word. Don't worry about upper case and
     * lower case. Extra challenge: implement two solutions, one that uses
     * groupingBy() and the other that uses toMap().
     *
     * @throws IOException
     */
    @Test 
    public void f3_wordFrequencies() throws IOException {
        Map<String, Long> result =
                reader.lines()
                        .flatMap(SPLIT_PATTERN::splitAsStream)
                        .collect(groupingBy(identity(), Collectors.counting()));

        result = reader.lines()
                        .flatMap(SPLIT_PATTERN::splitAsStream)
                                .collect(Collectors.toMap(identity(), word -> 1L, Long::sum));

        assertEquals(2L, (long)result.get("tender"));
        assertEquals(6L, (long)result.get("the"));
        assertEquals(1L, (long)result.get("churl"));
        assertEquals(2L, (long)result.get("thine"));
        assertEquals(1L, (long)result.get("world"));
        assertEquals(4L, (long)result.get("thy"));
        assertEquals(3L, (long)result.get("self"));
        assertFalse(result.containsKey("lambda"));
    }

    /**
     * From the words in the text file, create nested maps, where the outer map is a
     * map from the first letter of the word to an inner map. (Use a string of length
     * one as the key.) The inner map, in turn, is a mapping from the length of the
     * word to a list of words with that length. Don't bother with any lowercasing
     * or uniquifying of the words.
     *
     * For example, given the words "foo bar baz bazz foo" the string
     * representation of the result would be:
     *     {b={3=[bar, baz], 4=[bazz]}, f={3=[foo, foo]}}
     *
     * @throws IOException
     */
    @Test 
    public void f4_nestedMaps() throws IOException {
        Map<String, Map<Integer, List<String>>> result =
                reader.lines()
                        .flatMap(SPLIT_PATTERN::splitAsStream)
                        .collect(groupingBy(word -> word.substring(0, 1),
                                                       groupingBy(String::length)));

        assertEquals("[abundance]", result.get("a").get(9).toString());
        assertEquals("[by, be, by]", result.get("b").get(2).toString());
        assertEquals("[flame, fresh]", result.get("f").get(5).toString());
        assertEquals("[gaudy, grave]", result.get("g").get(5).toString());
        assertEquals("[should, spring]", result.get("s").get(6).toString());
        assertEquals("[substantial]", result.get("s").get(11).toString());
        assertEquals("[the, thy, thy, thy, too, the, the, thy, the, the, the]",
            result.get("t").get(3).toString());
        assertEquals("[where, waste, world]", result.get("w").get(5).toString());
    }


    /**
     * Given a stream of integers, compute separate sums of the even and odd values
     * in this stream. Since the input is a stream, this necessitates making a single
     * pass over the input.
     */
    @Test 
    public void f5_separateOddEvenSums() {
        IntStream input = new Random(987523).ints(20, 0, 100);

        Map<Boolean, Integer> result =
                input.boxed()
                        .collect(Collectors.partitioningBy(iint -> (iint.intValue() & 1) == 1, Collectors.summingInt(i -> i)));

        int sumEvens = result.get(Boolean.FALSE);
        int sumOdds  = result.get(Boolean.TRUE);

        assertEquals(516, sumEvens);
        assertEquals(614, sumOdds);
    }

    /**
     * Given a stream of strings, accumulate (collect) them into the result string
     * by inserting the input string at both the beginning and end. For example, given
     * input strings "x" and "y" the result should be "yxxy". Note: the input stream
     * is a parallel stream, so you MUST write a proper combiner function to get the
     * correct result.
     */
    @Test 
    public void f6_insertBeginningAndEnd() {
        Stream<String> input = List.of(
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t")
            .parallelStream();

        String result = input.collect(
                StringBuilder::new,
                (sb, str) -> sb.insert(0, str).append(str),
                (sb1, sb2) -> {
                    // if stream was not parallel, we wouldn't even need a combiner
                    int half = sb2.length() / 2;
                    sb1.append(sb2.substring(half));
                    sb1.insert(0, sb2.substring(0, half));
                }).toString();

        assertEquals("tsrqponmlkjihgfedcbaabcdefghijklmnopqrst", result);
    }

    /**
     * Count the total number of words and the number of distinct, lower case
     * words in a stream, in one pass. This exercise uses a helper class
     * that defines methods that are called by the Stream.collect() method.
     * Your task is to fill in the implementation of the accumulate() and
     * combine() methods in the helper class. You don't need to modify the
     * test method itself.
     *
     * The stream is run in parallel, so you must write a combine() method
     * that works properly.
     */
    static class TotalAndDistinct {
        private int count = 0;
        private final Set<String> set = new HashSet<>();

        // rely on implicit no-arg constructor

        void accumulate(String s) {
            count += 1;
            set.add(s);
        }

        void combine(TotalAndDistinct other) {
            count += other.count;
            set.addAll(other.set);
        }

        int getTotalCount() {
            return count;
        }

        int getDistinctCount() {
            return set.size();
        }

    }

    @Test 
    public void f7_countTotalAndDistinctWords() {
        List<String> allWords = reader.lines()
                                      .map(String::toLowerCase)
                                      .flatMap(line -> SPLIT_PATTERN.splitAsStream(line))
                                      .collect(toList());

        TotalAndDistinct totalAndDistinct =
            Collections.nCopies(100, allWords)
                       .parallelStream()
                       .flatMap(List::stream)
                       .collect(TotalAndDistinct::new,
                                TotalAndDistinct::accumulate,
                                TotalAndDistinct::combine);

        assertEquals(81, totalAndDistinct.getDistinctCount());
        assertEquals(10700, totalAndDistinct.getTotalCount());
    }

    /**
     * As you probably realized, these exercises were mostly about Collecting streams
     * We can cascade collecting elements using downStreams in the Collectors class.
     * As you could see, there is 1 basic groupBy method, whic hreturns Map<K,List<V>>
     *
     * For example :
     *              reader.lines()
     *                         .flatMap(SPLIT_PATTERN::splitAsStream)
     *                         .collect(Collectors.groupingBy(word -> word.substring(0, 1)));
     *
     * This is actually equals to this :
     *              reader.lines()
     *                         .flatMap(SPLIT_PATTERN::splitAsStream)
     *                         .collect(Collectors.groupingBy(word -> word.substring(0, 1),
     *                                                        mapping(line -> line, toList()));
     *
     *   So in default, we define mapper for Key of the map and gets all occurences as values.
     *
     *   This collecting is based on Collector interface and its non-extendable implementation CollectorImpl in Collectors class
     */

// ========================================================
// END OF EXERCISES
// TEST INFRASTRUCTURE IS BELOW
// ========================================================


    // Pattern for splitting a string into words
    static final Pattern SPLIT_PATTERN = Pattern.compile("[- .:,]+");

    private BufferedReader reader;

    @BeforeEach
    public void z_setUpBufferedReader() throws IOException {
        reader = Files.newBufferedReader(
                Paths.get("SonnetI.txt"), StandardCharsets.UTF_8);
    }

    @AfterEach
    public void z_closeBufferedReader() throws IOException {
        reader.close();
    }

}
