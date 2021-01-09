package telegrambot.configuration;

public interface Letter {

    KeyBoard getHeaderKeyboard();

    KeyBoard getBodyKeyboard();

    String  getBodyText();

    String getLetterNewText();

    String getLetterReadText();
}
