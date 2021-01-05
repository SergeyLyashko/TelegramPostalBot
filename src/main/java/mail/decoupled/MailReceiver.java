package mail.decoupled;

import telegrambot.decoupled.PostalBot;

public interface MailReceiver {

    void setMailConnection(MailConnection mailConnect);

    void setMailParser(MailParser mailParser);

    void setPostalBot(PostalBot bot);

    void receiveMail();
}
