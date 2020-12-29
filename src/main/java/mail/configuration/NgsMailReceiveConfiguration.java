package mail.configuration;

import mail.decoupled.*;
import mail.decoupled.PostalSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NgsMailReceiveConfiguration {

    @Bean
    public MailAuthenticator mailAuthenticator(){
        return new NgsMailAuthenticator();
    }

    @Bean
    public PostalSettings postalSettings(){
        return new NgsImapSettings();
    }

    @Bean
    public MailReceiver mailReceiver(){
        MailReceiver mailReceiver = new MailReceiverImpl();
        mailReceiver.setImapPostalSettings(postalSettings());
        mailReceiver.setMailAuthenticator(mailAuthenticator());
        mailReceiver.setMailSession(mailSession());
        return mailReceiver;
    }

    @Bean
    public MailSession mailSession(){
        MailSession mailSession = new MailSessionImpl();
        mailSession.setMailAuthenticator(mailAuthenticator());
        mailSession.setMailProperties(properties());
        return mailSession;
    }

    @Bean
    public MailProperties properties(){
        MailProperties mailReceiveProperties = new MailReceiveProperties();
        mailReceiveProperties.setPostalSettings(postalSettings());
        return mailReceiveProperties;
    }
}