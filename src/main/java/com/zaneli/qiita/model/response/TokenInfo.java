package com.zaneli.qiita.model.response;

@SuppressWarnings("serial")
public class TokenInfo extends QiitaResponse {

    private String urlName;
    private String token;

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
