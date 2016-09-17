package com.webcompiler.service;

import java.io.File;
import java.io.InputStream;

import com.google.inject.Inject;
import com.webcompiler.model.BuildResult;
import com.webcompiler.service.compiler.AppCompiler;
import com.webcompiler.service.compiler.AppCompilerFactory;
import com.webcompiler.service.compiler.AppCompilerLanguage;
import com.webcompiler.service.compression.CompressionService;

public class AppBuilderService {

  private AppCompilerFactory compilerFactory;
  private CompressionService compressionService;

  @Inject
  public AppBuilderService(AppCompilerFactory compilerFactory, CompressionService compressionService) {
    this.compilerFactory = compilerFactory;
    this.compressionService = compressionService;
  }

  public BuildResult buildApp(String user, InputStream input, AppCompilerLanguage compilerLanguage) {
    AppCompiler compiler = compilerFactory.getCompiler(compilerLanguage);
    String uncompressedAppFolderPath = compressionService.uncompressFile(input, user);
    File uncompressedAppFolder = new File(uncompressedAppFolderPath);
    BuildResult buildResult = compiler.test(uncompressedAppFolder, user);
    return buildResult;
  }
}
