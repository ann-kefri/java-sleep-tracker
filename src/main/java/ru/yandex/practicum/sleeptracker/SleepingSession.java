package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final SleepQuality quality;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public SleepingSession(LocalDateTime startTime, LocalDateTime endTime, SleepQuality quality) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.quality = quality;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    public long getDurationMinutes() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    public boolean intersectsNight(LocalDateTime nightDate) {
        LocalDateTime nightStart = nightDate.withHour(0).withMinute(0);
        LocalDateTime nightEnd = nightDate.withHour(6).withMinute(0);
        return !(endTime.isBefore(nightStart) || startTime.isAfter(nightEnd));
    }

    public boolean isNightSession() {
        return intersectsNight(startTime.toLocalDate().atStartOfDay()) ||
                intersectsNight(startTime.toLocalDate().plusDays(1).atStartOfDay());
    }

    public static SleepingSession fromString(String line) {
        String[] parts = line.split(";");
        LocalDateTime start = LocalDateTime.parse(parts[0], formatter);
        LocalDateTime end = LocalDateTime.parse(parts[1], formatter);
        SleepQuality quality = SleepQuality.valueOf(parts[2]);
        return new SleepingSession(start, end, quality);
    }

    @Override
    public String toString() {
        return "SleepingSession{" +
                "startTime=" + startTime.format(formatter) +
                ", endTime=" + endTime.format(formatter) +
                ", quality=" + quality +
                '}';
    }


}
