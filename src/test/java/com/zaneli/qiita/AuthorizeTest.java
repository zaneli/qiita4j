package com.zaneli.qiita;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import mockit.Mocked;

import org.junit.Test;

import com.zaneli.qiita.model.request.AuthRequest;
import com.zaneli.qiita.model.response.TokenInfo;

public class AuthorizeTest {

  @Test
  public void トークン取得成功(@Mocked("execute") QiitaExecutor executor) throws Exception {
    AuthRequest req = new AuthRequest("testname", "testpassword");
    QiitaClientTestUtil.configureMock(executor, SC_OK, "TokenInfo.json");
    QiitaClient client = new QiitaClient();
    TokenInfo info = client.authorize(req);
    assertEquals("testname", info.getUrlName());
    assertEquals("testtoken", info.getToken());
  }

  @Test
  public void トークン取得失敗(@Mocked("execute") QiitaExecutor executor) throws Exception {
    AuthRequest req = new AuthRequest("testname", "testpassword");
    QiitaClientTestUtil.configureMock(
        executor, SC_BAD_REQUEST, "Error.json");
    QiitaClient client = new QiitaClient();
    try {
      client.authorize(req);
      fail("QiitaException が発生しませんでした。");
    } catch (QiitaException e) {
      assertEquals("Mocked error message.", e.getMessage());
    }
  }
}
