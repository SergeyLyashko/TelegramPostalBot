package telegrambot.decoupled;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Map;

@Component("mailing")
public class BotMailService implements MailService, ApplicationContextAware {

    private Long chatId = 528647782L; // TODO TEST

    private final Map<Integer, Letter> mailBox;
    private ApplicationContext context;
    private PostalBot postalBot;
    private Keyboard bodyKeyboard;

    @Override
    @Autowired
    public void setPostalBot(PostalBot postalBot){
        this.postalBot = postalBot;
    }

    @Override
    @Autowired
    public void setBodyKeyboard(Keyboard bodyKeyboard){
        this.bodyKeyboard = bodyKeyboard;
    }

    public BotMailService(){
        mailBox = new HashMap<>();
    }

    @Override
    public void handle(String[] from, String text) {
        Letter letter = context.getBean("letter", Letter.class);
        letter.init(from, text);
        Keyboard headerKeyboard = context.getBean("headerKeyboard", Keyboard.class);
        headerKeyboard.addButtonName(letter.getHeader());
        String letterNew = letter.getStatusNew();
        Message sendingMessage = sendMessage(headerKeyboard, letterNew);
        Integer letterId = sendingMessage.getMessageId();
        mailBox.put(letterId, letter);
    }

    private Message sendMessage(Keyboard keyBoard, String text){
        SendMessage sendHeader = new SendMessage();
        sendHeader.setChatId(chatId.toString());
        sendHeader.setText(text);
        sendHeader.setReplyMarkup(keyBoard.getInlineKeyboardMarkup());
        return postalBot.sendMessage(sendHeader);
    }

    @Override
    public void executeCallback(CallbackQuery callbackQuery) {
        Integer messageId = callbackQuery.getMessage().getMessageId();
        String data = callbackQuery.getData();
        switch (data){
            case "delete":
                deleteMessage(messageId);
                break;
            case "hide":
                hideMessage(messageId);
                break;
            case "show":
                showMessage(messageId);
                break;
            default:
                throw new IllegalArgumentException("Bad callback data");
        }
    }

    private void hideMessage(Integer messageId) {
        Letter letter = mailBox.get(messageId);
        Keyboard headerKeyboard = context.getBean("headerKeyboard", Keyboard.class);
        headerKeyboard.addButtonName(letter.getHeader());
        String readText = letter.getStatusRead();
        sendEditMessage(messageId, headerKeyboard, readText);
    }

    private void showMessage(Integer messageId){
        Letter letter = mailBox.get(messageId);
        String bodyText = letter.getBody();
        sendEditMessage(messageId, this.bodyKeyboard, bodyText);
    }

    private void sendEditMessage(Integer messageId, Keyboard newKeyboard, String newText){
        EditMessageText message = new EditMessageText();
        message.setChatId(chatId.toString());
        message.setMessageId(messageId);
        message.setText(newText);
        message.setReplyMarkup(newKeyboard.getInlineKeyboardMarkup());
        postalBot.sendMessage(message);
    }

    private void deleteMessage(Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId.toString());
        deleteMessage.setMessageId(messageId);
        postalBot.deleteMessage(deleteMessage);
        mailBox.remove(messageId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
