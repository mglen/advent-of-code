package com.breadturtle.aoc2021;

import java.util.*;
import java.util.stream.Collectors;

public class Day4 implements Day {

    private List<Integer> numbers;
    private List<Board> boards;

    @Override
    public void setup() {
        List<String> input = Utils.getInputAsStream("day4.txt")
                .collect(Collectors.toUnmodifiableList());

        numbers = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toUnmodifiableList());

        boards = new ArrayList<>();

        for (int i = 2; i < input.size(); i += 6) {
            List<List<Integer>> boardBuilder = new ArrayList<>();
            for (int l = 0; l < 5; l++) {

                List<Integer> row = Arrays.stream(input.get(i + l).strip().split("\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toUnmodifiableList());
                boardBuilder.add(row);
            }
            boards.add(new Board(boardBuilder));
        }
    }

    @Override
    public int dayNumber() {
        return 4;
    }

    @Override
    public Object part1() {
        Set<Integer> numbersDrawn = new HashSet<>(numbers.subList(0, 4));
        for (Integer number : numbers.subList(4, numbers.size())) {
            numbersDrawn.add(number);

            for (Board board : boards) {
                if (board.hasWon(numbersDrawn)) {
                    return board.score(numbersDrawn) * number;
                }
            }
        }
        return "No winning board found";
    }

    @Override
    public Object part2() {
        Set<Integer> numbersDrawn = new HashSet<>(numbers.subList(0, 4));
        List<Board> remainingBoards = new ArrayList<>(this.boards);
        for (Integer number : numbers.subList(4, numbers.size())) {
            numbersDrawn.add(number);
            if (remainingBoards.size() == 1) {
                if (remainingBoards.get(0).hasWon(numbersDrawn)) return remainingBoards.get(0).score(numbersDrawn) * number;
            } else {
                remainingBoards.removeIf(board -> board.hasWon(numbersDrawn));
            }

        }
        return "No single losing board found";
    }

    static class Board {
        private final Set<Integer> numbers = new HashSet<>();
        private final Set<Set<Integer>> winningCombinations = new HashSet<>();

        public Board(List<List<Integer>> boardData) {
            assert boardData.size() == 5;
            assert boardData.get(0).size() == 5;

            for (int y = 0; y < 5; y++) {
                winningCombinations.add(new HashSet<>(boardData.get(y)));
                numbers.addAll(boardData.get(y));

                Set<Integer> column = new HashSet<>();
                for (int _y = 0; _y < 5; _y++) {
                    column.add(boardData.get(_y).get(y));
                }
                winningCombinations.add(column);
            }
        }

        public boolean hasWon(Set<Integer> inputs) {
            for (Set<Integer> winningCombination : winningCombinations) {
                if (inputs.containsAll(winningCombination)) return true;
            }
            return false;
        }

        public int score(Set<Integer> numbersDrawn) {
            HashSet<Integer> unmatchedNumbers = new HashSet<>(numbers) {{
                removeAll(numbersDrawn);
            }};
            return unmatchedNumbers.stream().mapToInt(Integer::intValue).sum();
        }
    }
}
