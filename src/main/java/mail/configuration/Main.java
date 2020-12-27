package mail.configuration;

import mail.decoupled.MailReceiver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String...args){
        ApplicationContext context = new AnnotationConfigApplicationContext(NgsMailReceiveConfiguration.class);
        MailReceiver receiver = context.getBean("mailreceiver", MailReceiver.class);
        receiver.receiveMail();
    }
}
