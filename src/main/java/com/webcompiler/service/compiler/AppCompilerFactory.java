package com.webcompiler.service.compiler;

import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.webcompiler.app.WebcompilerException;

public class AppCompilerFactory {
  private JavaCompiler javaCompiler;

  @Inject
  public AppCompilerFactory(JavaCompiler javaCompiler) {
    this.javaCompiler = javaCompiler;
  }

  public AppCompiler getCompiler(AppCompilerLanguage language) {
    switch (language) {
      case JAVA:
        return javaCompiler;
      default:
        throw new WebcompilerException(
            "The specified compiler is not available. It may not exist, or it may not be implemented yet.",
            Status.BAD_REQUEST);
    }
  }
}
