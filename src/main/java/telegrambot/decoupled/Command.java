package telegrambot.decoupled;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface Command {

    void execute(AbsSender absSender, User user, Chat chat, String[] arguments);

    BotCommand getBotCommand();
}
