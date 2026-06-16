package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class CountSessionsFunction implements AnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long count = (long) sessions.size();
        return new SleepAnalysisResult("Общее количество сессий сна", count);
    }
}
