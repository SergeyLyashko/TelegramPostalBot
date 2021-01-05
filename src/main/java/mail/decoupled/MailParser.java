package mail.decoupled;

import javax.mail.Message;

public interface MailParser {

    void parseMessage(Message message);

    String getMailText();

    String[] getFrom();
}
