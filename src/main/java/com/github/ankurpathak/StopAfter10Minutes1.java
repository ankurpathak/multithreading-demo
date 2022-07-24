package com.github.ankurpathak;

import java.util.concurrent.*;

public class StopAfter10Minutes1 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Future<?> future = executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()){
                System.out.println("Hello World");
            }
        });

        scheduler.schedule(() -> {
            future.cancel(true);
        }, 10, TimeUnit.SECONDS);

        executor.shutdown();
        scheduler.shutdown();
    }
}
