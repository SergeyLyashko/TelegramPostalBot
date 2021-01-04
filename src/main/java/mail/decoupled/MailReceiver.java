package mail.decoupled;

import telegrambot.decoupled.PostalBot;

public interface MailReceiver {

    void setMailSession(MailSession mailSession);

    void setImapPostalSettings(PostalSettings postalSettings);

    void setMailAuthenticator(MailAuthenticator mailAuthenticator);

    void setMailParser(MailParser mailParser);

    void setPostalBot(PostalBot bot);

    void receiveMail();
}
