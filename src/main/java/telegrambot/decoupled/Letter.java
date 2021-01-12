package telegrambot.decoupled;

public interface Letter {

    //Keyboard getHeaderKeyboard();

    //Keyboard getBodyKeyboard();

    String getHeader();

    String getBody();

    String getStatusNew();

    String getStatusRead();

    void init(String[] from, String text);
}
