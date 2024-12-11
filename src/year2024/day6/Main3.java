package year2024.day6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main3 {
    static int GRIDSIZE = 130;
    static String file = "src/year2024/day6/input.txt";
    static Set<String> blocks = new HashSet<>();

    public static void main(String[] args) {
        PositionAndMap position = initiateMap();
        double start = System.nanoTime();
        System.out.println(startWalking(position));
        double end = System.nanoTime();

        System.out.println((end - start) + " nano seconds or " + (end - start) / 1000000 + "ms");
//printMap(position.map);
  //      System.out.println(blocks);
        System.out.println(blocks.size());
    }

    public static int startWalking(PositionAndMap positionAndMap) {
        int total = 0;
        String direction = "up";
        char[][] map = positionAndMap.map;
        int currentX = positionAndMap.x;
        int currentY = positionAndMap.y;
        while (true) {
            Step step = takeStep(direction, map, currentX, currentY);
            //Guard fallen of map
            if (step == null) {
                break;
            }
            if (step.object != '#') {
                //temporary mark the position in front of you as a blockage
                map[step.x][step.y] = '#';
                if(isLoop(new PositionAndMap(map, currentX, currentY),direction)){
                    blocks.add(step.x+"."+step.y);
                    total++;
                };
                map[step.x][step.y] = '.';
            }

            if (step.object != '#') {
                currentX = step.x;
                currentY = step.y;
            }

            if (step.object == '#') {
                direction = changeDirection(direction);
            }
        }
        return total;
    }

    public static boolean isLoop(PositionAndMap positionAndMap, String direction) {

        char[][] map = positionAndMap.map;
        int currentX = positionAndMap.x;
        int currentY = positionAndMap.y;

        List<String> passedTurns = new ArrayList<>();

        while (true) {
            //takes a step forward in the current direction. return the information of the step forward.
            Step step = takeStep(direction, map, currentX, currentY);
            //Guard falls of map
            if (step == null) {
                return false;
            }
            //step forward is no block, we can go forward.
            if (step.object != '#') {
                currentX = step.x;
                currentY = step.y;
            }

            if (step.object == '#') {
                if(direction == "up") {
                    if (passedTurns.contains(step.x+","+step.y)) {
                        return true;
                    }
                    passedTurns.add(step.x+","+step.y);
                }
                direction = changeDirection(direction);
            }
        }
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
                int position = line.indexOf("^");
                if (position != -1) {
                    x = i;
                    y = position;
                }
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

