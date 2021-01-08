package telegrambot.configuration;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component("button")
public class ChatServiceButton implements ChatButton {

    private final InlineKeyboardButton inlineKeyboardButton;

    public ChatServiceButton(){
        inlineKeyboardButton = new InlineKeyboardButton();
    }

    public void setButtonName(String buttonName){
        inlineKeyboardButton.setText(buttonName);
    }

    public void setCallbackData(String data){
        inlineKeyboardButton.setCallbackData(data);
    }

    @Override
    public InlineKeyboardButton getInlineButton() {
        return inlineKeyboardButton;
    }
}
