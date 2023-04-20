package org.bot;


import org.bot.WildberriesHandler.WildberriesApiToMessage;
import org.json.JSONException;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws JSONException, IOException {

        String message = WildberriesApiToMessage.getOrders_All();

        System.out.println(message);


    }
}

