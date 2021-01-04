package mail.configuration;

import mail.decoupled.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:ngs-mail-token-context.xml",
        "classpath:ngs-imap-context.xml"})
@Configuration
public class NgsMailConfiguration {

    @Bean
    public MailParser mailParser(){
        return new MailParserImpl();
    }

    @Bean
    public MailReceiver mailReceiver(){
        return new MailReceiverImpl();
    }

    @Bean
    public MailSession mailSession(){
        return new MailSessionImpl();
    }

    @Bean
    public MailProperties properties(){
        return new MailPropertiesImpl();
    }
}