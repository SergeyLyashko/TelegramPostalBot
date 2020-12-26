package mail.ngs;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

/**
 * Получение сообщений
 */
public class MailReceiver implements Propertiesable {

    private String folderName = "INBOX";
    private PostalData postalData;

    public MailReceiver(PostalData postalData) {
        this.postalData = postalData;
    }

    /*
     * Store представляет собой хранилище сообщений,
     * поддерживаемых почтовым сервером и сгруппированных по владельцу,
     */
    public void readMail(NgsMailAuthenticator mailAuthenticator, Session newSession) {
        try {
            Store store = newSession.getStore();
            connect(store, mailAuthenticator);
            getMessage(store);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    /*
     * Параметры запроса на получение сообщений
     */
    public Properties getProperties() {
        String imapPort = postalData.getImapPort();
        Properties properties = new Properties();
        properties.put("mail.debug", "false");
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.port", imapPort);
        return properties;
    }

    /*
     * Подключение к почтовому серверу
     */
    private void connect(Store store, NgsMailAuthenticator mailAuthenticator) {
        String login = mailAuthenticator.getLogin();
        String pass = mailAuthenticator.getPass();
        String imapServer = postalData.getImapServer();
        try {
            store.connect(imapServer, login, pass);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /*
     * Folder - предоставляет возможность иерархически организовывать сообщения.
     * Папки могут содержать сообщения и поддиректории.
     */
    private void getMessage(Store store) {
        try {
            Folder folder = store.getFolder(folderName);
            folder.open(Folder.READ_ONLY);
            int lastMessage = folder.getMessageCount();
            Message message = folder.getMessage(lastMessage);
            readMail(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void readMail(Message message) {
        try {
            Multipart content = (Multipart) message.getContent();
            BodyPart bodyPart = content.getBodyPart(0);
            System.out.println(bodyPart.getContent());
            /*for(int index=0; index<content.getCount(); index++){
                BodyPart bodyPart = content.getBodyPart(index);
                if(bodyPart.getFileName() == null) {
                    System.out.println("index: " + index + " test message: " + bodyPart.getContent());
                }
            }*/
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
