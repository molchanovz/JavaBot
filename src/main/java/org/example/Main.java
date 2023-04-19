package org.example;

import org.example.Entities.TelegramBot;
import org.example.WildberriesHandler.WildberriesApiToMessage;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBot bot = new TelegramBot("6032861857:AAHKRKNdkCIX21wAvqAuGFktxJCPmGLKJoI");
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        Thread myThread = new Thread(new WB_ALL(), "#1");
        myThread.start();

    }
}

class WB_ALL implements Runnable {
    @Override
    public void run() {
        while (true) {
            TelegramBot bot = new TelegramBot("6032861857:AAHKRKNdkCIX21wAvqAuGFktxJCPmGLKJoI");
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

class WB_FBS implements Runnable {
    @Override
    public void run() {
        while (true) {
            TelegramBot bot = new TelegramBot("6032861857:AAHKRKNdkCIX21wAvqAuGFktxJCPmGLKJoI");
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