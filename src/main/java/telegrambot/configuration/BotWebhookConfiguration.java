package telegrambot.configuration;

import org.springframework.context.annotation.ComponentScan;
import telegrambot.decoupled.*;

@ComponentScan(basePackages = "telegrambot.decoupled, telegrambot.configuration")
public class BotWebhookConfiguration {

    public BotToken botToken(){
        return new BotTokenImpl();
    }

    public BotRequestSettings botRequest(){
        return new BotWebHookRequestSettings();
    }

    public WebhookBot postalBot(){
        WebhookBot bot = new PostalWebhookBot();
        bot.setPostalBotToken(botToken());
        bot.setBotUpdate(botUpdate());
        bot.setRequestSettings(botRequest());
        return bot;
    }

    public BotUpdate botUpdate(){
        return new PostalBotUpdate();
    }
}
