package org.bot;





import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.bot.Entities.Stock;
import org.bot.WildberriesHandler.WildberriesApiToEntity;
import org.json.JSONException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws JSONException, IOException {
        stocks();
    }

    public static void stocks() throws IOException {
        Workbook book = createExcel();
        String file = System.getProperty("user.dir") + "/Stocks.xls";
        Sheet sheet = book.getSheet("Остатки Wildberries");
        List<Stock> arr = null;
        try {
            arr = WildberriesApiToEntity.getStocks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Stock> sortedArr = arr.stream().sorted(new StockComparator()).toList();
        System.out.println(sortedArr);

        String nameInMemory = sortedArr.get(0).getName();
        Row row0 = sheet.createRow(1);
        Cell art0 = row0.createCell(0);
        art0.setCellValue(sortedArr.get(0).getName());
        int i = 2;
        for (Stock obj : sortedArr) {
            String name = obj.getName();
            if(!name.equals(nameInMemory)){
                row0 = sheet.createRow(i++);
                Cell art = row0.createCell(0);
                art.setCellValue(name);

                switch (obj.getWarehouseName()) {
                    case  ("Коледино"):
                        Cell koledino = row0.createCell(1);
                        koledino.setCellValue(obj.getCount());
                        break;
                    case ("Казань"):
                        Cell kazan = row0.createCell(2);
                        kazan.setCellValue(obj.getCount());
                        break;
                    case ("Краснодар"):
                        Cell krasnodar = row0.createCell(3);
                        krasnodar.setCellValue(obj.getCount());
                        break;
                    case ("Тула"):
                        Cell tula = row0.createCell(4);
                        tula.setCellValue(obj.getCount());
                        break;
                }

                nameInMemory = name;
            }else {
                switch (obj.getWarehouseName()) {
                    case  ("Коледино"):
                        Cell koledino = row0.createCell(1);
                        koledino.setCellValue(obj.getCount());
                        break;
                    case ("Казань"):
                        Cell kazan = row0.createCell(2);
                        kazan.setCellValue(obj.getCount());
                        break;
                    case ("Краснодар"):
                        Cell krasnodar = row0.createCell(3);
                        krasnodar.setCellValue(obj.getCount());
                        break;
                    case ("Тула"):
                        Cell tula = row0.createCell(4);
                        tula.setCellValue(obj.getCount());
                        break;
                }
            }
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(3);
        book.write(new FileOutputStream(file));
        book.close();
    }
    public static Workbook createExcel() throws IOException {
        String file = System.getProperty("user.dir") + "/Stocks.xls";
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Остатки Wildberries");

        // Нумерация начинается с нуля
        Row row = sheet.createRow(0);
        Cell name = row.createCell(0);
        name.setCellValue("Артикул");

        Cell koledino = row.createCell(1);
        koledino.setCellValue("Коледино");

        Cell kazan = row.createCell(2);
        kazan.setCellValue("Казань");

        Cell krasnodar = row.createCell(3);
        krasnodar.setCellValue("Краснодар");

        Cell tula = row.createCell(4);
        tula.setCellValue("Тула");

        // Меняем размер столбца
        return book;
    }
}

class StockComparator implements Comparator<Stock> {
    @Override
    public int compare(Stock a, Stock b){

        return a.getName().toUpperCase().compareTo(b.getName().toUpperCase());
    }

}

