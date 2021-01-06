package telegrambot.decoupled;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegrambot.configuration.KeyBoard;

public interface PostalBot {

    void setPostalBotToken(BotToken botToken);
    void setStartCommand(Command command);
    void setHelpCommand(Command command);
    void setKeyBoard(KeyBoard keyBoard);

    void registerBot();
    void deliverMail(String[] from);

    void sendMessage(SendMessage message);
}
