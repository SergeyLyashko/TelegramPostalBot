package telegrambot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import telegrambot.command.HelpCommand;
import telegrambot.command.StartCommand;
import telegrambot.decoupled.*;

@ImportResource(locations = {"classpath:bot-token-context.xml"})
@Configuration
public class BotConfiguration {

    @Bean
    public KeyBoard keyBoard(){
        return new ServiceKeyBoard();
    }

    @Bean
    public Command startCommand(){
        return new StartCommand();
    }

    @Bean
    public Command helpCommand(){
        return new HelpCommand();
    }

    @Bean
    public PostalBot postalBot(){
        return new PostalLongPollingBot();
    }
}
