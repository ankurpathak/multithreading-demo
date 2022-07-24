package com.github.ankurpathak;

import java.util.concurrent.*;

public class ScatterGatherPatternCountDownLatch  {
    public static void main(String[] args) throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(4);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        CountDownLatch latch = new CountDownLatch(3);

        executor.submit(new Task1(3, latch));
        executor.submit(new Task1(5, latch));
        executor.submit(new Task1(10, latch));



        latch.await(7, TimeUnit.SECONDS);



        System.out.println("completed");

        executor.shutdown();
        scheduler.shutdown();
    }
}




class Task1 implements Callable<Integer> {
    private final int val;
    private final CountDownLatch latch;


    public Task1(int val, CountDownLatch latch){
        this.val = val;
        this.latch = latch;
    }


    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(val);
        System.out.println("Completed "+ val);
        try{}finally {
            latch.countDown();
        }
        return val;
    }
}