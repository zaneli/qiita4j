package com.zaneli.qiita;

import static org.apache.http.Consts.UTF_8;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.arnx.jsonic.JSON;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.zaneli.qiita.model.response.Error;
import com.zaneli.qiita.model.response.QiitaResponse;

class QiitaExecutor {

  private static final String ENDPOINT_URL = "https://qiita.com/api/v1";

  private String token;

  QiitaExecutor() {
  }

  void setToken(String token) {
    this.token = token;
  }

  <T extends QiitaResponse> T getSingleContent(String apiPath, Class<T> responseType) throws IOException, QiitaException {
    return getSingleContent(apiPath, Collections.<String, String>emptyMap(), responseType);
  }

  <T extends QiitaResponse> T getSingleContent(
      String apiPath, Map<String, String> params, Class<T> responseType) throws IOException, QiitaException {
    HttpGet request = new HttpGet(createQuery(createUrl(apiPath, token), params));
    HttpResponse response = execute(request);
    verifyStatusCode(response, SC_OK);
    try (InputStream in = response.getEntity().getContent()) {
      T res = JSON.decode(in, responseType);
      return res;
    }
  }

  <T extends QiitaResponse> T[] getMultiContents(String apiPath, Class<T[]> responseType) throws IOException, QiitaException {
    return getMultiContents(apiPath, Collections.<String, String>emptyMap(), responseType);
  }

  <T extends QiitaResponse> T[] getMultiContents(
      String apiPath, Map<String, String> params, Class<T[]> responseType) throws IOException, QiitaException {
    HttpGet request = new HttpGet(createQuery(createUrl(apiPath, token), params));
    HttpResponse response = execute(request);
    verifyStatusCode(response, SC_OK);
    try (InputStream in = response.getEntity().getContent()) {
      T[] res = JSON.decode(in, responseType);
      return res;
    }
  }

  <T extends QiitaResponse> T postFormValue(
      String apiPath, Map<String, String> params, Class<T> responseType) throws IOException, QiitaException {
    HttpPost request = new HttpPost(createUrl(apiPath, token));
    setFormValue(request, params);
    HttpResponse response = execute(request);
    verifyStatusCode(response, SC_OK);
    try (InputStream in = response.getEntity().getContent()) {
      T res = JSON.decode(in, responseType);
      return res;
    }
  }

  <T extends QiitaResponse> T postBody(String apiPath, String body, Class<T> responseType) throws IOException, QiitaException {
    HttpPost request = new HttpPost(createUrl(apiPath, token));
    setBody(request, body);
    HttpResponse response = execute(request);
    verifyStatusCode(response, SC_CREATED);
    try (InputStream in = response.getEntity().getContent()) {
      return JSON.decode(in, responseType);
    }
  }

  <T extends QiitaResponse> T putBody(String apiPath, String body, Class<T> responseType) throws IOException, QiitaException {
    HttpPut request = new HttpPut(createUrl(apiPath, token));
    setBody(request, body);
    HttpResponse response = execute(request);
    verifyStatusCode(response, SC_OK);
    try (InputStream in = response.getEntity().getContent()) {
      return JSON.decode(in, responseType);
    }
  }

  void putNoContent(String apiPath) throws IOException, QiitaException {
    HttpRequestBase request = new HttpPut(createUrl(apiPath, token));
    HttpResponse response = execute(request);
    verifyStatusCode(response, SC_NO_CONTENT);
  }

  void deleteNoContent(String apiPath) throws IOException, QiitaException {
    HttpRequestBase request = new HttpDelete(createUrl(apiPath, token));
    HttpResponse response = execute(request);
    verifyStatusCode(response, SC_NO_CONTENT);
  }

  private String createQuery(String url, Map<String, String> params) throws QiitaException {
    StringBuilder sb = new StringBuilder(url);
    if (url.contains("?")) {
      sb.append('&');
    } else {
      sb.append('?');
    }
    try {
      for (Entry<String, String> entry : params.entrySet()) {
        String key = URLEncoder.encode(entry.getKey(), UTF_8.name());
        String value = URLEncoder.encode(entry.getValue(), UTF_8.name());
        sb.append(key).append('=').append(value).append('&');
      }
    } catch (UnsupportedEncodingException e) {
      throw new QiitaException(e);
    }
    return sb.delete(sb.length() - 1, sb.length()).toString();
  }

  private void setFormValue(HttpEntityEnclosingRequestBase request, Map<String, String> params) throws QiitaException {
    List<NameValuePair> nameValuePairs = new ArrayList<>();
    for (Entry<String, String> entry : params.entrySet()) {
      nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    }
    try {
      request.setEntity(new UrlEncodedFormEntity(nameValuePairs, UTF_8.name()));
    } catch (UnsupportedEncodingException e) {
      throw new QiitaException(e);
    }
  }

  private void setBody(HttpEntityEnclosingRequestBase request, String body) {
    request.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
  }

  private HttpResponse execute(HttpRequestBase request) throws IOException {
    HttpClient httpClient = new DefaultHttpClient();
    return httpClient.execute(request);
  }

  private static void verifyStatusCode(HttpResponse response, int expectedStatusCode) throws IOException, QiitaException {
    int statusCode = response.getStatusLine().getStatusCode();
    if (statusCode == SC_BAD_REQUEST || statusCode == SC_FORBIDDEN || statusCode == SC_NOT_FOUND) {
      try (InputStream in = response.getEntity().getContent()) {
        Error res = JSON.decode(in, Error.class);
        throw new QiitaException(res.getError());
      }
    }
    if (statusCode != expectedStatusCode) {
      throw new IllegalArgumentException("unexpected status code:" + statusCode);
    }
  }

  private static String createUrl(String apiPath, String token) {
    if (StringUtils.isEmpty(token)) {
      return ENDPOINT_URL + '/' + apiPath;
    }
    return ENDPOINT_URL + '/' + apiPath + "?token=" + token;
  }
}
