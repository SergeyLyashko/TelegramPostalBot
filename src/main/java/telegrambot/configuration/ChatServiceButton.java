package telegrambot.configuration;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component("button")
public class ChatServiceButton implements ChatButton {

    private final InlineKeyboardButton inlineKeyboardButton;

    public ChatServiceButton(String text){
        inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData("button pressed");
    }

    @Override
    public InlineKeyboardButton getInlineButton() {
        return inlineKeyboardButton;
    }
}
