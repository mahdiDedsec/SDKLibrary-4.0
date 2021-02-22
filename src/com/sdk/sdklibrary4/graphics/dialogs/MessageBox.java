package com.sdk.sdklibrary4.graphics.dialogs;

import com.sdk.sdklibrary4.data.structures.StringList;
import com.sdk.sdklibrary4.graphics.AWTSwingUI;

import javax.swing.*;
import java.awt.*;

public class MessageBox extends AWTSwingUI{

    private Font buttonFont;
    private Font messageFont;

    private Color backgroundColor;

    private String title;
    private String message;

    private String yesButtonText;
    private String noButtonText;
    private String cancelButtonText;

    private int messageType;

    private String[] buttons;
    private StringList list;

    public MessageBox(String[] buttons, String message) {
        super();

        this.buttons = buttons;
        this.message = message;

        initDefaultValues();
    }

    private void initDefaultValues() {
        title = "No title";

        buttonFont = new Font("Tahoma", Font.PLAIN, 16);
        messageFont = new Font("Tahoma", Font.PLAIN, 16);

        backgroundColor = Color.WHITE;

        yesButtonText = "Yes";
        noButtonText = "No";
        cancelButtonText = "Cancel";

        messageType = JOptionPane.WARNING_MESSAGE;
        list = new StringList(true);
        list.add(buttons);
    }

    public Font getButtonFont() {
        return buttonFont;
    }

    public void setButtonFont(Font buttonFont) {
        this.buttonFont = buttonFont;
    }

    public Font getMessageFont() {
        return messageFont;
    }

    public void setMessageFont(Font messageFont) {
        this.messageFont = messageFont;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getYesButtonText() {
        return yesButtonText;
    }

    public void setYesButtonText(String yesButtonText) {
        this.yesButtonText = yesButtonText;
    }

    public String getNoButtonText() {
        return noButtonText;
    }

    public void setNoButtonText(String noButtonText) {
        this.noButtonText = noButtonText;
    }

    public String getCancelButtonText() {
        return cancelButtonText;
    }

    public void setCancelButtonText(String cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
    }

    public MessageBox addButton(String value) {
        buttons = list.add(value).toArray();
        return this;
    }

    public MessageBox removeButton(String value) {
        list.remove(value);
        buttons = list.toArray();

        return this;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int show() {
        UIManager.put("OptionPane.buttonFont", buttonFont);
        UIManager.put("OptionPane.messageFont", messageFont);
        UIManager.put("OptionPane.yesButtonText", yesButtonText);
        UIManager.put("OptionPane.noButtonText", noButtonText);
        UIManager.put("OptionPane.cancelButtonText", cancelButtonText);

        return JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.NO_OPTION, messageType, null, buttons, null);
    }
}
