package telegrambot.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import telegrambot.command.HelpCommand;
import telegrambot.command.StartCommand;
import telegrambot.decoupled.*;

@ImportResource(locations = {"classpath:bot-token-context.xml"})
@ComponentScan(basePackages = {"telegrambot.configuration", "telegrambot.command"})
@Configuration
public class BotConfiguration {

    public MailService mailing(){
        return new BotMailService();
    }

    public Command startCommand(){
        return new StartCommand();
    }

    public Command helpCommand(){
        return new HelpCommand();
    }

    public PostalBot postalBot(){
        return new PostalLongPollingBot();
    }

    public Letter letter(){
        return new LetterImpl();
    }
}
