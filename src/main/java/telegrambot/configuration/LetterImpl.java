package telegrambot.configuration;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("letter")
@Scope("prototype")
public class LetterImpl implements Letter {

    private static final String HEADER_BUTTON_PREFIX = "@ from: ";
    private static final String LETTER_NEW = "@ NEW:";
    private static final String LETTER_READ = "@ read:";

    private String address;
    private String name;
    private StringBuilder bodyBuilder;
    private String splitLine;

    private KeyBoard headerKeyboard;
    private KeyBoard bodyKeyBoard;
    private String bodyText;

    @Override
    public void init(String[] from, String text){
        if(from.length < 2){
            this.name = "none";
            this.address = from[0];
        }else{
            this.name = from[0];
            this.address = from[1];
        }
        this.headerKeyboard = new LetterKeyBoard(HEADER_BUTTON_PREFIX+name);
        this.bodyKeyBoard = new LetterKeyBoard("delete", "hide");
        createBody(text);
    }

    private void createBody(String text) {
        createSplitLine();
        createHeaderTextToBody();
        this.bodyText = bodyBuilder.append(text).toString();
    }

    private void createSplitLine(){
        int lineLength = Math.max(name.length(), address.length());
        StringBuilder splitLine = new StringBuilder();
        while (lineLength-->0){
            splitLine.append("=");
        }
        splitLine.append("\n");
        this.splitLine = splitLine.toString();
    }

    private void createHeaderTextToBody(){
        this.bodyBuilder = new StringBuilder();
        bodyBuilder.append(splitLine);
        bodyBuilder.append("from: ");
        bodyBuilder.append(name);
        bodyBuilder.append("\n@: ");
        bodyBuilder.append(address);
        bodyBuilder.append("\n");
        bodyBuilder.append(splitLine);
    }

    @Override
    public String getLetterNewText() {
        return LETTER_NEW;
    }

    @Override
    public String getBodyText() {
        return bodyText;
    }

    @Override
    public KeyBoard getHeaderKeyboard() {
        return headerKeyboard;
    }

    @Override
    public KeyBoard getBodyKeyboard() {
        return bodyKeyBoard;
    }

    @Override
    public String getLetterReadText() {
        return LETTER_READ;
    }
}
