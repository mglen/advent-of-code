package com.breadturtle.aoc2021;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Day6 implements Day {

    List<Integer> fishes;

    @Override
    public void setup() {
        fishes = Arrays.stream(Utils.getInput("day6.txt").split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public int dayNumber() {
        return 6;
    }

    @Override
    public Object part1() {
        Map<Integer, Long> fishesByDay = groupFishIntoDays(fishes);
        return getFishAfterDays(fishesByDay, 80);
    }

    @Override
    public Object part2() {
        Map<Integer, Long> fishesByDay = groupFishIntoDays(fishes);
        return getFishAfterDays(fishesByDay, 256);
    }

    private static Map<Integer, Long> groupFishIntoDays(List<Integer> fishes) {
        Map<Integer, Long> fishesByDay = new HashMap<>();
        for (Integer fish : fishes) {
            fishesByDay.put(fish, fishesByDay.getOrDefault(fish, 0L) + 1);
        }
        return fishesByDay;
    }

    private static long getFishAfterDays(Map<Integer, Long> fishesByDay, long days) {
        for (int i = 0; i < days; i++) {
            fishesByDay = ageFish(fishesByDay);
        }
        return fishesByDay.values().stream()
                .mapToLong(Long::valueOf)
                .sum();
    }

    private static Map<Integer, Long> ageFish(Map<Integer, Long> fishesByDay) {
        Map<Integer, Long> newGroups = new HashMap<>();
        fishesByDay.forEach((group, numberOfFish) -> {
            if (group == 0) {
                // Reset the parents
                newGroups.put(6, newGroups.getOrDefault(6, 0L) +  numberOfFish);
                // The new fish
                newGroups.put(8, newGroups.getOrDefault(8, 0L) +  numberOfFish);
            } else {
                newGroups.put(group - 1, newGroups.getOrDefault(group - 1, 0L) + numberOfFish);
            }
        });
        return newGroups;
    }
}
