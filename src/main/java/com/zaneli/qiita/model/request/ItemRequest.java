package com.zaneli.qiita.model.request;

import java.io.Serializable;

import net.arnx.jsonic.JSON;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
public class ItemRequest extends JsonRequest {

    private String title;
    private Tag[] tags;
    private String body;
    private boolean privateItem;
    private boolean gist;
    private boolean tweet;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isPrivate() {
        return privateItem;
    }

    public void setPrivate(boolean privateItem) {
        this.privateItem = privateItem;
    }

    public boolean isGist() {
        return gist;
    }

    public void setGist(boolean gist) {
        this.gist = gist;
    }

    public boolean isTweet() {
        return tweet;
    }

    public void setTweet(boolean tweet) {
        this.tweet = tweet;
    }

    @Override
    public String createJson() {
        return JSON.encode(this);
    }

    public static class Tag implements Serializable {

        private String name;
        private String[] versions;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
}
