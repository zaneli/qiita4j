package com.zaneli.qiita;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import mockit.Mocked;

import org.junit.Test;

import com.zaneli.qiita.model.response.Comment;
import com.zaneli.qiita.model.response.ItemDetail;
import com.zaneli.qiita.model.response.ItemInfo.Tag;
import com.zaneli.qiita.model.response.User;

public class GetSpecificItemTest {

  @Test
  public void 特定の投稿取得成功(@Mocked("execute") QiitaExecutor executor) throws Exception {
    QiitaClientTestUtil.configureMock(executor, SC_OK, "ItemDetail.json");
    QiitaClient client = new QiitaClient();
    ItemDetail detail = client.getSpecificItem("test_uuid_0001");
    assertEquals(1, detail.getId());
    assertEquals("1a43e55e7209c8f3c565", detail.getUuid());
    User user = detail.getUser();
    assertEquals("Hiroshige Umino", user.getName());
    assertEquals("yaotti", user.getUrlName());
    assertEquals("https://si0.twimg.com/profile_images/2309761038/1ijg13pfs0dg84sk2y0h_normal", user.getProfileImageUrl());
    assertEquals("てすと", detail.getTitle());
    assertEquals("<p>foooooooooooooooo</p>\n", detail.getBody());
    assertEquals(QiitaClientTestUtil.parse("2012-10-03 22:12:36 +0900"), detail.getCreatedAt());
    assertEquals(QiitaClientTestUtil.parse("2012-10-03 22:12:36 +0900"), detail.getUpdatedAt());
    assertEquals("18 hours ago", detail.getCreatedAtInWords());
    assertEquals("18 hours ago", detail.getUpdatedAtInWords());
    Tag[] tags = detail.getTags();
    assertEquals(1, tags.length);
    assertEquals("FOOBAR", tags[0].getName());
    assertEquals("FOOBAR", tags[0].getUrlName());
    assertEquals("http://qiita.com/icons/thumb/missing.png", tags[0].getIconUrl());
    assertEquals(0, tags[0].getVersions().length);
    assertEquals(0, detail.getStockCount());
    assertEquals(0, detail.getStockUsers().length);
    assertEquals(1, detail.getCommentCount());
    assertEquals("http://qiita.com/items/1a43e55e7209c8f3c565", detail.getUrl());
    assertNull(detail.getGistUrl());
    assertFalse(detail.isPrivate());
    assertFalse(detail.isStocked());
    Comment[] comments = detail.getComments();
    assertEquals(1, comments.length);
    assertEquals(1, comments[0].getId());
    assertEquals("d6e319c64fb6d90d5b50", comments[0].getUuid());
    User commentUser = comments[0].getUser();
    assertEquals("Qiita_jp", commentUser.getName());
    assertEquals("Qiita_jp", commentUser.getUrlName());
    assertEquals("https://si0.twimg.com/profile_images/1542801560/Qiita_normal.png", commentUser.getProfileImageUrl());
    assertEquals("コメントです", comments[0].getBody());
  }

  @Test
  public void 特定の投稿取得失敗(@Mocked("execute") QiitaExecutor executor) throws Exception {
    QiitaClientTestUtil.configureMock(executor, SC_BAD_REQUEST, "Error.json");
    QiitaClient client = new QiitaClient();
    try {
      client.getSpecificItem("test_uuid_0001");
      fail("QiitaException が発生しませんでした。");
    } catch (QiitaException e) {
      assertEquals("Mocked error message.", e.getMessage());
    }
  }
}
