package de.meonwax.advent.day18;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Duet {

    private static final Pattern PATTERN = Pattern.compile("([a-z]+) ([a-z0-9-]+) ?([a-z0-9-]*)");

    private static Map<String, Long> registers = new HashMap<>();

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Last sound freq: " + run(parseCode(loadInput())));
    }

    private static Long run(List<Instruction> instructions) {
        Long lastSound = 0L;
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);
            switch (instruction.mnemonic) {
                case "snd":
                    lastSound = getValue(instruction.x);
                    break;
                case "set":
                    setValue(instruction.x, instruction.y);
                    break;
                case "add":
                    setValue(instruction.x, getValue(instruction.x) + getValue(instruction.y));
                    break;
                case "mul":
                    setValue(instruction.x, getValue(instruction.x) * getValue(instruction.y));
                    break;
                case "mod":
                    setValue(instruction.x, getValue(instruction.x) % getValue(instruction.y));
                    break;
                case "jgz":
                    if (getValue(instruction.x) > 0) {
                        i = (int) (i + getValue(instruction.y) - 1L);
                    }
                    break;
                case "rcv":
                    if (getValue(instruction.x) != 0) {
                        return lastSound;
                    }
                    break;
            }
        }
        return null;
    }

    private static List<Instruction> parseCode(String[] input) {
        List<Instruction> instructions = new ArrayList<>();
        for (String row : input) {
            Matcher m = PATTERN.matcher(row);
            if (m.find()) {
                Instruction instruction = new Instruction();
                instruction.mnemonic = m.group(1);
                instruction.x = m.group(2);
                instruction.y = m.group(3);
                instructions.add(instruction);
            }
        }
        return instructions;
    }

    private static Long getValue(Object s) {
        if (s instanceof Long) {
            return (Long) s;
        }
        try {
            return Long.parseLong((String) s);
        } catch (NumberFormatException e) {
            if (registers.containsKey(s)) {
                return registers.get(s);
            }
            return 0L;
        }
    }

    private static void setValue(String register, Object s) {
        registers.put(register, getValue(s));
    }

    private static String[] loadInput() throws IOException, URISyntaxException {
        String fileName = "day18/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Files.lines(Paths.get(uri)).toArray(String[]::new);
    }

    static class Instruction {

        String mnemonic;
        String x;
        String y;
    }
}

