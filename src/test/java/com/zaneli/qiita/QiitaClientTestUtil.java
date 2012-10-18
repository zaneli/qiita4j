package com.zaneli.qiita;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mockit.Deencapsulation;
import mockit.NonStrictExpectations;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;

public abstract class QiitaClientTestUtil {

  private static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {
    @Override
    protected DateFormat initialValue() {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss +0900");
      dateFormat.setLenient(false);
      return dateFormat;
    }
  };

  static Date parse(String dateString) throws ParseException {
    return DATE_FORMAT.get().parse(dateString);
  }

  static void configureMock(
      final QiitaExecutor executor,
      final int statusCode) throws IOException {
    configureMock(executor, statusCode, null);
  }

  @SuppressWarnings({ "resource", "unused" })
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
        returns(Integer.valueOf(statusCode));
      }
      {
        mockEntity.getContent();
        returns(content);
      }
    };
  }
}
