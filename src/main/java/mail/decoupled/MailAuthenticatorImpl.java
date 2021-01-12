package mail.decoupled;

import org.springframework.beans.factory.annotation.Autowired;
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
public class MailAuthenticatorImpl extends Authenticator implements MailAuthenticator {
    private final String login;
    private final String pass;

    @Autowired
    public MailAuthenticatorImpl(String login, String pass){
        this.login = login;
        this.pass = pass;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(login, pass);
    }

    @Override
    public Authenticator getAuthenticator() {
        return this;
    }

    @Override
    public String getLogin(){
        return login;
    }

    @Override
    public String getPass(){
        return pass;
    }
}
