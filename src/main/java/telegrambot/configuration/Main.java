package telegrambot.configuration;

import mail.configuration.NgsMailReceiveConfiguration;
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

        GenericXmlApplicationContext tokenContext = new GenericXmlApplicationContext();
        tokenContext.load("classpath:bot-token-context.xml");
        tokenContext.refresh();
        BotToken botToken = tokenContext.getBean("token", BotToken.class);

        ApplicationContext botContext = new AnnotationConfigApplicationContext(BotConfiguration.class);
        PostalBot postalBot = botContext.getBean("postalBot", PostalBot.class);
        postalBot.setPostalBotToken(botToken);
        postalBot.registerBot();
        tokenContext.close();

        ApplicationContext mailContext = new AnnotationConfigApplicationContext(NgsMailReceiveConfiguration.class);
        MailReceiver receiver = mailContext.getBean("mailReceiver", MailReceiver.class);

        receiver.setBot(postalBot);
        receiver.receiveMail();

    }
}
