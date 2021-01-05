package mail.decoupled;

import javax.mail.Store;

public interface MailConnection {

    void setMailAuthenticator(MailAuthenticator mailAuthenticator);

    void setMailProperties(MailProperties mailProperties);

    void setImapPostalSettings(PostalSettings postalSettings);

    Store storeConnection();
}
