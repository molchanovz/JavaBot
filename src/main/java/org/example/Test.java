package org.example;


import org.example.WildberriesHandler.WildberriesApiToMessage;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws JSONException, IOException {

    }
}

class Animal {
    void voice(){
        System.out.println("Голос");
    }
}
class Cat extends Animal {
    @Override
    void voice() {
        super.voice();
    }
}

