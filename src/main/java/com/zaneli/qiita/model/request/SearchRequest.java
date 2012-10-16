package com.zaneli.qiita.model.request;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class SearchRequest extends ParamsRequest {

  private String query;
  private final String[] queries;
  private Boolean stocked;

  public SearchRequest(String query, String... queries) {
    this.query = query;
    this.queries = queries;
  }

  public void setStocked(boolean stocked) {
    this.stocked = Boolean.valueOf(stocked);
  }

  @Override
  public Map<String, String> createParams() {
    Map<String, String> params = new HashMap<>();
    if (queries == null || queries.length < 1) {
      params.put("q", query);
    } else {
      StringBuilder sb = new StringBuilder(query);
      for (String query : queries) {
        sb.append(' ').append(query);
      }
      params.put("q", sb.toString());
    }
    if (stocked != null) {
      params.put("stocked", stocked.toString());
    }
    return params;
  }
}
