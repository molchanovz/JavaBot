package org.bot;


import org.json.JSONException;

import java.io.IOException;

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

