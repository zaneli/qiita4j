package com.zaneli.qiita;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import mockit.Mocked;

import org.junit.Test;

import com.zaneli.qiita.model.response.ItemInfo;
import com.zaneli.qiita.model.response.ItemInfo.Tag;
import com.zaneli.qiita.model.response.User;
import com.zaneli.qiita.util.DateTimeUtil;

public class GetTagItemsTest {

  @Test
  public void 特定タグの投稿取得成功_1件(@Mocked("execute") QiitaExecutor executor) throws Exception {
    QiitaClientTestUtil.configureMock(executor, SC_OK, "ItemInfo.json");
    QiitaClient client = new QiitaClient();
    ItemInfo[] infos = client.getTagItems("FOOBAR");
    assertEquals(1, infos.length);
    assertEquals(1, infos[0].getId());
    assertEquals("1a43e55e7209c8f3c565", infos[0].getUuid());
    User user = infos[0].getUser();
    assertEquals("Hiroshige Umino", user.getName());
    assertEquals("yaotti", user.getUrlName());
    assertEquals("https://si0.twimg.com/profile_images/2309761038/1ijg13pfs0dg84sk2y0h_normal", user.getProfileImageUrl());
    assertEquals("てすと", infos[0].getTitle());
    assertEquals("<p>foooooooooooooooo</p>\n", infos[0].getBody());
    assertEquals(DateTimeUtil.parse("2012-10-03 22:12:36 +0900"), infos[0].getCreatedAt());
    assertEquals(DateTimeUtil.parse("2012-10-03 22:12:36 +0900"), infos[0].getUpdatedAt());
    assertEquals("18 hours ago", infos[0].getCreatedAtInWords());
    assertEquals("18 hours ago", infos[0].getUpdatedAtInWords());
    Tag[] tags = infos[0].getTags();
    assertEquals(1, tags.length);
    assertEquals("FOOBAR", tags[0].getName());
    assertEquals("FOOBAR", tags[0].getUrlName());
    assertEquals("http://qiita.com/icons/thumb/missing.png", tags[0].getIconUrl());
    String[] versions = tags[0].getVersions();
    assertEquals(2, versions.length);
    assertEquals("1.2", versions[0]);
    assertEquals("1.3", versions[1]);
    assertEquals(0, infos[0].getStockCount());
    assertEquals(0, infos[0].getStockUsers().length);
    assertEquals(0, infos[0].getCommentCount());
    assertEquals("http://qiita.com/items/1a43e55e7209c8f3c565", infos[0].getUrl());
    assertNull(infos[0].getGistUrl());
    assertFalse(infos[0].isPrivate());
    assertFalse(infos[0].isStocked());
  }

