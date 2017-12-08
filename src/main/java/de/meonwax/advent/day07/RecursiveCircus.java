package de.meonwax.advent.day07;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecursiveCircus {

    private static final Pattern PATTERN_PROGRAM = Pattern.compile("([a-z]+) \\(([0-9]+)\\)( -> (.+))?");
    private static final Pattern PATTERN_CHILDREN = Pattern.compile(", ");

    public static void main(String[] args) throws IOException, URISyntaxException {
        Map<String, Program> programs = getPrograms(loadInput());
        System.out.println("Root program is: " + getRoot(programs).name);
        System.out.println("Unbalanced need to have weight of: " + getPartTwo(getRoot(programs)));
    }

    private static Map<String, Program> getPrograms(Stream<String> input) {

        // Determine all programs
        Map<String, Program> programs = input.map(RecursiveCircus::mapToProgram)
                .collect(Collectors.toMap(program -> program.name, Function.identity()));

        // Generate relations
        for (Map.Entry<String, Program> entry : programs.entrySet()) {
            Program program = entry.getValue();
            if (program.childrenNames != null) {
                for (String childName : program.childrenNames) {
                    Program child = programs.get(childName);
                    program.addChild(child);
                }
            }
        }

        return programs;
    }

    private static Program getRoot(Map<String, Program> programs) {
        Program root = programs.entrySet().iterator().next().getValue();
        while (root.parent != null) {
            root = root.parent;
        }
        return root;
    }

    private static int getPartTwo(Program root) {
        Result result = getBalanceResult(root);
        if (result.program != null) {
            int i = getPartTwo(result.program);
            if (i == 0) {
                System.out.println("Unbalanced program is: " + result.program.name);
                return result.program.weight + result.difference;
            }
            return i;
        }
        return 0;
    }

    private static Result getBalanceResult(Program root) {
        Map<Integer, Integer> occurrences = new HashMap<>();
        Map<Integer, Program> children = new HashMap<>();

        // Count occurrences of totals
        for (Program child : root.children) {
            int sumWeight = sumWeight(child);
            Integer o = occurrences.getOrDefault(sumWeight, 0);
            occurrences.put(sumWeight, ++o);
            children.put(sumWeight, child);
        }

        // Get unbalanced program and the difference
        Program program = null;
        int odd = 0;
        int normal = 0;

        for (Map.Entry<Integer, Integer> entry : occurrences.entrySet()) {
            if (entry.getValue() == 1) {
                program = children.get(entry.getKey());
                odd = entry.getKey();
            } else {
                normal = entry.getKey();
            }
        }

        Result result = new Result();
        result.program = program;
        result.difference = normal - odd;
        return result;
    }

    private static int sumWeight(Program root) {
        int total = 0;
        for (Program child : root.children) {
            total += sumWeight(child);
        }
        return total + root.weight;
    }

    private static Program mapToProgram(String row) {
        Matcher m = PATTERN_PROGRAM.matcher(row);
        Program program = new Program();
        if (m.find()) {
            program.name = m.group(1);
            program.weight = Integer.valueOf(m.group(2));
            String children = m.group(4);
            if (children != null) {
                program.childrenNames = PATTERN_CHILDREN.split(children);
            }
        }
        return program;
    }

    private static Stream<String> loadInput() throws IOException, URISyntaxException {
        String fileName = "day07/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Files.lines(Paths.get(uri));
    }

    static class Program {

        String name;
        int weight;
        String[] childrenNames;
        Program parent;
        List<Program> children = new ArrayList<>();

        void addChild(Program child) {
            child.parent = this;
            children.add(child);
        }
    }

    static class Result {
        Program program;
        int difference;
    }
}

