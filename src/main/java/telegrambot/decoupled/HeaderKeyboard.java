package telegrambot.decoupled;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component("headerKeyboard")
@Scope("prototype")
public class HeaderKeyboard implements Keyboard {

    private InlineKeyboardMarkup inlineKeyboard;

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        return inlineKeyboard;
    }

    @Override
    public void addButtonName(String buttonName) {
        inlineKeyboard = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardList = new ArrayList<>();
        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(keyboardList);
        inlineKeyboard.setKeyboard(row);
        InlineKeyboardButton inlineButton = new InlineKeyboardButton(buttonName);
        inlineButton.setCallbackData("show");
        keyboardList.add(inlineButton);
    }
}
