package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.DOMImplementation;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {

    private List<SleepingSession> sessions;

    @BeforeEach
    void setUp() {
        sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 15),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 8, 0),
                        SleepQuality.NORMAL),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 14, 30),
                        LocalDateTime.of(2025, 10, 3, 15, 20),
                        SleepQuality.NORMAL),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 30),
                        LocalDateTime.of(2025, 10, 4, 6, 20),
                        SleepQuality.BAD));
    }

    @Test
    void testCountSessions() {
        CountSessionsFunction func = new CountSessionsFunction();
        var result = func.apply(sessions);
        assertEquals(4L, result.getValue());
    }

    @Test
    void testCountSessionsEmpty() {
        CountSessionsFunction func = new CountSessionsFunction();
        var result = func.apply(List.of());
        assertEquals(0L, result.getValue());
    }

    @Test
    void testMinDuration() {
        MinDurationFunction func = new MinDurationFunction();
        var result = func.apply(sessions);
        assertEquals(50L, result.getValue());
    }

    @Test
    void testMinDurationSingle() {
        MinDurationFunction func = new MinDurationFunction();
        var result = func.apply(List.of(sessions.get(0)));
        assertEquals(585L, result.getValue());
    }

    @Test
    void testMaxDuration() {
        MaxDurationFunction func = new MaxDurationFunction();
        var result = func.apply(sessions);
        assertEquals(585L, result.getValue());
    }

    @Test
    void testMaxDurationTwo() {
        MaxDurationFunction func = new MaxDurationFunction();
        var result = func.apply(List.of(sessions.get(0), sessions.get(1)));
        assertEquals(585L, result.getValue());
    }

    @Test
    void testAvgDuration() {
        AvgDurationFunction func = new AvgDurationFunction();
        var result = func.apply(sessions);
        assertEquals(396.25, (Double) result.getValue());
    }

    @Test
    void testAvgDurationEmpty() {
        AvgDurationFunction func = new AvgDurationFunction();
        var result = func.apply(List.of());
        assertEquals(0.0, (Double) result.getValue());
    }

    @Test
    void testBadQualityCount() {
        BadQualityCountFunction func = new BadQualityCountFunction();
        var result = func.apply(sessions);
        assertEquals(1L, result.getValue());
    }

    @Test
    void testNoBadQualityCount() {
        BadQualityCountFunction func = new BadQualityCountFunction();
        var result = func.apply(List.of(sessions.get(0), sessions.get(1)));
        assertEquals(0L, result.getValue());
    }

    @Test
    void testSleeplessNight() {
        SleeplessNightFunction func = new SleeplessNightFunction();
        var result = func.apply(sessions);
        assertEquals(0L, result.getValue());
    }

    @Test
    void testOneSleeplessNight() {
        List<SleepingSession> daySessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 7, 0),
                        LocalDateTime.of(2025, 10, 1, 8, 0),
                        SleepQuality.NORMAL
                )
        );
        SleeplessNightFunction func = new SleeplessNightFunction();
        var result = func.apply(daySessions);
        assertEquals(1L, result.getValue());
    }

    @Test
    void testChronotypeOwl() {
        List<SleepingSession> owlSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 45),
                        LocalDateTime.of(2025, 10, 3, 9, 15),
                        SleepQuality.NORMAL
                )
        );
        ChronotypeFunction func = new ChronotypeFunction();
        var result = func.apply(owlSessions);
        assertEquals("Сова", result.getValue());
    }

    @Test
    void testChronotypeLark() {
        List<SleepingSession> larkSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 30),
                        SleepQuality.NORMAL
                )
        );
        ChronotypeFunction func = new ChronotypeFunction();
        var result = func.apply(larkSessions);
        assertEquals("Жаворонок", result.getValue());
    }


}