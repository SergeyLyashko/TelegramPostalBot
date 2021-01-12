package mail.configuration;

import mail.decoupled.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:ngs-mail-token-context.xml", "classpath:ngs-imap-context.xml"})
@ComponentScan(basePackages = {"mail.decoupled"})
@Configuration
public class MailConfiguration {

    public MailParser parser(){
        return new MailParserImpl();
    }

    public MailReceiver mailReceiver(){
        return new MailReceiverImpl();
    }

    public MailConnection connection(){
        return new MailStoreConnection();
    }

    public MailProperties properties(){
        return new MailPropertiesImpl();
    }
}