  @Test
  public void 特定タグの投稿取得成功_複数件(@Mocked("execute") QiitaExecutor executor) throws Exception {
    QiitaClientTestUtil.configureMock(executor, SC_OK, "ItemInfos.json");
    QiitaClient client = new QiitaClient();
    ItemInfo[] infos = client.getTagItems("FOOBAR");
    assertEquals(3, infos.length);
    
    assertEquals(1, infos[0].getId());
    assertEquals("1a43e55e7209c8f3c561", infos[0].getUuid());
    User user1 = infos[0].getUser();
    assertEquals("Dummy User1", user1.getName());
    assertEquals("dummy1", user1.getUrlName());
    assertEquals("https://si0.twimg.com/profile_images/dummy_url1", user1.getProfileImageUrl());
    assertEquals("てすと1", infos[0].getTitle());
    assertEquals("<p>foooooooooooooooo1</p>\n", infos[0].getBody());
    assertEquals(DateTimeUtil.parse("2012-09-01 01:02:03 +0900"), infos[0].getCreatedAt());
    assertEquals(DateTimeUtil.parse("2012-09-02 04:05:06 +0900"), infos[0].getUpdatedAt());
    assertEquals("1 hours ago", infos[0].getCreatedAtInWords());
    assertEquals("1 hours ago", infos[0].getUpdatedAtInWords());
    Tag[] tags1 = infos[0].getTags();
    assertEquals(1, tags1.length);
    assertEquals("tag1", tags1[0].getName());
    assertEquals("tag1", tags1[0].getUrlName());
    assertEquals("http://qiita.com/icons/thumb/tag1.png", tags1[0].getIconUrl());
    String[] versions1 = tags1[0].getVersions();
    assertEquals(2, versions1.length);
    assertEquals("1.1", versions1[0]);
    assertEquals("1.2", versions1[1]);
    assertEquals(1, infos[0].getStockCount());
    assertEquals(0, infos[0].getStockUsers().length);
    assertEquals(1, infos[0].getCommentCount());
    assertEquals("http://qiita.com/items/dummy_url1", infos[0].getUrl());
    assertNull(infos[0].getGistUrl());
    assertTrue(infos[0].isPrivate());
    assertFalse(infos[0].isStocked());

    assertEquals(2, infos[1].getId());
    assertEquals("1a43e55e7209c8f3c562", infos[1].getUuid());
    User user2 = infos[1].getUser();
    assertEquals("Dummy User2", user2.getName());
    assertEquals("dummy2", user2.getUrlName());
    assertEquals("https://si0.twimg.com/profile_images/dummy_url2", user2.getProfileImageUrl());
    assertEquals("てすと2", infos[1].getTitle());
    assertEquals("<p>foooooooooooooooo2</p>\n", infos[1].getBody());
    assertEquals(DateTimeUtil.parse("2012-10-01 01:02:03 +0900"), infos[1].getCreatedAt());
    assertEquals(DateTimeUtil.parse("2012-10-02 04:05:06 +0900"), infos[1].getUpdatedAt());
    assertEquals("2 hours ago", infos[1].getCreatedAtInWords());
    assertEquals("2 hours ago", infos[1].getUpdatedAtInWords());
    Tag[] tags2 = infos[1].getTags();
    assertEquals(1, tags2.length);
    assertEquals("tag2", tags2[0].getName());
    assertEquals("tag2", tags2[0].getUrlName());
    assertEquals("http://qiita.com/icons/thumb/tag2.png", tags2[0].getIconUrl());
    String[] versions2 = tags2[0].getVersions();
    assertEquals(2, versions2.length);
    assertEquals("2.1", versions2[0]);
    assertEquals("2.2", versions2[1]);
    assertEquals(2, infos[1].getStockCount());
    assertEquals(0, infos[1].getStockUsers().length);
    assertEquals(2, infos[1].getCommentCount());
    assertEquals("http://qiita.com/items/dummy_url2", infos[1].getUrl());
    assertNull(infos[1].getGistUrl());
    assertTrue(infos[1].isPrivate());
    assertTrue(infos[1].isStocked());

    assertEquals(3, infos[2].getId());
    assertEquals("1a43e55e7209c8f3c563", infos[2].getUuid());
    User user3 = infos[2].getUser();
    assertEquals("Dummy User3", user3.getName());
    assertEquals("dummy3", user3.getUrlName());
    assertEquals("https://si0.twimg.com/profile_images/dummy_url3", user3.getProfileImageUrl());
    assertEquals("てすと3", infos[2].getTitle());
    assertEquals("<p>foooooooooooooooo3</p>\n", infos[2].getBody());
    assertEquals(DateTimeUtil.parse("2012-11-01 01:02:03 +0900"), infos[2].getCreatedAt());
    assertEquals(DateTimeUtil.parse("2012-11-02 04:05:06 +0900"), infos[2].getUpdatedAt());
    assertEquals("3 hours ago", infos[2].getCreatedAtInWords());
    assertEquals("3 hours ago", infos[2].getUpdatedAtInWords());
    Tag[] tags3 = infos[2].getTags();
    assertEquals(1, tags3.length);
    assertEquals("tag3", tags3[0].getName());
    assertEquals("tag3", tags3[0].getUrlName());
    assertEquals("http://qiita.com/icons/thumb/tag3.png", tags3[0].getIconUrl());
    String[] versions3 = tags3[0].getVersions();
    assertEquals(2, versions3.length);
    assertEquals("3.1", versions3[0]);
    assertEquals("3.2", versions3[1]);
    assertEquals(3, infos[2].getStockCount());
    assertEquals(0, infos[2].getStockUsers().length);
    assertEquals(3, infos[2].getCommentCount());
    assertEquals("http://qiita.com/items/dummy_url3", infos[2].getUrl());
    assertNull(infos[2].getGistUrl());
    assertFalse(infos[2].isPrivate());
    assertTrue(infos[2].isStocked());
  }

  @Test
  public void 特定タグの投稿取得失敗(@Mocked("execute") QiitaExecutor executor) throws Exception {
    QiitaClientTestUtil.configureMock(executor, SC_BAD_REQUEST, "Error.json");
    QiitaClient client = new QiitaClient();
    try {
      client.getTagItems("FOOBAR");
      fail("QiitaException が発生しませんでした。");
    } catch (QiitaException e) {
      assertEquals("Mocked error message.", e.getMessage());
    }
  }
}
