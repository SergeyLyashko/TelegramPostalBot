package telegrambot.decoupled;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component("headerKeyboard")
@Scope("prototype")
public class HeaderKeyboard implements Keyboard {

    private final InlineKeyboardMarkup inlineKeyboard;
    private final List<InlineKeyboardButton> keyboardList;

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        return inlineKeyboard;
    }

    public HeaderKeyboard(){
        inlineKeyboard = new InlineKeyboardMarkup();
        this.keyboardList = new ArrayList<>();
        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(keyboardList);
        inlineKeyboard.setKeyboard(row);
    }

    @Override
    public void addButtonName(String buttonName) {
        InlineKeyboardButton inlineButton = new InlineKeyboardButton(buttonName);
        inlineButton.setCallbackData("show");
        keyboardList.add(inlineButton);
    }
}
