package com.webcompiler.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.InputStream;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.verification.VerificationModeFactory;

import com.webcompiler.app.WebcompilerException;
import com.webcompiler.model.BuildResult;
import com.webcompiler.model.BuildStatus;
import com.webcompiler.service.compiler.AppCompiler;
import com.webcompiler.service.compiler.AppCompilerFactory;
import com.webcompiler.service.compiler.AppCompilerLanguage;
import com.webcompiler.service.compression.CompressionService;

public class AppBuilderServiceTest {
  private AppBuilderService appBuilderService;
  private String user = "user";
  private InputStream input = mock(InputStream.class);
  private AppCompilerFactory compilerFactory = mock(AppCompilerFactory.class);
  private CompressionService compressionService = mock(CompressionService.class);
  private AppCompiler compiler = mock(AppCompiler.class);
  private static final String FOLDER = "folder";
  private BuildResult buildSuccess = new BuildResult(BuildStatus.SUCCESS);

  @Before
  public void setUp() {
    appBuilderService = new AppBuilderService(compilerFactory, compressionService);
    doReturn(compiler).when(compilerFactory).getCompiler(AppCompilerLanguage.JAVA);
    doReturn(FOLDER).when(compressionService).uncompressFile(any(InputStream.class), anyString());
  }

  @Test
  public void testBuildSuccess() {
    doReturn(buildSuccess).when(compiler).test(any(File.class), anyString());
    BuildResult result = appBuilderService.buildApp(user, input, AppCompilerLanguage.JAVA);
    verify(compilerFactory, VerificationModeFactory.times(1)).getCompiler(AppCompilerLanguage.JAVA);
    verify(compressionService, VerificationModeFactory.times(1)).uncompressFile(input, user);
    verify(compiler, VerificationModeFactory.times(1)).test(any(File.class), anyString());
    Assert.assertEquals(buildSuccess, result);
  }

  @Test(expected = WebcompilerException.class)
  public void testBuildUncompressionFailure() {
    doThrow(new WebcompilerException("", Status.INTERNAL_SERVER_ERROR)).when(compressionService)
        .uncompressFile(any(InputStream.class), anyString());
    try {
      appBuilderService.buildApp(user, input, AppCompilerLanguage.JAVA);
    } catch (Exception e) {
      verify(compilerFactory, VerificationModeFactory.times(1)).getCompiler(AppCompilerLanguage.JAVA);
      verify(compressionService, VerificationModeFactory.times(1)).uncompressFile(input, user);
      verify(compiler, VerificationModeFactory.noMoreInteractions()).compile(any(File.class), anyString());
      throw e;
    }
  }

  @Test(expected = WebcompilerException.class)
  public void testBuildUnsupportedLanguageFailure() {
    doThrow(new WebcompilerException("Unsupported language", Status.INTERNAL_SERVER_ERROR)).when(compilerFactory)
        .getCompiler(AppCompilerLanguage.C);
    try {
      appBuilderService.buildApp(user, input, AppCompilerLanguage.C);
    } catch (Exception e) {
      verify(compilerFactory, VerificationModeFactory.times(1)).getCompiler(AppCompilerLanguage.C);
      verify(compressionService, VerificationModeFactory.noMoreInteractions()).uncompressFile(any(InputStream.class),
          anyString());
      verify(compiler, VerificationModeFactory.noMoreInteractions()).compile(any(File.class), anyString());
      throw e;
    }
  }
}
