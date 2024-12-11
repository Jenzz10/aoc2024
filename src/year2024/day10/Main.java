package year2024.day10;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static int GRIDSIZE = 57;
    static String file = "src/year2024/day10/input.txt";
    static Set<String> uniqueTrailHeads = new HashSet<>();


    public static void main(String[] args) {
        PositionAndMap position = initiateMap();
        double start = System.nanoTime();
        System.out.println(startWalking(position));
        double end = System.nanoTime();
    }

    public static int startWalking(PositionAndMap positionAndMap) {
        int total = 0;
        char[][] map = positionAndMap.map;
        for(int i=0; i<GRIDSIZE;i++){
            for (int j=0;j<GRIDSIZE;j++){
                if(map[i][j] == '0'){
                    uniqueTrailHeads = new HashSet<>();
                    int sub = findTrailHeads(positionAndMap.map, i, j, 0, "up") +
                            findTrailHeads(positionAndMap.map, i, j, 0, "down") +
                            findTrailHeads(positionAndMap.map, i, j, 0, "left") +
                            findTrailHeads(positionAndMap.map, i, j, 0, "right");
                    //System.out.println(uniqueTrailHeads);
                    //System.out.println(sub);
                    total += sub;
                }
            }
        }
        return total;
    }

    public static int findTrailHeads(char[][] map, int x, int y, int previousElevation, String direction) {
        Step s = takeStep(direction, map, x, y);
        if (s == null || s.object == '.' || uniqueTrailHeads.contains(s.x+","+s.y)) {
            return 0;
        }
        if (Integer.parseInt(String.valueOf(s.object)) - previousElevation != 1) {
            return 0;
        }
        if (Integer.parseInt(String.valueOf(s.object)) == 9) {
            System.out.println(s.x+","+s.y);
            uniqueTrailHeads.add(s.x+","+s.y);
            return 1;
        }
        return findTrailHeads(map, s.x, s.y, Integer.parseInt(String.valueOf(s.object)), "up") +
                findTrailHeads(map, s.x, s.y, Integer.parseInt(String.valueOf(s.object)), "down") +
                findTrailHeads(map, s.x, s.y, Integer.parseInt(String.valueOf(s.object)), "right") +
                findTrailHeads(map, s.x, s.y, Integer.parseInt(String.valueOf(s.object)), "left");
    }


    private static String changeDirection(String direction) {
        if (direction == "up") {
            direction = "right";
        } else if (direction.equals("right")) {
            direction = "down";
        } else if (direction.equals("down")) {
            direction = "left";
        } else {
            direction = "up";
        }

        return direction;
    }

    private static Step takeStep(String direction, char[][] map, int x, int y) {
        try {
            if (direction.equals("up")) {
                return new Step(map[x - 1][y], x - 1, y);
            }
            if (direction.equals("down")) {
                return new Step(map[x + 1][y], x + 1, y);
            }
            if (direction.equals("right")) {
                return new Step(map[x][y + 1], x, y + 1);
            }
            if (direction.equals("left")) {
                return new Step(map[x][y - 1], x, y - 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return null;
    }

    public static PositionAndMap initiateMap() {
        BufferedReader reader;
        char[][] map = new char[GRIDSIZE][GRIDSIZE];
        int x = 0;
        int y = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            int i = 0;
            while (line != null) {
                map[i] = line.toCharArray();
                i++;
                line = reader.readLine();
            }
            return new PositionAndMap(map, x, y);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printMap(char[][] map) {
        for (int i = 0; i < GRIDSIZE; i++) {
            System.out.println("" + new String(map[i]));
        }
    }

    record PositionAndMap(char[][] map, int x, int y) {
    }

    record Step(char object, int x, int y) {
    }

    record point(int x, int y) {
    }

    record guidedCoordination(int x, int y, String direction) {
    }

}

