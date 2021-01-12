package mail.decoupled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("imapsettings")
public class ImapPostalSettings implements PostalSettings {

    private final String imapServer;
    private final String imapPort;

    @Autowired
    public ImapPostalSettings(String imapServer, String imapPort){
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
