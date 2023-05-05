package org.bot.WildberriesHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


class WildberriesApi {
    private static String getDate(int deltaHours) {
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        date.setTime(date.getTime()+(deltaHours*3600000));
        return formatterDate.format(date) + "T" + formatterTime.format(date);
    }


    //Возвращает массив json объектов
    static JSONArray getOrders_All() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String dateFrom = getDate(0);
        System.out.println(dateFrom);
        try {

            // создаем объект клиента
            HttpGet request = new HttpGet("https://statistics-api.wildberries.ru/api/v1/supplier/orders");

            URI uri = new URIBuilder(request.getURI())
                    .addParameter("dateFrom", dateFrom)
                    .addParameter("flag", "1")
                    .build();
            request.setURI(uri);

            // добавляем заголовки запроса

            request.setHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NJRCI6IjIwZWJiYWM3LWI4MmQtNGExYS04YzdlLWU3NmE5ZmRhNDJiNiJ9.v4t6dDDC_h1sjX02p-0kSaTgAfEc0WHPokPz_QxgElY");
            request.addHeader("dateFrom", dateFrom);
            request.addHeader("flag", "1");
            //System.out.println(Arrays.stream(request.getAllHeaders()).toList().toString());
            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);

                //JSONObject object = new JSONObject(result);
                JSONArray arr;
                arr = new JSONArray(result);
                return arr;

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } finally {
            httpClient.close();
        }
    }

    static JSONArray getOrders_FBS() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpGet request = new HttpGet("https://suppliers-api.wildberries.ru/api/v3/orders/new");

            // добавляем заголовки запроса
            request.setHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NJRCI6ImNjMzU2MGI4LTk5NjctNDRkOC04OTU2LTI4YjRkYjE3MGZlYSJ9.EpNeXljvhZCh-wMeLgfJ58-xVP7fBF7LgE_AMf_0gRs");
            CloseableHttpResponse response = httpClient.execute(request);

            try {
                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject object = new JSONObject(result);
                JSONArray arr = object.getJSONArray("orders");
                return arr;

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

    static JSONArray getStocks() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateFrom = formatterDate.format(date);
        try {

            // создаем объект клиента
            HttpGet request = new HttpGet("https://statistics-api.wildberries.ru/api/v1/supplier/stocks");

            URI uri = new URIBuilder(request.getURI())
                    .addParameter("dateFrom", dateFrom)
                    .build();
            request.setURI(uri);

            // добавляем заголовки запроса
            request.setHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NJRCI6IjIwZWJiYWM3LWI4MmQtNGExYS04YzdlLWU3NmE5ZmRhNDJiNiJ9.v4t6dDDC_h1sjX02p-0kSaTgAfEc0WHPokPz_QxgElY");
            request.addHeader("dateFrom", dateFrom);
            //System.out.println(Arrays.stream(request.getAllHeaders()).toList().toString());
            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                //JSONObject object = new JSONObject(result);
                JSONArray arr;
                arr = new JSONArray(result);
                return arr;

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } finally {
            httpClient.close();
        }
    }
}
