package mail.decoupled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Session;

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
    public MailAuthenticator getMailAuthenticator(){
        return mailAuthenticator;
    }

    @Override
    @Autowired
    public void setMailProperties(MailProperties mailProperties){
        this.mailProperties = mailProperties;
    }

    @Override
    public MailProperties getMailProperties(){
        return mailProperties;
    }

    @Override
    public Session getSession(){
        Session session = Session.getDefaultInstance(mailProperties.createNewProperties(), mailAuthenticator.getAuthenticator());
        session.setDebug(false);
        return session;
    }
}
