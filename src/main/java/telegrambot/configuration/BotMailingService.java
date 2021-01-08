package telegrambot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegrambot.decoupled.PostalBot;

import java.util.HashMap;
import java.util.Map;

@Component("mailing")
public class BotMailingService implements MailingService {

    private PostalBot postalBot;
    private final Map<Integer, ChatLetter> mailBox;

    @Override
    @Autowired
    public void setPostalBot(PostalBot postalBot){
        this.postalBot = postalBot;
    }

    public BotMailingService(){
        mailBox = new HashMap<>();
    }

    @Override
    public void createMail(String[] from, String text) {
        ChatLetter letter = new ChatLetter(from, text);
        Message headerMessage = postalBot.sendMessage(letter.getHeaderMessage());
        Integer letterId = headerMessage.getMessageId();
        mailBox.put(letterId, letter);
    }

    @Override
    public void executeCallback(CallbackQuery callbackQuery) {
        Integer messageId = callbackQuery.getMessage().getMessageId();
        String data = callbackQuery.getData();
        switch (data){
            case "delete":
                deleteMessage(messageId);
                break;
            case "turn off":
                turnOffBodyMessage(messageId);
                break;
            case "header":
                callbackBodyMessage(messageId);
                break;
            default:
                throw new IllegalArgumentException("Bad callback data");
        }
    }

    private void turnOffBodyMessage(Integer messageId) {
        ChatLetter letter = mailBox.get(messageId);
        EditMessageText turnOffMessage = letter.getTurnOffMessage();
        turnOffMessage.setMessageId(messageId);
        postalBot.sendMessage(turnOffMessage);
    }

    private void deleteMessage(Integer messageId) {
        ChatLetter letter = mailBox.get(messageId);
        DeleteMessage deleteMessage = letter.getDeleteMessage();
        deleteMessage.setMessageId(messageId);
        postalBot.deleteMessage(deleteMessage);
    }

    private void callbackBodyMessage(Integer messageId){
        ChatLetter letter = mailBox.get(messageId);
        EditMessageText bodyMessage = letter.getBodyMessage();
        bodyMessage.setMessageId(messageId);
        postalBot.sendMessage(bodyMessage);
    }
}
