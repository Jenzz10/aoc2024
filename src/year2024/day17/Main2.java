package year2024.day17;

import java.io.IOException;

public class Main2 {
    static String file = "src/year2024/day13/input.txt";

    static int REGISTER_A = 0;

    static int REGISTER_B = 0;

    static int REGISTER_C = 0;
    static int[] program = new int[]{2, 4, 1, 1, 7, 5, 0, 3, 1, 4, 4, 5, 5, 5, 3, 0};
    //static int[] program = new int[]{0, 3, 5, 4, 3, 0};

    static String currentOutput = "";
    //static String wantedOutput = "0,3,5,4,3,0,";
    static String wantedOutput = "2,4,1,1,7,5,0,3,1,4,4,5,5,5,3,0,";

    public static void main(String[] args) throws IOException {

        int i = 0;
        while (true) {
            currentOutput = "";
            if (i % 10000 == 0) {
                System.out.println(REGISTER_A);
            }
            runProgram();
            if (currentOutput.equals(wantedOutput)) {
                System.out.println(i);
                break;
            }
            i++;
            REGISTER_A = i;
        }

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
        currentOutput += o;
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