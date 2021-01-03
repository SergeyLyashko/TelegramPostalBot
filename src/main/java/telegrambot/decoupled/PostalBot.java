package telegrambot.decoupled;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface PostalBot {

    void setPostalBotToken(BotToken botToken);

    void setStartCommand(Command command);
    void setHelpCommand(Command command);

    void registerBot();

    void sendMessage(SendMessage message);
}
