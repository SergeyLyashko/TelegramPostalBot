package telegrambot.decoupled;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegrambot.command.Command;

public interface PostalBot {

    void setStartCommand(Command command);
    void setHelpCommand(Command command);

    void setMailService(MailService mailService);

    void registerBot();

    void deliverMail(String[] from, String text);

    Message sendMessage(SendMessage message);

    void deleteMessage(DeleteMessage message);

    void sendMessage(EditMessageText headerMessage);
}
