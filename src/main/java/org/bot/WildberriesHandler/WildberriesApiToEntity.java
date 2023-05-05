package org.bot.WildberriesHandler;

import org.bot.Entities.Order;
import org.bot.Protection;
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
        List<Order> orderFBSList = WildberriesApiToEntity.getOrders_FBS();
        List<Order> orderList = new ArrayList<>();

        String path = Protection.fileFBO;
        FileReader fr = new FileReader(path);
        Scanner scan = new Scanner(fr);
        List<String> arrFBO = new ArrayList<>();
        while (scan.hasNextLine()) {
            arrFBO.add(scan.nextLine());
        }
        fr.close();

        String pathFBS = Protection.fileFBS;
        FileReader frFBS = new FileReader(pathFBS);
        Scanner scanFBS = new Scanner(frFBS);
        List<String> arrFBS = new ArrayList<>();
        while (scanFBS.hasNextLine()) {
            arrFBS.add(scanFBS.nextLine());
        }
        frFBS.close();

        for (int i = 0; i < jsonArray.length(); i++) {
            String srid;
            try {
                srid = jsonArray.getJSONObject(i).getString("srid");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //ищем только новые заказы
            if (!arrFBO.contains(srid) && (!arrFBS.contains(srid))) {
                arrFBO.add(srid);
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
        String allOrders = "";

        for (String str : arrFBO) {
            allOrders += str + "\n";
        }
        nFile.write(allOrders);

        nFile.close();
        return orderList;
    }

    static List<Order> getOrders_FBS() throws IOException {
        JSONArray jsonArray = WildberriesApi.getOrders_FBS();
        List<Order> orderList = new ArrayList<>();
        String path = Protection.fileFBS;
        FileReader fr = new FileReader(path);
        Scanner scan = new Scanner(fr);
        List<String> arr = new ArrayList<>();
        while (scan.hasNextLine()) {
            arr.add(scan.nextLine());
        }
        fr.close();

        for (int i = 0; i < jsonArray.length(); i++) {
            String srid;
            try {
                srid = jsonArray.getJSONObject(i).getString("rid");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //ищем только новые заказы
            if (!arr.contains(srid)) {
                arr.add(srid);
                String article;
                double price;
                try {
                    article = jsonArray.getJSONObject(i).getString("article");
                    double totalPrice = jsonArray.getJSONObject(i).getDouble("convertedPrice");
                    price = totalPrice / 100.0;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Order order = new Order(article, price);
                orderList.add(order);
            }
        }
        FileWriter nFile = new FileWriter(path);
        String allOrders = "";

        for (String str : arr) {
            allOrders += str + "\n";
        }
        nFile.write(allOrders);

        nFile.close();
        return orderList;
    }

    public static String getStocks() throws IOException {
        JSONArray jsonArray = WildberriesApi.getStocks();
        List<String> arr = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String name;
            String count;
            String warehouseName;
            try {
                name = jsonArray.getJSONObject(i).getString("supplierArticle");
                count = jsonArray.getJSONObject(i).getString("quantity");
                warehouseName = jsonArray.getJSONObject(i).getString("warehouseName");
                System.out.println(name+"  "+count+" "+warehouseName);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return "Конец";
    }
}
