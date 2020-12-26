package mail.ngs;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Класс Authenticator обеспечивает доступ к защищенным почтовым ресурсам на сервере.
 * Когда потребуется авторизация, библиотека JavaMail будет вызывать метод getPasswordAuthentication.
 * Authenticator – класс, который обеспечивает доступ к защищенным ресурсам с помощью имени пользователя и пароля.
 * Ресурсами, может быть что угодно, начиная от простых файлов на серверах. Для JavaMail, ресурс – это сервер.
 * Приложения используют этот класс при получении сессии. Когда требуется авторизация,
 * система будет вызывать метод подкласса (например, getPasswordAuthentication).
 * Этот метод подкласса может делать запросы аутентификации.
 */
class NgsMailAuthenticator extends Authenticator {

    private static final String LOGIN = "u-ko@ngs.ru";
    private static final String PASS = "lKyiz8";
    private final PostalData postalData;

    NgsMailAuthenticator() {
        postalData = new NgsPostalDataImpl();
    }

    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(LOGIN, PASS);
    }

    public String getLogin() {
        return LOGIN;
    }

    public String getPass() {
        return PASS;
    }

    public PostalData getPostalData(){
        return postalData;
    }
}
