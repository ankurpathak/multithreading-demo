package com.github.ankurpathak;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ControlThreadOrder {


    private static final List<Integer> sequence = List.of(3,2,1);
    private static final Lock lock = new ReentrantLock();
    public static final Condition increment = lock.newCondition();
    public static final Condition execute = lock.newCondition();
    public  static final AtomicInteger activated = new AtomicInteger();
    private static final List<Thread> THREADS = new ArrayList<>();
    public static void main(String[] args) throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(3);
       // executor.submit(new Worker(1));
       // executor.submit(new Worker(2));
       // executor.submit(new Worker(3));
        new Thread(new FutureTask<>(new Worker(1))).start();
        new Thread(new FutureTask<>(new Worker(2))).start();
        new Thread(new FutureTask<>(new Worker(3))).start();
        for(Integer id: sequence){
            lock.lock();
            try{
                activated.set(id);
                increment.signalAll();
                while (activated.get() != 0){
                    execute.await();
                }
            }finally {
                lock.unlock();
            }
        }

        executor.shutdown();
    }


    private static class Worker implements Callable<Boolean> {
        private final Integer id;
        public Worker(int id){
            this.id = id;
        }
        @Override
        public Boolean call() throws Exception {
            lock.lock();
            try{
                while (id != activated.get()){
                    increment.await();
                }
                activated.set(0);
                execute.signalAll();
                System.out.println("Worked for thread "+ getID());
            }finally {
                lock.unlock();
            }

            return true;
        }

        public Integer getID() {
            return id;
        }
    }


}
