package com.webcompiler.service.compiler.builder.java;

import java.io.File;
import java.util.Collections;

import javax.ws.rs.core.Response.Status;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import com.google.inject.Inject;
import com.webcompiler.app.WebcompilerException;
import com.webcompiler.model.BuildResult;
import com.webcompiler.model.BuildStatus;
import com.webcompiler.service.compiler.builder.Builder;
import com.webcompiler.service.logs.LoggingService;

public class MavenBuilder implements Builder {
  private LoggingService loggingService;
  private final File MVN_HOME = new File(System.getenv("MVN_HOME"));

  @Inject
  public MavenBuilder(LoggingService loggingService) {
    this.loggingService = loggingService;
  }

  @Override
  public BuildResult test(File folderToBuild, String user) {
    return build(folderToBuild, user, "test");
  }

  @Override
  public BuildResult build(File folderToBuild, String user) {
    return build(folderToBuild, user, "clean install");
  }

  public BuildResult compile(File folderToBuild, String user) {
    return build(folderToBuild, user, "compile");
  }

  private BuildResult build(File folderToBuild, String user, String goalsToRun) {
    InvocationRequest request = new DefaultInvocationRequest();
    request.setPomFile(new File(folderToBuild.getAbsolutePath() + "/pom.xml"));
    request.setGoals(Collections.singletonList(goalsToRun));

    Invoker invoker = new DefaultInvoker();
    InvocationResult mavenResult = null;

    try {
      invoker.setMavenHome(MVN_HOME);
      mavenResult = invoker.execute(request);
    } catch (MavenInvocationException e) {
      loggingService.error("Error compiling using Maven.", user, e);
      throw new WebcompilerException("Error compiling using Maven.", Status.INTERNAL_SERVER_ERROR);
    }

    BuildResult buildResult = null;
    int exitCode = mavenResult.getExitCode();
    if (exitCode == 0) {
      buildResult = new BuildResult(BuildStatus.SUCCESS);
    } else {
      buildResult = new BuildResult(BuildStatus.FAILURE,
          "Though the application submitted is a Maven based app, BUILD failed. Please check your submitted app, and try again.");
    }

    return buildResult;
  }
}
