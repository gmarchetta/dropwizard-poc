package com.webcompiler.service.compiler.builder;

import static org.mockito.Mockito.mock;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.webcompiler.service.compiler.builder.java.MavenBuilder;
import com.webcompiler.service.compiler.builder.java.PlainBuilder;

public class BuilderFactoryTest {
  private String webcompilerProjectFolder = System.getProperty("user.dir");
  private MavenBuilder mavenBuilder = mock(MavenBuilder.class);
  private PlainBuilder plainBuilder = mock(PlainBuilder.class);
  private BuilderFactory builderFactory = new BuilderFactory(mavenBuilder, plainBuilder);

  @Test
  public void testBuildMavenBuilder() {
    Builder builder = builderFactory.getBuilder(new File(webcompilerProjectFolder));
    Assert.assertEquals(mavenBuilder, builder);
  }
}
