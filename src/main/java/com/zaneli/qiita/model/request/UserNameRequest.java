package com.zaneli.qiita.model.request;

@SuppressWarnings("serial")
public class UserNameRequest extends QiitaRequest {

    private final String userName;

    public UserNameRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
