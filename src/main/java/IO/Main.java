package IO;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<Athlete> athletes = new ArrayList<>();

        Path fileIn = new File("resources/Biathlon Results.csv").toPath();
        try (BufferedReader reader = Files.newBufferedReader(fileIn)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                athletes.add(getAthletesFromFile(line));

            }
        } catch (IOException e) {
            System.err.println("IOException: " + e);
        }


        Map<Integer, Athlete> athletesByFinalResults = new TreeMap<>();
        for (Athlete athlete : athletes) {
            int missedShots = missedShotsNo(athlete.firstShootingRange) + missedShotsNo(athlete.secondShootingRange) + missedShotsNo(athlete.thirdShootingRange);
            System.out.println("\n" + "Athlete with ID " + athlete.getAthleteNumber() + " has a total of " + calculatePenalty(missedShots) + " seconds penalty");
            int finalTime = calculateFinalTime(convertTime(athlete.timeResult), calculatePenalty(missedShots));
            System.out.println("Final time for " + athlete.getAthleteName() + " is: " + finalTime + " seconds");
            athletesByFinalResults.put(finalTime, athlete);

             }

        Set<Map.Entry<Integer, Athlete>> entries = athletesByFinalResults.entrySet();
        Map.Entry<Integer, Athlete>[] entryArray = entries.toArray(new Map.Entry[entries.size()]);
        int i = 0;
        System.out.println("\n" + "The final standings are: ");
        for (i = 0; i < entries.size(); i++) {
            if (i == 0) {
                System.out.println("Winner - " + entryArray[0].getValue() + " " + formatTime((Integer) entryArray[0].getKey()));
            }
            else {
                if (i == 1) {
                    System.out.println("Runner-Up - " + entryArray[1].getValue() + " " + formatTime((Integer) entryArray[1].getKey()));
                }
                else {
                    if (i == 2) {
                        System.out.println("Third Place - " + entryArray[2].getValue() + " " + formatTime((Integer) entryArray[2].getKey()));
                    }
                    else System.out.println("Top 3 positions reached");
                }
            }

        }

    }

    public static Athlete getAthletesFromFile(String line) {
        String [] tokens = line.split(",");
        if (tokens.length != 7) {
            return null;
        }
        int athleteNumber = Integer.parseInt(tokens[0].trim());
        return new Athlete(athleteNumber, tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
    }

    public static int missedShotsNo(String shootingRange) {
        String failedAttempt = "o";
        return StringUtils.countMatches(shootingRange, failedAttempt);
    }

    public static int calculatePenalty(int noOfFailedAttempts) {
        return noOfFailedAttempts * 10;
    }


   public static int convertTime (String skiTime) {
        String [] chars = skiTime.split("");
        int dozenOfMinutes = Integer.parseInt(chars[0]);
        int minutes = Integer.parseInt(chars[1]);
        int dozenOfSeconds = Integer.parseInt(chars[3]);
        int seconds = Integer.parseInt(chars[4]);
        int skiTimeInSeconds = (dozenOfMinutes*10+minutes)*60 + dozenOfSeconds*10 + seconds;
        return skiTimeInSeconds;

   }

   public static String formatTime (Integer result) {
        int minutes = (int) Math.floor(result/60);
        int seconds = (int) result - minutes *60;
        String ret = minutes + ":" + seconds;
        return ret;
   }

   public static int calculateFinalTime (int skiTime, int penaltyTime) {
        return skiTime+penaltyTime;
   }


}
