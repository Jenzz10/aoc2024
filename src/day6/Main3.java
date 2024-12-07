package day6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main3 {
    static int GRIDSIZE = 10;
    static String file = "src/day6/test.txt";
    static Set<guidedCoordination> blocks = new HashSet<>();
    static int steps = 0;

    public static void main(String[] args) {
        PositionAndMap position = initiateMap();
        double start = System.nanoTime();
        System.out.println(startWalking(position));
        double end = System.nanoTime();

        System.out.println((end - start) + " nano seconds or " + (end - start) / 1000000 + "ms");

        //System.out.println(blocks);
        System.out.println(blocks.size());
    }

    public static int startWalking(PositionAndMap positionAndMap) {
        boolean outOfMap = false;
        int total = 0;
        String direction = "up";
        char[][] map = positionAndMap.map;
        int currentX = positionAndMap.x;
        int currentY = positionAndMap.y;
        while (!outOfMap) {
            Step step = takeStep(direction, map, currentX, currentY);

            //Guard fallen of map
            if (step == null) {
                System.out.println(currentX + " " + currentY + " " + direction);
                outOfMap = true;
                break;
            }

            guidedCoordination c = null;

            if (step.object != '#') {
                c = checkLoop(new PositionAndMap(map, currentX, currentY), new guidedCoordination(step.x, step.y, direction), changeDirection(direction));
            }

            if (c != null) {
                blocks.add(c);
                total++;
            }

            if (step.object != '#') {
                currentX = step.x;
                currentY = step.y;
                steps++;
                if (steps % 10 == 0)
                    System.out.println(steps);
            }

            if (step.object == '#') {
                direction = changeDirection(direction);
            }
        }
        return total;
    }

    public static guidedCoordination checkLoop(PositionAndMap positionAndMap, guidedCoordination guard, String direction) {

        char[][] map = positionAndMap.map;
        int currentX = positionAndMap.x;
        int currentY = positionAndMap.y;

        List<guidedCoordination> alreadySeenGuards = new ArrayList<>();
       // alreadySeenGuards.add(guard);

        while (true) {
            Step item = takeStep(direction, map, currentX, currentY);
            //Guard fallen of map
            if (item == null) {
                return null;
            }
            if (alreadySeenGuards.contains(new guidedCoordination(item.x, item.y, direction))) {
                return guard;
            }
            alreadySeenGuards.add(new guidedCoordination(item.x, item.y, direction));

            if (item.object != '#') {
                currentX = item.x;
                currentY = item.y;
            }

            //If we come across the placed guard in the same direction, we are in a loop.
           /* if (guard.equals(new guidedCoordination(item.x, item.y, direction))) {
                return guard;
            }*/

            if (item.object == '#') {
                //if we see a coordinate again in the same direction, we are in a loop.
               /* if (alreadySeenGuards.contains(new guidedCoordination(item.x, item.y, direction))) {
                    return guard;
                }*/
                alreadySeenGuards.add(new guidedCoordination(item.x, item.y, direction));
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

    record coordination(int x, int y) {
    }

    record guidedCoordination(int x, int y, String direction) {
    }

}

