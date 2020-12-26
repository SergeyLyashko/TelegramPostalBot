package telegrambot;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Обработчик сообщений по webhook
 * 
 */
public final class MessageWebhookHandlers extends TelegramWebhookBot {

    private final String botName;
    private final String botToken;

    public MessageWebhookHandlers(String botName, String botToken){
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    /**
     *  объект Update, сериализованный в JSON.
     * @param update Этот объект представляет из себя входящее обновление.
     *               Под обновлением подразумевается действие,
     *               совершённое с ботом — например, получение сообщения от пользователя.
     * @return
     */
    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        if(update.hasMessage()){
            return sendMessage(update);
        }
        return null;
    }

    private SendMessage sendMessage(Update update){
        SendMessage sendMessage = new SendMessage();
        Integer updateId = update.getUpdateId();
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        sendMessage.setChatId(chatId.toString());
        return sendMessage;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
