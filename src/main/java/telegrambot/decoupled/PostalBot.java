package telegrambot.decoupled;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface PostalBot {

    void setPostalBotToken(BotToken botToken);
    BotToken getPostalBotToken();

    void setBotUpdate(BotUpdate botUpdate);
    BotUpdate getBotUpdate();

    void registerBot();

    void sendMessage(SendMessage message);
}