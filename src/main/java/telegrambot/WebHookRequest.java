package telegrambot;

import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;

/**
 * https://core.telegram.org/bots/api#setwebhook
 * https://tlgrm.ru/docs/bots/api#setwebhook
 * При использовании самоподписанного сертификата, вам необходимо загрузить публичный ключ с помощью параметра certificate.
 * На текущий момент отправка обновлений через вебхуки доступна только на эти порты: 443, 80, 88, 8443.
 * https://retifrav.github.io/blog/2018/12/02/telegram-bot-webhook-ru/
 * https://tlgrm.ru/docs/bots/self-signed
 * https://tlgrm.ru/docs/bots/faq#how-can-i-make-requests-in-response-to-updates
 * https://github.com/pengrad/java-telegram-bot-api
 * https://github.com/rubenlagus/TelegramBotsExample/tree/master/src/main/java/org/telegram
 * https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started
 * https://habr.com/ru/post/528694/
 */
public class WebHookRequest extends SetWebhook {

    public static final String PORT = "443";
    public static final String INTERNALWEBHOOKURL = "https://localhost:" + PORT;

    private final String url = INTERNALWEBHOOKURL;
    private final InputFile certificate = null;

    public WebHookRequest(){
        super.setUrl(url);
        super.setCertificate(certificate);
    }
}
