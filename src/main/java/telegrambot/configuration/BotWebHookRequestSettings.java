package telegrambot.configuration;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import telegrambot.decoupled.BotRequestSettings;

/**
 * https://core.telegram.org/bots/api#setwebhook
 * https://tlgrm.ru/docs/bots/api#setwebhook
 * https://retifrav.github.io/blog/2018/12/02/telegram-bot-webhook-ru/
 * https://tlgrm.ru/docs/bots/self-signed
 * https://tlgrm.ru/docs/bots/faq#how-can-i-make-requests-in-response-to-updates
 * https://github.com/pengrad/java-telegram-bot-api
 * https://github.com/rubenlagus/TelegramBotsExample/tree/master/src/main/java/org/telegram
 * https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started
 * https://habr.com/ru/post/528694/
 *
 * Запрос по webhook
 */
@Component("webhookrequest")
public class BotWebHookRequestSettings extends SetWebhook implements BotRequestSettings {

    // На текущий момент отправка обновлений через вебхуки доступна только на эти порты: 443, 80, 88, 8443.
    public static final String PORT = "443";
    // Host url
    public static final String INTERNAL_WEBHOOK_URL = "https://postalservicebot.herokuapp.com:";
    // При использовании самоподписанного сертификата, вам необходимо загрузить публичный ключ с помощью параметра certificate.
    // https://tlgrm.ru/docs/bots/self-signed#java-keystore
    public static final String CERTIFICATE_SOURCE = "TelegramPostalBot.cer";

    public BotWebHookRequestSettings(){
        super.setUrl(INTERNAL_WEBHOOK_URL+PORT);
        InputFile certificate = new InputFile(CERTIFICATE_SOURCE);
        super.setCertificate(certificate);
    }

    @Override
    public SetWebhook getWebhook(){
        return this;
    }
}
