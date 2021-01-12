package telegrambot.decoupled;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface MailService {

    void setPostalBot(PostalBot postalBot);
    //void setBodyKeyboard(Keyboard bodyKeyboard);

    void executeCallback(CallbackQuery callback);

    void handle(String[] from, String text);
}
