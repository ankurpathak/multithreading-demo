package com.github.ankurpathak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ScatterGatherPattern  {
    public static void main(String[] args) throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(4);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        List<Task> tasks = Collections.synchronizedList(new ArrayList<>());

        tasks.add(new Task(3));
        tasks.add(new Task(5));
        tasks.add(new Task(10));

        executor.invokeAll(tasks, 7, TimeUnit.SECONDS);

        System.out.println("completed");

        executor.shutdown();
        scheduler.shutdown();
    }
}




class Task implements Callable<Integer> {
    private int val;

    public Task(int val){
        this.val = val;
    }


    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(val);
        System.out.println("Completed "+ val);
        return val;
    }
}