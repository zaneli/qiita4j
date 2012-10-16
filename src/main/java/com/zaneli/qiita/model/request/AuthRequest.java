package com.zaneli.qiita.model.request;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class AuthRequest extends ParamsRequest {

  private final String urlName;
  private final String password;

  public AuthRequest(String urlName, String password) {
    this.urlName = urlName;
    this.password = password;
  }

  @Override
  public Map<String, String> createParams() {
    Map<String, String> params = new HashMap<>();
    params.put("url_name", urlName);
    params.put("password", password);
    return params;
  }
}
