package year2024.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main5 {
    static String file = "src/year2024/day13/test.txt";

    static double EXT = 0l;

    private static List<game> games = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        double tokens = 0;
        findGames();

        for (game g : games) {
            //System.out.println(g + " " + winPrize(g));
            tokens += winPrize(g);
        }

        System.out.println(tokens);


    }

    private static double winPrize(game g) {

        double a = 0;
        double b = 0;

        /*
        g.x = b* g.b.x + a* g.a.x;
        g.y = b* g.b.y + a* g.a.y;

        g.x - a * g.a.x = b * g.b.x;

        (g.x / g.b.x) - ((a * g.a.x)/g.b.x) = b

         g.y = (g.x / g.b.x)  * g.b.y - (((a * g.a.x)/g.b.x) *g.b.y) + a * g.a.y

         */






        return 0;
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