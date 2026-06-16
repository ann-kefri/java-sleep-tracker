package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChronotypeFunction implements AnalysisFunction {

    private ChronoType classifyNight(SleepingSession session) {
        if (!session.isNightSession()) return null;

        LocalTime bedTime = session.getStartTime().toLocalTime();
        LocalTime wakeTime = session.getEndTime().toLocalTime();

        if (bedTime.isAfter(LocalTime.of(23, 0)) &&
                wakeTime.isAfter(LocalTime.of(9, 0))) {
            return ChronoType.OWL;
        }

        if (bedTime.isBefore(LocalTime.of(22, 0)) &&
                wakeTime.isBefore(LocalTime.of(7, 0))) {
            return ChronoType.LARK;
        }

        return ChronoType.PIGEON;
    }

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Map<ChronoType, Long> counts = sessions.stream()
                .map(this::classifyNight)
                .filter(type -> type != null)
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));

        if (counts.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя", ChronoType.PIGEON);
        }

        ChronoType result = counts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(ChronoType.PIGEON);
        long maxCount = counts.getOrDefault(result, 0L);
        boolean isPigeon = counts.values().stream()
                .filter(c -> c.equals(maxCount))
                .count() > 1;
        if (isPigeon) {
            result = ChronoType.PIGEON;
        }

        String displayName = switch (result) {
            case OWL -> "Сова";
            case LARK -> "Жаворонок";
            case PIGEON -> "Голубь";
        };

        return new SleepAnalysisResult("Хронотип пользователя", displayName);
    }
}
