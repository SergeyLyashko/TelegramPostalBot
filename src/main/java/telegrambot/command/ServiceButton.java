package telegrambot.command;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class ServiceButton extends InlineKeyboardButton {

    public ServiceButton(String text){
        super();
        super.setText(text);
        super.setCallbackData("Button pressed !");
    }

    @Override
    public void setCallbackData(String callbackData) {
        super.setCallbackData(callbackData);
    }
}
