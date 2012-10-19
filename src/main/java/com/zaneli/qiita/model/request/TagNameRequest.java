package com.zaneli.qiita.model.request;

@SuppressWarnings("serial")
public class TagNameRequest extends QiitaRequest {

    private final String tagName;

    public TagNameRequest(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
