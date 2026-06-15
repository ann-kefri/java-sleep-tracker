package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class SleepTrackerApp {

    private static final List<AnalysisFunction> analysisFunctions = new ArrayList<>();

    static {
        analysisFunctions.add(new CountSessionsFunction());
        analysisFunctions.add(new MinDurationFunction());
        analysisFunctions.add(new MaxDurationFunction());
        analysisFunctions.add(new AvgDurationFunction());
        analysisFunctions.add(new BadQualityCountFunction());
        analysisFunctions.add(new SleeplessNightFunction());
        analysisFunctions.add(new ChronotypeFunction());
    }

    public static void main(String[] args) {
        Path filePath = Paths.get(args[0]);

        try {
            List<SleepingSession> sessions = Files.lines(filePath)
                    .filter(line -> !line.trim().isEmpty())
                    .map(SleepingSession::fromString)
                    .toList();

            System.out.println("Загружено сессий сна: " + sessions.size());

            analysisFunctions.stream()
                    .map(func -> func.apply(sessions))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла" + e.getMessage());
        }

    }
}