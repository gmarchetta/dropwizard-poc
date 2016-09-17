package com.webcompiler.service.compiler.builder.java;

import java.io.File;

import javax.ws.rs.core.Response.Status;

import com.webcompiler.app.WebcompilerException;
import com.webcompiler.model.BuildResult;
import com.webcompiler.service.compiler.builder.Builder;

public class PlainBuilder implements Builder {
  @Override
  public BuildResult build(File folderToBuild, String user) {
    throw new WebcompilerException("Not yet implemented!", Status.BAD_REQUEST);
  }
  
  @Override
  public BuildResult compile(File folderToBuild, String user) {
    throw new WebcompilerException("Not yet implemented!", Status.BAD_REQUEST);
  }
  
  @Override
  public BuildResult test(File folderToBuild, String user) {
    throw new WebcompilerException("Not yet implemented!", Status.BAD_REQUEST);
  }
}
