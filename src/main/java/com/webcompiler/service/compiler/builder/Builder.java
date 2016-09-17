package com.webcompiler.service.compiler.builder;

import java.io.File;

import com.webcompiler.model.BuildResult;

public interface Builder {
  BuildResult build(File folderToBuild, String user);
  BuildResult compile(File folderToBuild, String user);
  BuildResult test(File folderToBuild, String user);
}
