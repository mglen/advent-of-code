package com.breadturtle.aoc2021;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3 implements Day {

    int height;
    int width;
    boolean[][] bits; // y then x

    @Override
    public void setup() {
        List<String> input = Utils.getInputAsStream("day3.txt")
                .collect(Collectors.toUnmodifiableList());
        height = input.size();
        width = input.get(0).length();
        bits = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            char[] row = input.get(y).toCharArray();
            for (int x = 0; x < width; x++) {
                switch (row[x]) {
                    case '1' -> bits[y][x] = true;
                    case '0' -> bits[y][x] = false;
                    default -> throw new RuntimeException(
                            String.format("Unexpected character=[%s] row %s col %s", row[x], y, x));
                }
            }
        }
    }

    @Override
    public int dayNumber() {
        return 3;
    }

    @Override
    public Object part1() {
        Boolean[][] pivot = new Boolean[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pivot[x][y] = bits[y][x];
            }
        }
        int gamma = 0;
        int epsilon = 0;

        for (int x = 0; x < width; x++) {
            Integer positiveBits = Utils.reduce(pivot[x], (acc, bit) -> bit ? acc + 1 : acc, 0);
            if (positiveBits > height / 2) {
                gamma = gamma << 1 | 1;
                epsilon = epsilon << 1;
            } else {
                gamma = gamma << 1;
                epsilon = epsilon << 1 | 1;
            }
        }
        return String.valueOf(gamma * epsilon);
    }

    @Override
    public Object part2() {
        return ratingCalculator(RatingType.OXYGEN_GENERATOR)
                * ratingCalculator(RatingType.C02_SCRUBBER);
    }

    enum RatingType {
        OXYGEN_GENERATOR, C02_SCRUBBER
    }

    private int ratingCalculator(RatingType ratingType) {
        List<Integer> indexes = IntStream.range(0, bits.length).boxed().collect(Collectors.toList());
        int bit = 0;
        while (indexes.size() > 1) {
            List<Integer> positiveIndexes = new ArrayList<>();
            List<Integer> negativeIndexes = new ArrayList<>();
            for(Integer index: indexes) {
                if (bits[index][bit]) {
                    positiveIndexes.add(index);
                } else {
                    negativeIndexes.add(index);
                }
            }
            indexes = switch (ratingType) {
                case OXYGEN_GENERATOR -> positiveIndexes.size() >= negativeIndexes.size()
                        ? positiveIndexes
                        : negativeIndexes;
                case C02_SCRUBBER -> negativeIndexes.size() <= positiveIndexes.size()
                        ? negativeIndexes
                        : positiveIndexes;
            };
            bit += 1;
        }

        return booleanArrayToInt(bits[indexes.get(0)]);
    }

    private int booleanArrayToInt(boolean[] arr) {
        int result = 0;
        for (boolean b : arr) {
            result = b ? result << 1 | 1: result << 1;
        }
        return result;
    }
}
