package com.webcompiler.service.compiler;

import java.io.File;

import com.google.inject.Inject;
import com.webcompiler.model.BuildResult;
import com.webcompiler.service.compiler.builder.Builder;
import com.webcompiler.service.compiler.builder.BuilderFactory;

public class JavaCompiler implements AppCompiler {

  private BuilderFactory builderFactory;

  @Inject
  public JavaCompiler(BuilderFactory builderFactory) {
    this.builderFactory = builderFactory;
  }

  @Override
  public BuildResult compile(File folderToCompile, String user) {
    Builder builder = builderFactory.getBuilder(folderToCompile);
    return builder.build(folderToCompile, user);
  }
  
  @Override
  public BuildResult test(File folderToCompile, String user) {
    Builder builder = builderFactory.getBuilder(folderToCompile);
    return builder.test(folderToCompile, user);
  }
}
