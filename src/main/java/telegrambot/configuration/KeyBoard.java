package telegrambot.configuration;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import telegrambot.decoupled.PostalBot;

public interface KeyBoard {

    //void setPostalBot(PostalBot postalBot);

    void addButton(ChatButton chatServiceButton);

    void addNewRowButton();

    InlineKeyboardMarkup getInlineKeyboardMarkup();

    //void sendNewKeyboard(String text);
}
