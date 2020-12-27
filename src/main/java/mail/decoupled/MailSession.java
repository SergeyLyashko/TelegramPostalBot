package mail.decoupled;

import javax.mail.Session;

public interface MailSession {

    void setMailAuthenticator(MailAuthenticator mailAuthenticator);
    MailAuthenticator getMailAuthenticator();

    void setMailProperties(MailProperties mailProperties);
    MailProperties getMailProperties();

    Session getSession();
}
