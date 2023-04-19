package org.example.WildberriesHandler;

import org.example.Entities.Order;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WildberriesApiToMessage {
    public static String getOrders_All() throws IOException {
        List<Order> orderList = WildberriesApiToEntity.getOrders_All();
        HashMap<String, Integer> uniqElements = new HashMap<>();
        double price = 0;
        for (Order el : orderList) {
            price+=el.getPrice();
            if (uniqElements.containsKey(el.getArticle())) {
                uniqElements.put(el.getArticle(), uniqElements.get(el.getArticle()) + 1);
            } else uniqElements.put(el.getArticle(), 1);
        }
        String message = "Новый заказ на вб:\n\n";
        for (Map.Entry entry : uniqElements.entrySet()) {
            message += "• " + entry + "шт\n\n";
        }

        String resultPrice = String.format("%.2f",price);

        message+="Всего: "+resultPrice+"₽";
        return message;
    }
}
