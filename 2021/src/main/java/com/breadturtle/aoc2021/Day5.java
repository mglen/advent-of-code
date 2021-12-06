package com.breadturtle.aoc2021;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 implements Day {

    private List<Line> lines;

    @Override
    public void setup() {
        lines = Utils.getInputAsStream("day5.txt")
                .map(line -> {
                    String[] aAndB = line.split(" -> ");
                    String[] a = aAndB[0].split(",");
                    String[] b = aAndB[1].split(",");
                    return new Line(
                            new Point(Integer.parseInt(a[0]), Integer.parseInt(a[1])),
                            new Point(Integer.parseInt(b[0]), Integer.parseInt(b[1]))
                    );
                })
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public int dayNumber() {
        return 5;
    }

    @Override
    public Object part1() {
        Map<Point, Integer> pointCounter = lines.stream()
                .filter(Line::isCardinal)
                .flatMap(line -> line.getPoints().stream())
                .collect(Collectors.toMap(p -> p, p -> 1, Integer::sum));
        return pointCounter.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .count();
    }

    @Override
    public Object part2() {
        Map<Point, Integer> pointCounter = lines.stream()
                .flatMap(line -> line.getPoints().stream())
                .collect(Collectors.toMap(p -> p, p -> 1, Integer::sum));
        return pointCounter.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .count();
    }

    static class Line {
        final Point a;
        final Point b;

        public Line(Point a, Point b) {
            this.a = a;
            this.b = b;
        }

        public boolean isCardinal() {
            return a.x == b.x || a.y == b.y;
        }

        public Set<Point> getPoints() {
            if (a.x == b.x) {
                // Vertical
                return reversibleRange(a.y, b.y)
                        .map(y -> new Point(a.x, y))
                        .collect(Collectors.toSet());
            } else if (a.y == b.y) {
                // Horizontal
                return reversibleRange(a.x, b.x)
                        .map(x -> new Point(x, a.y))
                        .collect(Collectors.toSet());
            } else {
                // Diagonal
                return Utils.poorZip(
                        reversibleRange(a.x, b.x),
                        reversibleRange(a.y, b.y),
                        Point::new
                ).collect(Collectors.toSet());
            }
        }
    }

    private static Stream<Integer> reversibleRange(int a, int b) {
        return (a < b ? IntStream.rangeClosed(a, b) : IntStream.rangeClosed(b, a).map(i -> b + (a - i))).boxed();
    }

    static class Point {
        final int x;
        final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "{" + x + ", " + y + '}';
        }
    }


    static String visualizeVentLines(Map<Point, Integer> pointCounter) {
        Integer maxValue = pointCounter.values().stream().max(Integer::compare)
                .orElseThrow(() -> new IllegalArgumentException("Input must not be empty"));

        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        for (Point point : pointCounter.keySet()) {
            if (point.x < minX) minX = point.x;
            if (point.x > maxX) maxX = point.x;
            if (point.y < minY) minY = point.y;
            if (point.y > maxY) maxY = point.y;
        }

        int padding = maxValue.toString().length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Point point = new Point(x, y);
                String s = pointCounter.getOrDefault(point, 0).toString();
                stringBuilder.append(" ".repeat(padding - s.length())).append(s).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    static void generateImageForVentLines(Map<Point, Integer> pointCounter) {
        Integer maxValue = pointCounter.values().stream().max(Integer::compare)
                .orElseThrow(() -> new IllegalArgumentException("Input must not be empty"));

        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        for (Point point : pointCounter.keySet()) {
            if (point.x < minX) minX = point.x;
            if (point.x > maxX) maxX = point.x;
            if (point.y < minY) minY = point.y;
            if (point.y > maxY) maxY = point.y;
        }

        int width = maxX - minX;
        int height = maxY - minY;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, width, height);
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Point point = new Point(x, y);
                Integer numberOfOverlaps = pointCounter.get(point);
                if (numberOfOverlaps != null) {
                    int imageX = point.x - minX;
                    int imageY = point.y - minY;
                    int brightness = Math.round(((float) numberOfOverlaps / maxValue) * 255);
                    graphics.setColor(new Color(brightness, brightness, brightness));
                    graphics.fillRect(imageX, imageY, 1, 1);
                }
            }
        }

        File outputImage = new File("day5.png");
        try {
            ImageIO.write(bufferedImage, "png", outputImage);
        } catch (IOException e) {
            throw new RuntimeException("Could not write image at " + outputImage.getAbsolutePath(), e);
        }
    }
}
