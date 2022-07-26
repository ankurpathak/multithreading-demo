package com.github.ankurpathak;

import java.io.*;
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
        File file = File.createTempFile("Singleton", ".ser");
        ObjectOutputStream oOut = new ObjectOutputStream(new FileOutputStream(file));
        oOut.writeObject(Singleton.getInstance());
        ObjectInputStream oIn =  new ObjectInputStream(new FileInputStream(file));
        Singleton singleton = (Singleton) oIn.readObject();
        System.out.println(singleton);
    }

    private static class Singleton implements Serializable {
        private static Singleton instance;
        private Singleton()  {
            if(instance != null){
                throw new IllegalStateException();
            }
        }


        protected Object readResolve() {
            return instance;
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
