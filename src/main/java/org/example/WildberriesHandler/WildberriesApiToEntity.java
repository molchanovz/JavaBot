package org.example.WildberriesHandler;

import org.example.Entities.Order;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WildberriesApiToEntity {

    static List<Order> getOrders_All() throws IOException {
        JSONArray jsonArray = WildberriesApi.getOrders_All();
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                String srid = jsonArray.getJSONObject(i).getString("srid");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            String article;
            double price;
            try {
                article = jsonArray.getJSONObject(i).getString("supplierArticle");
                double totalPrice = jsonArray.getJSONObject(i).getDouble("totalPrice");
                int discountPercent = jsonArray.getJSONObject(i).getInt("discountPercent");
                price = totalPrice * (1 - discountPercent / 100);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Order order = new Order(article, price);
            orderList.add(order);

        }
        return orderList;
    }
}
