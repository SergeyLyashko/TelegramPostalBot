package mail.decoupled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegrambot.decoupled.PostalBot;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Arrays;

/**
 * Получение сообщений
 */
@Service("mailReceiver")
public class MailReceiverImpl implements MailReceiver {

    private String folderName = "INBOX";
    private MailSession mailSession;
    private PostalSettings postalSettings;
    private MailAuthenticator mailAuthenticator;

    private PostalBot bot;

    @Override
    public void setBot(PostalBot bot){
        this.bot = bot;
    }

    @Override
    public PostalBot getBot(){
        return bot;
    }

    @Override
    @Autowired
    public void setMailSession(MailSession mailSession){
        this.mailSession = mailSession;
    }

    @Override
    public MailSession getMailSession(){
        return mailSession;
    }

    @Override
    @Autowired
    public void setImapPostalSettings(PostalSettings postalSettings){
        this.postalSettings = postalSettings;
    }

    @Override
    public PostalSettings getImapPostalSettings(){
        return postalSettings;
    }

    @Override
    @Autowired
    public void setMailAuthenticator(MailAuthenticator mailAuthenticator){
        this.mailAuthenticator = mailAuthenticator;
    }

    @Override
    public MailAuthenticator getMailAuthenticator(){
        return mailAuthenticator;
    }

    /**
     * Получение писем с сервера почты
     */
    @Override
    public void receiveMail() {
        Store store = getStore();
        if (store != null){
            connect(store);
            Folder folder = getFolder(store);
            if(folder != null) {
                receiveNewMessage(folder);
            }
        }
    }

    private Store getStore(){
        Session session = mailSession.getSession();
        try {
            return session.getStore();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Подключение к почтовому серверу
     * Store представляет собой хранилище сообщений,
     * поддерживаемых почтовым сервером и сгруппированных по владельцу
     */
    private void connect(Store store) {
        String login = mailAuthenticator.getLogin();
        String pass = mailAuthenticator.getPass();
        String imapServer = postalSettings.getServer();
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
    private Folder getFolder(Store store) {
        try {
            Folder folder = store.getFolder(folderName);
            folder.open(Folder.READ_WRITE);
            return folder;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // получение только новых сообщений
    private void receiveNewMessage(Folder folder) {
        Flags seen = new Flags(Flags.Flag.SEEN);
        FlagTerm flagUnseen = new FlagTerm(seen,  false);
        try {
            Message[] messages = folder.search(flagUnseen);
            Arrays.stream(messages).forEach(this::showContent);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // TODO переписать метод как метод отправки сообщения ботом в чат
    private void showContent(Message message) {
        try {
            Multipart content = (Multipart) message.getContent();
            BodyPart bodyPart = content.getBodyPart(0);
            System.out.println(bodyPart.getContent());
            // TODO передача сообщения боту
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId("528647782");
            sendMessage.setText("Для чего я создан ?");
            bot.sendMessage(sendMessage);
            // TODO варианты получения текстового сообщения или в виде MIME
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
