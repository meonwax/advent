package de.meonwax.advent.day12;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitalPlumber {

    private static final Pattern PATTERN = Pattern.compile("\\d+");

    public static void main(String[] args) throws IOException, URISyntaxException {

        List<Program> programs = new ArrayList<>();
        for (String row : loadInput()) {
            Matcher m = PATTERN.matcher(row);
            if (m.find()) {
                Program program = new Program();
                program.id = Integer.valueOf(m.group(0));
                while (m.find()) {
                    program.partnerIds.add(Integer.valueOf(m.group(0)));
                }
                programs.add(program);
            }
        }

        List<Integer> counts = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        for (Program program : programs) {
            if (seen.add(program.id)) {
                int count = countPartners(programs, seen, program);
                counts.add(count);
            }
        }

        System.out.println("Programs in Group 0: " + counts.get(0));
        System.out.println("Total number of groups: " + counts.size());
    }

    private static int countPartners(List<Program> programs, Set<Integer> seen, Program program) {
        int count = 1;
        for (int id : program.partnerIds) {
            if (seen.add(id)) {
                count += countPartners(programs, seen, programs.get(id));
            }
        }
        return count;
    }

    private static class Program {
        int id;
        List<Integer> partnerIds = new ArrayList<>();
    }

    private static String[] loadInput() throws IOException, URISyntaxException {
        String fileName = "day12/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Files.lines(Paths.get(uri)).toArray(String[]::new);
    }
}

