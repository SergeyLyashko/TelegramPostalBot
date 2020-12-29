package telegrambot;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
/*
 * https://github.com/rubenlagus/TelegramBots/blob/master/telegrambots-extensions/src/main/java/org/telegram/telegrambots/extensions/bots/commandbot/TelegramLongPollingCommandBot.java
 */
public abstract class TelegramWebhookCommandBot extends TelegramWebhookBot implements ICommandRegistry {


    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

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

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
