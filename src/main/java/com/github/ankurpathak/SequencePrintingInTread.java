package com.github.ankurpathak;

import java.util.concurrent.*;

public class SequencePrintingInTread {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Counter counter = new Counter();
        Future<Boolean> future1 = executor.submit(()->{
            while (!Thread.currentThread().isInterrupted()){
                counter.printOne();
            }
            return true;
        });

        Future<Boolean> future2 = executor.submit(()->{
            while (!Thread.currentThread().isInterrupted()){
                counter.printTwo();
            }
            return true;
        });

        Future<Boolean> future3 = executor.submit(()->{
            while (!Thread.currentThread().isInterrupted()){
                counter.printThree();
            }
            return true;
        });


        scheduler.schedule(()-> {
            future1.cancel(true);
            future2.cancel(true);
            future3.cancel(true);
        }, 2, TimeUnit.SECONDS);

        executor.shutdown();
        scheduler.shutdown();
    }





    private static class Counter {
        private volatile int count = 1;

        synchronized public void printOne() throws InterruptedException{
            while((count - 1) % 3 != 0){
                wait();
            }
            System.out.println("Thread 1: " + count);
            count++;
            notifyAll();
        }

        synchronized public void printTwo() throws InterruptedException{
            while((count - 2) % 3 != 0){
                wait();
            }
            System.out.println("Thread 2: " + count);
            count++;
            notifyAll();
        }

        synchronized public void printThree() throws InterruptedException{
            while((count - 3) % 3 != 0){
                wait();
            }
            System.out.println("Thread 3: " + count);
            count++;
            notifyAll();
        }
    }
}



