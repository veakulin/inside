package ru.akulin.output;

public class Token extends Response {

    private String token;

    public Token(String token) {
        super(true);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
