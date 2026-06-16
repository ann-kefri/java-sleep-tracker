package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    private final String description;
    private final Object value;

    public SleepAnalysisResult(String description, Object value) {
        this.description = description;
        if (value instanceof Integer) {
            this.value = ((Integer) value).longValue();
        } else {
            this.value = value;
        }
    }

    public String getDescription() {
        return description;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return description + ": " + value;
    }
}
