package year2024.day14;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
    static int TALL = 103;
    static int WIDTH = 101;
/*
    static int TALL = 7;
    static int WIDTH = 11;*/


    static String file = "src/year2024/day14/input.txt";
    static List<Robot> robots = new ArrayList<>();

    static String[][] map = new String[TALL][WIDTH];

    public static void main(String[] args) throws IOException {
        int total = 1;
        long start = System.currentTimeMillis();
        initiateMap();
        for (int i = 0; i < 500000; i++) {
            tick();
            if (findTree(i)) {
                printMap(map, i);
            }
        }
        long end = System.currentTimeMillis();
/*
        total *= countKwadrant(0, 0, ((TALL - 1) / 2) - 1, ((WIDTH - 1) / 2) - 1);
        total *= countKwadrant(0, ((WIDTH - 1) / 2) + 1, ((TALL - 1) / 2) - 1, WIDTH);
        total *= countKwadrant(((TALL - 1) / 2) + 1, 0, TALL, ((WIDTH - 1) / 2) - 1);
        total *= countKwadrant(((TALL - 1) / 2) + 1, ((WIDTH - 1) / 2) + 1, TALL, (WIDTH));
*/

        System.out.println(total);

        System.out.println("Took " + (end - start) + "ms");
    }

    private static boolean findTree(int i) {
        try {
            for (Robot r : robots) {
                int current = 0;
                for (Robot r2 : robots) {
                    if (r2.x == r.x && r2.y > r.y && r2.y < (r.y + 16)) {
                        current++;
                    }
                }
                if (current == 15) {
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }


    private static int countKwadrant(int x_left, int y_left, int x_right, int y_right) {
        int total = 0;
        for (Robot r : robots) {
            if (r.x >= x_left && r.x <= x_right
                    && r.y >= y_left && r.y <= y_right) {
                total++;
            }
        }
        return total;
    }

    private static void tick() {
        for (Robot r : robots) {
            takeStep(r);
        }
    }

    private static void takeStep(Robot r) {
        int newXPosition = r.x + r.speed_x;
        int newYPosition = r.y + r.speed_y;
        try {
            if (newXPosition >= TALL) {
                newXPosition = newXPosition - TALL;
            }
            if (newXPosition < 0) {
                newXPosition = TALL + newXPosition;
            }
            if (newYPosition >= WIDTH) {
                newYPosition = newYPosition - WIDTH;
            }
            if (newYPosition < 0) {
                newYPosition = WIDTH + newYPosition;
            }
            r.x = newXPosition;
            r.y = newYPosition;

        } catch (IndexOutOfBoundsException e) {

        }
    }

    public static void initiateMap() throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(file));
        //initialise map
        for (int i = 0; i < TALL; i++) {
            for (int j = 0; j < WIDTH; j++) {
                map[i][j] = " ";
            }
        }

        String line = reader.readLine();
        int i = 0;
        while (line != null) {
            line = line.replace("p=", "").replace("v=", "");
            String[] lineArray = line.split(" ");
            String[] positionArray = lineArray[0].split(",");
            String[] speedArray = lineArray[1].split(",");

            Robot r = new Robot(Integer.parseInt(positionArray[1]), Integer.parseInt(positionArray[0]), Integer.parseInt(speedArray[1]),
                    Integer.parseInt(speedArray[0]));
            robots.add(r);
            if (map[r.x][r.y] != " ") {
                map[r.x][r.y] = "" + (Integer.parseInt(map[r.x][r.y]) + 1);
            } else {
                map[r.x][r.y] = "1";
            }
            line = reader.readLine();
        }

    }

    public static void printMap(String[][] map, int x) throws IOException {
        String[][] localMap = new String[TALL][WIDTH];
        for (int i = 0; i < TALL; i++) {
            for (int j = 0; j < WIDTH; j++) {
                localMap[i][j] = " ";
            }
        }

        for (Robot r : robots) {
            localMap[r.x][r.y] = "X";
        }

        FileWriter fw = new FileWriter("src/year2024/day14/trees.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.newLine();
        bw.write(x + "");
        bw.newLine();
        bw.newLine();


        for (int i = 0; i < TALL; i++) {
            for (int j = 0; j < WIDTH; j++) {
                bw.write(localMap[i][j]);
            }
            bw.newLine();
        }
        bw.close();
    }

    record Step(char object, int x, int y) {

    }

    static class Robot {
        public int x, y, speed_x, speed_y;

        public Robot(int x, int y, int speed_x, int speed_y) {
            this.x = x;
            this.y = y;
            this.speed_x = speed_x;
            this.speed_y = speed_y;
        }
    }


}

