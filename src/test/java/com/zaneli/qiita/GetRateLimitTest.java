package com.zaneli.qiita;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import mockit.Mocked;

import org.junit.Test;

import com.zaneli.qiita.model.response.RateLimit;

public class GetRateLimitTest {

    @Test
    public void RateLimit取得成功(@Mocked("execute") QiitaExecutor executor) throws Exception {
        QiitaClientTestUtil.configureMock(executor, SC_OK, "RateLimit.json");
        QiitaClient client = new QiitaClient();
        RateLimit rateLimit = client.getRateLimit();
        assertEquals(140, rateLimit.getRemaining());
        assertEquals(150, rateLimit.getLimit());
    }

    @Test
    public void RateLimit取得失敗(@Mocked("execute") QiitaExecutor executor) throws Exception {
        QiitaClientTestUtil.configureMock(executor, SC_BAD_REQUEST, "Error.json");
        QiitaClient client = new QiitaClient();
        try {
            client.getRateLimit();
            fail("QiitaException が発生しませんでした。");
        } catch (QiitaException e) {
            assertEquals("Mocked error message.", e.getMessage());
        }
    }
}
