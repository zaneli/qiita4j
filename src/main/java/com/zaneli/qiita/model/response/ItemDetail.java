package com.zaneli.qiita.model.response;

@SuppressWarnings("serial")
public class ItemDetail extends ItemInfo {

    private Comment[] comments;

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }
}
