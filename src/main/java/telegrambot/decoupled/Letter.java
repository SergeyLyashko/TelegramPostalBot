package telegrambot.decoupled;

public interface Letter {

    //Keyboard getHeaderKeyboard();

    //Keyboard getBodyKeyboard();

    String getHeader();

    String getBody();

    String getLetterNewText();

    String getLetterReadText();

    void init(String[] from, String text);
}
