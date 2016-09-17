package com.webcompiler.service.compiler;

import static org.mockito.Mockito.mock;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Test;

import com.webcompiler.app.WebcompilerException;

public class AppCompilerFactoryTest {
  private JavaCompiler javaCompiler = mock(JavaCompiler.class);
  private AppCompilerFactory appCompilerFactory = new AppCompilerFactory(javaCompiler);

  @Test
  public void getJavaCompilerSuccess() {
    AppCompiler compiler = appCompilerFactory.getCompiler(AppCompilerLanguage.JAVA);
    Assert.assertEquals(compiler, javaCompiler);
    Assert.assertTrue(compiler instanceof JavaCompiler);
  }

  @Test(expected = WebcompilerException.class)
  public void getCCompilerNotYetImplementedFailure() {
    try {
      appCompilerFactory.getCompiler(AppCompilerLanguage.C);
    } catch (WebcompilerException e) {
      Assert.assertEquals(
          "The specified compiler is not available. It may not exist, or it may not be implemented yet.",
          e.getDetail());
      Assert.assertEquals(Status.BAD_REQUEST, e.getStatus());
      throw e;
    }
  }
}
