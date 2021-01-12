package telegrambot.decoupled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component("bodyKeyboard")
public class BodyKeyboard implements Keyboard {

    private final InlineKeyboardMarkup inlineKeyboard;
    private final List<InlineKeyboardButton> keyboardList;

    @Autowired
    public BodyKeyboard(String delButton, String hideButton){
        inlineKeyboard = new InlineKeyboardMarkup();
        keyboardList = new ArrayList<>();
        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(keyboardList);
        inlineKeyboard.setKeyboard(row);
        addButtonName(delButton);
        addButtonName(hideButton);
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        return inlineKeyboard;
    }

    @Override
    public void addButtonName(String buttonName) {
        InlineKeyboardButton inlineButton = new InlineKeyboardButton(buttonName);
        inlineButton.setCallbackData(buttonName);
        keyboardList.add(inlineButton);
    }
}
