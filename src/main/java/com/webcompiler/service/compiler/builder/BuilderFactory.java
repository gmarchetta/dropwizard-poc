package com.webcompiler.service.compiler.builder;

import java.io.File;
import java.io.FilenameFilter;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.ArrayUtils;

import com.google.inject.Inject;
import com.webcompiler.app.WebcompilerException;
import com.webcompiler.service.compiler.builder.java.JavaBuildType;
import com.webcompiler.service.compiler.builder.java.MavenBuilder;
import com.webcompiler.service.compiler.builder.java.PlainBuilder;

public class BuilderFactory {
  private MavenBuilder mavenBuilder;
  private PlainBuilder plainBuilder;

  @Inject
  public BuilderFactory(MavenBuilder mavenBuilder, PlainBuilder plainBuilder) {
    this.mavenBuilder = mavenBuilder;
    this.plainBuilder = plainBuilder;
  }

  public Builder getBuilder(File folderToBuild) {
    JavaBuildType buildType = scanFolderForBuildType(folderToBuild);
    switch (buildType) {
      case MAVEN:
        return mavenBuilder;
      case PLAIN_JAR:
        return plainBuilder;
      case GRADLE:
      default:
        throw new WebcompilerException(
            "The submitted app does not match any of our supported builder structure. Please, check the submitted app and try again.",
            Status.BAD_REQUEST);
    }
  }

  public JavaBuildType scanFolderForBuildType(File folderToBuild) {
    if (hasMavenStructure(folderToBuild)) {
      return JavaBuildType.MAVEN;
    }

    throw new WebcompilerException(
        "The submitted app does not match any of our supported builder structure. Please, check the submitted app and try again.",
        Status.BAD_REQUEST);
  }

  private boolean hasMavenStructure(File folderToBuild) {
    File[] matches = folderToBuild.listFiles(new FilenameFilter() {
      public boolean accept(File folder, String filename) {
        return filename.startsWith("pom") && filename.endsWith(".xml");
      }
    });
    return ArrayUtils.isNotEmpty(matches);
  }
}
