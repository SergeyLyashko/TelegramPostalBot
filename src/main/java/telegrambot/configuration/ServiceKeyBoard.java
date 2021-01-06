package telegrambot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegrambot.decoupled.PostalBot;

import java.util.ArrayList;
import java.util.List;

@Component("keyboard")
public class ServiceKeyBoard implements KeyBoard {

    private final InlineKeyboardMarkup inlineKeyboard;
    private Long chatId = 528647782L; // TODO TEST
    private List<InlineKeyboardButton> keyboardList;
    private final List<List<InlineKeyboardButton>> row;
    private PostalBot postalBot;

    public ServiceKeyBoard(){
        inlineKeyboard = new InlineKeyboardMarkup();
        keyboardList = new ArrayList<>();
        row = new ArrayList<>();
        row.add(keyboardList);
        inlineKeyboard.setKeyboard(row);
    }

    @Override
    @Autowired
    public void setPostalBot(PostalBot postalBot){
        this.postalBot = postalBot;
    }

    @Override
    public void sendNewKeyboard(String text){
        SendMessage keyboardMessage = new SendMessage();
        keyboardMessage.setChatId(chatId.toString());
        keyboardMessage.setText(text);
        keyboardMessage.setReplyMarkup(inlineKeyboard);
        postalBot.sendMessage(keyboardMessage);

    }

    public void addNewRowButton(){
        keyboardList = new ArrayList<>();
        row.add(keyboardList);
    }

    @Override
    public void addButton(ChatButton chatButton) {
        InlineKeyboardButton button = chatButton.getInlineButton();
        keyboardList.add(button);
    }
}
