package mail.ngs;

public class Main {
    public static void main(String...args){

        NgsMailAuthenticator ngsMailAuthenticator = new NgsMailAuthenticator();
        MailHandler mailHandler = new MailHandler(ngsMailAuthenticator);
        mailHandler.receiveMail();
    }
}
