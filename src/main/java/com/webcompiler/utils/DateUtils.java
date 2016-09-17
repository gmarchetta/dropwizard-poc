package com.webcompiler.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
  public static String getCurrentDate() {
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    return format.format(new Date());
  }
}
