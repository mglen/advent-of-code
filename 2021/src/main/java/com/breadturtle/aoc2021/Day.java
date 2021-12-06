package com.breadturtle.aoc2021;

public interface Day {

    int dayNumber();
    default void setup() {}
    Object part1();
    Object part2();

    default void runPretty() {
        System.out.println("Day " + this.dayNumber());
        setup();
        System.out.println("  Part 1 answer: " + part1());
        System.out.println("  Part 2 answer: " + part2());
    }

}
