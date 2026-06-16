package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MinDurationFunction implements AnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long minMinutes = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .min()
                .orElse(0);
        return new SleepAnalysisResult("Минимальная продолжительность сессии сна ", minMinutes);
    }
}
