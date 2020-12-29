package telegrambot.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import telegrambot.decoupled.WebhookBot;

/**
 * https://habr.com/ru/post/528694/
 */
public class Main {
    public static void main(String...args){

        /*try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            // TODO убрать имя и токен
            String botName = "msgVkontakteBot";
            String botToken = "1328867240:AAERZyHSBoeT9gpbmYGypCO9LJ-8XnCLmzQ";
            BotWebHookRequest botWebHookRequest = new BotWebHookRequest();
            telegramBotsApi.registerBot(new PostalWebhookBot(botName, botToken), botWebHookRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/

        ApplicationContext context = new AnnotationConfigApplicationContext(BotWebhookConfiguration.class);
        WebhookBot postalBot = context.getBean("postalwebhookbot", WebhookBot.class);
        postalBot.registerBot();
    }
}
