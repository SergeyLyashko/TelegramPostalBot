package telegrambot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import telegrambot.decoupled.BotToken;

@Component("token")
public class BotTokenImpl implements BotToken {

    private final String botName;
    private final String botToken;

    @Autowired
    public BotTokenImpl(String botName, String botToken){
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public String getBotName(){
        return botName;
    }

    @Override
    public String getBotToken(){
        return botToken;
    }
}
