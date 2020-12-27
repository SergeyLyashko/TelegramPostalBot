package mail.configuration;

import mail.decoupled.PostalSettings;
import org.springframework.stereotype.Component;

@Component("imapsettings")
public class NgsImapPostalSettings implements PostalSettings {

    private static final String IMAP_SERVER = "mail.ngs.ru";
    private static final String IMAP_PORT = "143";

    @Override
    public String getServer(){
        return IMAP_SERVER;
    }

    @Override
    public String getPort(){
        return IMAP_PORT;
    }
}
