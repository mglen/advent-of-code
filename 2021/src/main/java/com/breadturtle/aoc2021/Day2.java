package com.breadturtle.aoc2021;

import java.util.List;
import java.util.stream.Collectors;

public class Day2 implements Day {

    List<Movement> movements;

    @Override
    public void setup() {
        movements = Utils.getInputAsStream("day2.txt")
                .map(Movement::parse)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public int dayNumber() {
        return 2;
    }

    @Override
    public String part1() {
        int horizontal = 0;
        int depth = 0;

        for (Movement m: movements) {
            switch (m.direction) {
                case "forward" -> horizontal += m.units;
                case "down" -> depth += m.units;
                case "up" -> depth -= m.units;
                default -> throw new RuntimeException("Unexpected direction: " + m.direction);
            }
        }

        return String.valueOf(horizontal * depth);
    }

    @Override
    public String part2() {
        int horizontal = 0;
        int depth = 0;
        int aim = 0;

        for (Movement m: movements) {
            switch (m.direction) {
                case "forward" -> {
                    horizontal += m.units;
                    depth += aim * m.units;
                }
                case "down" -> aim += m.units;
                case "up" -> aim -= m.units;
                default -> throw new RuntimeException("Unexpected direction: " + m.direction);
            }
        }

        return String.valueOf(horizontal * depth);
    }

    static class Movement {
        private final String direction;
        private final int units;

        Movement(String direction, int units) {
            this.direction = direction;
            this.units = units;
        }

        public static Movement parse(String line) {
            String[] s = line.split(" ");
            return new Movement(s[0], Integer.parseInt(s[1]));
        }
    }
}
