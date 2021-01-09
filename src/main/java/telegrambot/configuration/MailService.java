package telegrambot.configuration;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import telegrambot.decoupled.PostalBot;

public interface MailService {

    void setPostalBot(PostalBot postalBot);

    void executeCallback(CallbackQuery callback);

    void handle(Letter letter);
}
