package year2024.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main3 {
    static String file = "src/year2024/day13/input.txt";

    private static List<game> games = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        double tokens = 0;
        findGames();

        for (game g : games) {
            System.out.println(g + " " + winPrize(g));
            tokens += winPrize(g);
        }

        System.out.println(tokens);


    }

    private static double winPrize(game g) {

        double maxTimesButtonA = Math.floor(Math.min(Math.abs(g.y / g.a.y), Math.abs(g.x / g.a.x)));
        double maxTimesButtonB = Math.floor(Math.min(Math.abs(g.x / g.b.x), Math.abs(g.y / g.b.y)));

        double minTimesToPressToReachX = g.x / Math.max(g.a.x, g.b.x);
        double minTimesToPressToReachY = g.y / Math.max(g.a.y, g.b.y);

        if (maxTimesButtonA + maxTimesButtonB < minTimesToPressToReachY || maxTimesButtonA + maxTimesButtonB < minTimesToPressToReachX) {
            return 0;
        }

        //check this
        double startValue = Math.min(minTimesToPressToReachX, minTimesToPressToReachY);

        double minTokens = Double.MAX_VALUE;

        for (double i = maxTimesButtonB; i >= 0; i--) {
            //Only continue if the rest is divisble by the number of button a.
            if ((g.x - (i * g.b.x)) % g.a.x == 0 && (g.y - (i * g.b.y)) % g.a.y == 0) {

                maxTimesButtonA = Math.floor(Math.min(((g.y - (i * g.b.y)) / g.a.y), ((g.x - (i * g.b.x)) / g.a.x)));
                for (double j = maxTimesButtonA; j >= 0; j--) {

                    if (g.x > (i * g.b.x) + (j * g.a.x) && g.y > (i * g.b.y) + (j * g.a.y)) {
                        break;
                    }

                    if (g.x == (i * g.b.x) + (j * g.a.x) && g.y == (i * g.b.y) + (j * g.a.y)) {
                        double tokens = (j * 3) + (i * 1);
                        if (tokens < minTokens) {
                            minTokens = tokens;
                        }
                    }
                }
            }
        }
        if (minTokens == Double.MAX_VALUE) {
            return 0;
        }

        return minTokens;
    }


    private static void findGames() throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(file));
        String buttonALine = reader.readLine();
        String buttonBLine = reader.readLine();
        String prizeLine = reader.readLine();
        String empty = reader.readLine();
        while (buttonALine != null) {
            Matcher m = Pattern.compile("(?<=\\+)(\\d+)")
                    .matcher(buttonALine);
            m.find();
            double a = Double.parseDouble(m.group());
            m.find();
            double b = Double.parseDouble(m.group());

            button buttonA = new button(a, b);
            m = Pattern.compile("(?<=\\+)(\\d+)")
                    .matcher(buttonBLine);
            m.find();
            a = Double.parseDouble(m.group());
            m.find();
            b = Double.parseDouble(m.group());
            button buttonb = new button(a, b);
            m = Pattern.compile("(?<==)(\\d+)")
                    .matcher(prizeLine);
            m.find();
            a = Double.parseDouble(m.group());
            m.find();
            b = Double.parseDouble(m.group());
            game game = new game(a + 10000000000000l, b + 10000000000000l, buttonA, buttonb);

            games.add(game);

            buttonALine = reader.readLine();
            buttonBLine = reader.readLine();
            prizeLine = reader.readLine();
            empty = reader.readLine();
        }
        reader.close();
    }


    record game(double x, double y, button a, button b) {

    }

    record button(double x, double y) {

    }
}