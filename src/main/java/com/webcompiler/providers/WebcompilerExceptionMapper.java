package com.webcompiler.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.webcompiler.app.WebcompilerException;
import com.webcompiler.model.ErrorResponse;

@Provider
public class WebcompilerExceptionMapper implements ExceptionMapper<WebcompilerException> {
  @Override
  public Response toResponse(WebcompilerException exception) {
    return Response.status(exception.getStatus()).entity(new ErrorResponse(exception.getDetail())).build();
  }
}
