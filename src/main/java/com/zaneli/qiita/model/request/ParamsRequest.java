package com.zaneli.qiita.model.request;

import java.util.Map;

@SuppressWarnings("serial")
public abstract class ParamsRequest extends QiitaRequest {

  public abstract Map<String, String> createParams();
}
