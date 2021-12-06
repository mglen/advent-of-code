package com.breadturtle.aoc2021;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

public class Utils {
    public static Stream<String> getInputAsStream(String filename) {
        return Arrays.stream(getInput(filename).split("\n"));
    }

    static String getInput(String filename) {
        String path = "/day-inputs/" + filename;
        InputStream resourceAsStream = Utils.class.getResourceAsStream(path);
        if (resourceAsStream == null) {
            throw new RuntimeException("Couldn't open resource " + path);
        }
        try {
            return new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read resource " + path, e);
        }
    }

    static <ValueType, AccType> AccType reduce(
            ValueType[] arr,
            BiFunction<AccType, ValueType, AccType> accumulator,
            final AccType initial) {
        AccType current = initial;
        for (ValueType v : arr) {
            current = accumulator.apply(current, v);
        }
        return current;
    }

    static <A, B, R> Stream<R> poorZip(BaseStream<A, ?> a, BaseStream<B, ?> b, BiFunction<A, B, R> merge) {
        Iterator<A> aIter = a.iterator();
        Iterator<B> bIter = b.iterator();
        return Stream.generate(() -> true)
                .takeWhile(_b -> {
                    if (aIter.hasNext() ^ bIter.hasNext()) {
                        System.err.println("Got two different length streams");
                    }
                    return aIter.hasNext() && bIter.hasNext();
                })
                .map(_b -> merge.apply(aIter.next(), bIter.next()));
    }
}
