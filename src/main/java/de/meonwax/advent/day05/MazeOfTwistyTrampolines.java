package de.meonwax.advent.day05;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MazeOfTwistyTrampolines {

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Maze steps part one: " + getNumberOfStepsOne(loadInput()));
        System.out.println("Maze steps part two: " + getNumberOfStepsTwo(loadInput()));
    }

    private static int getNumberOfStepsOne(int[] instructions) {
        int steps = 0;
        int address = 0;
        while (address < instructions.length) {
            address += instructions[address]++;
            steps++;
        }
        return steps;
    }

    private static int getNumberOfStepsTwo(int[] instructions) {
        int steps = 0;
        int address = 0;
        while (address < instructions.length) {
            int offset = instructions[address];
            instructions[address] += offset >= 3 ? -1 : 1;
            address += offset;
            steps++;
        }
        return steps;
    }

    private static int[] loadInput() throws IOException, URISyntaxException {
        String fileName = "day05/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Files.lines(Paths.get(uri)).mapToInt(Integer::valueOf).toArray();
    }
}
