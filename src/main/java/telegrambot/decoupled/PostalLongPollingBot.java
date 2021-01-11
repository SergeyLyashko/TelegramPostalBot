package telegrambot.decoupled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegrambot.command.Command;

/**
 * Бот
 * задать описание бота
 * https://ru.stackoverflow.com/questions/668818/%D0%9A%D0%B0%D0%BA-%D0%B7%D0%B0%D0%B4%D0%B0%D1%82%D1%8C-%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B1%D0%BE%D1%82%D0%B0-telegram
 */
@Component("postalBot")
public class PostalLongPollingBot extends TelegramLongPollingBot implements PostalBot {

    private BotToken botToken;
    private Command startCommand; // TODO ???
    private Command helpCommand; // TODO ???

    private MailService mailService;

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
    @Autowired
    public void setMailing(MailService mailService){
        this.mailService = mailService;
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
        //super.register(startCommand.getBotCommand());
        //super.register(helpCommand.getBotCommand());
    }

    @Override
    public void deliverMail(String[] from, String text) {
        mailService.handle(from, text);
    }

    @Override
    public Message sendMessage(SendMessage message) {
        try {
            return super.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendMessage(EditMessageText message){
        try {
            super.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMessage(DeleteMessage message){
        try {
            super.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            mailService.executeCallback(callbackQuery);
        }
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
