package telegrambot.configuration;

import mail.configuration.NgsMailConfiguration;
import mail.decoupled.MailReceiver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import telegrambot.decoupled.BotToken;
import telegrambot.decoupled.PostalBot;

/**
 * https://habr.com/ru/post/528694/
 */
public class Main {
    public static void main(String...args){

        GenericXmlApplicationContext botTokenContext = new GenericXmlApplicationContext();
        botTokenContext.load("classpath:bot-token-context.xml");
        botTokenContext.refresh();
        BotToken botToken = botTokenContext.getBean("token", BotToken.class);

        ApplicationContext botContext = new AnnotationConfigApplicationContext(BotConfiguration.class);
        PostalBot postalBot = botContext.getBean("postalBot", PostalBot.class);
        postalBot.setPostalBotToken(botToken);
        postalBot.registerBot();
        botTokenContext.close();

        ApplicationContext mailContext = new AnnotationConfigApplicationContext(NgsMailConfiguration.class);
        MailReceiver receiver = mailContext.getBean("mailReceiver", MailReceiver.class);
        receiver.setBot(postalBot);
        receiver.receiveMail();
    }
}
