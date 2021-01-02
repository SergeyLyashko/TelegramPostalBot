package telegrambot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import telegrambot.command.StartCommand;
import telegrambot.decoupled.*;

@ImportResource(locations = {"classpath:bot-token-context.xml"})
@Configuration
public class BotConfiguration {

    @Bean
    public Command command(){
        return new StartCommand();
    }

    @Bean
    public PostalBot postalBot(){
        return new PostalLongPollingBot();
    }

    @Bean
    public BotUpdate botUpdate(){
        return new PostalBotUpdate();
    }
}
