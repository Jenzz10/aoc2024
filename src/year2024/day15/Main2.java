package year2024.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main2 {
    static int GRIDSIZE_X = 8;
    static int GRIDSIZE_Y = 8;
    static String file = "src/year2024/day15/input.txt";

    static char[][] map;

    static char[] moves;

    static int robot_X;
    static int robot_Y;


    public static void main(String[] args) throws IOException {
        initiateMap();
      //  printMap();
        double start = System.nanoTime();
        startWalking();
        System.out.println("GPS : " + findGPS());
        double end = System.nanoTime();
        System.out.println((end - start) / 1000000 + "ms");
    }

    private static int findGPS() {
        int total = 0;
        for (int i = 0; i < GRIDSIZE_X; i++) {
            for (int j = 0; j < GRIDSIZE_Y; j++) {
                if (map[i][j] == '[') {
                    total += ((i * 100) + j);
                }
            }
        }
        return total;

    }

    private static void startWalking() {

        for (int i = 0; i < moves.length; i++) {
            char move = moves[i];
            if (canMove(robot_X, robot_Y, move)) {
                move(move, robot_X, robot_Y);
                changeRobotPosition(move);
            }
        }
    }

    private static void changeRobotPosition(char move) {
        if (move == '<') {
            robot_Y -= 1;
        }
        if (move == '>') {
            robot_Y += 1;
        }
        if (move == '^') {
            robot_X -= 1;
        }
        if (move == 'v') {
            robot_X += 1;
        }
    }

    private static void move(char move, int x, int y) {
        if (move == '<') {
            if (map[x][y - 1] != '.') {
                move(move, x, y - 1);
            }
            map[x][y - 1] = map[x][y];
            map[x][y] = '.';
        }
        if (move == '>') {
            if (map[x][y + 1] != '.') {
                move(move, x, y + 1);
            }
            map[x][y + 1] = map[x][y];
            map[x][y] = '.';
        }
        if (move == '^') {
            if (map[x - 1][y] == '[' || map[x - 1][y] == ']') {
                int offset = map[x - 1][y] == '[' ? +1 : -1;
                move(move, x - 1, y);
                move(move, x - 1, y + offset);
            }
            map[x - 1][y] = map[x][y];
            map[x][y] = '.';
        }
        if (move == 'v') {
            if (map[x + 1][y] == '[' || map[x + 1][y] == ']') {
                int offset = map[x + 1][y] == '[' ? +1 : -1;
                move(move, x + 1, y);
                move(move, x + 1, y + offset);
            }
            map[x + 1][y] = map[x][y];
            map[x][y] = '.';
        }
    }


    private static boolean canMove(int robotX, int robotY, char move) {
        //When going up or down we should see if a piramide is starting to build.

        if (move == '<') {
            //there must be 1 space open to the left in order to be able to move
            for (int i = robotY - 1; i > 0; i--) {
                if (map[robotX][i] == '.') {
                    return true;
                }
                if (map[robotX][i] == '#') {
                    return false;
                }
            }
        } else if (move == '>') {
            for (int i = robotY + 1; i < GRIDSIZE_Y; i++) {
                if (map[robotX][i] == '.') {
                    return true;
                }
                if (map[robotX][i] == '#') {
                    return false;
                }
            }
        } else if (move == '^') {
            for (int i = robotX - 1; i > 0; i--) {
                if (map[i][robotY] == '.') {
                    return true;
                }
                if (map[i][robotY] == '#') {
                    return false;
                }
                if (map[i][robotY] == '[' || map[i][robotY] == ']') {
                    //We can do like the
                    if (map[i][robotY] == '[') {
                        return canMove(i, robotY, move) && canMove(i, robotY + 1, move);
                    } else {
                        return canMove(i, robotY, move) && canMove(i, robotY - 1, move);
                    }
                }
            }
        } else {
            for (int i = robotX + 1; i < GRIDSIZE_X; i++) {
                if (map[i][robotY] == '.') {
                    return true;
                }
                if (map[i][robotY] == '#') {
                    return false;
                }
                if (map[i][robotY] == '[' || map[i][robotY] == ']') {
                    //We can do like the
                    if (map[i][robotY] == '[') {
                        return canMove(i, robotY, move) && canMove(i, robotY + 1, move);
                    } else {
                        return canMove(i, robotY, move) && canMove(i, robotY - 1, move);
                    }
                }
            }
        }
        return false;
    }

    public static void initiateMap() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String moveString = "";
        String line = reader.readLine();
        map = new char[line.length() * 2][line.length()];
        GRIDSIZE_X = line.length();
        GRIDSIZE_Y = line.length() * 2;
        for (int i = 0; i < GRIDSIZE_X; i++) {
            line = line.replace("#", "##").replace("O", "[]").replace(".", "..").replace("@", "@.");
            map[i] = line.toCharArray();
            if (line.contains("@")) {
                robot_X = i;
                robot_Y = line.indexOf("@");
            }
            line = reader.readLine();
        }
        while (line != null) {
            moveString += line;
            line = reader.readLine();
        }
        moves = moveString.toCharArray();
    }


    public static void printMap() {
        for (int i = 0; i < GRIDSIZE_X; i++) {
            System.out.println("" + new String(map[i]));
        }
    }

    record position(int x, int y) {
    }

}

