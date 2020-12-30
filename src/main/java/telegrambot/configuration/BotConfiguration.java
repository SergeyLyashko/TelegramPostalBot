package telegrambot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import telegrambot.decoupled.*;

@Configuration
public class BotConfiguration {

    @Bean
    public BotToken botToken(){
        return new BotTokenImpl();
    }

    @Bean
    public PostalBot postalBot(){
        PostalBot bot = new PostalLongPollingBot();
        bot.setPostalBotToken(botToken());
        bot.setBotUpdate(botUpdate());
        return bot;
    }

    @Bean
    public BotUpdate botUpdate(){
        return new PostalBotUpdate();
    }
}
