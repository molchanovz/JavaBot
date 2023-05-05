package org.bot.WildberriesHandler;

import org.bot.Entities.Order;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WildberriesApiToMessage {
    public static String getOrders_All() throws IOException {
        /** получаем массив заказов **/
        List<Order> orderList = WildberriesApiToEntity.getOrders_All();
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements = new HashMap<>();
        double price = 0;
        for (Order el : orderList) {
            price+=el.getPrice();
            if (uniqElements.containsKey(el.getArticle())) {
                uniqElements.put(el.getArticle(), uniqElements.get(el.getArticle()) + 1);
            } else uniqElements.put(el.getArticle(), 1);
        }
        /** генерируем сообщения для отправки **/
        if(orderList.size()>0){
            String message = "Новый FBO заказ на вб:\n\n";
            for (Map.Entry entry : uniqElements.entrySet()) {
                message += "• " + entry + "шт\n\n";
            }
            String resultPrice = String.format("%.2f",price);
            message+="Всего: "+resultPrice+"₽";
            return message;
        }else
            return "";


    }

    public static String getOrders_FBS() throws IOException {
        /** получаем массив заказов **/
        List<Order> orderList = WildberriesApiToEntity.getOrders_FBS();
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements = new HashMap<>();
        double price = 0;
        for (Order el : orderList) {
            price+=el.getPrice();
            if (uniqElements.containsKey(el.getArticle())) {
                uniqElements.put(el.getArticle(), uniqElements.get(el.getArticle()) + 1);
            } else uniqElements.put(el.getArticle(), 1);
        }
        /** генерируем сообщения для отправки **/
        if(orderList.size()>0){
            String message = "Новый FBS заказ на вб:\n\n";
            for (Map.Entry entry : uniqElements.entrySet()) {
                message += "• " + entry + "шт\n\n";
            }
            String resultPrice = String.format("%.2f",price);
            message+="Всего: "+resultPrice+"₽";
            return message;
        }else
            return "";


    }
}
