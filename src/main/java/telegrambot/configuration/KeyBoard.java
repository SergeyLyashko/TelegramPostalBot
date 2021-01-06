package telegrambot.configuration;

import telegrambot.decoupled.PostalBot;

public interface KeyBoard {

    void setPostalBot(PostalBot postalBot);

    void addButton(ChatButton chatServiceButton);

    void sendNewKeyboard(String text);
}
