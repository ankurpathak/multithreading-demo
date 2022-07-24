package com.github.ankurpathak;

import java.util.concurrent.*;

public class StopAfter10Minutes {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future = executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()){
                System.out.println("Hello World");
            }
        });

        try {
            future.get(10, TimeUnit.SECONDS);
        }catch (InterruptedException | ExecutionException ignored){}
        catch (TimeoutException ex){
            future.cancel(true);
        }

        executor.shutdown();

    }
}
