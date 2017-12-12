package de.meonwax.advent.day11;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Good resource:
 * https://www.redblobgames.com/grids/hexagons/#coordinates-axial
 */
public class HexEd {

    private static final Pattern PATTERN = Pattern.compile(",");

    // Directions for a flat topped hexagon
    private static final String NORTH = "n";
    private static final String NORTH_EAST = "ne";
    private static final String SOUTH_EAST = "se";
    private static final String SOUTH = "s";
    private static final String SOUTH_WEST = "sw";
    private static final String NORTH_WEST = "nw";

    public static void main(String[] args) throws IOException, URISyntaxException {
        // Test cases
        System.out.println("ne,ne,ne:\t\t" + getSteps(PATTERN.split("ne,ne,ne")) + " steps");
        System.out.println("ne,ne,sw,sw:\t" + getSteps(PATTERN.split("ne,ne,sw,sw")) + " steps");
        System.out.println("ne,ne,s,s:\t\t" + getSteps(PATTERN.split("ne,ne,s,s")) + " steps");
        System.out.println("se,sw,se,sw,sw:\t" + getSteps(PATTERN.split("se,sw,se,sw,sw")) + " steps");

        // Puzzle input
        System.out.println("Puzzle input:\t" + getSteps(loadInput()) + " steps");
    }

    private static int getSteps(String[] input) {
        int[] axis = {0, 0};
        Set<Integer> seen = new HashSet<>();
        for (String direction : input) {
            switch (direction) {
                case NORTH:
                    axis[1]--;
                    break;
                case NORTH_EAST:
                    axis[0]++;
                    axis[1]--;
                    break;
                case SOUTH_EAST:
                    axis[0]++;
                    break;
                case SOUTH:
                    axis[1]++;
                    break;
                case SOUTH_WEST:
                    axis[0]--;
                    axis[1]++;
                    break;
                case NORTH_WEST:
                    axis[0]--;
                    break;
            }
            seen.add(getDistance(axis));
        }
        System.out.println("\nMax distance was: " + Collections.max(seen));
        return getDistance(axis);
    }

    private static int getDistance(int[] axis) {
        if ((axis[0] ^ axis[1]) > 0) {
            return Math.abs(axis[0] + axis[1]);
        } else {
            return Math.max(Math.abs(axis[0]), Math.abs(axis[1]));
        }
    }

    private static String[] loadInput() throws IOException, URISyntaxException {
        String fileName = "day11/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return PATTERN.split(new String(Files.readAllBytes(Paths.get(uri))));
    }
}

