package com.zaneli.qiita.model.response;

@SuppressWarnings("serial")
public class Error extends QiitaResponse {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
