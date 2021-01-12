package telegrambot.configuration;

import mail.configuration.MailConfiguration;
import mail.decoupled.MailReceiver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import telegrambot.decoupled.PostalBot;

/**
 * https://habr.com/ru/post/528694/
 */
public class Main {
    public static void main(String...args){

        AnnotationConfigApplicationContext botContext = new AnnotationConfigApplicationContext(BotConfiguration.class);
        AnnotationConfigApplicationContext mailContext = new AnnotationConfigApplicationContext(MailConfiguration.class);
        mailContext.setParent(botContext);

        PostalBot postalBot = botContext.getBean("postalBot", PostalBot.class);
        MailReceiver receiver = mailContext.getBean("mailReceiver", MailReceiver.class);
        postalBot.registerBot();
        receiver.receiveMail();
    }
}
