package mail.decoupled;

import telegrambot.decoupled.PostalBot;

public interface MailReceiver {

    void setMailSession(MailSession mailSession);
    MailSession getMailSession();

    void setImapPostalSettings(PostalSettings postalSettings);
    PostalSettings getImapPostalSettings();

    void setMailAuthenticator(MailAuthenticator mailAuthenticator);
    MailAuthenticator getMailAuthenticator();

    void receiveMail();

    void setBot(PostalBot bot);
    PostalBot getBot();
}
