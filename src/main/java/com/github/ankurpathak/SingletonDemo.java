package com.github.ankurpathak;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class SingletonDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(() -> {
            Singleton.getInstance();
        });

        executor.submit(() -> {
            Singleton.getInstance();
        });

        executor.shutdown();
    }

    private static class Singleton {
        private static Singleton instance;
        private Singleton(){}


        public static synchronized Singleton getInstance(){
            if(instance == null){
                synchronized (Singleton.class){
                    if(instance == null){
                        instance = new Singleton();
                        System.out.println("Instance Created");
                    }
                }
            }
            return instance;
        }
    }
}
