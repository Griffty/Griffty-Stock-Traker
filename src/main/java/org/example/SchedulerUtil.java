package org.example;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerUtil {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public static void scheduleDayleTaskAt(int hour, int minute, Runnable runnable) {
        long initialDelay = calculateInitialDelay(hour, minute);
        long period = TimeUnit.DAYS.toSeconds(1);

        scheduler.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);
    }

    private static long calculateInitialDelay(int targetHour, int targetMinute) {
        LocalTime now = LocalTime.now();
        LocalTime targetTime = LocalTime.of(targetHour, targetMinute);

        if (now.isAfter(targetTime)) {
            targetTime = targetTime.plusHours(24);
        }

        return Duration.between(now, targetTime).getSeconds();
    }

}
