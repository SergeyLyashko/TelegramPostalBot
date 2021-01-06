package telegrambot.configuration;

import telegrambot.decoupled.PostalBot;

public interface Mailing {

    //void setKeyBoard(KeyBoard keyBoard);
    void setPostalBot(PostalBot postalBot);

    void createNewMail(String[] from, String text);

    void sendMailHeader();

    void sendMailBody();
}
