package telegrambot.decoupled;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface Keyboard {

    InlineKeyboardMarkup getInlineKeyboardMarkup();

    void addButtonName(String buttonName);
}
