package mail.decoupled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Параметры подключения
 */
@Service("properties")
public class MailPropertiesImpl implements MailProperties {

    private final Properties properties;
    private PostalSettings postalSettings;

    @Override
    @Autowired
    public void setPostalSettings(PostalSettings postalSettings){
        this.postalSettings = postalSettings;
    }

    public MailPropertiesImpl(){
        this.properties = new Properties();
    }

    @Override
    public Properties createNewProperties() {
        String imapPort = postalSettings.getPort();
        properties.put("mail.debug", "false");
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.port", imapPort);
        return properties;
    }
}
