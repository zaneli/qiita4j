package com.zaneli.qiita;

import java.io.IOException;

import com.zaneli.qiita.model.request.AuthRequest;
import com.zaneli.qiita.model.request.ItemRequest;
import com.zaneli.qiita.model.request.SearchRequest;
import com.zaneli.qiita.model.response.ItemDetail;
import com.zaneli.qiita.model.response.ItemInfo;
import com.zaneli.qiita.model.response.PageableResponse;
import com.zaneli.qiita.model.response.RateLimit;
import com.zaneli.qiita.model.response.TagInfo;
import com.zaneli.qiita.model.response.TokenInfo;
import com.zaneli.qiita.model.response.User;
import com.zaneli.qiita.model.response.UserInfo;

public class QiitaClient {

  private final QiitaExecutor executor = new QiitaExecutor();

  public QiitaClient() {
  }

  public QiitaClient(String token) {
    executor.setToken(token);
  }

  public void setToken(String token) {
    executor.setToken(token);
  }

  public void setPerPage(int perPage) {
    executor.setPerPage(perPage);
  }

  public TokenInfo authorize(AuthRequest req) throws IOException, QiitaException {
    return executor.postFormValue("auth", req.createParams(), TokenInfo.class);
  }

  public RateLimit getRateLimit() throws IOException, QiitaException {
    return executor.getContent("rate_limit", RateLimit.class);
  }

  public UserInfo getOwnInfo() throws IOException, QiitaException {
    return executor.getContent("user", UserInfo.class);
  }

  public UserInfo getUserInfo(String userName) throws IOException, QiitaException {
    return executor.getContent("users/" + userName, UserInfo.class);
  }

  public PageableResponse<ItemInfo> getUserItems(String userName) throws IOException, QiitaException {
    return executor.getPageableContents("users/" + userName + "/items", ItemInfo.class);
  }

  public PageableResponse<ItemInfo> getUserStocks(String userName) throws IOException, QiitaException {
    return executor.getPageableContents("users/" + userName + "/stocks", ItemInfo.class);
  }

  public PageableResponse<User> getFollowingUsers(String userName) throws IOException, QiitaException {
    return executor.getPageableContents("users/" + userName + "/following_users", User.class);
  }

  public PageableResponse<TagInfo> getFollowingTags(String userName) throws IOException, QiitaException {
    return executor.getPageableContents("users/" + userName + "/following_tags", TagInfo.class);
  }

  public PageableResponse<ItemInfo> getTagItems(String tagName) throws IOException, QiitaException {
    return executor.getPageableContents("tags/" + tagName + "/items", ItemInfo.class);
  }

  public PageableResponse<TagInfo> getTags() throws IOException, QiitaException {
    return executor.getPageableContents("tags", TagInfo.class);
  }

  public PageableResponse<ItemInfo> searchItems(SearchRequest req) throws IOException, QiitaException {
    return executor.getPageableContents("search", req.createParams(), ItemInfo.class);
  }

  public PageableResponse<ItemInfo> getNewItems() throws IOException, QiitaException {
    return executor.getPageableContents("items", ItemInfo.class);
  }

  public PageableResponse<ItemInfo> getOwnStocks() throws IOException, QiitaException {
    return executor.getPageableContents("stocks", ItemInfo.class);
  }

  public ItemInfo createItem(ItemRequest req) throws IOException, QiitaException {
    return executor.postBody("items", req.createJson(), ItemInfo.class);
  }

  public ItemInfo updateItem(String uuid, ItemRequest req) throws IOException, QiitaException {
    return executor.putBody("items/" + uuid, req.createJson(), ItemInfo.class);
  }

  public void deleteItem(String uuid) throws IOException, QiitaException {
    executor.deleteNoContent("items/" + uuid);
  }

  public ItemDetail getSpecificItem(String uuid) throws IOException, QiitaException {
    return executor.getContent("items/" + uuid, ItemDetail.class);
  }

  public void stockItem(String uuid) throws IOException, QiitaException {
    executor.putNoContent("items/" + uuid + "/stock");
  }

  public void unstockItem(String uuid) throws IOException, QiitaException {
    executor.deleteNoContent("items/" + uuid + "/stock");
  }
}
