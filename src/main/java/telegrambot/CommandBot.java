package telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
/*
 * https://github.com/rubenlagus/TelegramBots/blob/master/telegrambots-extensions/src/main/java/org/telegram/telegrambots/extensions/bots/commandbot/TelegramLongPollingCommandBot.java
 */
public abstract class CommandBot extends TelegramLongPollingBot implements ICommandRegistry {

    @Override
    public void registerDefaultAction(BiConsumer<AbsSender, Message> biConsumer) {

    }

    @Override
    public boolean register(IBotCommand iBotCommand) {
        return false;
    }

    @Override
    public Map<IBotCommand, Boolean> registerAll(IBotCommand... iBotCommands) {
        return null;
    }

    @Override
    public boolean deregister(IBotCommand iBotCommand) {
        return false;
    }

    @Override
    public Map<IBotCommand, Boolean> deregisterAll(IBotCommand... iBotCommands) {
        return null;
    }

    @Override
    public Collection<IBotCommand> getRegisteredCommands() {
        return null;
    }

    @Override
    public IBotCommand getRegisteredCommand(String s) {
        return null;
    }
}
