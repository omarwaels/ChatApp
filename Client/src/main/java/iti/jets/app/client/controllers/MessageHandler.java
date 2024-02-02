package iti.jets.app.client.controllers;


public class MessageHandler {
    private int fontSize;
    private String fontColor;
    private String fontWeight;
    private String fontFamily;
    private String fontStyle;
    private Boolean underline;

    public MessageHandler() {
    }

    public MessageHandler(int fontSize, String fontColor, String fontWeight, String fontFamily, String fontStyle, Boolean underline)
    {
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.fontWeight = fontWeight;
        this.fontFamily = fontFamily;
        this.fontStyle = fontStyle;
        this.underline = underline;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public void setUnderline(Boolean underline) {
        this.underline = underline;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public Boolean getUnderline() {
        return underline;
    }
}
