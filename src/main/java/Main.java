import org.bot.Entities.TelegramBot;
import org.bot.Protection;
import org.bot.WildberriesHandler.WildberriesApiToMessage;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
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
            bot.sendMessage("406363099", "Бот запущен");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        File fileFBO = new File(Protection.fileFBO);
        if(!fileFBO.isFile()){
            try {
                fileFBO.createNewFile();
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }

        File fileFBS = new File(Protection.fileFBS);
        if(!fileFBS.isFile()){
            try {
                fileFBS.createNewFile();
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }




        /** поток для фбо заказов **/
        Thread fbo_Thread = new Thread(new WB_ALL(), "#1");
        fbo_Thread.start();

        /** поток для фбc заказов **/
        Thread fbs_Thread = new Thread(new WB_FBS(), "#2");
        fbs_Thread.start();

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
                    bot.sendMessage("406363099", e.getMessage());
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

    static class WB_FBS implements Runnable {
        @Override
        public void run() {
            while (true) {
                TelegramBot bot = new TelegramBot(token);
                String message;
                try {
                    message = WildberriesApiToMessage.getOrders_FBS();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (message != "") {
                    bot.sendMessage("406363099", message);
                } else System.out.println("Новых FBS заказов нет");
                try {
                    Thread.sleep(30 * 60 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}

