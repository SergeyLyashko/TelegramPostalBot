package telegrambot.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegrambot.decoupled.Command;

import java.util.ArrayList;
import java.util.List;

@Service("startCommand")
public class StartCommand extends BotCommand implements Command {

    private static final String IDENTIFIER = "start";
    private static final String DESCRPTION = "старт";

    public StartCommand(){
        super(IDENTIFIER, DESCRPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments){
        Long chatId = chat.getId();
        System.out.println("test ID: "+chatId);
    }

    @Override
    public BotCommand getBotCommand(){
        return this;
    }
}
