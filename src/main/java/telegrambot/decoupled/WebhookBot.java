package telegrambot.decoupled;

public interface WebhookBot {

    void setPostalBotToken(BotToken botToken);
    BotToken getPostalBotToken();

    void setBotUpdate(BotUpdate botUpdate);
    BotUpdate getBotUpdate();

    void setRequestSettings(BotRequestSettings requestSettings);
    BotRequestSettings getRequestSettings();

    void registerBot();

}
