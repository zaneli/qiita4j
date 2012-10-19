package com.zaneli.qiita;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import mockit.Mocked;

import org.junit.Test;

public class StockItemTest {

    @Test
    public void 投稿のストック成功(@Mocked("execute") QiitaExecutor executor) throws Exception {
        QiitaClientTestUtil.configureMock(executor, SC_NO_CONTENT);
        QiitaClient client = new QiitaClient();
        client.stockItem("test_uuid_0001");
    }

    @Test
    public void 投稿のストック失敗(@Mocked("execute") QiitaExecutor executor) throws Exception {
        QiitaClientTestUtil.configureMock(executor, SC_BAD_REQUEST, "Error.json");
        QiitaClient client = new QiitaClient();
        try {
            client.stockItem("test_uuid_0001");
            fail("QiitaException が発生しませんでした。");
        } catch (QiitaException e) {
            assertEquals("Mocked error message.", e.getMessage());
        }
    }
}
