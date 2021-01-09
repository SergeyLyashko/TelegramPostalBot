package telegrambot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
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
public class BotMailingService implements MailingService {

    private PostalBot postalBot;
    private final Map<Integer, LetterImpl> mailBox;
    private Long chatId = 528647782L; // TODO TEST

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
        LetterImpl letter = new LetterImpl(from, text);
        SendMessage sendHeader = letter.getHeaderMessage();
        sendHeader.setChatId(chatId.toString());
        Message headerMessage = postalBot.sendMessage(sendHeader);
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
        LetterImpl letter = mailBox.get(messageId);
        EditMessageText turnOffMessage = letter.getTurnOffMessage();
        turnOffMessage.setChatId(chatId.toString());
        turnOffMessage.setMessageId(messageId);
        postalBot.sendMessage(turnOffMessage);
    }

    private void deleteMessage(Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId.toString());
        deleteMessage.setMessageId(messageId);
        postalBot.deleteMessage(deleteMessage);
        mailBox.remove(messageId);
    }

    private void callbackBodyMessage(Integer messageId){
        LetterImpl letter = mailBox.get(messageId);
        EditMessageText bodyMessage = letter.getBodyMessage();
        bodyMessage.setChatId(chatId.toString());
        bodyMessage.setMessageId(messageId);
        postalBot.sendMessage(bodyMessage);
    }
}
