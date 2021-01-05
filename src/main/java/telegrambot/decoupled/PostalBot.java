package telegrambot.decoupled;

public interface PostalBot {

    void setPostalBotToken(BotToken botToken);
    void setStartCommand(Command command);
    void setHelpCommand(Command command);

    void registerBot();
    void deliverMail(String[] from);
}
