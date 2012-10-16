package com.zaneli.qiita.model.response;

@SuppressWarnings("serial")
public class RateLimit extends QiitaResponse {

  private int remaining;
  private int limit;

  public int getRemaining() {
    return remaining;
  }

  public void setRemaining(int remaining) {
    this.remaining = remaining;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }
}
