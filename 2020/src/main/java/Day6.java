import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {

    private static Set<Character> letters = "abcdefghijklmnopqrstuvwxyz".chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toSet());

    public static void main(String... args) {

        List<String> groups = Arrays.stream(Utils.readFile("day6.txt")
                .split("\\n\\n"))
                .collect(Collectors.toList());

        long part1 = groups.stream()
                .map(group -> Utils.charStream(group)
                        .filter(letters::contains)
                        .collect(Collectors.toSet()).size())
                .mapToLong(i -> i)
                .sum();

        System.out.println("Day6 part1: " + part1);

        long part2 = groups.stream().map(group -> Stream.of(group.split("\\n"))
                .map(person -> Utils.charStream(person)
                        .collect(Collectors.toSet()))
                .reduce((acc, s) -> {
                    acc.retainAll(s);
                    return acc;
                }).get().size())
                .mapToLong(i -> i)
                .sum();

        System.out.println("Day6 part2: " + part2);
    }
}
