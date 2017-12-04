package de.meonwax.advent.day04;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HighEntropyPassphrases {

    private static Pattern PATTERN = Pattern.compile("\\s");

    private static int ASCII_PRIMES[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Passphrases part one: " + getValidPartOne(loadInput()));
        System.out.println("Passphrases part two: " + getValidPartTwo(loadInput()));
    }

    private static long getValidPartOne(Stream<String> input) {
        return input
                .filter(row -> areUnique(getWords(row)))
                .count();
    }

    private static long getValidPartTwo(Stream<String> input) {
        return input
                .filter(row -> areNotAnagrams(getWords(row)))
                .count();
    }

    private static boolean areUnique(Stream<String> words) {
        final Set<String> seen = new HashSet<>();
        return words.allMatch(seen::add);
    }

    private static boolean areNotAnagrams(Stream<String> words) {
        final Set<Long> seen = new HashSet<>();
        return words
                .map(HighEntropyPassphrases::primeHash)
                .allMatch(seen::add);
    }

    private static long primeHash(String word) {
        long hash = 1;
        for (int i = 0; i < word.length(); i++) {
            hash *= ASCII_PRIMES[word.charAt(i) - 'a'];
        }
        return hash;
    }

    private static Stream<String> getWords(String row) {
        return PATTERN.splitAsStream(row);
    }

    private static Stream<String> loadInput() throws IOException, URISyntaxException {
        String fileName = "day04/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Files.lines(Paths.get(uri));
    }
}
