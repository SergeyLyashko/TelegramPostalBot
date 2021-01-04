package mail.decoupled;

import java.util.Properties;

public interface MailProperties {

    void setPostalSettings(PostalSettings postalSettings);

    Properties createNewProperties();
}
