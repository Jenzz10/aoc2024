package year2024.day10;

public class Day10 {
    public record Point(int x, int y) {}
    public record Grid(char[][] cells) {

        boolean isInGrid(int x, int y) {
            return y >= 0 && y < cells.length && x >= 0 && x < cells[y].length;
        }

        char at(Point p) {
            return cells[p.y][p.x];
        }

        int countInclines(int x, int y) {
            int altitude = cells[y][x] - '0';

            if (altitude == 9) {
                return 1;
            }

            int result = 0;

            if (isInGrid(x - 1, y) && cells[y][x - 1] != '.') {
                int nextAltitude = cells[y][x - 1] - '0';
                if (nextAltitude - altitude == 1) {
                    result += countInclines(x - 1, y);
                }
            }
            if (isInGrid(x + 1, y) && cells[y][x + 1] != '.') {
                int nextAltitude = cells[y][x + 1] - '0';
                if (nextAltitude - altitude == 1) {
                    result += countInclines(x + 1, y);
                }
            }
            if (isInGrid(x, y - 1) && cells[y - 1][x] != '.') {
                int nextAltitude = cells[y - 1][x] - '0';
                if (nextAltitude - altitude == 1) {
                    result += countInclines(x, y - 1);
                }
            }
            if (isInGrid(x, y + 1) && cells[y + 1][x] != '.') {
                int nextAltitude = cells[y + 1][x] - '0';
                if (nextAltitude - altitude == 1) {
                    result += countInclines(x, y + 1);
                }
            }

            return result;
        }

        int trailRating() {
            int result = 0;
            for(int y = 0; y < cells.length; y++) {
                for(int x = 0; x < cells[y].length; x++) {
                    if (cells[y][x] == '0') {
                        result += countInclines(x, y);
                    }
                }
            }

            return result;
        }

        static Grid parse(String s) {
            return new Grid(s.lines().map(String::toCharArray).toArray(char[][]::new));
        }
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        var grid = Grid.parse(REAL);
        int trailRating = grid.trailRating();
        long end = System.nanoTime();
        System.out.println("TrailRating: " + trailRating);

        System.out.println("Time spent: " + ( (end - start) / 1_000_000) + "ms");
    }

    public static final String TEST = """
            ...0...
            ...1...
            ...2...
            6543456
            7.....7
            8.....8
            9.....9
            """;
    public static final String TEST2 = """
            ..90..9
            ...1.98
            ...2..7
            6543456
            765.987
            876....
            987....
            """;

    public static final String TEST3 = """
            10..9..
            2...8..
            3...7..
            4567654
            ...8..3
            ...9..2
            .....01
            """;
    public static final String TEST4 = """
            89010123
            78121874
            87430965
            96549874
            45678903
            32019012
            01329801
            10456732
            """;

    public static final String TEST5 = """
            .....0.
            ..4321.
            ..5..2.
            ..6543.
            ..7..4.
            ..8765.
            ..9....
            """;

    public static final String TEST6 = """
            ..90..9
            ...1.98
            ...2..7
            6543456
            765.987
            876....
            987....
            """;
    public static final String TEST7 = """
            012345
            123456
            234567
            345678
            4.6789
            56789.
            """;

    public static final String REAL = """
                                         56566534304501010567543210985433456765656
                                         47887015213432123498698561876322019808765
                                         30996326012569765486787478901011326719454
                                         21345437867678894345695323010110458328323
                                         76543078998667801232184010923234569634912
                                         89012123456758910321073423874325678765801
                                         21010010969843210078934014965410987106702
                                         10121089878102102127125675894303201215010
                                         89439654389289043036001986765214106304321
                                         76548701274376558945432787652105687455901
                                         05456910465985467876013498943034796567812
                                         12367876544100389860323437410589892378743
                                         09450987033201276541410526323478901489656
                                         98541056124334985432569615410564876503456
                                         67632341034321650124378700198323498912987
                                         50721050125610743765234893267010567023478
                                         41890121036781812890103484786567832110569
                                         32103430549898905632112545691432945021654
                                         21234589658923456543007631210321876930323
                                         10045678767810167652108920101210967845610
                                         20176501658701078981017011234101456934791
                                         65281012349612569896543210965789321129887
                                         74392309658543478787612323872370110001236
                                         89654498767018762123205478961465200100145
                                         21763567346529456032118567450554321012298
                                         10892100256432376543089012329689876521347
                                         21090121187601789434308743018778989430456
                                         34789032098965438325219650898569876543265
                                         25676543107178721116778541767430987650101
                                         18789054236019980007865432054121456765567
                                         09652112345127872126957654123098387891438
                                         58543905439034565434548723210367294380129
                                         45678876098749654349659510301250193210034
                                         34589232105658701278765465432348984523895
                                         21090145234343210989852379821023479610796
                                         00203453456950197870141089760010568723687
                                         10112762367869086521236781051023478154554
                                         23234891056778076430145692342310879034563
                                         54965910189880125031036780123498960123676
                                         69876101098799034123321099834567254012985
                                         78765212345688765434210126765650123323476""";
}
