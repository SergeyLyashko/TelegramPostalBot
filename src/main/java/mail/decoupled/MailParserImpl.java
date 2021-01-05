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
    private static final String WITHOUT_RETURN_CARET_REGEX = "([^\\n]+[\\n].?)";
    private static final String ADDRESS_SPLIT_REGEX = "([\\S\\s]+).([<][\\S]+[>])";

    private Message message;

    @Override
    public void parseMessage(Message message) {
        this.message = message;
        // TODO test show
        //String s = parseMailText();
        //System.out.println(s);
        //getFrom();
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
                System.out.println("addr: "+address.toString());// TODO TEST
                try {
                    String decodeText = MimeUtility.decodeText(address.toString());
                    System.out.println("test decode: "+decodeText);// TODO TEST
                    return decodeText;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private String[] addressParser(String decodeAddress) {
        Pattern compile = Pattern.compile(ADDRESS_SPLIT_REGEX);
        Matcher matcher = compile.matcher(decodeAddress);
        String[] from = new String[2];
        while (matcher.find()) {
            String name = matcher.group(1);
            from[0] = name;
            String address = matcher.group(2);
            from[1] = address;
            System.out.println("test name: " + name);// TODO TEST
            System.out.println("test addr: " + address);// TODO TEST
        }
        return from;
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

    private String textParser(String content) {
        Pattern compile = Pattern.compile(WITHOUT_RETURN_CARET_REGEX);
        Matcher matcher = compile.matcher(content);
        StringBuilder text = new StringBuilder();
        while (matcher.find()){
            text.append(matcher.group(1));
        }
        return text.toString();
        //return content; // TODO TEST full text
    }
}
/*
([^\n]+[\n])((От:|От кого:|From:)([^\n]+[\n]))?((Кому:|To:)([^\n]+[\n]))?((Дата:|Date:)([^\n]+[\n]))?((Тема:|Theme:)([^\n]+[\n]))?

private static final String GROUP_SPLIT =
            // Группа 1: [текст письма (без лишних переносов строк)]
            "([^\\n]+[\\n])" +
            // Группа 2: [from: <Имя отправителя> <адрес>]
            // Группа 3: [from:]
            // Группа 4: []
            "((От:|От кого:|From:)([^\\n]+[\\n]))?" +
            "((Кому:|To:)([^\\n]+[\\n]))?" +
            "((Дата:|Date:)([^\\n]+[\\n]))?" +
            "((Тема:|Theme:)([^\\n]+[\\n]))?";

Pattern.compile(
        // Группы 1-2 (-get: clients_list)
        "^[\\-{1}](?!send)(?!scanIP)([a-z]+)\\s([a-z]+)|"
        // Группы 3-4-5 (-send: client_name "message")
        +"^[\\-{1}](send){1}\\s([a-z]+)\\s\\\"([\\S\\s]+)\\\"|"
        // Группы 6-7-8 (-scanIP: IP_address_start:IP_address_end)
        +"^[\\-{1}]([a-z]+)\\s([0-9.]{7,15})\\:([0-9.]{7,15})|"
        // Группы 9-10-11 (-scanIP host_name port_number)
        +"[\\-{1}](scanIP){1}\\s([a-z]+)\\s([0-9]{1,5})|"
        // Группа 12 (help)
        +"^(help)\\z");
 */
