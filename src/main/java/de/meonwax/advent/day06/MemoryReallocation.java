package de.meonwax.advent.day06;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MemoryReallocation {

    private static Pattern PATTERN = Pattern.compile("\\s");

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<Integer> banks = loadInput();
        Map<List<Integer>, Integer> seen = new HashMap<>();

        int cycles;
        for (cycles = 0; !seen.containsKey(banks); cycles++) {
            seen.put(banks, cycles);
            banks = reallocation(banks);
        }

        int loopSize = cycles - seen.get(banks);

        System.out.println("Cycles: " + cycles);
        System.out.println("Loop length: " + loopSize);
    }

    private static List<Integer> reallocation(List<Integer> banks) {
        int blocks = Collections.max(banks);
        int bank = banks.indexOf(blocks);

        banks.set(bank, 0);

        for (int i = 1; i <= blocks; i++) {
            int index = (bank + i) % banks.size();
            banks.set(index, banks.get(index) + 1);
        }

        return banks;
    }

    private static List<Integer> loadInput() throws IOException, URISyntaxException {
        String fileName = "day06/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return PATTERN
                .splitAsStream(
                        new String(Files.readAllBytes(Paths.get(uri))))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}
