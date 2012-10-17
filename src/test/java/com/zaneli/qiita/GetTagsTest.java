package com.zaneli.qiita;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import mockit.Mocked;

import org.junit.Test;

import com.zaneli.qiita.model.response.TagInfo;

public class GetTagsTest {

  @Test
  public void タグ一覧取得成功(@Mocked("execute") QiitaExecutor executor) throws Exception {
    QiitaClientTestUtil.configureMock(executor, SC_OK, "TagInfos.json");
    QiitaClient client = new QiitaClient();
    TagInfo[] infos = client.getTags().getContents();
    assertEquals(3, infos.length);

    assertEquals("Java", infos[0].getName());
    assertEquals("Java", infos[0].getUrlName());
    assertEquals("http://qiita.com/system/tags/icons/000/000/002/thumb/Java-Runtime-Environment-V6.0.260.png?1316130938", infos[0].getIconUrl());
    assertEquals(157, infos[0].getItemCount());
    assertEquals(1994, infos[0].getFollowerCount());
    assertTrue(infos[0].isFollowing());

    assertEquals("Python", infos[1].getName());
    assertEquals("Python", infos[1].getUrlName());
    assertEquals("http://qiita.com/system/tags/icons/000/000/004/thumb/search.gif?1316130880", infos[1].getIconUrl());
    assertEquals(213, infos[1].getItemCount());
    assertEquals(1554, infos[1].getFollowerCount());
    assertTrue(infos[1].isFollowing());

    assertEquals("Haskell", infos[2].getName());
    assertEquals("Haskell", infos[2].getUrlName());
    assertEquals("http://qiita.com/system/tags/icons/000/000/017/thumb/%E3%83%95%E3%82%A1%E3%82%A4%E3%83%AB:Haskell-Logo.png?1316130328", infos[2].getIconUrl());
    assertEquals(117, infos[2].getItemCount());
    assertEquals(645, infos[2].getFollowerCount());
    assertFalse(infos[2].isFollowing());
  }

  @Test
  public void タグ一覧取得失敗(@Mocked("execute") QiitaExecutor executor) throws Exception {
    QiitaClientTestUtil.configureMock(executor, SC_BAD_REQUEST, "Error.json");
    QiitaClient client = new QiitaClient();
    try {
      client.getTags();
      fail("QiitaException が発生しませんでした。");
    } catch (QiitaException e) {
      assertEquals("Mocked error message.", e.getMessage());
    }
  }
}
