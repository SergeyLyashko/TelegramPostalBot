package telegrambot.decoupled;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

public interface Command {

    BotCommand getBotCommand();
}
