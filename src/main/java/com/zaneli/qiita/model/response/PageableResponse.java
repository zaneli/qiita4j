package com.zaneli.qiita.model.response;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zaneli.qiita.QiitaException;
import com.zaneli.qiita.QiitaExecutor;
import com.zaneli.qiita.model.response.QiitaResponse;

@SuppressWarnings("serial")
public class PageableResponse<T extends QiitaResponse> implements Serializable {

  private static enum Rel {
    FIRST("first"), PREV("prev"), NEXT("next"), LAST("last");
    private final String value;
    private Rel(String value) {
      this.value = value;
    }
    String getValue() {
        return value;
    }
  }

  private final QiitaExecutor executor;
  private final Map<String, String> params;
  private final Class<T> responseType;
  private final T[] contents;
  private final URI firstUri;
  private final URI prevUri;
  private final URI nextUri;
  private final URI lastUri;

  public PageableResponse(
      QiitaExecutor executor, Map<String, String> params, Class<T> responseType, T[] contents, String[] linkHeaderValues) throws QiitaException {
    this.executor = executor;
    this.params = params;
    this.responseType = responseType;
    this.contents = contents;
    this.firstUri = retrieveUri(Rel.FIRST, linkHeaderValues);
    this.prevUri = retrieveUri(Rel.PREV, linkHeaderValues);
    this.nextUri = retrieveUri(Rel.NEXT, linkHeaderValues);
    this.lastUri = retrieveUri(Rel.LAST, linkHeaderValues);
  }

  public T[] getContents() {
    return contents;
  }

  public PageableResponse<T> getFirst() throws IOException, QiitaException {
    if (firstUri == null) {
      return new NullPageableResponse<>(responseType);
    }
    return executor.getPageableContents(firstUri, params, responseType);
  }

  public PageableResponse<T> getPrev() throws IOException, QiitaException {
    if (prevUri == null) {
      return new NullPageableResponse<>(responseType);
    }
    return executor.getPageableContents(prevUri, params, responseType);
  }

  public PageableResponse<T> getNext() throws IOException, QiitaException {
    if (nextUri == null) {
      return new NullPageableResponse<>(responseType);
    }
    return executor.getPageableContents(nextUri, params, responseType);
  }

  public PageableResponse<T> getLast() throws IOException, QiitaException {
    if (lastUri == null) {
      return new NullPageableResponse<>(responseType);
    }
    return executor.getPageableContents(lastUri, params, responseType);
  }

  private static URI retrieveUri(Rel rel, String[] linkHeaderValues) throws QiitaException {
    Pattern pattern = Pattern.compile("^<(.+)>;\\s+rel=\"" + rel.getValue() + "\"$");
    for (String linkHeaderValue : linkHeaderValues) {
      String[] splitedLinkHeaderValues = linkHeaderValue.split(",");
      for (String splitedLinkHeaderValue : splitedLinkHeaderValues) {
        Matcher matcher = pattern.matcher(splitedLinkHeaderValue.trim());
        if (matcher.matches()) {
          try {
            return new URI(matcher.group(1));
          } catch (URISyntaxException e) {
            throw new QiitaException(e);
          }
        }
      }
    }
    return null;
  }

  private static class NullPageableResponse<T extends QiitaResponse> extends PageableResponse<T> {
    private final T[] emptyContent;
    @SuppressWarnings("unchecked")
    NullPageableResponse(Class<T> responseType) throws QiitaException {
      super(null, Collections.<String, String>emptyMap(), responseType, null, new String[0]);
      this.emptyContent = (T[]) Array.newInstance(responseType, 0);
    }
    @Override
    public T[] getContents() {
      return emptyContent;
    }
    @Override
    public PageableResponse<T> getFirst() {
      return this;
    }
    @Override
    public PageableResponse<T> getPrev() {
      return this;
    }
    @Override
    public PageableResponse<T> getNext() {
      return this;
    }
    @Override
    public PageableResponse<T> getLast() {
      return this;
    }
  }
}
