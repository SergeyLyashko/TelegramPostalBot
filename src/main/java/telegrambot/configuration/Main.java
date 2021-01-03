package telegrambot.configuration;

import mail.configuration.NgsMailConfiguration;
import mail.decoupled.MailReceiver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import telegrambot.decoupled.PostalBot;

/**
 * https://habr.com/ru/post/528694/
 */
public class Main {
    public static void main(String...args){

        ApplicationContext botContext = new AnnotationConfigApplicationContext(BotConfiguration.class);
        PostalBot postalBot = botContext.getBean("postalBot", PostalBot.class);
        postalBot.registerBot();

        /*
        ApplicationContext mailContext = new AnnotationConfigApplicationContext(NgsMailConfiguration.class);
        MailReceiver receiver = mailContext.getBean("mailReceiver", MailReceiver.class);
        receiver.setBot(postalBot);
        receiver.receiveMail();
        */
    }
}
