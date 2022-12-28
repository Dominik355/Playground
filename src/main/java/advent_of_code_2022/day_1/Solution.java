package advent_of_code_2022.day_1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        solution.part1();
        solution.part2();
    }

    // top 1
    void part1() throws Exception {
        List<String> lines = Files.readAllLines(Path.of(getClass().getClassLoader().getResource("day_1.txt").toURI()));
        int biggest = 0;
        int sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                if (biggest < sum) {
                    biggest = sum;
                }
                sum = 0;
                continue;
            }
            sum += Integer.valueOf(line);
        }
        System.out.println("Biggest: " + biggest);
    }

    // top 3
    void part2() throws Exception {
        List<String> lines = Files.readAllLines(Path.of(getClass().getClassLoader().getResource("day_1.txt").toURI()));
        int[] threeBest = new int[3];
        int sum = -1;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                int lastBigger = -1;
                for (int j = 0; j < threeBest.length; j++) {
                    if (sum > threeBest[j]) {
                        lastBigger = j;
                    }
                }
                if (lastBigger >= 0) {
                    if (lastBigger > 0) {
                        //shift array by 1
                        for (int k = 1; k <= lastBigger; k++) {
                            threeBest[k-1] = threeBest[k];
                        }
                    }
                    threeBest[lastBigger] = sum;
                }
                sum = 0;
                continue;
            }
            sum += Integer.valueOf(line);
        }
        System.out.println("Biggest: " + Arrays.toString(threeBest));
        System.out.println("sum: " + Arrays.stream(threeBest).sum());
    }

}
