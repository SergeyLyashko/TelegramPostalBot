package telegrambot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import telegrambot.command.StartCommand;
import telegrambot.decoupled.*;

@Configuration
public class BotConfiguration {

    @Bean
    public Command command(){
        return new StartCommand();
    }

    @Bean
    public PostalBot postalBot(){
        PostalBot bot = new PostalLongPollingBot();
        bot.setCommand(command());
        bot.setBotUpdate(botUpdate());
        return bot;
    }

    @Bean
    public BotUpdate botUpdate(){
        return new PostalBotUpdate();
    }
}
