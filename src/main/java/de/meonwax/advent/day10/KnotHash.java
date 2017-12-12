package de.meonwax.advent.day10;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class KnotHash {

    private static final Pattern PATTERN = Pattern.compile(",");

    public static void main(String[] args) throws IOException, URISyntaxException {
        int[] input = loadInput();
        int[] numbers = IntStream.range(0, 256).toArray();
        int skipSize = 0;
        int currentPosition = 0;
        for (int length : input) {
            for (int i = 0; i < (int) Math.floor(length / 2); i++) {
                int x = (currentPosition + i) % numbers.length;
                int y = (currentPosition + length - i - 1) % numbers.length;
                numbers[x] = swap(numbers[y], numbers[y] = numbers[x]);
            }
            currentPosition = (currentPosition + length + skipSize) % numbers.length;
            skipSize++;
        }
        System.out.println("Result part one: " + numbers[0] * numbers[1]);
    }

    private static int swap(int x, int y) {
        return x;
    }

    private static int[] loadInput() throws IOException, URISyntaxException {
        String fileName = "day10/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return PATTERN
                .splitAsStream(new String(Files.readAllBytes(Paths.get(uri))))
                .mapToInt(Integer::new).toArray();
    }
}
