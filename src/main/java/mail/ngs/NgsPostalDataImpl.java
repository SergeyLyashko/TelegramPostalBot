package mail.ngs;

public class NgsPostalDataImpl implements PostalData {

    private static final String IMAP_SERVER = "mail.ngs.ru";
    private static final String IMAP_PORT = "143";

    public String getImapServer(){
        return IMAP_SERVER;
    }

    public String getImapPort(){
        return IMAP_PORT;
    }

}
