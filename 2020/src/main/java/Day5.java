import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 {

    public static void main(String... args) {

        List<Integer> sortedSeatIds = Utils.readLines("day5.txt")
                .map(boardingPass -> {
                    String row = boardingPass.substring(0, 7);
                    String col = boardingPass.substring(7);

                    int rowNum = Integer.parseInt(
                            row.replace('B', '1').replace('F', '0'),
                            2);

                    int colNum = Integer.parseInt(
                            col.replace('R', '1').replace('L', '0'),
                            2);

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
}
