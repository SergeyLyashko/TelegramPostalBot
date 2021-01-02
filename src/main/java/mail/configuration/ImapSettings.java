package mail.configuration;

import mail.decoupled.PostalSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("imapsettings")
public class ImapSettings implements PostalSettings {

    private final String imapServer;
    private final String imapPort;

    @Autowired
    public ImapSettings(String imapServer, String imapPort){
        this.imapServer = imapServer;
        this.imapPort = imapPort;
    }

    @Override
    public String getServer(){
        return imapServer;
    }

    @Override
    public String getPort(){
        return imapPort;
    }
}
