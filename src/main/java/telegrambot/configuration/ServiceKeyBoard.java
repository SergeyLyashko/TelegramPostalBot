package telegrambot.configuration;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ServiceKeyBoard {

    private final InlineKeyboardMarkup inlineKeyboard;
    private List<InlineKeyboardButton> keyboardList;
    private final List<List<InlineKeyboardButton>> row;

    public ServiceKeyBoard(){
        inlineKeyboard = new InlineKeyboardMarkup();
        keyboardList = new ArrayList<>();
        row = new ArrayList<>();
        row.add(keyboardList);
        inlineKeyboard.setKeyboard(row);
    }

    public SendMessage sendKeyboard(Long chatId, String text){
        SendMessage keyboardMessage = new SendMessage();
        keyboardMessage.setChatId(chatId.toString());
        keyboardMessage.setText(text);
        keyboardMessage.setReplyMarkup(inlineKeyboard);
        return keyboardMessage;
    }

    public void addButton(InlineKeyboardButton button){
        keyboardList.add(button);
    }

    public void addNewRowButton(){
        keyboardList = new ArrayList<>();
        row.add(keyboardList);
    }
}
