package telegrambot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegrambot.decoupled.PostalBot;

@Component("mailing")
public class MailingImpl implements Mailing {

    private static final String HEADER_TEXT = "new mail:";
    private static final String HEADER_BUTTON_PREFIX = "@ from: ";

    /*
        private String fromName;
        private String fromAddress;
        private String mailText;
        */
    //private KeyBoard keyBoard;
    private PostalBot postalBot;
    private SendMessage headerMessage;
    private SendMessage bodyMessage;
    private Long chatId = 528647782L; // TODO TEST
    private KeyBoard serviceKeyBoard;

    /*
    public MailingImpl(String[] from, String text){
        if(from.length < 2){
            this.fromName = "empty";
            this.fromAddress = from[0];
        }else{
            this.fromName = from[0];
            this.fromAddress = from[1];
        }
        this.mailText = text;
    }*/
    /*
    @Override
    @Autowired
    public void setKeyBoard(KeyBoard keyBoard){
        this.keyBoard = keyBoard;
    }*/

    @Override
    @Autowired
    public void setPostalBot(PostalBot postalBot){
        this.postalBot = postalBot;
    }

    @Override
    public void createNewMail(String[] from, String text) {
        createHeaderMessage(from);
        createBodyMessage(text);
    }

    @Override
    public void sendMailHeader() {
        postalBot.sendMessage(headerMessage);
    }

    @Override
    public void sendMailBody() {
        postalBot.sendMessage(bodyMessage);
    }

    private void createBodyMessage(String text) {
        this.serviceKeyBoard = new ServiceKeyBoard();
        ChatButton[] buttons = new ChatButton[2];
        buttons[0] = new ChatServiceButton("delete");
        buttons[1] = new ChatServiceButton("turn");
        this.bodyMessage = mailing(text, buttons);
    }

    private void createHeaderMessage(String[] from){
        this.serviceKeyBoard = new ServiceKeyBoard();
        String buttonText = HEADER_BUTTON_PREFIX+from[0];
        this.headerMessage = mailing(HEADER_TEXT, new ChatServiceButton(buttonText));
    }

    private SendMessage mailing(String text, ChatButton...buttons){
        for(ChatButton chatButton: buttons){
            serviceKeyBoard.addButton(chatButton);
        }
        return createMessage(text);
    }

    private SendMessage createMessage(String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(serviceKeyBoard.getInlineKeyboardMarkup());
        return sendMessage;
    }

}
