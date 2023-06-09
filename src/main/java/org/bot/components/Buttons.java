package org.bot.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton Ozon_FBS = new InlineKeyboardButton("Штрихкод Ozon FBS");
    private static final InlineKeyboardButton Stocks = new InlineKeyboardButton("Остатки");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");

    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData("/start");
        Ozon_FBS.setCallbackData("/штрихкод");
        Ozon_FBS.setCallbackData("/stocks");
        HELP_BUTTON.setCallbackData("/help");

        List<InlineKeyboardButton> rowInline = List.of(START_BUTTON, Ozon_FBS, HELP_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
