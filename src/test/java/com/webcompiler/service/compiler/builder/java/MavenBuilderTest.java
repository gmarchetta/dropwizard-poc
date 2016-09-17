package com.webcompiler.service.compiler.builder.java;

import static org.mockito.Mockito.mock;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.webcompiler.model.BuildResult;
import com.webcompiler.model.BuildStatus;
import com.webcompiler.service.logs.LoggingService;

public class MavenBuilderTest {
  private LoggingService loggingService = mock(LoggingService.class);
  private MavenBuilder mavenBuilder = new MavenBuilder(loggingService);
  private String webcompilerProjectFolder = System.getProperty("user.dir");
  private String user = "user";
  
  // Can not run tests within tests. Infinite loop. Just compile
  @Test
  public void testMavenCompile() {
     BuildResult result = mavenBuilder.compile(new File(webcompilerProjectFolder), user);
     Assert.assertEquals(BuildStatus.SUCCESS, result.getBuildStatus());
  }
}
