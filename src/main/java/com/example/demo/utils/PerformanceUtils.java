package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PerformanceUtils {
    public static void measureRuntime(Runnable task, String taskName) {
        long startTime = System.nanoTime();

        try {
            task.run();
        } finally {
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            log.info(String.format("Execution time for %s: %d ms%n", taskName, duration / 1000000));
        }
    }
}
