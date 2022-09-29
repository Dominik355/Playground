package basics.lambdas.exercises;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JDK 9 added several new streams-producing APIs in the java.util.regex.Matcher
 * and java.util.Scanner classes. These APIs make it even easier to process text
 * using pattern matching and fluent streams pipelines. The exercises in this file
 * use the new JDK 9 pattern matching APIs.
 */
public class G_MatcherScanner {
    /**
     * Shakespeare used "poetic contractions" which (for purposes of this exercise)
     * are words that have an apostrophe (') in a position farther than one letter from
     * the end of the word. An example of modern English contraction is "can't" where
     * the apostrophe precedes the last letter of the word. An example of a poetic
     * contraction is "o'er" (over) where the apostrophe occurs earlier in the word.
     *
     * Use the Pattern WORD_PAT (defined below) to match words with an apostrophe
     * earlier in the word. Match the text from Shakespeare's first sonnet, which has
     * been loaded into the String variable SONNET, using the Matcher class, and
     * process the results using a Stream.
     */
    @Test 
    public void g1_wordsWithApostrophes() {
        Set<String> result =
                WORD_PAT.matcher(SONNET)
                        .results()
                        .map(res -> res.group())
                        .collect(Collectors.toSet());

        assertEquals(Set.of("Feed'st", "mak'st"), result);
    }

    /**
     * Perform the same task as in exercise g1, except using Scanner instead of Matcher.
     * Use the Scanner class to process the String variable SONNET, matching words as
     * described above using WORD_PAT.
     */
    @Test 
    public void g2_wordsWithApostrophes() {
        Set<String> result =
                new Scanner(SONNET)
                        .findAll(WORD_PAT)
                        .map(res -> res.group())
                        .collect(Collectors.toSet());

        assertEquals(Set.of("Feed'st", "mak'st"), result);
    }

    /**
     * Find all vowel trigraphs (that is, sequences of three consecutive vowels)
     * in the string variable SONNET. Replace each matching substring with
     * the substring converted to upper case and surrounded by square brackets "[]".
     * Use the predefined pattern TRIGRAPH_PAT to match vowel trigraphs.
     */
    @Test 
    public void g3_vowelTrigraphs() {
        final Pattern TRIGRAPH_PAT = Pattern.compile("[aeiou]{3}", Pattern.CASE_INSENSITIVE);
        String result = TRIGRAPH_PAT.matcher(SONNET)
                .replaceAll(word -> "[" + word.group().toUpperCase() + "]");

        assertTrue(result.contains("b[EAU]ty's"));
        assertEquals(614, result.length());
    }

    /**
     * Use Scanner to parse the SONNET string into whitespace-separated tokens.
     * Scanner's default token delimiter is whitespace, so no additional setup
     * needs to be done. Then, find the first such token that is of length 10
     * or more.
     *
     * (This task can be performed with String.split() or Pattern.splitAsStream(),
     * but the advantage of Scanner is that it operate on a file, an InputStream,
     * or a Channel, and all the input need not be loaded into memory.)
     */
    @Test 
    public void g4_firstLongWhitespaceSeparatedToken() {
        String result =
                new Scanner(SONNET).tokens()
                                .filter(word -> word.length() == 10)
                                .findFirst()
                                .get();

        assertEquals("contracted", result);
    }


// ========================================================
// END OF EXERCISES
// TEST INFRASTRUCTURE IS BELOW
// ========================================================

    /**
     * Pattern for use in exercises g1 and g2. This regex matches a word that contains
     * an apostrophe, by matching a word boundary, one or more letters, an apostrophe,
     * two or more letters, and a word boundary. Matching is case insensitive.
     */
    static final Pattern WORD_PAT = Pattern.compile("\\b[a-z]+'[a-z]{2,}\\b", Pattern.CASE_INSENSITIVE);

    /**
     * The text of Shakespeare's first sonnet, in a string. Note, this string
     * contains newline characters.
     */
    private String SONNET;

    @BeforeEach
    public void z_readFileIntoString() throws IOException {
        SONNET = new String(Files.readAllBytes(Paths.get("SonnetI.txt")),
                            StandardCharsets.UTF_8);
    }
}
