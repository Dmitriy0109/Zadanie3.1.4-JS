package ru.kata.spring.boot_security.demo.util;

public class UserInfo {
    private String message;

    public UserInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
