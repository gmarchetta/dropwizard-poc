package com.webcompiler.service.logs;

import java.util.HashMap;
import java.util.Map;

import com.splunk.Receiver;
import com.splunk.Service;
import com.webcompiler.utils.DateUtils;

/**
 * Commented out. Need more testing. This is the implementation of the logging service class, so we
 * can communicate with splunk. Additional implemenations of LoggingService can be included to log
 * to other logging apps.
 * 
 * @author gaston
 */
public class SplunkLoggingService implements LoggingService {
  private Service splunkService;
  private static final String INFO_FORMAT = "[INFO] User: %s - %s - %s";
  private static final String ERROR_FORMAT = "[ERROR] User: %s - %s - %s. Cause: %s";
  private static final String WARNING_FORMAT = "[WARNING] User: %s - %s - %s";


  public SplunkLoggingService() {
    Map<String, Object> connectionArguments = new HashMap<>();
    connectionArguments.put("host", "localhost");
    connectionArguments.put("username", "admin");
    connectionArguments.put("password", "splunk");
    connectionArguments.put("port", 8089);
    connectionArguments.put("scheme", "https");
    splunkService = Service.connect(connectionArguments);
  }

  @Override
  public void info(String message, String user) {
     String logMessage = String.format(INFO_FORMAT, user, message, DateUtils.getCurrentDate());
     Receiver splunkReceiver = splunkService.getReceiver();
     splunkReceiver.log(logMessage);
  }

  @Override
  public void error(String message, String user, Exception exception) {
     String logMessage = String.format(ERROR_FORMAT, user, message, DateUtils.getCurrentDate(),
     exception.getMessage());
     Receiver splunkReceiver = splunkService.getReceiver();
     splunkReceiver.log(logMessage);
  }

  @Override
  public void warning(String message, String user) {
     String logMessage = String.format(WARNING_FORMAT, user, message, DateUtils.getCurrentDate());
     Receiver splunkReceiver = splunkService.getReceiver();
     splunkReceiver.log(logMessage);
  }
}
