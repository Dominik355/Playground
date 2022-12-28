package advent_of_code_2022.day_4;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Solution {

    public static void main(String[] args) throws Exception {
        Solution s = new Solution();
        s.part1();
        s.part2();
    }

    void part1() throws Exception {
        List<String> lines = Files.readAllLines(Path.of(getClass().getClassLoader().getResource("day_4.txt").toURI()));

        int total = 0;
        for(String line : lines) {
            String[] parts = line.split(",");
            String[] range1 = parts[0].split("-");
            String[] range2 = parts[1].split("-");

            int rs = Integer.valueOf(range1[0]);
            int re = Integer.valueOf(range1[1]);

            int r2s = Integer.valueOf(range2[0]);
            int r2e = Integer.valueOf(range2[1]);

            int dis1 = distance(rs, re);
            int dis2 = distance(r2s, r2e);

            boolean fullyContains;
            if (dis1 >= dis2) {
                fullyContains = (r2s >= rs && r2e <= re);
            } else {
                fullyContains = (rs >= r2s && re <= r2e);
            }
            if (fullyContains) total += 1;
        }
        System.out.println("part1 total: " + total);
    }

    void part2() throws Exception {
        List<String> lines = Files.readAllLines(Path.of(getClass().getClassLoader().getResource("day_4.txt").toURI()));

        int total = 0;
        for(String line : lines) {
            String[] parts = line.split(",");
            String[] range1 = parts[0].split("-");
            String[] range2 = parts[1].split("-");

            int rs = Integer.valueOf(range1[0]);
            int re = Integer.valueOf(range1[1]);

            int r2s = Integer.valueOf(range2[0]);
            int r2e = Integer.valueOf(range2[1]);

            if ((rs <= r2e) && (r2s <= re)) total += 1;
        }
        System.out.println("part2 total: " + total);
    }

    int distance(int first, int second) {
        return second - first;
    }

}
