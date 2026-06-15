package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class AvgDurationFunction implements AnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        double avgMinutes = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0);
        return new SleepAnalysisResult("Средняя продолжительность сна ", avgMinutes);
    }
}
