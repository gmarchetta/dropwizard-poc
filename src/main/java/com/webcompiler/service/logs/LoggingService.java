package com.webcompiler.service.logs;

import com.google.inject.ImplementedBy;

@ImplementedBy(SplunkLoggingService.class)
public interface LoggingService {

  void info(String info, String user);

  void error(String error, String user, Exception exception);

  void warning(String warning, String user);

}
