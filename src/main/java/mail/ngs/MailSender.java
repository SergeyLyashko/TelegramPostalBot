package mail.ngs;

import javax.mail.Session;
import java.util.Properties;

public class MailSender implements Propertiesable {

    private static final String SMTP_SERVER = "smtp.ngs.ru";
    private static final String SMTP_PORT = "465";
    private NgsMailAuthenticator mailAuthenticator;

    public void sendMail(NgsMailAuthenticator mailAuthenticator, Session newSession) {

    }

    // Настройка SMTP SSL
    public Properties getProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.host"               , SMTP_SERVER);
        properties.put("mail.smtp.port"               , SMTP_PORT);
        properties.put("mail.smtp.auth"               , "true"     );
        properties.put("mail.smtp.ssl.enable"         , "true"     );
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return properties;
    }
}
