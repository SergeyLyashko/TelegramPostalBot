package telegrambot.decoupled;

import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

public interface BotRequestSettings {

    SetWebhook getWebhook();

}
