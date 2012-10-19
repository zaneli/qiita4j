package com.zaneli.qiita.model.response;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
public class ItemInfo extends QiitaResponse {

    private long id;
    private String uuid;
    private User user;
    private String title;
    private String body;
    private Date createdAt;
    private Date updatedAt;
    private String createdAtInWords;
    private String updatedAtInWords;
    private Tag[] tags;
    private int stockCount;
    private String[] stockUsers;
    private int commentCount;
    private String url;
    private String gistUrl;
    private boolean privateItem;
    private boolean stocked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) throws ParseException {
        this.createdAt = parse(createdAt);
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) throws ParseException {
        this.updatedAt = parse(updatedAt);
    }

    public String getCreatedAtInWords() {
        return createdAtInWords;
    }

    public void setCreatedAtInWords(String createdAtInWords) {
        this.createdAtInWords = createdAtInWords;
    }

    public String getUpdatedAtInWords() {
        return updatedAtInWords;
    }

    public void setUpdatedAtInWords(String updatedAtInWords) {
        this.updatedAtInWords = updatedAtInWords;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public String[] getStockUsers() {
        return stockUsers;
    }

    public void setStockUsers(String[] stockUsers) {
        this.stockUsers = stockUsers;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGistUrl() {
        return gistUrl;
    }

    public void setGistUrl(String gistUrl) {
        this.gistUrl = gistUrl;
    }

    public boolean isPrivate() {
        return privateItem;
    }

    public void setPrivate(boolean privateItem) {
        this.privateItem = privateItem;
    }

    public boolean isStocked() {
        return stocked;
    }

    public void setStocked(boolean stocked) {
        this.stocked = stocked;
    }

    public static class Tag implements Serializable {
        private String name;
        private String urlName;
        private String iconUrl;
        private String[] versions;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrlName() {
            return urlName;
        }

        public void setUrlName(String urlName) {
            this.urlName = urlName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String[] getVersions() {
            return versions;
        }

        public void setVersions(String[] versions) {
            this.versions = versions;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    }

    private static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss +0900");
            dateFormat.setLenient(false);
            return dateFormat;
        }
    };

    private static Date parse(String dateString) throws ParseException {
        return DATE_FORMAT.get().parse(dateString);
    }
}
