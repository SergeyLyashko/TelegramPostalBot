package mail.decoupled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegrambot.decoupled.PostalBot;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import java.io.IOException;
import java.util.Arrays;

/**
 * Получение сообщений
 */
@Service("mailReceiver")
public class MailReceiverImpl implements MailReceiver {

    private String folderName = "INBOX";
    private MailConnection mailConnection;
    private MailParser mailParser;
    private PostalBot postalBot;

    @Override
    @Autowired
    @Lazy
    public void setPostalBot(PostalBot postalBot){
        this.postalBot = postalBot;
    }

    @Override
    @Autowired
    public void setMailConnection(MailConnection mailConnection){
        this.mailConnection = mailConnection;
    }

    @Override
    @Autowired
    public void setMailParser(MailParser mailParser){
        this.mailParser = mailParser;
    }

    /**
     * Получение писем с сервера почты
     */
    @Override
    public void receiveMail() {
        Store store = mailConnection.storeConnection();
        if (store != null){
            Folder folder = getFolder(store);
            if(folder != null) {
                receiveNewMessage(folder);
            }
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
            Arrays.stream(messages).forEach(this::mailParser);
            //Arrays.stream(messages).forEach(this::showContent);//TODO TEST
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void mailParser(Message message){
        mailParser.setMessage(message);
    }
///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO DEL
    private void showContent(Message message) {
        try {
            Multipart content = (Multipart) message.getContent();
            BodyPart bodyPart = content.getBodyPart(0);
            System.out.println(bodyPart.getContent());
            // TODO передача сообщения боту
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId("528647782");
            sendMessage.setText("Для чего я создан ?");
            postalBot.sendMessage(sendMessage);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
