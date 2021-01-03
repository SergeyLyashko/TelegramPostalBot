package telegrambot.decoupled;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface PostalBot {

    void setPostalBotToken(BotToken botToken);

    void setCommand(Command command);

    void registerBot();

    void sendMessage(SendMessage message);
}
