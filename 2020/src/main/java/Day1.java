import java.util.*;
import java.util.stream.Collectors;

public class Day1 {

    public static void main(String... args) {
        List<Integer> ints = Utils.readLines("day1.txt")
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int size = ints.size();

        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                int a = ints.get(i);
                int b = ints.get(j);
                if (a + b == 2020) {
                    Utils.println("Day1 Part1: %d * %d == %d", a, b, a * b);
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                for (int k = j; k < size; k++) {
                    int a = ints.get(i);
                    int b = ints.get(j);
                    int c = ints.get(k);
                    if (a + b + c == 2020) {
                        Utils.println("Day1 Part2: %d * %d * %d == %d", a, b, c, a * b * c);
                    }
                }
            }
        }
    }
}
