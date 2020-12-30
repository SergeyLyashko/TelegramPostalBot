package telegrambot.configuration;

import mail.configuration.NgsMailReceiveConfiguration;
import mail.decoupled.MailReceiver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import telegrambot.decoupled.PostalBot;

/**
 * https://habr.com/ru/post/528694/
 */
public class Main {
    public static void main(String...args){

        AnnotationConfigApplicationContext botContext = new AnnotationConfigApplicationContext(BotConfiguration.class);
        PostalBot postalBot = botContext.getBean("postalBot", PostalBot.class);
        postalBot.registerBot();

        AnnotationConfigApplicationContext mailContext = new AnnotationConfigApplicationContext(NgsMailReceiveConfiguration.class);
        MailReceiver receiver = mailContext.getBean("mailReceiver", MailReceiver.class);

        receiver.setBot(postalBot);
        receiver.receiveMail();

    }
}
