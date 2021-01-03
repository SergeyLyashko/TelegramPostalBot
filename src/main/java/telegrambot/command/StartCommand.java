package telegrambot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrambot.decoupled.Command;

@Component("startCommand")
public class StartCommand extends BotCommand implements Command {

    private static final String IDENTIFIER = "start";
    private static final String DESCRIPTION = "старт";

    private static final String HELLO_TEXT = "Привет, ";
    private static final String BOT_TEXT = "!\nЯ почтовый бот, который доставляет сообщения с вашей электронной почты.";

    private Long chatId;

    public StartCommand(){
        super(IDENTIFIER, DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments){
        this.chatId = chat.getId();
        System.out.println("test ID: "+chatId); // TODO test
        try {
            absSender.execute(helloMessage(user));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage helloMessage(User user) {
        SendMessage hello = new SendMessage();
        hello.setChatId(chatId.toString());
        hello.setText(HELLO_TEXT+user.getFirstName()+BOT_TEXT);
        return hello;
    }

    @Override
    public BotCommand getBotCommand(){
        return this;
    }

    public Long getChatId(){
        return chatId;
    }
}
