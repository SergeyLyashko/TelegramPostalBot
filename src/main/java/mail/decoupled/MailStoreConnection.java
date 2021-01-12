package mail.decoupled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.util.Properties;

@Service("connection")
public class MailStoreConnection implements MailConnection {

    private MailAuthenticator mailAuthenticator;
    private MailProperties mailProperties;
    private PostalSettings postalSettings;

    @Override
    @Autowired
    public void setImapPostalSettings(PostalSettings postalSettings){
        this.postalSettings = postalSettings;
    }

    @Override
    @Autowired
    public void setMailAuthenticator(MailAuthenticator mailAuthenticator){
        this.mailAuthenticator = mailAuthenticator;
    }

    @Override
    @Autowired
    public void setMailProperties(MailProperties mailProperties){
        this.mailProperties = mailProperties;
    }

    @Override
    public Store storeConnection(){
        Session session = getSession();
        try {
            Store store = session.getStore();
            if(store != null){
                connection(store);
                return store;
            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * определяет основную сессию почты.
     * Чтобы передать значения в объект сессии использованы Properties.
     */
    private Session getSession(){
        Properties newProperties = mailProperties.createNewProperties();
        Authenticator authenticator = mailAuthenticator.getAuthenticator();
        Session session = Session.getDefaultInstance(newProperties, authenticator);
        session.setDebug(false);
        return session;
    }

    /*
     * Подключение к почтовому серверу
     * Store представляет собой хранилище сообщений,
     * поддерживаемых почтовым сервером и сгруппированных по владельцу
     */
    private void connection(Store store) {
        String login = mailAuthenticator.getLogin();
        String pass = mailAuthenticator.getPass();
        String imapServer = postalSettings.getServer();
        try {
            store.connect(imapServer, login, pass);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
