package telegrambot.configuration;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.decoupled.PostalBot;

public interface MailingService {

    void setPostalBot(PostalBot postalBot);

    void createMail(String[] from, String text);

    void executeCallback(CallbackQuery callback);

}
