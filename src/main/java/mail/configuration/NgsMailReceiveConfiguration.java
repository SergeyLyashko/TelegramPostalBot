package mail.configuration;

import mail.decoupled.*;
import mail.decoupled.PostalSettings;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "mail.decoupled, mail.configuration")
public class NgsMailReceiveConfiguration {

    public MailAuthenticator mailAuthenticator(){
        return new NgsMailAuthenticator();
    }

    public PostalSettings postalSettings(){
        return new NgsImapSettings();
    }

    public MailReceiver mailReceiver(){
        MailReceiver mailReceiver = new MailReceiverImpl();
        mailReceiver.setImapPostalSettings(postalSettings());
        mailReceiver.setMailAuthenticator(mailAuthenticator());
        mailReceiver.setMailSession(mailSession());
        return mailReceiver;
    }

    public MailSession mailSession(){
        MailSession mailSession = new MailSessionImpl();
        mailSession.setMailAuthenticator(mailAuthenticator());
        mailSession.setMailProperties(properties());
        return mailSession;
    }

    public MailProperties properties(){
        MailProperties mailReceiveProperties = new MailReceiveProperties();
        mailReceiveProperties.setPostalSettings(postalSettings());
        return mailReceiveProperties;
    }
}