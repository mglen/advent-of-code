import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 {

    public static void main(String... args) {

        List<Integer> sortedSeatIds = Utils.readLines("day5.txt")
                .map(boardingPass -> {
                    int rowNum = bitStrToNum(boardingPass.substring(0, 7), 'F');
                    int colNum = bitStrToNum(boardingPass.substring(7), 'R');

                    return rowNum * 8 + colNum;
                })
                .sorted()
                .collect(Collectors.toList());

        Integer minSeatId = sortedSeatIds.get(0);
        Integer maxSeatId = sortedSeatIds.get(sortedSeatIds.size() - 1);

        System.out.println("Day5 part1: " + maxSeatId);

        int missingSeatNumber =
                IntStream.rangeClosed(minSeatId, maxSeatId).sum()
                        - sortedSeatIds.stream().mapToInt(i -> i).sum();

        System.out.println("Day5 part2: " + missingSeatNumber);
    }

    private static int bitStrToNum(String bitStr, char onBit) {
        int r = 0;
        for (char c : bitStr.toCharArray())
            r = c == onBit ? (r << 1) + 1: r << 1;
        return r;
    }
}
