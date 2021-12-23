package ru.akulin.output;

public class Error extends Response {

    private String error;

    public Error(String error) {
        super(false);
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
