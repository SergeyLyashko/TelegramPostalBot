package telegrambot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegrambot.decoupled.BotToken;
import telegrambot.decoupled.BotUpdate;
import telegrambot.decoupled.Command;
import telegrambot.decoupled.PostalBot;

import java.util.ArrayList;
import java.util.List;

/**
 * Бот
 */
@Service("postalBot")
public class PostalLongPollingBot extends TelegramLongPollingCommandBot implements PostalBot {

    private BotToken botToken;
    private BotUpdate botUpdate;

    @Autowired
    public PostalLongPollingBot(@Value("startCommand") Command command) {
        super.register(command.getBotCommand());

    }

    @Override
    @Autowired
    public void setPostalBotToken(BotToken botToken){
        this.botToken = botToken;
    }

    @Override
    @Autowired
    public void setBotUpdate(BotUpdate botUpdate){
        this.botUpdate = botUpdate;
    }

    @Override
    public BotToken getPostalBotToken(){
        return botToken;
    }

    @Override
    public BotUpdate getBotUpdate(){
        return botUpdate;
    }

    @Override
    public void registerBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            startButton();
        } catch (TelegramApiException e) {
            e.printStackTrace();
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

    /**
     * Ответы на запросы (не команды)
     * @param update
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        // TODO
    }

    /**
     *  Метод для приема сообщений.
     *  объект Update, сериализованный в JSON.
     *  Этот объект представляет из себя входящее обновление.
     *  Под обновлением подразумевается действие,
     *  совершённое с ботом — например, получение сообщения от пользователя.
     */
    /*
    @Override
    public void onUpdateReceived(Update update) {
        //SendMessage message = botUpdate.send(update);
        //sendMessage(message);
        setChatId(update);
        System.out.println("Тест update bot"); // my chatID 528647782
    }
    */

    private void setChatId(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
    }

    @Override
    public void sendMessage(SendMessage message){
        try {
            super.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void startButton(){
        InlineKeyboardMarkup button = createButton();
        SendMessage buttonMessage = new SendMessage();
        buttonMessage.setChatId("528647782");
        buttonMessage.setText("test");
        buttonMessage.setReplyMarkup(button);
        try {
            super.execute(buttonMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup createButton() {
        System.out.println("test button");
        InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        keyboardButton.setText("start");
        keyboardButton.setCallbackData("Button pressed !");
        List<InlineKeyboardButton> keyboard = new ArrayList<>();
        keyboard.add(keyboardButton);
        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        rowList.add(keyboard);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    /*private void send(Update update) {
        SendMessage newMessage = new SendMessage();
        //Integer updateId = update.getUpdateId();
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        newMessage.setChatId(chatId.toString());
        newMessage.setText("Привет, Человек!");// TEST
        try {
            super.execute(newMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }*/
}
