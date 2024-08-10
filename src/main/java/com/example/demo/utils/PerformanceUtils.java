package com.example.demo.utils;

public class PerformanceUtils {
    public static void measureRuntime(Runnable task, String taskName) {
        long startTime = System.nanoTime();

        try {
            task.run();
        } finally {
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.printf("Execution time for %s: %d ms%n", taskName, duration / 1000000);
        }
    }
}
