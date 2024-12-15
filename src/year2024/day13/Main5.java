package year2024.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main5 {
    static String file = "src/year2024/day13/input.txt";

    static double EXT = 10000000000000l;

    private static List<game> games = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        double tokens = 0;
        findGames();

        for (game g : games) {
            tokens += winPrize(g);
        }

        //too high 100930526988697
        System.out.printf("%f", tokens);
        System.out.println();

    }

    private static double winPrize(game g) {

        double a = 0;
        double b = 0;

        double a1 = g.x / g.a.x; //8400/94 -> 89,362
        double a2 = g.b.x / g.a.x * -1; // 22/94 -> 0,234

        double b1 = g.a.y * (a2); //-7,957 b
        double b2 = g.a.y * (a1);

        b = (g.y - b2) / (b1 + g.b.y);

        b = round(b, 0);
        //b = Math.floor(b);
        a = (a2 * b) + a1;
        a = round(a, 0);

        if (g.x == (g.b.x * b) + (g.a.x * a) && g.y == (b * g.b.y) + (a * g.a.y)) {
            return (a * 3) + b;
        }
        return 0;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
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
            game game = new game(a + EXT, b + EXT, buttonA, buttonb);

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