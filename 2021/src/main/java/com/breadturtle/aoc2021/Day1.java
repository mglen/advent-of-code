package com.breadturtle.aoc2021;

import java.util.List;
import java.util.stream.Collectors;

public class Day1 implements Day {

    List<Integer> depths;

    @Override
    public int dayNumber() {
        return 1;
    }

    @Override
    public void setup() {
        depths = Utils.getInputAsStream("day1.txt")
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Override
    public Object part1() {
        int timesDepthIncreased = 0;
        for (int i = 1; i < depths.size(); i++) {
            if (depths.get(i - 1) < depths.get(i)) {
                timesDepthIncreased += 1;
            }
        }

        return timesDepthIncreased;
    }

    @Override
    public Object part2() {
        int prev_window = depths.get(0) + depths.get(1) + depths.get(2);
        int timesDepthIncreased = 0;
        for (int i = 3; i < depths.size(); i++) {
            int next_window = depths.get(i - 2) + depths.get(i - 1) + depths.get(i);
            if (prev_window < next_window) {
                timesDepthIncreased += 1;
            }
            prev_window = next_window;
        }

        return timesDepthIncreased;
    }
}
