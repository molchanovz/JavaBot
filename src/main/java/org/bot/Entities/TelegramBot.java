package org.bot.Entities;

import org.bot.Protection;
import org.bot.WildberriesHandler.WildberriesStocks;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

import static org.bot.components.BotCommands.LIST_OF_COMMANDS;

public class TelegramBot extends TelegramLongPollingBot {

    public TelegramBot(String botToken) {
        super(botToken);
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String mainMessage = update.getMessage().getText();
            switch (mainMessage) {
                case ("привет"):
                case ("Привет"):
                    sendMessage(String.valueOf(update.getMessage().getChatId()), "Привет");
                    break;
                case "id":
                    System.out.println(update.getMessage().getChatId());
                    break;
                case ("/start"):
                    sendMessage(String.valueOf(update.getMessage().getChatId()), "Привет  " + update.getMessage().getForwardSenderName());
                    break;
                case ("/shtrihkod"):
                case ("Штрихкод"):
                case ("штрихкод"):
                    sendMessage(String.valueOf(update.getMessage().getChatId()), "Отправляю штрихкод фбс");
                    break;
                case ("/stocks"):
                    sendMessage(String.valueOf(update.getMessage().getChatId()), "Отправляю остатки");
                    try {
                        WildberriesStocks.stocks();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sendFile(String.valueOf(update.getMessage().getChatId()), Protection.stocksWB);
                    break;
                case ("/help"):
                    sendMessage(String.valueOf(update.getMessage().getChatId()), "Помощь уже в пути!");
                    break;
            }
        }

    }


    @Override
    public String getBotUsername() {
        return "jvmHelperBot";
    }

    public Runnable sendMessage(String chatId, String message) {
        SendMessage someMessage = new SendMessage();
        someMessage.setChatId(chatId);
        someMessage.setText(message);
        try {
            execute(someMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Runnable sendFile(String chatId, String file) {
        System.out.println("Остатки");
        File fileToUpload = new File(file);
        InputFile file2 = new InputFile(fileToUpload);
        SendDocument someDocument = new SendDocument(chatId,file2);
        try {
            execute(someDocument);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}


