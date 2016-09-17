package com.webcompiler.app;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.webcompiler.app.authentication.BasicAuthenticator;
import com.webcompiler.app.authentication.BasicUser;
import com.webcompiler.app.config.WebcompilerConfiguration;
import com.webcompiler.app.daos.UserDAO;
import com.webcompiler.providers.WebcompilerExceptionMapper;
import com.webcompiler.resource.AppBuilderResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class WebcompilerApp extends Application<WebcompilerConfiguration> {

  public static void main(String[] args) throws Exception {
    new WebcompilerApp().run(args);
  }

  @Override
  public void run(WebcompilerConfiguration config, Environment environment) {
    Injector injector = Guice.createInjector(new WebcompilerModule(config, environment));
    environment.jersey().register(MultiPartFeature.class);
    environment.jersey().register(injector.getInstance(AppBuilderResource.class));
    BasicAuthenticator authenticator = new BasicAuthenticator(injector.getInstance(UserDAO.class));
    environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<BasicUser>()
        .setAuthenticator(authenticator).setPrefix("Basic").buildAuthFilter()));
    configureCors(environment);
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(BasicUser.class));
    environment.jersey().register(new WebcompilerExceptionMapper());
  }

  @Override
  public void initialize(Bootstrap<WebcompilerConfiguration> bootstrap) {
    bootstrap.addBundle(new AssetsBundle("/assets/", "/site"));
  }

  private void configureCors(Environment environment) {
    FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
    filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD,PATCH");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization,Content-Disposition,Access-Control-Expose-Headers,Pragma,Cache-Control,Expires");
    filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
  }

}