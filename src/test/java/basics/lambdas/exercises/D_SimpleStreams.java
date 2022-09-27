package basics.lambdas.exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This set of exercises covers simple stream pipelines,
 * including intermediate operations and basic collectors.
 *
 * Some of these exercises use a BufferedReader variable
 * named "reader" that the test has set up for you.
 */
public class D_SimpleStreams {
    /**
     * Given a list of words, create an output list that contains
     * only the odd-length words, converted to upper case.
     */
    @Test 
    public void d1_upcaseOddLengthWords() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

        List<String> result = input.stream()
                        .filter(word -> word.length() % 2 == 1)
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());

        assertEquals(List.of("BRAVO", "CHARLIE", "DELTA", "FOXTROT"), result);
    }

    /**
     * Take the third through fifth words of the list, extract the
     * second letter from each, and join them, separated by commas,
     * into a single string. Watch for off-by-one errors.
     */
    @Test 
    public void d2_joinStreamRange() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

        String result = input.stream()
                        .skip(2)
                        .limit(3)
                        .map(str -> str.substring(1, 2))
                        .collect(Collectors.joining(","));

        assertEquals("h,e,c", result);
    }

    /**
     * Count the number of lines in the text file. (Remember to
     * use the BufferedReader named "reader" that has already been
     * opened for you.)
     *
     * @throws IOException
     */
    @Test 
    public void d3_countLinesInFile() throws IOException {
        long count = reader.lines()
                        .count();

        assertEquals(14, count);
    }

    /**
     * Find the length of the longest line in the text file.
     *
     * @throws IOException
     */
    @Test 
    public void d4_findLengthOfLongestLine() throws IOException {
        int longestLength = reader.lines()
                        .max(Comparator.comparingInt(String::length))
                        .get()
                        .length();
        /* OR
        longestLength = reader.lines()
                        .mapToInt(String::length)
                        .max()
                        .orElse(0);
        */

        assertEquals(53, longestLength);
    }

    /**
     * Find the longest line in the text file.
     *
     * @throws IOException
     */
    @Test 
    public void d5_findLongestLine() throws IOException {
        // here we can use the first solution from last test
        String longest = reader.lines()
                .max(Comparator.comparingInt(String::length))
                .orElse("");

        assertEquals("Feed'st thy light's flame with self-substantial fuel,", longest);
    }

    /**
     * Select the longest words from the input list. That is, select the words
     * whose lengths are equal to the maximum word length.
     */
    @Test 
    public void d6_selectLongestWords() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");

        int maxLength = input.stream()
                        .mapToInt(String::length)
                        .max()
                        .orElse(-1);

        List<String> result = input.stream()
                        .filter(str -> str.length() == maxLength)
                        .collect(Collectors.toList());

        assertEquals(List.of("charlie", "foxtrot"), result);
    }

    /**
     * Select the list of words from the input list whose length is greater than
     * the word's position in the list (starting from zero) .
     */
    @Test 
    public void d7_selectByLengthAndPosition() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");

        List<String> result = IntStream.range(0, input.size())
                    .filter(i -> input.get(i).length() > i)
                    .mapToObj(i -> input.get(i))
                    .collect(Collectors.toList());

        assertEquals(List.of("alfa", "bravo", "charlie", "delta", "foxtrot"), result);
    }

// ========================================================
// END OF EXERCISES
// TEST INFRASTRUCTURE IS BELOW
// ========================================================


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
