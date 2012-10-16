package com.zaneli.qiita.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

  private static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {
    @Override
    protected DateFormat initialValue() {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss +0900");
      dateFormat.setLenient(false);
      return dateFormat;
    }
  };

  public static Date parse(String dateString) throws ParseException {
    return DATE_FORMAT.get().parse(dateString);
  }

  private DateTimeUtil() {
  }
}
