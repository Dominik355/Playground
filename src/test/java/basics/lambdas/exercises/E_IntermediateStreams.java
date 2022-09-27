package basics.lambdas.exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import basics.lambdas.exercises.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This set of exercises covers more advanced stream operations
 * longer stream pipelines, and simple reductions.
 */
public class E_IntermediateStreams {

    /**
     * Convert a list of strings into a list of characters.
     */
    @Test 
    public void e1_stringsToCharacters() {
        List<String> input = List.of("alfa", "bravo", "charlie");

        List<Character> result = input.stream()
                        .flatMapToInt(String::chars)
                        .mapToObj(i -> (char) i)
                        .collect(Collectors.toList());

        assertEquals("[a, l, f, a, b, r, a, v, o, c, h, a, r, l, i, e]", result.toString());
        assertTrue(result.stream().allMatch(x -> x instanceof Character));
    }

    /**
     * Collect all the words from the text file into a list.
     * Use the regular expression Pattern SPLIT_PATTERN to split
     * a string into words, and use Pattern.splitAsStream(String)
     * to do the splitting. SPLIT_PATTERN is defined at the bottom
     * of this file. As before, use the BufferedReader variable
     * named "reader" that has been set up for you to read from
     * the text file.
     *
     * @throws IOException
     */
    @Test 
    public void e2_listOfAllWords() throws IOException {
        List<String> output = reader.lines()
                        .flatMap(line -> SPLIT_PATTERN.splitAsStream(line))
                        .collect(Collectors.toList());

        assertEquals(
            List.of(
                "From", "fairest", "creatures", "we", "desire", "increase",
                "That", "thereby", "beauty's", "rose", "might", "never", "die",
                "But", "as", "the", "riper", "should", "by", "time", "decease",
                "His", "tender", "heir", "might", "bear", "his", "memory",
                "But", "thou", "contracted", "to", "thine", "own", "bright", "eyes",
                "Feed'st", "thy", "light's", "flame", "with", "self", "substantial", "fuel",
                "Making", "a", "famine", "where", "abundance", "lies",
                "Thy", "self", "thy", "foe", "to", "thy", "sweet", "self", "too", "cruel",
                "Thou", "that", "art", "now", "the", "world's", "fresh", "ornament",
                "And", "only", "herald", "to", "the", "gaudy", "spring",
                "Within", "thine", "own", "bud", "buriest", "thy", "content",
                "And", "tender", "churl", "mak'st", "waste", "in", "niggarding",
                "Pity", "the", "world", "or", "else", "this", "glutton", "be",
                "To", "eat", "the", "world's", "due", "by", "the", "grave", "and", "thee"),
            output);
    }

    /**
     * Read the words from the text file, and create a list containing the words
     * of length 8 or longer, converted to lower case, and sorted alphabetically.
     *
     * @throws IOException
     */
    @Test 
    public void e3_longLowerCaseSortedWords() throws IOException {
        List<String> output = reader.lines()
                        .flatMap(line -> SPLIT_PATTERN.splitAsStream(line))
                        .filter(line -> line.length() > 7)
                        .map(String::toLowerCase)
                        .sorted(Comparator.naturalOrder())
                        .collect(Collectors.toList());

        assertEquals(
            List.of(
                "abundance", "beauty's", "contracted", "creatures",
                "increase", "niggarding", "ornament", "substantial"),
            output);
    }

    /**
     * Read the words from the text file, and create a list containing the words
     * of length 8 or longer, converted to lower case, and sorted reverse alphabetically.
     * (Same as above except for reversed sort order.)
     *
     * @throws IOException
     */
    @Test 
    public void e4_longLowerCaseReverseSortedWords() throws IOException {
        List<String> result = reader.lines()
                .flatMap(line -> SPLIT_PATTERN.splitAsStream(line))
                .filter(line -> line.length() > 7)
                .map(String::toLowerCase)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        assertEquals(
            List.of(
                "substantial", "ornament", "niggarding", "increase",
                "creatures", "contracted", "beauty's", "abundance"),
            result);
    }

    /**
     * Read words from the text file, and sort unique, lower-cased words by length,
     * then alphabetically within length, and place the result into an output list.
     *
     * @throws IOException
     */
    @Test 
    public void e5_sortedLowerCaseDistinctByLengthThenAlphabetically() throws IOException {
        List<String> result = reader.lines()
                        .flatMap(SPLIT_PATTERN::splitAsStream)
                        .map(String::toLowerCase)
                        .distinct()
                        .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                        .collect(Collectors.toList());

        assertEquals(
            List.of(
                "a", "as", "be", "by", "in", "or", "to", "we",
                "and", "art", "bud", "but", "die", "due", "eat", "foe",
                "his", "now", "own", "the", "thy", "too", "bear", "else",
                "eyes", "from", "fuel", "heir", "lies", "only",
                "pity", "rose", "self", "that", "thee", "this", "thou",
                "time", "with", "churl", "cruel", "flame", "fresh", "gaudy",
                "grave", "might", "never", "riper", "sweet", "thine",
                "waste", "where", "world", "bright", "desire", "famine",
                "herald", "mak'st", "making", "memory", "should", "spring",
                "tender", "within", "buriest", "content", "decease",
                "fairest", "feed'st", "glutton", "light's", "thereby", "world's", "beauty's",
                "increase", "ornament", "abundance", "creatures", "contracted", "niggarding",
                "substantial"),
            result);
    }

    /**
     * Compute the value of 21!, that is, 21 factorial. This value is larger than
     * Long.MAX_VALUE, so you must use BigInteger.
     */
    @Test 
    public void e6_bigFactorial() {
        BigInteger result = LongStream.rangeClosed(1, 21)
                        .mapToObj(BigInteger::valueOf)
                        .reduce(BigInteger.ONE, BigInteger::multiply);

        assertEquals(new BigInteger("51090942171709440000"), result);
    }

    /**
     * Get the last word in the text file.
     *
     * @throws IOException
     */
    @Test 
    public void e7_getLastWord() throws IOException {
        String result = reader.lines()
                .flatMap(SPLIT_PATTERN::splitAsStream)
                .reduce((a, b) -> b)
                .orElse("");

        assertEquals("thee", result);
    }

    /**
     * Create a list containing ArrayList.class and all its super classes.
     */
    @Test 
    public void e8_selectTheSuperClassesOfArrayList() {
        Class<?> origin = ArrayList.class;

        List<Class<?>> result = Stream.<Class<?>>iterate(origin, Class::getSuperclass)
                .takeWhile(Objects::nonNull)
                .collect(Collectors.toList());

        assertEquals(
            List.of(ArrayList.class, AbstractList.class, AbstractCollection.class, Object.class),
            result);
    }

    /**
     * Count the length of a stream dropping the first elements on a predicate.
     */
    @Test 
    public void e9_countTheElementsAfterAPredicate() {

        Random rand = new Random(314L);
        Stream<String> stream = Stream.iterate(
                "",
                (String s) -> {
                    final int nextInt = rand.nextInt(10);
                    return (nextInt == 0 && !s.isEmpty()) ? s.substring(0, s.length() - 1) :
                           (nextInt == 8 || nextInt == 9) ? s + "+"
                                                          : s;
                }).limit(100);

        long count = stream.dropWhile(s -> s.length() < 3).count();


        assertEquals(53, count);
    }

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
