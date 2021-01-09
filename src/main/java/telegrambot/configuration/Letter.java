package telegrambot.configuration;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class Letter {

    private static final String HEADER_NEW_MAIL = "@ NEW:";
    private static final String HEADER_READ_MAIL = "@ read:";
    private static final String HEADER_BUTTON_PREFIX = "@ from: ";
    private final String address;
    private final String name;
    private Long chatId = 528647782L; // TODO TEST

    private SendMessage headerMessage;
    private EditMessageText bodyMessage;
    private DeleteMessage deleteMessage;
    private EditMessageText turnOffMessage;
    private StringBuilder bodyBuilder;
    private String splitLine;

    public Letter(String[] from, String text) {
        if(from.length < 2){
            this.name = "none";
            this.address = from[0];
        }else{
            this.name = from[0];
            this.address = from[1];
        }
        createHeaderTextToBody();
        createHeader(HEADER_BUTTON_PREFIX+name);
        createBody(text);
        deleteLetter();
        turnOff(HEADER_BUTTON_PREFIX+name);
    }

    private void createHeaderTextToBody(){
        createSplitLine();
        this.bodyBuilder = new StringBuilder();
        bodyBuilder.append(splitLine);
        bodyBuilder.append("from: ");
        bodyBuilder.append(name);
        bodyBuilder.append("\n@: ");
        bodyBuilder.append(address);
        bodyBuilder.append("\n");
        bodyBuilder.append(splitLine);
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

    private void deleteLetter() {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId.toString());
        this.deleteMessage = deleteMessage;
    }

    private void createHeader(String headerText){
        KeyBoard headerKeyboard = new LetterKeyBoard(headerText);
        this.headerMessage = createSendMessage(headerKeyboard);
    }

    private SendMessage createSendMessage(KeyBoard serviceKeyBoard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(HEADER_NEW_MAIL);
        sendMessage.setReplyMarkup(serviceKeyBoard.getInlineKeyboardMarkup());
        return sendMessage;
    }

    private void createBody(String text) {
        KeyBoard keyBoard = new LetterKeyBoard("delete", "turn off");
        String textBody = bodyBuilder.append(text).toString();
        this.bodyMessage = createEditMessage(textBody, keyBoard);
    }

    private EditMessageText createEditMessage(String text, KeyBoard serviceKeyBoard) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId.toString());
        editMessage.setText(text);
        editMessage.setReplyMarkup(serviceKeyBoard.getInlineKeyboardMarkup());
        return editMessage;
    }

    private void turnOff(String headerText) {
        KeyBoard turnOfKeyboard = new LetterKeyBoard(headerText);
        this.turnOffMessage = createEditMessage(HEADER_READ_MAIL, turnOfKeyboard);
    }

    public SendMessage getHeaderMessage() {
        return headerMessage;
    }

    public EditMessageText getBodyMessage(){
        return bodyMessage;
    }

    public DeleteMessage getDeleteMessage(){
        return deleteMessage;
    }

    public EditMessageText getTurnOffMessage() {
        return turnOffMessage;
    }
}
