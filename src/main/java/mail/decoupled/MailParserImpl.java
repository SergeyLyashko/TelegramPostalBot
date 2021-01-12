package mail.decoupled;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("parser")
public class MailParserImpl implements MailParser {

    private static final String TEXT = "text/plain; charset=utf-8";
    private static final String WITHOUT_RETURN_CARET_REGEX = "([^\\n]+[\\n]{1,2}.*)"; /*([^\\n]+[\\n].*)";*/
    private static final String ADDRESS_SPLIT_REGEX = "([\\S\\s]+).([<][\\S]+[>])";

    private Message message;

    @Override
    public void parseMessage(Message message) {
        this.message = message;
    }

    @Override
    public String getMailText(){
        try {
            Multipart content = (Multipart) message.getContent();
            String stringContent = defineStringContent(content);
            return textParser(stringContent);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String defineStringContent(Multipart content) throws MessagingException, IOException {
        // TODO варианты получения текстового сообщения или в виде MIME
        for(int index=0; index<content.getCount(); index++){
            BodyPart bodyPart = content.getBodyPart(index);
            String contentType = bodyPart.getContentType();
            System.out.println("num: "+index+" content: "+contentType);// TODO test
            if(contentType.equals(TEXT)){
                return (String) bodyPart.getContent();
            }
        }
        return null;
    }

    @Override
    public String[] getFrom(){
        try {
            Address[] from = message.getFrom();
            String decodeAddress = decodeAddress(from);
            return addressParser(decodeAddress);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String decodeAddress(Address[] addresses){
        for(Address address: addresses){
            if(address != null){
                try {
                    return MimeUtility.decodeText(address.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /*
     *  Mail from string splitter on name & address
     */
    private String[] addressParser(String decodeAddress) {
        Pattern compile = Pattern.compile(ADDRESS_SPLIT_REGEX);
        Matcher matcher = compile.matcher(decodeAddress);
        String[] from = new String[2];
        while (matcher.find()) {
            String name = matcher.group(1);
            from[0] = name;
            String address = matcher.group(2);
            from[1] = address;
        }
        return from;
    }

    private String textParser(String content) {
        Pattern compile = Pattern.compile(WITHOUT_RETURN_CARET_REGEX);
        Matcher matcher = compile.matcher(content);
        StringBuilder text = new StringBuilder();
        while (matcher.find()){
            text.append(matcher.group(1));
        }
        //System.out.println(text.toString()); // TODO TEST
        return text.toString();
    }
}
