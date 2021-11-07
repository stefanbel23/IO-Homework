package IO;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {


    @Test
    public void testCsvInput() {
        //given
        String inputLine = "14,Dan Helciug,RO,28:47,xoxxx,oxxxo,xxxoox";


        //when
        Athlete result = Main.getAthletesFromFile(inputLine);

        //then
        assert result != null;
        assertEquals(14, result.getAthleteNumber());
        assertEquals("Dan Helciug", result.getAthleteName());
        assertEquals("RO", result.getCountryCode());
        assertEquals("28:47", result.getTimeResult());
    }
    @Test
    public void testAthleteNumberWithWhitespace() {
        //given
        String inputLine = "14  ,Dan Helciug,RO,28:47,xo xxx,o  xxxo,xxxoox ";
        //when
        Athlete result = Main.getAthletesFromFile(inputLine);
        //then
        assert result != null;
        assertEquals(14, result.getAthleteNumber());
    }

    @Test
    public void testMissedShotsNo() {
        //given
        String inputLine = "14,Dan Helciug,RO,28:47,xoxxx,oxxxo,xxxoox";
        //when
        Athlete athlete = Main.getAthletesFromFile(inputLine);
        int result = Main.missedShotsNo(athlete.getFirstShootingRange()) + Main.missedShotsNo(athlete.getSecondShootingRange()) + Main.missedShotsNo((athlete.getThirdShootingRange()));
        //then
        assertEquals(5, result);
    }

    @Test
    public void testTimeCalculation() {
        //given
        String inputLine = "14,Dan Helciug,RO,28:47,xoxxx,oxxxo,xxxoox";
        //when
        Athlete athlete = Main.getAthletesFromFile(inputLine);
        int skyTimeInSeconds = Main.convertTime(athlete.getTimeResult());
        //then
        assert skyTimeInSeconds != 0;
        assertEquals(1727, skyTimeInSeconds);
    }

    @Test
    public void testFinalResults(){
        //given
        String inputLine = "14,Dan Helciug,RO,28:47,xoxxx,oxxxo,xxxoox";
        //when
        Athlete athlete = Main.getAthletesFromFile(inputLine);
        int missedShots = Main.missedShotsNo(athlete.getFirstShootingRange()) + Main.missedShotsNo(athlete.getSecondShootingRange()) + Main.missedShotsNo((athlete.getThirdShootingRange()));
        int finalTime = Main.calculateFinalTime(Main.convertTime(athlete.getTimeResult()), Main.calculatePenalty(missedShots));
        //then
       assert finalTime != 0;
       assertEquals(1777, finalTime);

    }

}