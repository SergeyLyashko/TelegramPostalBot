package mail.decoupled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

/**
 * Session - класс, который определяет основные сессии почты.
 * Чтобы передать значения в объект сессии использованы Properties.
 */
@Service("session")
public class MailSessionImpl implements MailSession {

    private MailAuthenticator mailAuthenticator;
    private MailProperties mailProperties;

    @Override
    @Autowired
    public void setMailAuthenticator(MailAuthenticator mailAuthenticator){
        this.mailAuthenticator = mailAuthenticator;
    }

    @Override
    @Autowired
    public void setMailProperties(MailProperties mailProperties){
        this.mailProperties = mailProperties;
    }

    @Override
    public Session getSession(){
        Properties newProperties = mailProperties.createNewProperties();
        Authenticator authenticator = mailAuthenticator.getAuthenticator();
        Session session = Session.getDefaultInstance(newProperties, authenticator);
        session.setDebug(false);
        return session;
    }
}
