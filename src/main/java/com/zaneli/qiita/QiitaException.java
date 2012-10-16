package com.zaneli.qiita;

@SuppressWarnings("serial")
public class QiitaException extends Exception {

  public QiitaException(String message) {
    super(message);
  }

  public QiitaException(Throwable cause) {
    super(cause);
  }

  public QiitaException(String message, Throwable cause) {
    super(message, cause);
  }
}
