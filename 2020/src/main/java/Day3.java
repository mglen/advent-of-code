import java.util.List;
import java.util.stream.Collectors;

public class Day3 {

    public static void main(String... args) {
        List<String> lines = Utils.readLines("day3.txt")
                .collect(Collectors.toList());

        TreeRun treeRun = new TreeRun(lines);

        System.out.println("Day3 part1: " + treeRun.walkAngle(3, 1));

        System.out.println("Day3 part2: " + (
                treeRun.walkAngle(1, 1) *
                treeRun.walkAngle(3, 1) *
                treeRun.walkAngle(5, 1) *
                treeRun.walkAngle(7, 1) *
                treeRun.walkAngle(1, 2)));
    }

    static class TreeRun {

        private final boolean[][] treeMap;
        private final int x_length;
        private final int y_length;

        public TreeRun(List<String> lines) {
            x_length = lines.get(0).length();
            y_length = lines.size();

            if (!lines.stream().allMatch(line -> line.length() == x_length)) {
                throw Utils.fail("Input had lines with uneven lengths");
            }

            treeMap = new boolean[x_length][y_length];

            for (int y = 0; y < y_length; y++) {
                for (int x = 0; x < x_length; x++) {
                    // All non-'#'s are false
                    treeMap[x][y] = lines.get(y).charAt(x) == '#';
                }
            }
        }

        public long walkAngle(int rightStep, int downStep) {
            long trees = treeMap[0][0] ? 1 : 0;
            int currentDown = downStep;
            int currentRight = rightStep;

            while (currentDown < y_length) {
                trees += treeMap[currentRight][currentDown] ? 1 : 0;
                currentDown += downStep;
                currentRight = (currentRight + rightStep) % x_length;
            }
            return trees;
        }
    }
}
