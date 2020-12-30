package telegrambot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegrambot.decoupled.BotToken;
import telegrambot.decoupled.BotUpdate;
import telegrambot.decoupled.PostalBot;

/**
 * Бот
 */
@Service("postalBot")
public class PostalLongPollingBot extends TelegramLongPollingBot implements PostalBot {

    private BotToken botToken;
    private BotUpdate botUpdate;

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
     *  Метод для приема сообщений.
     *  объект Update, сериализованный в JSON.
     *  Этот объект представляет из себя входящее обновление.
     *  Под обновлением подразумевается действие,
     *  совершённое с ботом — например, получение сообщения от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        //SendMessage message = botUpdate.send(update);
        //sendMessage(message);
        System.out.println("Тест update bot"); // my chatID 528647782
    }

    @Override
    public void sendMessage(SendMessage message){
        try {
            super.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
