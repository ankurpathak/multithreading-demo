package com.github.ankurpathak;

import java.util.concurrent.*;

public class EvenOddAlternatePrinter {
    public static void main(String[] args) {
        Counter counter = new Counter(1);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Future<?> even = executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()){
                counter.printEven();
            }
            return true;
        });

        Future<?> odd = executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()){
                counter.printOdd();
            }
            return true;
        });

        scheduler.schedule(() -> {
            even.cancel(true);
            odd.cancel(true);
            System.out.println("Stopped");
        }, 10, TimeUnit.SECONDS);


        executor.shutdown();
        scheduler.shutdown();

    }
}


class Counter {
    private int count;

    public Counter(int count){
        this.count = count;
    }


    synchronized public void printEven() throws InterruptedException{
        while(count % 2 != 0){
            wait();
        }
        System.out.println("Even "+ count);
        count++;
        notifyAll();
    }


    synchronized public void printOdd() throws InterruptedException{
        while(count % 2 != 1){
            wait();
        }
        System.out.println("Odd "+ count);
        count++;
        notifyAll();
    }
}
