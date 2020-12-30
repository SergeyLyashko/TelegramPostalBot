package telegrambot.decoupled;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotUpdate {

    void updateMessage(String message);

    SendMessage send(Update update);

}
