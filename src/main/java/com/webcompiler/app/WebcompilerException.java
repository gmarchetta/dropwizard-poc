package com.webcompiler.app;

import javax.ws.rs.core.Response.Status;

public class WebcompilerException extends RuntimeException {
  private static final long serialVersionUID = 649049766967768234L;
  private String detail;
  private Status status;

  public WebcompilerException(String detail, Status status) {
    this.detail = detail;
    this.status = status;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
