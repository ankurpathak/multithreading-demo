package com.github.ankurpathak;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerUsingLocks {
    public static void main(String[] args) {
        MyBlockingQueue<Long> queue = new MyBlockingQueue<>(10);
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

        scheduler.schedule(()->

        {
            producer.cancel(true);
            consumer.cancel(true);
            System.out.println("Stopped");
        },10,TimeUnit.SECONDS);


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

class MyBlockingQueue<T>{
    private int size;
    final private Queue<T> queue;
    final private Lock lock = new ReentrantLock(true);
    final private Condition empty = lock.newCondition();
    final private Condition full = lock.newCondition();


    MyBlockingQueue(int size){
        this.size = size;
        this.queue = new LinkedList<>();
    }


    public T take() throws InterruptedException{
        lock.lock();
        try {
            while (queue.isEmpty()){
                empty.await();
            }
            T item = queue.poll();
            full.signalAll();
            return item;
        }finally {
            lock.unlock();
        }
    }

    public void put(T t) throws InterruptedException{
        lock.lock();
        try {
            while (size == queue.size()){
                full.await();
            }
            queue.offer(t);
            empty.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
