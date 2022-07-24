package com.github.ankurpathak;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonDemoEnum {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(()-> {
            System.out.println(Singleton.INSTANCE);
            return true;
        });

        executor.submit(()-> {
            System.out.println(Singleton.INSTANCE);
            return true;
        });

    }

    private enum Singleton {
        INSTANCE;
        Singleton(){
            System.out.println("Instance Created");
        }

    }
}

