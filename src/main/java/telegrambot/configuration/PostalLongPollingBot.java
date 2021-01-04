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

    @Override
    @Autowired
    public void setPostalBotToken(BotToken botToken) {
        this.botToken = botToken;
    }

    @Override
    @Autowired
    @Qualifier("startCommand")
    public void setStartCommand(Command startCommand) {
        super.register(startCommand.getBotCommand());
    }

    @Override
    @Autowired
    @Qualifier("helpCommand")
    public void setHelpCommand(Command helpCommand){
        super.register(helpCommand.getBotCommand());
    }

    @Override
    public void registerBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ответы на запросы
     * @param update
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        System.out.println("test non command chat ID: "+chatId);
        start(chatId);
    }

    @Override
    public String getBotUsername() {
        return botToken.getBotName();
    }

    @Override
    public String getBotToken() {
        return botToken.getBotToken();
    }

    @Override
    public void sendMessage(SendMessage message){
        try {
            super.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    // TODO TEST
    private void start(Long chatId){
        // TODO test
        ServiceKeyBoard startKeyBoard = new ServiceKeyBoard();
        //startKeyBoard.addButton(new ServiceButton("enter email"));
        //startKeyBoard.addNewRowButton();
        startKeyBoard.addButton(new ServiceButton("enter password"));
        SendMessage startMessage = startKeyBoard.sendKeyboard(chatId, "keyboard");
        try {
            super.execute(startMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
