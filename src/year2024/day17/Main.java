package year2024.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String file = "src/year2024/day13/input.txt";

    static int REGISTER_A = 117440;

    static int REGISTER_B = 0;

    static int REGISTER_C = 0;
    //static int[] program = new int[]{0,1,5,4,3,0};
    static int[] program = new int[]{0,3,5,4,3,0};

    static String currentOutput = "";

    public static void main(String[] args) throws IOException {


        runProgram();
        System.out.println();
        System.out.println("REGISTER A " + REGISTER_A);
        System.out.println("REGISTER B " + REGISTER_B);
        System.out.println("REGISTER C " + REGISTER_C);
    }

    private static void runProgram() {
        int pointer = 0;
        while (pointer < program.length) {
            int operand = program[pointer + 1];
            switch (program[pointer]) {
                case 0:
                    REGISTER_A = adv(comboOperand(operand));
                    pointer += 2;
                    break;
                case 1:
                    bxl(operand);
                    pointer += 2;
                    break;
                case 2:
                    bst(comboOperand(operand));
                    pointer += 2;
                    break;
                case 3:
                    if (REGISTER_A == 0) {
                        pointer += 2;
                        break;
                    } else {
                        pointer = operand;
                    }
                    break;
                case 4:
                    bxc();
                    pointer += 2;
                    break;
                case 5:
                    out(comboOperand(operand));
                    pointer += 2;
                    break;
                case 6:
                    REGISTER_B = adv(comboOperand(operand));
                    pointer += 2;

                    break;
                case 7:
                    REGISTER_C = adv(comboOperand(operand));
                    pointer += 2;
                    break;
            }
        }
    }


    static int adv(int input) {
        return (int) (REGISTER_A / (Math.pow(2, input)));
    }

    static void bxl(int input) {
        REGISTER_B = REGISTER_B ^ input;
    }

    static void bst(int input) {
        REGISTER_B = input % 8;
    }

    static void bxc() {
        REGISTER_B = REGISTER_B ^ REGISTER_C;
    }

    static void out(int input) {
        String o = (input % 8) + ",";
        System.out.print(o);
    }

    static int comboOperand(int i) {
        if (i <= 3) {
            return i;
        }
        if (i == 4) {
            return REGISTER_A;
        }
        if (i == 5) {
            return REGISTER_B;
        }
        if (i == 6) {
            return REGISTER_C;
        }
        return 0;
    }
}