package advent_of_code_2022.day_2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws Exception {
        Part1 part1 = new Part1();
        part1.part1();

        System.out.println(part1.calculateMatch("A", "Y"));
        System.out.println(part1.calculateMatch("B", "X"));
        System.out.println(part1.calculateMatch("C", "Z"));
    }

    void part1() throws Exception {
        List<String> lines = Files.readAllLines(Path.of(getClass().getClassLoader().getResource("day_2.txt").toURI()));
        String[] vals;
        int total = 0;
        for (String line : lines) {
            vals = line.split(" ");
            total += calculateMatch(vals[0], vals[1]);
        }
        System.out.println("total: " + total);
    }

    /**

     * @return -1 -> Lose
     *          0 -> Draw
     *          1 -> Win
     */
    private int calculateMatch(String opponentPlay, String myPlay) {
        OpponentPlay op = OpponentPlay.valueOf(opponentPlay);
        MyPlay mp = MyPlay.valueOf(myPlay);
        return Result.valueOf(baseThreeDistance(op.value, mp.place)) + mp.value;
    }

    enum OpponentPlay {
        A(0),
        B(1),
        C(2);

        private final int value;

        OpponentPlay(int value) {
            this.value = value;
        }
    }

    enum MyPlay {
        X(0, 1),
        Y(1, 2),
        Z(2, 3);

        private final int place;
        private final int value;

        MyPlay(int place, int value) {
            this.place = place;
            this.value = value;
        }
    }

    enum Result {
        W(6, 1),
        D(3, 0),
        L(0, -1);

        private final int value;
        private final int from;

        Result(int value, int from) {
            this.value = value;
            this.from = from;
        }

        public static int valueOf(int from) {
            for(Result r : Result.values()) {
                if (r.from == from) {
                    return r.value;
                }
            }
            return 0;
        }
    }

    public static int baseThreeDistance(int numA, int numB) {
        int diff = numB - numA;
        return (Math.abs(diff) > 1) ? (diff / -2) : diff;
    }


}
