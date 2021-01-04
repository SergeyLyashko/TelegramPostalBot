package mail.decoupled;

import javax.mail.Session;

public interface MailSession {

    void setMailAuthenticator(MailAuthenticator mailAuthenticator);

    void setMailProperties(MailProperties mailProperties);

    Session getSession();
}
