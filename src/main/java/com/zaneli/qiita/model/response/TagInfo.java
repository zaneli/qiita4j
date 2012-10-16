package com.zaneli.qiita.model.response;

@SuppressWarnings("serial")
public class TagInfo extends QiitaResponse {

  private String name;
  private String urlName;
  private String iconUrl;
  private int itemCount;
  private int followerCount;
  private boolean following;

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

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  public int getFollowerCount() {
    return followerCount;
  }

  public void setFollowerCount(int followerCount) {
    this.followerCount = followerCount;
  }

  public boolean isFollowing() {
    return following;
  }

  public void setFollowing(boolean following) {
    this.following = following;
  }
}
