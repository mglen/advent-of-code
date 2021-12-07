package com.breadturtle.aoc2021;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7 implements Day {

    List<Integer> crabPositions;
    int minPosition;
    int maxPosition;

    @Override
    public int dayNumber() {
        return 7;
    }

    @Override
    public void setup() {
        crabPositions = Arrays.stream(Utils.getInput("day7.txt").split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toUnmodifiableList());
        minPosition = crabPositions.stream().mapToInt(i -> i).min().orElseThrow();
        maxPosition = crabPositions.stream().mapToInt(i -> i).max().orElseThrow();
    }

    @Override
    public Object part1() {
        // TODO: Median or mean may be the best answer?
        int minFuel = Integer.MAX_VALUE;
        for (int position = minPosition; position <= maxPosition; position++) {
            int fuelUsed = 0;
            for (Integer crabPosition : crabPositions) {
                fuelUsed += Math.abs(crabPosition - position);
            }
            if (fuelUsed < minFuel) minFuel = fuelUsed;
        }
        return minFuel;
    }

    @Override
    public Object part2() {
        int minFuel = Integer.MAX_VALUE;
        for (int position = minPosition; position <= maxPosition; position++) {
            int fuelUsed = 0;
            for (Integer crabPosition : crabPositions) {
                fuelUsed += fuelForDistance(Math.abs(crabPosition - position));
            }
            if (fuelUsed < minFuel) {
                minFuel = fuelUsed;
            }
        }
        return minFuel;
    }

    private static int fuelForDistance(int distance) {
        return IntStream.rangeClosed(1, distance).sum();
    }
}
