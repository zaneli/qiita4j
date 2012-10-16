package com.zaneli.qiita;

import java.io.IOException;
import java.io.InputStream;

import mockit.Deencapsulation;
import mockit.NonStrictExpectations;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;

public abstract class QiitaClientTestUtil {

  static void configureMock(
      final QiitaExecutor executor,
      final int statusCode) throws IOException {
    configureMock(executor, statusCode, null);
  }

  static void configureMock(
      final QiitaExecutor executor,
      final int statusCode,
      String contentFileName) throws IOException {
    final InputStream content;
    if (StringUtils.isEmpty(contentFileName)) {
      content = null;
    } else {
      content = QiitaClientTestUtil.class.getResourceAsStream(contentFileName);
    }
    new NonStrictExpectations() {
      HttpResponse mockResponse;
      StatusLine mockStatusLine;
      HttpEntity mockEntity;
      {
        Deencapsulation.invoke(executor, "execute", withAny(HttpRequestBase.class));
        returns(mockResponse);
      }
      {
        mockResponse.getEntity();
        returns(mockEntity);
      }
      {
        mockResponse.getStatusLine();
        returns(mockStatusLine);
      }
      {
        mockStatusLine.getStatusCode();
        returns(statusCode);
      }
      {
        mockEntity.getContent();
        returns(content);
      }
    };
  }
}
