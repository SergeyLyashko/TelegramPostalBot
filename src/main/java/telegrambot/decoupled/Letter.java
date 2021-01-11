package telegrambot.decoupled;

public interface Letter {

    KeyBoard getHeaderKeyboard();

    KeyBoard getBodyKeyboard();

    String  getBodyText();

    String getLetterNewText();

    String getLetterReadText();

    void init(String[] from, String text);
}
