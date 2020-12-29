package telegrambot.decoupled;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotUpdate {

    BotApiMethod updateBot(Update update);

}
