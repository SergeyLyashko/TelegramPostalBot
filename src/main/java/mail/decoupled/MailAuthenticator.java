package mail.decoupled;

import javax.mail.Authenticator;

public interface MailAuthenticator {

    Authenticator getAuthenticator();

    String getLogin();

    String getPass();
}
