package com.breadturtle.aoc2021;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Runner {

    public static void main(String[] args) {
        System.out.println("Advent of Code 2021");
        for (Day day : dayImplementations()) {
            day.runPretty();
        }
    }

    /**
     * Hacky introspection to find all classes implementing {@link Day}.
     */
    private static List<Day> dayImplementations() {
        String daysPackage = "com.breadturtle.aoc2021";

        ArrayList<Day> days = new ArrayList<>();
        for (String classFile : filesForPackage(daysPackage)) {
            String simpleClassName = classFile.substring(0, classFile.lastIndexOf('.'));
            String fullClassName = daysPackage + "." + simpleClassName;

            try {
                Class<?> clazz = Class.forName(fullClassName);
                if (Arrays.asList(clazz.getInterfaces()).contains(Day.class)) {
                    Day d = (Day) clazz.getConstructor().newInstance();
                    days.add(d);
                }
            } catch (ClassNotFoundException e) {
                System.err.println("WARN: Couldn't find class for " + fullClassName);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                System.err.printf("WARN: class=[%s] did not have expected no argument constructor\n", fullClassName);
            }
        }

        days.sort(Comparator.comparing(Day::dayNumber));
        return days;
    }

    private static List<String> filesForPackage(String _package) {
        try {
            InputStream packageFilesStream = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(_package.replace('.', '/'));
            if (packageFilesStream == null) {
                throw new RuntimeException("Could not find package " + _package);
            }

            String packageFiles = new String(packageFilesStream.readAllBytes(), StandardCharsets.UTF_8);

            return Arrays.asList(packageFiles.split("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Could list files in package " + _package);
        }
    }
}
