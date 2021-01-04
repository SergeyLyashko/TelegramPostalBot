package mail.decoupled;

import javax.mail.Message;

public interface MailParser {

    void setMessage(Message message);
}
