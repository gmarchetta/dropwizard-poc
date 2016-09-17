package com.webcompiler.app;

import org.skife.jdbi.v2.DBI;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.webcompiler.app.config.WebcompilerConfiguration;
import com.webcompiler.app.daos.UserDAO;
import com.webcompiler.service.AppBuilderService;
import com.webcompiler.service.compiler.AppCompilerFactory;
import com.webcompiler.service.compiler.JavaCompiler;
import com.webcompiler.service.compiler.builder.BuilderFactory;
import com.webcompiler.service.compiler.builder.java.MavenBuilder;
import com.webcompiler.service.compiler.builder.java.PlainBuilder;
import com.webcompiler.service.compression.CompressionService;
import com.webcompiler.service.logs.LoggingService;
import com.webcompiler.service.logs.SplunkLoggingService;

import io.dropwizard.Configuration;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

public class WebcompilerModule extends AbstractModule {

  @Inject
  private final WebcompilerConfiguration config;

  @Inject
  private final Environment environment;

  public WebcompilerModule(WebcompilerConfiguration configuration, Environment environment) {
    this.config = configuration;
    this.environment = environment;
  }

  @Override
  protected void configure() {
    bind(Configuration.class).toInstance(config);
    bind(Environment.class).toInstance(environment);
    bind(AppCompilerFactory.class);
    bind(JavaCompiler.class);
    bind(CompressionService.class);
    bind(MavenBuilder.class);
    bind(PlainBuilder.class);
    bind(BuilderFactory.class);
    bind(AppBuilderService.class);
    bind(LoggingService.class).annotatedWith(Names.named("SplunkLogging")).to(SplunkLoggingService.class);
  }

  @Provides
  @Singleton
  public UserDAO getUserDAO() {
    final DBIFactory factory = new DBIFactory();
    DBI jdbi = factory.build(environment, config.getDatabase(), "mysql");
    return jdbi.onDemand(UserDAO.class);
  }
}

