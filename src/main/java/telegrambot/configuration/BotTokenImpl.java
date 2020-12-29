package telegrambot.configuration;

import org.springframework.stereotype.Component;
import telegrambot.decoupled.BotToken;

@Component("bottoken")
public class BotTokenImpl implements BotToken {

    private static final String BOT_NAME = "PostalService_Bot";
    private static final String BOT_TOKEN = "1473280674:AAEtKNNzsRuAwbv4ZnDrqrq8vLBC27ZYQ0A";

    public String getBotName(){
        return BOT_NAME;
    }

    public String getBotToken(){
        return BOT_TOKEN;
    }
}
