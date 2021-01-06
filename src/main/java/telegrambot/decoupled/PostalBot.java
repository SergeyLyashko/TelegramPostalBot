package telegrambot.decoupled;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegrambot.configuration.KeyBoard;
import telegrambot.configuration.Mailing;

public interface PostalBot {

    void setPostalBotToken(BotToken botToken);
    void setStartCommand(Command command);
    void setHelpCommand(Command command);

    //void setKeyBoard(KeyBoard keyBoard);
    void setMailing(Mailing mailing);

    void registerBot();

    void deliverMail(String[] from, String text);

    void sendMessage(SendMessage message);
}
