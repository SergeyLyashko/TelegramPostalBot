package mail.ngs;

import javax.mail.Session;
import java.util.Properties;

public class MailHandler {

    private final NgsMailAuthenticator mailAuthenticator;

    public MailHandler(NgsMailAuthenticator ngsMailAuthenticator){
        this.mailAuthenticator = ngsMailAuthenticator;
    }

    public void receiveMail(){
        PostalData postalData = mailAuthenticator.getPostalData();
        MailReceiver mailReceiver = new MailReceiver(postalData);
        Session newSession = createNewSession(mailReceiver);
        mailReceiver.readMail(mailAuthenticator, newSession);
    }

    public void sendMail(){
        MailSender mailSender = new MailSender();
        Session newSession = createNewSession(mailSender);
        mailSender.sendMail(mailAuthenticator, newSession);
    }

    /*
     * Session - класс, который определяет основные сессии почты.
     * Чтобы передать значения в объект сессии, могут быть использованы Properties.
     */
    private Session createNewSession(Propertiesable propertiesable){
        Properties properties = propertiesable.getProperties();
        Session session = Session.getDefaultInstance(properties, mailAuthenticator);
        session.setDebug(false);
        return session;
    }

}
