package com.github.ankurpathak;

import java.util.Random;
import java.util.concurrent.*;

public class ProducerConsumerUsingBlockingQueue {
    public static void main(String[] args) {
        BlockingQueue<Long> queue = new ArrayBlockingQueue<>(10);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Future<?> producer = executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()){
                queue.put(createItem());
            }
            return true;
        });

        Future<?> consumer = executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()){
                processItem(queue.take());
            }
            return true;
        });

        scheduler.schedule(() -> {
           producer.cancel(true);
           consumer.cancel(true);
            System.out.println("Stopped");
        }, 10, TimeUnit.SECONDS);


        executor.shutdown();
        scheduler.shutdown();

    }


    public static long createItem(){
        long item =  new Random().nextLong(1, 1000);
        System.out.println("Item produced " +item);
        return item;
    }

    public static void processItem(long item){
        System.out.println("Item consumed " +item);
    }
}
