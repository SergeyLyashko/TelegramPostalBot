package telegrambot.decoupled;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service("botupdate")
public class PostalBotUpdate implements BotUpdate {

    /**
     *  объект Update, сериализованный в JSON.
     * @param update Этот объект представляет из себя входящее обновление.
     *               Под обновлением подразумевается действие,
     *               совершённое с ботом — например, получение сообщения от пользователя.
     * @return
     */
    @Override
    public BotApiMethod updateBot(Update update) {
        if(update.hasMessage()){
            return sendMessage(update);
        }
        return null;
    }

    private BotApiMethod sendMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        //Integer updateId = update.getUpdateId();
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        sendMessage.setChatId(chatId.toString());
        return sendMessage;
    }
}
