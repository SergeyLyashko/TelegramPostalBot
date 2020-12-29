package telegrambot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegrambot.decoupled.BotRequestSettings;
import telegrambot.decoupled.BotToken;
import telegrambot.decoupled.WebhookBot;
import telegrambot.decoupled.BotUpdate;

/**
 * Бот
 */
@Service("postalwebhookbot")
public final class PostalWebhookBot extends TelegramWebhookBot implements WebhookBot {

    private BotToken botToken;
    private BotUpdate botUpdate;
    private BotRequestSettings requestSettings;

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
    @Autowired
    public void setRequestSettings(BotRequestSettings requestSettings){
        this.requestSettings = requestSettings;
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
    public BotRequestSettings getRequestSettings(){
        return requestSettings;
    }

    /*
     * setWebhook - push: по мере поступления новых сообщений сервер Telegram отправляет их вашему боту.
     */
    @Override
    public void registerBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            SetWebhook webhook = requestSettings.getWebhook();
            telegramBotsApi.registerBot(this, webhook);
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
     *  объект Update, сериализованный в JSON.
     * @param update Этот объект представляет из себя входящее обновление.
     *               Под обновлением подразумевается действие,
     *               совершённое с ботом — например, получение сообщения от пользователя.
     * @return
     */
    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        return botUpdate.updateBot(update);
    }

    /*
     * Gets in the url for the webhook
     */
    @Override
    public String getBotPath() {
        return null; // TODO
    }
}
