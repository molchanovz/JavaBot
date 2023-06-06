package org.bot.WildberriesHandler;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.bot.Entities.Order;
import org.bot.Protection;
import org.bot.components.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WildberriesApiToMessage {
    public static String getOrders_All() throws IOException {
        String date = Service.getDate(0);
        /** получаем массив заказов **/
        List<Order> orderList = WildberriesApiToEntity.getOrders_All(date);
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

    public static String getLastOrders() throws IOException {
        String date = Service.getDate(-24);
        /** получаем массив заказов **/
        List<Order> orderList = WildberriesApiToEntity.getLastDayOrders(date);
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements = new HashMap<>();
        double price = 0;
        for (Order el : orderList) {
            price+=el.getPrice();
            if (uniqElements.containsKey(el.getArticle())) {
                uniqElements.put(el.getArticle(), uniqElements.get(el.getArticle()) + 1);
            } else uniqElements.put(el.getArticle(), 1);
        }
        Workbook book = Service.createExcel_LastDayOrders();
        String file = Protection.lastOrdersWB;
        Sheet sheet = book.getSheet("Продажи Wildberries");
        int i = 1;
        if(orderList.size()>0){
            String message = "Заказы за вчера:\n\n";
            for (Map.Entry entry : uniqElements.entrySet()) {
                Row row0 = sheet.createRow(i++);
                Cell art1 = row0.createCell(0);
                art1.setCellValue(String.valueOf(entry.getKey()));
                Cell art2 = row0.createCell(1);
                art2.setCellValue(String.valueOf(entry.getValue()));
            }
            String resultPrice = String.format("%.2f",price);
            Row row0 = sheet.createRow(i);
            Cell art1 = row0.createCell(0);
            art1.setCellValue("Всего");
            Cell art2 = row0.createCell(1);
            art2.setCellValue(resultPrice+"₽");
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            book.write(new FileOutputStream(file));
            book.close();
            return message;
        }else
            return "";
    }


}
