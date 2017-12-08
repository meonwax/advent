package de.meonwax.advent.day02;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CorruptionChecksum {

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Checksum part one: " + loadInput().mapToInt(CorruptionChecksum::getRowChecksumOne).sum());
        System.out.println("Checksum part two: " + loadInput().mapToInt(CorruptionChecksum::getRowChecksumTwo).sum());
    }

    private static int getRowChecksumOne(String row) {
        Pattern pattern = Pattern.compile("\\s");
        Integer maxValue = (pattern
                .splitAsStream(row)
                .mapToInt(Integer::valueOf)
                .max())
                .orElse(0);
        Integer minValue = pattern.splitAsStream(row)
                .mapToInt(Integer::valueOf)
                .min()
                .orElse(0);
        return maxValue - minValue;
    }

    private static int getRowChecksumTwo(String row) {
        int[] numbersInRow = getNumbersInRow(row).toArray();
        for (int number1 : numbersInRow) {
            for (int number2 : numbersInRow) {
                if ((number1 != number2) && number1 % number2 == 0) {
                    return number1 / number2;
                }
            }
        }
        return 0;
    }

    private static IntStream getNumbersInRow(String row) {
        Pattern pattern = Pattern.compile("\\s");
        return pattern
                .splitAsStream(row)
                .mapToInt(Integer::valueOf);
    }

    private static Stream<String> loadInput() throws IOException, URISyntaxException {
        String fileName = "day02/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Files.lines(Paths.get(uri));
    }
}
