package com.example.client.Models;

public class User {
    private String name;
    private String imgSrc;
    private Status status;


    public enum Status {Online, Offline}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Status getStatus() { // Getter for Status
        return status;
    }

    public void setStatus(Status status) { // Setter for Status
        this.status = status;
    }



}
