package com.example.kickmyb;

public class Singleton {
    private static Singleton sSoleInstance;

    public String username;

    private Singleton(){}  //private constructor.

    public static Singleton getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new Singleton();
        }

        return sSoleInstance;
    }
}
