package com.zaneli.qiita;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import mockit.Mocked;

import org.junit.Test;

import com.zaneli.qiita.model.request.ItemRequest;
import com.zaneli.qiita.model.response.ItemInfo;
import com.zaneli.qiita.model.response.User;
import com.zaneli.qiita.model.response.ItemInfo.Tag;

public class UpdateItemTest {

    @Test
    public void 投稿の実行成功(@Mocked("execute") QiitaExecutor executor) throws Exception {
        ItemRequest req = new ItemRequest();
        req.setTitle("タイトル");
        req.setBody("本文");
        QiitaClientTestUtil.configureMock(executor, SC_OK, "SingleItemInfo.json");
        QiitaClient client = new QiitaClient();
        ItemInfo info = client.updateItem("test_uuid_0001", req);
        assertEquals(1, info.getId());
        assertEquals("1a43e55e7209c8f3c565", info.getUuid());
        User user = info.getUser();
        assertEquals("Hiroshige Umino", user.getName());
        assertEquals("yaotti", user.getUrlName());
        assertEquals(
                "https://si0.twimg.com/profile_images/2309761038/1ijg13pfs0dg84sk2y0h_normal",
                user.getProfileImageUrl());
        assertEquals("てすと", info.getTitle());
        assertEquals("<p>foooooooooooooooo</p>\n", info.getBody());
        assertEquals(QiitaClientTestUtil.parse("2012-10-03 22:12:36 +0900"), info.getCreatedAt());
        assertEquals(QiitaClientTestUtil.parse("2012-10-03 22:12:36 +0900"), info.getUpdatedAt());
        assertEquals("18 hours ago", info.getCreatedAtInWords());
        assertEquals("18 hours ago", info.getUpdatedAtInWords());
        Tag[] tags = info.getTags();
        assertEquals(1, tags.length);
        assertEquals("FOOBAR", tags[0].getName());
        assertEquals("FOOBAR", tags[0].getUrlName());
        assertEquals("http://qiita.com/icons/thumb/missing.png", tags[0].getIconUrl());
        String[] versions = tags[0].getVersions();
        assertEquals(2, versions.length);
        assertEquals("1.2", versions[0]);
        assertEquals("1.3", versions[1]);
        assertEquals(0, info.getStockCount());
        assertEquals(0, info.getStockUsers().length);
        assertEquals(0, info.getCommentCount());
        assertEquals("http://qiita.com/items/1a43e55e7209c8f3c565", info.getUrl());
        assertNull(info.getGistUrl());
        assertFalse(info.isPrivate());
        assertFalse(info.isStocked());
    }

    @Test
    public void 投稿の実行失敗(@Mocked("execute") QiitaExecutor executor) throws Exception {
        ItemRequest req = new ItemRequest();
        req.setTitle("タイトル");
        req.setBody("本文");
        QiitaClientTestUtil.configureMock(executor, SC_BAD_REQUEST, "Error.json");
        QiitaClient client = new QiitaClient();
        try {
            client.updateItem("test_uuid_0001", req);
            fail("QiitaException が発生しませんでした。");
        } catch (QiitaException e) {
            assertEquals("Mocked error message.", e.getMessage());
        }
    }
}
