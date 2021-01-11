package telegrambot.configuration;

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
import telegrambot.decoupled.PostalBot;

import java.util.HashMap;
import java.util.Map;

@Component("mailing")
public class BotMailService implements MailService, ApplicationContextAware {

    private PostalBot postalBot;
    private final Map<Integer, Letter> mailBox;
    private Long chatId = 528647782L; // TODO TEST
    private ApplicationContext context;

    @Override
    @Autowired
    public void setPostalBot(PostalBot postalBot){
        this.postalBot = postalBot;
    }

    public BotMailService(){
        mailBox = new HashMap<>();
    }

    @Override
    public void handle(String[] from, String text) {
        Letter letter = context.getBean("letter", Letter.class);
        letter.init(from, text);
        KeyBoard headerKeyboard = letter.getHeaderKeyboard();
        String letterNew = letter.getLetterNewText();
        Message sendingMessage = sendMessage(headerKeyboard, letterNew);
        Integer letterId = sendingMessage.getMessageId();
        mailBox.put(letterId, letter);
    }

    private Message sendMessage(KeyBoard keyBoard, String text){
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
        KeyBoard headerKeyboard = letter.getHeaderKeyboard();
        String readText = letter.getLetterReadText();
        sendEditMessage(messageId, headerKeyboard, readText);
    }

    private void showMessage(Integer messageId){
        Letter letter = mailBox.get(messageId);
        KeyBoard bodyKeyboard = letter.getBodyKeyboard();
        String bodyText = letter.getBodyText();
        sendEditMessage(messageId, bodyKeyboard, bodyText);
    }

    private void sendEditMessage(Integer messageId, KeyBoard newKeyboard, String newText){
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
