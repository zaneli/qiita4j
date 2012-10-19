package com.zaneli.qiita;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import mockit.Mocked;

import org.junit.Test;

import com.zaneli.qiita.model.response.User;

public class GetFollowingUsersTest {

    @Test
    public void 特定ユーザーのフォローしているユーザー取得成功_1件(@Mocked("execute") QiitaExecutor executor) throws Exception {
        QiitaClientTestUtil.configureMock(executor, SC_OK, "User.json");
        QiitaClient client = new QiitaClient();
        User[] users = client.getFollowingUsers("yaotti").getContents();
        assertEquals(1, users.length);
        assertEquals("Hiroshige Umino", users[0].getName());
        assertEquals("yaotti", users[0].getUrlName());
        assertEquals(
                "https://si0.twimg.com/profile_images/2309761038/1ijg13pfs0dg84sk2y0h_normal.jpeg",
                users[0].getProfileImageUrl());
    }

    @Test
    public void 特定ユーザーのフォローしているユーザー取得成功_複数件(@Mocked("execute") QiitaExecutor executor) throws Exception {
        QiitaClientTestUtil.configureMock(executor, SC_OK, "Users.json");
        QiitaClient client = new QiitaClient();
        User[] users = client.getFollowingUsers("yaotti").getContents();
        assertEquals(3, users.length);

        assertEquals("Dummy User1", users[0].getName());
        assertEquals("dummy1", users[0].getUrlName());
        assertEquals(
                "https://si0.twimg.com/profile_images/dummy_url1.jpeg",
                users[0].getProfileImageUrl());

        assertEquals("Dummy User2", users[1].getName());
        assertEquals("dummy2", users[1].getUrlName());
        assertEquals(
                "https://si0.twimg.com/profile_images/dummy_url2.jpeg",
                users[1].getProfileImageUrl());

        assertEquals("Dummy User3", users[2].getName());
        assertEquals("dummy3", users[2].getUrlName());
        assertEquals(
                "https://si0.twimg.com/profile_images/dummy_url3.jpeg",
                users[2].getProfileImageUrl());
    }

    @Test
    public void 特定ユーザーのフォローしているユーザー取得失敗(@Mocked("execute") QiitaExecutor executor) throws Exception {
        QiitaClientTestUtil.configureMock(executor, SC_BAD_REQUEST, "Error.json");
        QiitaClient client = new QiitaClient();
        try {
            client.getFollowingUsers("yaotti");
            fail("QiitaException が発生しませんでした。");
        } catch (QiitaException e) {
            assertEquals("Mocked error message.", e.getMessage());
        }
    }
}
