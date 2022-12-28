package advent_of_code_2022.day_3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Solution {

    final int LOWERCASE_MINUS = 96;
    final int UPPERCASE_MINUS = 38;

    List<String> testInput = List.of(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg"
    );

    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        solution.part1();
        solution.part2();
    }

    void part1() throws Exception {
        List<String> input = Files.readAllLines(Path.of(getClass().getClassLoader().getResource("day_3.txt").toURI()));

        int total = 0;
        for(String s : input) {
            String first = s.substring(0, s.length() / 2);
            String second = s.substring(s.length() / 2, s.length());
            boolean[] found = new boolean[53];
            for (char c : first.toCharArray()) {
                int val = Character.isUpperCase(c) ? c - UPPERCASE_MINUS : c - LOWERCASE_MINUS;
                found[val] = true;
            }
            Set<Integer> zhoda = new HashSet<>();
            for (char c : second.toCharArray()) {
                int val = Character.isUpperCase(c) ? c - UPPERCASE_MINUS : c - LOWERCASE_MINUS;
                if (found[val]) {
                    zhoda.add(val);
                }
            }

            total += zhoda.stream()
                    .mapToInt(i -> i)
                    .sum();
        }
        System.out.println("Total: " + total);
    }

    void part2() throws Exception {
        List<String> input = Files.readAllLines(Path.of(getClass().getClassLoader().getResource("day_3.txt").toURI()));

        int total = 0;
        for (int i = 0; i < input.size(); i += 3) {
            List<String> lines = input.subList(i, i + 3);
            int[] appearences = new int[53];
            for (String line: lines) {
                boolean[] appearencesLine = new boolean[53];
                for (char c : line.toCharArray()) {
                    int val = Character.isUpperCase(c) ? c - UPPERCASE_MINUS : c - LOWERCASE_MINUS;
                    appearencesLine[val] = true;
                }
                for (int p = 0; p < appearences.length; p++) {
                    if (appearencesLine[p]) {
                        appearences[p] += 1;
                    }
                }
            }

            for (int j = 1; j < appearences.length; j++) {
                if (appearences[j] == 3) {
                    total += j;
                    break;
                }
            }
        }
        System.out.println("Total: " + total);
    }
    
}
