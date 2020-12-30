package telegrambot.decoupled;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service("botupdate")
public class PostalBotUpdate implements BotUpdate {

    private SendMessage sendMessage;

    /**
     *  объект Update, сериализованный в JSON.
     * @param update Этот объект представляет из себя входящее обновление.
     *               Под обновлением подразумевается действие,
     *               совершённое с ботом — например, получение сообщения от пользователя.
     * @return
     */
    @Override
    public void updateMessage(String message) {
        this.sendMessage = new SendMessage();
        sendMessage.setText("Привет, Человек!");// TEST
    }

    @Override
    public SendMessage send(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        System.out.println("chatID: "+chatId.toString()); // 528647782 //528647782
        sendMessage.setChatId(chatId.toString());
        return sendMessage;
    }
}
