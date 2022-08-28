package com.github.ankurpathak;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SingletonDemoBreakSerialization {
    public static void main(String[] args) throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(() -> {
            Singleton.getInstance();
        });

        executor.submit(() -> {
            Singleton.getInstance();
        });

        executor.shutdown();
        System.out.println(Singleton.getInstance());

    }

    private static class Singleton implements Cloneable{
        private static Singleton instance;
        private Singleton()  {

        }


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
