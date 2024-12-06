package day6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main2 {
    static int GRIDSIZE = 10;
    static int stepsRight = 0;
    static int stepsLeft = 0;
    static int stepsUp = 0;
    static int stepsDown = 0;

    public static void main(String[] args) {
        PositionAndMap position = initiateMap();
        System.out.println("X,Y  (" + position.x() + "," + position.y() + ")");

        System.out.println("TOTAL");
        System.out.println(startWalking(position));
    }

    public static int startWalking(PositionAndMap positionAndMap) {
        int totalCircles = 0;
        boolean outOfMap = false;
        String direction = "up";
        char[][] map = positionAndMap.map;
        int currentX = positionAndMap.x;
        int currentY = positionAndMap.y;

        boolean startedTurning = false;


        while (!outOfMap) {
            Step item = takeStep(direction, map, currentX, currentY);
            //Guard fallen of map
            if (item == null) {
                outOfMap = true;
                break;
            }

            //We need at least 1 obstacle arround to guard to create a circular pattern.
            String obstacleSide = hasObstacleArroundGuard(map, currentX, currentY);
            if (obstacleSide != null) {
                totalCircles += searchPattern(map, obstacleSide, currentX, currentY);
            }

            if (item.object == '.' || item.object == '^') {
                /*if (direction.equals("up") || direction.equals("down")) {
                    map[item.x][item.y] = '|';
                } else {
                    map[item.x][item.y] = '-';
                }*/
                currentX = item.x;
                currentY = item.y;
            }/*
            if (item.object == '|' || item.object == '-') {
                currentX = item.x;
                currentY = item.y;
                map[item.x][item.y] = '+';
            }
            if (item.object == '+') {
                currentX = item.x;
                currentY = item.y;
            }*/

            //rotate direction, keep x,y
            if (item.object == '#') {
                direction = changeDirection(direction);
                //for debugging
            }
        }
        printMap(map);
        return totalCircles;
    }

    private static int searchPattern(char[][] map, String direction, int currentX, int currentY) {
        int numberOfPatternsFound = 0;
        int startX = currentX;
        int startY = currentY;
        int directionChanges = 1;
        int stepsTakenInDirection = 0;
        //start searching to the right.

        boolean anyMoreDirectionsToCheck = true;
        String currentDirection = changeDirection(direction);

        while (anyMoreDirectionsToCheck) {

            boolean obstructionPlaced = false;
            while(true) {
                Step s = takeStep(currentDirection, map, startX + stepsTakenInDirection, currentY);
                if(!obstructionPlaced && !hasObstacleNext(map, currentX, currentY, currentDirection)){
                    changeDirection(currentDirection);
                    obstructionPlaced = true;
                }
                if (s == null) {
                    //we have already place an obstruction,
                    if(obstructionPlaced)
                        anyMoreDirectionsToCheck = false;
                }
                if (s.object == '.' || s.object == '^') {
                    countSteps(direction);
                    currentX = s.x;
                    currentY = s.y;
                }

                if (s.object == '#') {
                    if (currentX == startX && currentY == startY) {
                        numberOfPatternsFound++;
                        break;
                    }
                    direction = changeDirection(direction);
                    directionChanges++;
                    //He does not retun home after an obstruction is placed. So continue search.
                    if (directionChanges > 3) {
                        break;
                    }
                }
            }




        }
        return 0;
    }

    private static void countSteps(String direction) {
        if (direction == "right") {
            stepsRight++;
        }
        if (direction == "up") {
            stepsUp++;
        }
        if (direction == "down") {
            stepsDown++;
        }
        if (direction == "left") {
            stepsLeft++;
        }
    }


    private static String hasObstacleArroundGuard(char[][] map, int x, int y) {
        if (hasObstacleNext(map, x, y, "up")) {
            return "up";
        }
        if (hasObstacleNext(map, x, y, "down")) {
            return "down";
        }
        if (hasObstacleNext(map, x, y, "right")) {
            return "right";
        }
        if (hasObstacleNext(map, x, y, "left")) {
            return "left";
        }
        return null;
    }

    private static boolean hasObstacleNext(char[][] map, int x, int y, String direction){
        if(direction.equals("up")){
           return map[x + 1][y] == '#';
        }
        if(direction.equals("down")){
            return map[x - 1][y] == '#';
        }
        if(direction.equals("right")){
            return map[x][y+1] == '#';
        }
        if(direction.equals("left")){
            return map[x][y-1] == '#';
        }
        return false;
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
            reader = new BufferedReader(new FileReader("src/day6/test.txt"));
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

}

