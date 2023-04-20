package org.bot.WildberriesHandler;

import org.bot.Entities.Order;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WildberriesApiToEntity {

    static List<Order> getOrders_All() throws IOException {
        JSONArray jsonArray = WildberriesApi.getOrders_All();
        List<Order> orderList = new ArrayList<>();

        String path = System.getProperty("user.dir")+"/src/main/java/org/bot/file.txt";
        FileReader fr= new FileReader(path);
        Scanner scan = new Scanner(fr);
        List<String> arr = new ArrayList<>();
        while (scan.hasNextLine()) {
            arr.add(scan.nextLine());
        }
        fr.close();

        for (int i = 0; i < jsonArray.length(); i++) {
            String srid;
            try {
                 srid = jsonArray.getJSONObject(i).getString("srid");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //ищем только новые заказы
            if(!arr.contains(srid)){
                arr.add(srid);
                String article;
                double price;
                try {
                    article = jsonArray.getJSONObject(i).getString("supplierArticle");
                    double totalPrice = jsonArray.getJSONObject(i).getDouble("totalPrice");
                    int discountPercent = jsonArray.getJSONObject(i).getInt("discountPercent");
                    price = totalPrice * (1 - discountPercent / 100.0);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Order order = new Order(article, price);
                orderList.add(order);
            }
        }
        FileWriter nFile = new FileWriter(path);
        String allOrders="";

        for (String str : arr) {
            allOrders+=str+"\n";
        }
        nFile.write(allOrders);

        nFile.close();
        return orderList;
    }
}
