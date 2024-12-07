package day6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static int GRIDSIZE = 130;

    public static void main(String[] args) {
        PositionAndMap position = initiateMap();
        //System.out.println("X,Y  (" + position.x() + "," + position.y() + ")");

        //System.out.println("TOTAL");
        double start = System.nanoTime();
        System.out.println(startWalking(position));
        double end = System.nanoTime();
        //System.out.println();
        System.out.println((end - start)  + " nano seconds or " + (end-start)/1000000 + "ms");
    }

    public static int startWalking(PositionAndMap positionAndMap) {
        boolean outOfMap = false;
        int totalDistinctSteps = 1;
        String direction = "up";
        char[][] map = positionAndMap.map;
        int currentX = positionAndMap.x;
        int currentY = positionAndMap.y;
        while (!outOfMap) {
            Step item = takeStep(direction, map, currentX, currentY);
            //Guard fallen of map
            if (item == null) {
                System.out.println(currentX + " "+  currentY + " " + direction);
                outOfMap = true;
                break;
            }
            if (item.object == '.') {
                map[item.x][item.y] = 'X';
                currentX = item.x;
                currentY = item.y;
                totalDistinctSteps++;
            }

            if (item.object == '^') {
                map[item.x][item.y] = 'X';
                currentX = item.x;
                currentY = item.y;
            }
            //allready been here
            if (item.object == 'X') {
                currentX = item.x;
                currentY = item.y;
            }
            //rotate direction, keep x,y
            if (item.object == '#') {
                direction = changeDirection(direction);
            }
        }
        //printMap(map);
        return totalDistinctSteps;
    }

    private static String changeDirection(String direction) {
        if (direction == "up") {
            direction = "right";
        } else if (direction.equals("right")) {
            direction = "down";
        } else if (direction.equals("down")) {
            direction = "left";
        }else{
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
            reader = new BufferedReader(new FileReader("src/day6/input.txt"));
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

    public static void printMap(char[][] map){
        for(int i =0 ; i<GRIDSIZE; i++){
            System.out.println("" + new String(map[i]));
        }
    }

    record PositionAndMap(char[][] map, int x, int y) {
    }

    record Step(char object, int x, int y) {
    }

}

