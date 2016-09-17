package com.webcompiler.service.compiler;

import java.io.File;

import com.webcompiler.model.BuildResult;

public interface AppCompiler {
  BuildResult compile(File folderToCompile, String user);
  BuildResult test(File folderToCompile, String user);
}
