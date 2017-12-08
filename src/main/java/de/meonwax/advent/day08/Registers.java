package de.meonwax.advent.day08;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registers {

    private static final Pattern PATTERN = Pattern.compile("([a-z]+) ([incde]+) ([-0-9]+) if ([a-z]+) ([!=<>]{1,2}) ([-0-9]+)");

    public static void main(String[] args) throws IOException, URISyntaxException {
        Set<Integer> maxValues = new HashSet<>();
        Map<String, Integer> registers = new HashMap<>();
        for (String row : loadInput()) {
            Matcher m = PATTERN.matcher(row);
            if (m.find()) {
                if (isCondition(registers, registers.get(m.group(4)), m.group(5), Integer.valueOf(m.group(6)))) {
                    String register = m.group(1);
                    Integer content = doOperation(registers.get(register), m.group(2), Integer.valueOf(m.group(3)));
                    registers.put(register, content);
                    maxValues.add(getMaxValue(registers));
                }
            }
        }
        System.out.println("Largest value at the end is: " + getMaxValue(registers));
        System.out.println("Largest value overall is: " + Collections.max(maxValues));
    }

    private static int getMaxValue(Map<String, Integer> registers) {
        return registers.values().stream().reduce(Integer::max).get();
    }

    private static int doOperation(Integer content, String operation, Integer value) {
        if (content == null) {
            content = 0;
        }
        switch (operation) {
            case "inc":
                return content + value;
            case "dec":
                return content - value;
            default:
                return content;
        }
    }

    private static boolean isCondition(Map<String, Integer> registers, Integer content, String operator, int value) {
        if (content == null) {
            content = 0;
        }
        switch (operator) {
            case ">":
                return content > value;
            case "<":
                return content < value;
            case "==":
                return content == value;
            case "!=":
                return content != value;
            case ">=":
                return content >= value;
            case "<=":
                return content <= value;
            default:
                return false;
        }
    }

    private static String[] loadInput() throws URISyntaxException, IOException {
        String fileName = "day08/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Files.lines(Paths.get(uri)).toArray(String[]::new);
    }
}

