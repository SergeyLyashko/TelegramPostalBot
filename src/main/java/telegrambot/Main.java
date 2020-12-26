package telegrambot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegrambot.MessageWebhookHandlers;
import telegrambot.WebHookRequest;

public class Main {
    public static void main(String...args){

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            // TODO убрать имя и токен
            String botName = "msgVkontakteBot";
            String botToken = "1328867240:AAERZyHSBoeT9gpbmYGypCO9LJ-8XnCLmzQ";
            WebHookRequest webHookRequest = new WebHookRequest();
            telegramBotsApi.registerBot(new MessageWebhookHandlers(botName, botToken), webHookRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
