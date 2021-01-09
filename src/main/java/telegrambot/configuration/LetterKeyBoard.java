package telegrambot.configuration;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component("keyboard")
public class LetterKeyBoard implements KeyBoard {

    private final InlineKeyboardMarkup inlineKeyboard;
    private final List<InlineKeyboardButton> keyboardList;
    private final List<List<InlineKeyboardButton>> row;

    public LetterKeyBoard(String...buttons){
        inlineKeyboard = new InlineKeyboardMarkup();
        keyboardList = new ArrayList<>();
        row = new ArrayList<>();
        row.add(keyboardList);
        inlineKeyboard.setKeyboard(row);
        createButtons(buttons);
    }

    // TODO optimize it!
    private void createButtons(String[] buttons) {
        for(String button : buttons){
            InlineKeyboardButton inlineButton = new InlineKeyboardButton(button);
            if(isHeader(button)){
                inlineButton.setCallbackData("show");
            }else{
                inlineButton.setCallbackData(button);
            }
            keyboardList.add(inlineButton);
        }
    }

    private boolean isHeader(String text){
        String[] split = text.split(" ");
        return split[0].equals("@");
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        return inlineKeyboard;
    }
}
