import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class NBAPlayerAA {
    private int id;
    private int year;
    private int rank;
    private int overallPick;
    private String team;
    private String player;
    private String college;
    private int yearsActive;
    private int games;
    private int minutesPlayed;
    private int points;
    private int totalRebounds;
    private int assists;
    private double fieldGoalPercentage;
    private double threePointPercentage;
    private double freeThrowPercentage;
    private double averageMinutesPlayed;
    private double pointsPerGame;
    private double averageTotalRebounds;
    private double averageAssists;
    private double winShares;
    private double winSharesPer48Minutes;
    private double boxPlusMinus;
    private double valueOverReplacement;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
    public NBAPlayerAA() {
    }

    public NBAPlayerAA(int id, int year, int rank, int overallPick, String team, String player, String college,
                       int yearsActive, int games, int minutesPlayed, int points, int totalRebounds, int assists,
                       double fieldGoalPercentage, double threePointPercentage, double freeThrowPercentage,
                       double averageMinutesPlayed, double pointsPerGame, double averageTotalRebounds, double averageAssists,
                       double winShares, double winSharesPer48Minutes, double boxPlusMinus, double valueOverReplacement) {
        this.id = id;
        this.year = year;
        this.rank = rank;
        this.overallPick = overallPick;
        this.team = team;
        this.player = player;
        this.college = college;
        this.yearsActive = yearsActive;
        this.games = games;
        this.minutesPlayed = minutesPlayed;
        this.points = points;
        this.totalRebounds = totalRebounds;
        this.assists = assists;
        this.fieldGoalPercentage = fieldGoalPercentage;
        this.threePointPercentage = threePointPercentage;
        this.freeThrowPercentage = freeThrowPercentage;
        this.averageMinutesPlayed = averageMinutesPlayed;
        this.pointsPerGame = pointsPerGame;
        this.averageTotalRebounds = averageTotalRebounds;
        this.averageAssists = averageAssists;
        this.winShares = winShares;
        this.winSharesPer48Minutes = winSharesPer48Minutes;
        this.boxPlusMinus = boxPlusMinus;
        this.valueOverReplacement = valueOverReplacement;
    }

    public int getID() {
        return id;
    }

    public int setID(int id) {
        return this.id = id;
    }

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(out);
        stream.writeInt(this.getID());
        stream.writeInt(this.getYear());
        stream.writeInt(this.getRank());
        stream.writeInt(this.getOverallPick());
        stream.writeUTF(this.getTeam());
        stream.writeUTF(this.getPlayer());
        stream.writeUTF(this.getCollege());
        stream.writeInt(this.getYearsActive());
        stream.writeInt(this.getGames());
        stream.writeInt(this.getMinutesPlayed());
        stream.writeInt(this.getPoints());
        stream.writeInt(this.getTotalRebounds());
        stream.writeInt(this.getAssists());
        stream.writeDouble(this.getFieldGoalPercentage());
        stream.writeDouble(this.getThreePointPercentage());
        stream.writeDouble(this.getFreeThrowPercentage());
        stream.writeDouble(this.getAverageMinutesPlayed());
        stream.writeDouble(this.getPointsPerGame());
        stream.writeDouble(this.getAverageTotalRebounds());
        stream.writeDouble(this.getAverageAssists());
        stream.writeDouble(this.getWinShares());
        stream.writeDouble(this.getWinSharesPer48Minutes());
        stream.writeDouble(this.getBoxPlusMinus());
        stream.writeDouble(this.getValueOverReplacement());
        return out.toByteArray();
    }

    public void deserialize(byte[] ba) throws IOException {
        var in = new ByteArrayInputStream(ba);
        var stream = new DataInputStream(in);

        int id = stream.readInt();
        int year = stream.readInt();
        int rank = stream.readInt();
        int overallPick = stream.readInt();
        String team = stream.readUTF();
        String player = stream.readUTF();
        String college = stream.readUTF();
        int yearsActive = stream.readInt();
        int games = stream.readInt();
        int minutesPlayed = stream.readInt();
        int points = stream.readInt();
        int totalRebounds = stream.readInt();
        int assists = stream.readInt();
        double fieldGoalPercentage = stream.readDouble();
        double threePointPercentage = stream.readDouble();
        double freeThrowPercentage = stream.readDouble();
        double averageMinutesPlayed = stream.readDouble();
        double pointsPerGame = stream.readDouble();
        double averageTotalRebounds = stream.readDouble();
        double averageAssists = stream.readDouble();
        double winShares = stream.readDouble();
        double winSharesPer48Minutes = stream.readDouble();
        double boxPlusMinus = stream.readDouble();
        double valueOverReplacement = stream.readDouble();

        this.id = id;
        this.year = year;
        this.rank = rank;
        this.overallPick = overallPick;
        this.team = team;
        this.player = player;
        this.college = college;
        this.yearsActive = yearsActive;
        this.games = games;
        this.minutesPlayed = minutesPlayed;
        this.points = points;
        this.totalRebounds = totalRebounds;
        this.assists = assists;
        this.fieldGoalPercentage = fieldGoalPercentage;
        this.threePointPercentage = threePointPercentage;
        this.freeThrowPercentage = freeThrowPercentage;
        this.averageMinutesPlayed = averageMinutesPlayed;
        this.pointsPerGame = pointsPerGame;
        this.averageTotalRebounds = averageTotalRebounds;
        this.averageAssists = averageAssists;
        this.winShares = winShares;
        this.winSharesPer48Minutes = winSharesPer48Minutes;
        this.boxPlusMinus = boxPlusMinus;
        this.valueOverReplacement = valueOverReplacement;

    }

    public int getBytes() throws IOException {
        int teamBytes = Short.BYTES + team.getBytes("UTF-8").length;
        int playerBytes = Short.BYTES + player.getBytes("UTF-8").length;
        int collegeBytes = Short.BYTES + college.getBytes("UTF-8").length;
        return 11 * Double.BYTES + 10 * Integer.BYTES + teamBytes + playerBytes + collegeBytes;
    }

    public static List<NBAPlayerAA> readCSV(String csvPath) {
        List<NBAPlayerAA> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line = br.readLine();
            while (br.ready()) {
                line = br.readLine();
                NBAPlayerAA player = lerLinha(line);
                list.add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static NBAPlayerAA lerLinha(String linha) {
        // Divide a linha em campos usando a vírgula como delimitador
        String[] fields = linha.split(",");

        // Verifica se o número de campos é menor do que o esperado e ajusta o array se
        // necessário
        if (fields.length < 24) {
            fields = Arrays.copyOf(fields, 24);
        }
        NBAPlayerAA playerAA = new NBAPlayerAA();
        try {
            // Preenche os campos com valores padrão se estiverem vazios ou nulos
            playerAA.setID(parseInteger(fields[0]));
            playerAA.setYear(parseInteger(fields[1]));
            playerAA.setRank(parseInteger(fields[2]));
            playerAA.setOverallPick(parseInteger(fields[3]));
            playerAA.setTeam(parseString(fields[4]));
            playerAA.setPlayer(parseString(fields[5]));
            playerAA.setCollege(parseString(fields[6]));
            playerAA.setYearsActive(parseInteger(fields[7]));
            playerAA.setGames(parseInteger(fields[8]));
            playerAA.setMinutesPlayed(parseInteger(fields[9]));
            playerAA.setPoints(parseInteger(fields[10]));
            playerAA.setTotalRebounds(parseInteger(fields[11]));
            playerAA.setAssists(parseInteger(fields[12]));
            playerAA.setFieldGoalPercentage(parseDouble(fields[13]));
            playerAA.setThreePointPercentage(parseDouble(fields[14]));
            playerAA.setFreeThrowPercentage(parseDouble(fields[15]));
            playerAA.setAverageMinutesPlayed(parseDouble(fields[16]));
            playerAA.setPointsPerGame(parseDouble(fields[17]));
            playerAA.setAverageTotalRebounds(parseDouble(fields[18]));
            playerAA.setAverageAssists(parseDouble(fields[19]));
            playerAA.setWinShares(parseDouble(fields[20]));
            playerAA.setWinSharesPer48Minutes(parseDouble(fields[21]));
            playerAA.setBoxPlusMinus(parseDouble(fields[22]));
            playerAA.setValueOverReplacement(parseDouble(fields[23]));
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter dados numéricos: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na linha: " + e.getMessage());
        }
        return playerAA;
    }

    // Método auxiliar para parse de Inteiro
    private static Integer parseInteger(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? Integer.parseInt(value.trim()) : 0;
        } catch (NumberFormatException e) {
            return 0; // Retorna 0 se o valor não puder ser convertido
        }
    }

    // Método auxiliar para parse de Double
    private static Double parseDouble(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? Double.parseDouble(value.trim()) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0; // Retorna 0.0 se o valor não puder ser convertido
        }
    }

    // Método auxiliar para parse de String
    private static String parseString(String value) {
        return (value != null && !value.trim().isEmpty()) ? value.trim() : "";
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "id=" + id + ", year=" + year + ", rank=" + rank + ", overallPick=" + overallPick
                + ", team=" + team + ", player=" + player + ", college=" + college + ", yearsActive=" + yearsActive
                + ", games=" + games + ", minutesPlayed=" + minutesPlayed + ", points=" + points + ", totalRebounds="
                + totalRebounds + ", assists=" + assists + ", fieldGoalPercentage=" + fieldGoalPercentage
                + ", threePointPercentage=" + threePointPercentage + ", freeThrowPercentage=" + freeThrowPercentage
                + ", averageMinutesPlayed=" + averageMinutesPlayed + ", pointsPerGame=" + pointsPerGame
                + ", averageTotalRebounds=" + averageTotalRebounds + ", averageAssists=" + averageAssists
                + ", winShares=" + winShares + ", winSharesPer48Minutes=" + winSharesPer48Minutes + ", boxPlusMinus="
                + boxPlusMinus + ", valueOverReplacement=" + valueOverReplacement + "]";
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getOverallPick() {
        return overallPick;
    }

    public void setOverallPick(int overallPick) {
        this.overallPick = overallPick;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        if (team.length() != 3) {
            throw new IllegalArgumentException("O time deve ter exatamente 3 caracteres.");
        }
        this.team = team;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getYearsActive() {
        return yearsActive;
    }

    public void setYearsActive(int yearsActive) {
        this.yearsActive = yearsActive;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTotalRebounds() {
        return totalRebounds;
    }

    public void setTotalRebounds(int totalRebounds) {
        this.totalRebounds = totalRebounds;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public double getFieldGoalPercentage() {
        return fieldGoalPercentage;
    }

    public void setFieldGoalPercentage(double fieldGoalPercentage) {
        this.fieldGoalPercentage = fieldGoalPercentage;
    }

    public double getThreePointPercentage() {
        return threePointPercentage;
    }

    public void setThreePointPercentage(double threePointPercentage) {
        this.threePointPercentage = threePointPercentage;
    }

    public double getFreeThrowPercentage() {
        return freeThrowPercentage;
    }

    public void setFreeThrowPercentage(double freeThrowPercentage) {
        this.freeThrowPercentage = freeThrowPercentage;
    }

    public double getAverageMinutesPlayed() {
        return averageMinutesPlayed;
    }

    public void setAverageMinutesPlayed(double averageMinutesPlayed) {
        this.averageMinutesPlayed = averageMinutesPlayed;
    }

    public double getPointsPerGame() {
        return pointsPerGame;
    }

    public void setPointsPerGame(double pointsPerGame) {
        this.pointsPerGame = pointsPerGame;
    }

    public double getAverageTotalRebounds() {
        return averageTotalRebounds;
    }

    public void setAverageTotalRebounds(double averageTotalRebounds) {
        this.averageTotalRebounds = averageTotalRebounds;
    }

    public double getAverageAssists() {
        return averageAssists;
    }

    public void setAverageAssists(double averageAssists) {
        this.averageAssists = averageAssists;
    }

    public double getWinShares() {
        return winShares;
    }

    public void setWinShares(double winShares) {
        this.winShares = winShares;
    }

    public double getWinSharesPer48Minutes() {
        return winSharesPer48Minutes;
    }

    public void setWinSharesPer48Minutes(double winSharesPer48Minutes) {
        this.winSharesPer48Minutes = winSharesPer48Minutes;
    }

    public double getBoxPlusMinus() {
        return boxPlusMinus;
    }

    public void setBoxPlusMinus(double boxPlusMinus) {
        this.boxPlusMinus = boxPlusMinus;
    }

    public double getValueOverReplacement() {
        return valueOverReplacement;
    }

    public void setValueOverReplacement(double valueOverReplacement) {
        this.valueOverReplacement = valueOverReplacement;
    }
}
