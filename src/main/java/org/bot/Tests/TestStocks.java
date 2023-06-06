package org.bot.Tests;





import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.bot.Entities.Stock;
import org.bot.WildberriesHandler.WildberriesApiToEntity;
import org.bot.WildberriesHandler.WildberriesStocks;
import org.json.JSONException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class TestStocks {
    public static void main(String[] args) throws JSONException, IOException {
        WildberriesStocks.stocks();
    }
}



