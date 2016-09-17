package com.webcompiler.service.compiler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.internal.verification.VerificationModeFactory;

import com.webcompiler.model.BuildResult;
import com.webcompiler.model.BuildStatus;
import com.webcompiler.service.compiler.builder.BuilderFactory;
import com.webcompiler.service.compiler.builder.java.MavenBuilder;


public class JavaCompilerTest {
  private BuilderFactory builderFactory = mock(BuilderFactory.class);
  private JavaCompiler javaCompiler = new JavaCompiler(builderFactory);
  private MavenBuilder mavenBuilder = mock(MavenBuilder.class);
  private File folderToCompile = mock(File.class);
  private String user = "user";
  private BuildResult successResult = new BuildResult(BuildStatus.SUCCESS);
  private BuildResult failureResult = new BuildResult(BuildStatus.ERROR);

  @Test
  public void testCompileSuccess() {
    doReturn(mavenBuilder).when(builderFactory).getBuilder(folderToCompile);
    doReturn(successResult).when(mavenBuilder).build(any(File.class), anyString());
    BuildResult result = javaCompiler.compile(folderToCompile, user);
    verify(mavenBuilder, VerificationModeFactory.times(1)).build(folderToCompile, user);
    Assert.assertEquals(successResult, result);
  }

  @Test
  public void testCompileFailure() {
    doReturn(mavenBuilder).when(builderFactory).getBuilder(folderToCompile);
    doReturn(failureResult).when(mavenBuilder).build(any(File.class), anyString());
    BuildResult result = javaCompiler.compile(folderToCompile, user);
    verify(mavenBuilder, VerificationModeFactory.times(1)).build(folderToCompile, user);
    Assert.assertEquals(failureResult, result);
  }
}
