package mail.decoupled;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailParserImpl implements MailParser {

    private static final String TEXT = "text/plain; charset=utf-8";
    private static final String WITHOUT_RETURN_CARET = "([^\\n]+[\\n].?)";

    private Message message;

    @Override
    public void setMessage(Message message) {
        this.message = message;
        // TODO test
        String s = parseText();
        System.out.println(s);
    }

    public String parseText(){
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

    private String textParser(String content) {
        Pattern compile = Pattern.compile(WITHOUT_RETURN_CARET);
        Matcher matcher = compile.matcher(content);
        StringBuilder text = new StringBuilder();
        while (matcher.find()){
            text.append(matcher.group(1));
        }
        return text.toString();
        //return content; // TODO TEST
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
