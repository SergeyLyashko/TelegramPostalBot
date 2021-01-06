package telegrambot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
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
public class PostalLongPollingBot extends TelegramLongPollingBot implements PostalBot {

    private BotToken botToken;
    private Command startCommand;
    private Command helpCommand;
    private KeyBoard keyBoard;
    private Long chatId = 528647782L; // TODO TEST
    private Integer messageId;

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
    public void setKeyBoard(KeyBoard keyBoard){
        this.keyBoard = keyBoard;
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
    public void deliverMail(String[] from) {
        //String mailText = mailParser.getMailText();
        //String[] from = mailParser.getFrom();
        createButton(from[0]);
    }

    private void createButton(String buttonText){
        ChatButton chatButton = new ChatServiceButton("@ from: " + buttonText);
        keyBoard.addButton(chatButton);
        keyBoard.sendNewKeyboard("new mail:");
    }

    @Override
    public void sendMessage(SendMessage message){
        try {
            Message execute = super.execute(message);
            this.messageId = execute.getMessageId();
            System.out.println("test execute id: "+messageId);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void deleteMessage(){
        DeleteMessage deleteMessage = new DeleteMessage(chatId.toString(), messageId);
        try {
            super.execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("test del msg id: "+messageId);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message message = callbackQuery.getMessage();
            Integer messageId = message.getMessageId();
            String data = callbackQuery.getData();
            System.out.println("test callback: "+data+" msg id: "+messageId);
            deleteMessage();
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
