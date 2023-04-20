package org.bot;

import org.bot.Entities.TelegramBot;
import org.bot.WildberriesHandler.WildberriesApiToMessage;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;


public class Main {

    /**
     * получаем защищенный токен
     **/
    static String token = Protection.token;

    public static void main(String[] args) throws TelegramApiException {
        /** регистрируем бота **/
        TelegramBot bot = new TelegramBot(token);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        /** поток для фбо заказов **/
        Thread myThread = new Thread(new WB_ALL(), "#1");
        myThread.start();

    }

    static class WB_ALL implements Runnable {
        @Override
        public void run() {
            while (true) {
                TelegramBot bot = new TelegramBot(token);
                String message = null;
                try {
                    message = WildberriesApiToMessage.getOrders_All();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (RuntimeException e) {
                    bot.sendMessage("406363099", "Runtime Exception");
                    run();
                }
                if (message != "") {
                    bot.sendMessage("406363099", message);
                } else System.out.println("Новых заказов нет");
                try {
                    Thread.sleep(30 * 60 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    class WB_FBS implements Runnable {
        @Override
        public void run() {
            while (true) {
                TelegramBot bot = new TelegramBot(token);
                String message;
                try {
                    message = WildberriesApiToMessage.getOrders_All();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                bot.sendMessage("406363099", message);
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}

