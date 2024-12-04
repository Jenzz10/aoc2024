package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main2 {
    static int MAX = 140;

    public static void main(String[] args) {
        int total = 0;

        char[][] grid = new char[MAX][MAX];
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("src/day4/input.txt"));
            String line = reader.readLine();
            int index = 0;
            while (line != null) {
                grid[index] = line.toCharArray();
                line = reader.readLine();
                index++;
            }

            for (int i = 0; i < MAX; i++) {
                for (int j = 0; j < MAX; j++) {
                    if(grid[i][j] == 'A') {
                        if(checkDiagonal(grid, i, j, true)){
                           total += (checkDiagonal(grid, i,j, false) ? 1 : 0);
                        }

                    }
                }
            }
            System.out.println("TOTAL :::::");
            System.out.println(total);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkDiagonal(char[][] grid, int row, int column, boolean leftRight){
        if(row == 0 || row == (MAX -1) || column == 0 || column == (MAX -1) ){
            return false;
        }
        if(leftRight){
            return (grid[row - 1][column - 1] == 'M' && grid[row + 1][column + 1] == 'S') ||
                    (grid[row - 1][column - 1] == 'S' && grid[row + 1][column + 1] == 'M');
        }
        else{
            return (grid[row - 1][column + 1] == 'M' && grid[row + 1][column - 1] == 'S') ||
                    (grid[row - 1][column + 1] == 'S' && grid[row + 1][column - 1] == 'M');
        }

    }

    private static int checkNorthWest(char[][] grid, int row, int column, char c) {
        if(row < 3 || column < 3){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row - 1][column - 1],grid[row - 2][column - 2], grid[row - 3][column - 3 ])) ? 1 : 0;
    }

    private static int checkSouthWest(char[][] grid, int row, int column, char c) {
        if(row > (MAX -4) || column < 3){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row + 1][column - 1],grid[row + 2][column - 2], grid[row + 3][column - 3 ])) ? 1 : 0;

    }

    private static int checkSouthEast(char[][] grid, int row, int column, char c) {
        if(row > (MAX - 4) || column > (MAX - 4)){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row + 1][column + 1],grid[row + 2][column + 2], grid[row + 3][column + 3 ])) ? 1 : 0;
    }

    private static int checkNorthEast(char[][] grid, int row, int column, char c) {
        if(row < 3 || column > (MAX - 4)){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row - 1][column + 1],grid[row - 2][column + 2], grid[row - 3][column + 3 ])) ? 1 : 0;

    }
    private static String testXMAS(char X, char M, char A, char S){
        char[] test = new char[]{X,M,A,S};
        return String.valueOf(test);
    }
}