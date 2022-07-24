package com.github.ankurpathak;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

public class ProducerConsumerUsingWaitAndNotify {
    public static void main(String[] args) {
        MyBlockingQueue1<Long> queue = new MyBlockingQueue1<>(10);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Future<?> producer = executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(createItem());
            }
            return true;
        });

        Future<?> consumer = executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                processItem(queue.take());
            }
            return true;
        });

        scheduler.schedule(() ->

        {
            producer.cancel(true);
            consumer.cancel(true);
            System.out.println("Stopped");
        }, 10, TimeUnit.SECONDS);


        executor.shutdown();
        scheduler.shutdown();
    }


    public static long createItem() {
        long item = new Random().nextLong(1, 1000);
        System.out.println("Item produced " + item);
        return item;
    }

    public static void processItem(long item) {
        System.out.println("Item consumed " + item);
    }
}

class MyBlockingQueue1<T> {
    private int size;
    final private Queue<T> queue;


    MyBlockingQueue1(int size) {
        this.size = size;
        this.queue = new LinkedList<>();
    }


    synchronized public T take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T item = queue.poll();
        notifyAll();
        return item;
    }

    synchronized public void put(T t) throws InterruptedException {
        while (size == queue.size()) {
            wait();
        }
        queue.offer(t);
        notifyAll();
    }
}
