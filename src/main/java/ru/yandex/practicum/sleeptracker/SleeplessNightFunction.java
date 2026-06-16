package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleeplessNightFunction implements AnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }

        LocalDateTime firstSession = sessions.get(0).getStartTime();
        LocalDateTime lastSession = sessions.get(sessions.size() - 1).getEndTime();

        LocalDate startDate = firstSession.toLocalDate();
        LocalDate endtDate = lastSession.toLocalDate();

        if (firstSession.getHour() >= 12) {
            startDate = startDate.plusDays(1);
        }

        if (lastSession.getHour() < 6) {
            endtDate = endtDate.minusDays(1);
        }

        Set<LocalDate> allNights = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate, endtDate) + 1)
                .collect(Collectors.toSet());

        Set<LocalDate> nightsWithSleep = sessions.stream()
                .filter(SleepingSession::isNightSession)
                .flatMap(session -> {
                    Set<LocalDate> nights = new HashSet<>();
                    LocalDate nightStart = session.getStartTime().toLocalDate();
                    if (session.getStartTime().getHour() >= 0 && session.getStartTime().getHour() < 6) {
                        nightStart = nightStart.minusDays(1);
                    }
                    nights.add(nightStart);
                    if (session.getEndTime().getHour() >= 6 &&
                            session.getEndTime().getHour() < 24 &&
                            !session.getEndTime().toLocalDate().equals(nightStart)) {
                        nights.add(session.getEndTime().toLocalDate());
                    }
                    return nights.stream();
                })
                .collect(Collectors.toSet());
        allNights.removeAll(nightsWithSleep);
        return new SleepAnalysisResult("Количество бессонных ночей", (long) allNights.size());
    }
}
