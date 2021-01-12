package mail.configuration;

import mail.decoupled.MailReceiver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/*
http://java-online.ru/javax-mail.xhtml
http://www.quizful.net/post/java-mail-api
https://javaee.github.io/javamail/docs/api/
 */

public class Main {
    public static void main(String...args){

        ApplicationContext context = new AnnotationConfigApplicationContext(MailConfiguration.class);
        MailReceiver receiver = context.getBean("mailReceiver", MailReceiver.class);
        receiver.receiveMail();
    }
}

