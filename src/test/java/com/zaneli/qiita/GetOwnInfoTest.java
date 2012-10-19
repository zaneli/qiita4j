package com.zaneli.qiita;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import mockit.Mocked;

import org.junit.Test;

import com.zaneli.qiita.model.response.UserInfo;

public class GetOwnInfoTest {

    @Test
    public void リクエストユーザーの情報取得成功(@Mocked("execute") QiitaExecutor executor) throws Exception {
        QiitaClientTestUtil.configureMock(executor, SC_OK, "UserInfo.json");
        QiitaClient client = new QiitaClient();
        UserInfo info = client.getOwnInfo();
        assertEquals("Hiroshige Umino", info.getName());
        assertEquals("yaotti", info.getUrlName());
        assertEquals(
                "https://si0.twimg.com/profile_images/2309761038/1ijg13pfs0dg84sk2y0h_normal.jpeg",
                info.getProfileImageUrl());
        assertEquals("http://qiita.com/users/yaotti", info.getUrl());
        assertEquals("Qiita(Ruby on Rails)やKobito(Macアプリ)の開発をしています．", info.getDescription());
        assertEquals("http://yaotti.hatenablog.com", info.getWebsiteUrl());
        assertEquals("Increments Inc", info.getOrganization());
        assertEquals("Tokyo, Japan", info.getLocation());
        assertEquals("yaotti", info.getFacebook());
        assertEquals("yaotti", info.getLinkedin());
        assertEquals("yaotti", info.getTwitter());
        assertEquals("yaotti", info.getGithub());
        assertEquals(181, info.getFollowers());
        assertEquals(118, info.getFollowingUsers());
        assertEquals(101, info.getItems());
    }

    @Test
    public void リクエストユーザーの情報取得失敗(@Mocked("execute") QiitaExecutor executor) throws Exception {
        QiitaClientTestUtil.configureMock(executor, SC_BAD_REQUEST, "Error.json");
        QiitaClient client = new QiitaClient();
        try {
            client.getOwnInfo();
            fail("QiitaException が発生しませんでした。");
        } catch (QiitaException e) {
            assertEquals("Mocked error message.", e.getMessage());
        }
    }
}
