package telegrambot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrambot.decoupled.Command;

@Component("helpCommand")
public class HelpCommand extends BotCommand implements Command {

    private static final String IDENTIFIER = "help";
    private static final String DESCRIPTION = "помощь";
    private static final String HELP_TEXT = "HELP!";

    public HelpCommand() {
        super(IDENTIFIER, DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        Long chatId = chat.getId();
        try {
            absSender.execute(helpMessage(chatId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage helpMessage(Long chatId) {
        SendMessage help = new SendMessage();
        help.setChatId(chatId.toString());
        help.setText(HELP_TEXT);
        return help;
    }

    @Override
    public BotCommand getBotCommand() {
        return this;
    }
}
