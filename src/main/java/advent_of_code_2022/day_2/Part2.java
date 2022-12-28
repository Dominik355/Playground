package advent_of_code_2022.day_2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws Exception {
        Part2 part2 = new Part2();
        part2.sol();

        System.out.println(part2.calculateMatch("A", "Y"));
        System.out.println(part2.calculateMatch("B", "X"));
        System.out.println(part2.calculateMatch("C", "Z"));
        System.out.println();
        System.out.println(part2.calculateMatch("A", "X"));
        System.out.println(part2.calculateMatch("A", "Y"));
        System.out.println(part2.calculateMatch("A", "Z"));
    }

    void sol() throws Exception {
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
    private int calculateMatch(String opponentPlay, String result) {
        OpponentPlay op = OpponentPlay.valueOf(opponentPlay);
        Result r = Result.valueOf(result);
        return r.value + MyPlay.fromPlace(getBFromResult(op.value, r.from));
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

        public static int fromPlace(int place) {
            for(MyPlay m : MyPlay.values()) {
                if (m.place == place) {
                    return m.value;
                }
            }
            return 0;
        }
    }

    enum Result {
        X(0, -1),
        Y(3, 0),
        Z(6, 1);

        private final int value;
        private final int from;

        Result(int value, int from) {
            this.value = value;
            this.from = from;
        }

    }

    public static int getBFromResult(int numA, int result) {
        int numB = result + numA;
        if (numB > 2) numB -= 3;
        int r = (numB < 0) ? (numB * -2) : numB;
        return r;
    }

}