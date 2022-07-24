package com.github.ankurpathak;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SingletonDemoBreakReflection {
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
      //  System.out.println(Singleton.class.getDeclaredConstructor().newInstance());
    }

    private static class Singleton implements Cloneable{
        private static Singleton instance;
        private Singleton()  {
            if(instance != null){
                throw new IllegalStateException();
            }
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

        @Override
        public Object clone() throws CloneNotSupportedException {
            //return super.clone();
            throw new CloneNotSupportedException();
        }
    }
}
