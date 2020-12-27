package mail.configuration;

import mail.decoupled.MailAuthenticator;
import org.springframework.stereotype.Component;

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
@Component("authenticator")
public class NgsMailAuthenticator extends Authenticator implements MailAuthenticator {

    private static final String LOGIN = "u-ko@ngs.ru";
    private static final String PASS = "lKyiz8";

    @Override
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(LOGIN, PASS);
    }

    @Override
    public Authenticator getAuthenticator() {
        return this;
    }

    @Override
    public String getLogin(){
        return LOGIN;
    }

    @Override
    public String getPass(){
        return PASS;
    }
}
