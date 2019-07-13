package com.example.denemechat;

public class Chat {
    String userName, eMail, imgURL, message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Chat(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public Chat(String userName, String message, String eMail){
        this.userName = userName;
        this.message = message;
        this.eMail = eMail;
    }

    public Chat(String userName, String message, String eMail, String imgURL){
        this.userName = userName;
        this.message = message;
        this.eMail = eMail;
        this.imgURL = imgURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Chat(String userName) {
        this.userName = userName;
    }
}
