package telegrambot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegrambot.decoupled.BotToken;
import telegrambot.decoupled.Command;
import telegrambot.decoupled.PostalBot;

/**
 * Бот
 * задать описание бота
 * https://ru.stackoverflow.com/questions/668818/%D0%9A%D0%B0%D0%BA-%D0%B7%D0%B0%D0%B4%D0%B0%D1%82%D1%8C-%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B1%D0%BE%D1%82%D0%B0-telegram
 */
@Component("postalBot")
public class PostalLongPollingBot extends TelegramLongPollingCommandBot implements PostalBot {

    private BotToken botToken;
    private Command startCommand;
    private Command helpCommand;

    @Override
    @Autowired
    public void setPostalBotToken(BotToken botToken) {
        this.botToken = botToken;
    }

    @Override
    @Autowired
    @Qualifier("startCommand")
    public void setStartCommand(Command startCommand) {
        this.startCommand = startCommand;
    }

    @Override
    @Autowired
    @Qualifier("helpCommand")
    public void setHelpCommand(Command helpCommand){
        this.helpCommand = helpCommand;
    }

    @Override
    public void registerBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            registerCommand();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void registerCommand() {
        super.register(startCommand.getBotCommand());
        super.register(helpCommand.getBotCommand());
    }

    @Override
    public void deliverMail(String[] from) {
        //String mailText = mailParser.getMailText();
        //String[] from = mailParser.getFrom();
        createButton(from[0]);
    }

    private void createButton(String buttonText){
        Long chatId = 528647782L;//startCommand.getChatId();
        ServiceKeyBoard keyBoard = new ServiceKeyBoard();
        keyBoard.addButton(new ServiceButton("@ from: "+buttonText));
        SendMessage startMessage = keyBoard.sendKeyboard(chatId, "new mail:");
        try {
            super.execute(startMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ответы на текстовые запросы
     * @param update
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        System.out.println("test non command chat ID: "+chatId);
    }

    @Override
    public String getBotUsername() {
        return botToken.getBotName();
    }

    @Override
    public String getBotToken() {
        return botToken.getBotToken();
    }
}
