package com.webcompiler.resource;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.inject.Inject;
import com.webcompiler.app.WebcompilerException;
import com.webcompiler.app.authentication.BasicUser;
import com.webcompiler.model.BuildResult;
import com.webcompiler.service.AppBuilderService;
import com.webcompiler.service.compiler.AppCompilerLanguage;

import io.dropwizard.auth.Auth;

@Path("/build")
public class AppBuilderResource {

  private AppBuilderService appBuilderService;

  @Inject
  public AppBuilderResource(AppBuilderService appBuilderService) {
    this.appBuilderService = appBuilderService;
  }

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response buildApp(@FormDataParam("file") InputStream input, @FormDataParam("file") FormDataBodyPart fileDetail,
      @Auth BasicUser user, @QueryParam("compilerLanguage") AppCompilerLanguage compilerLanguage) {
    if (compilerLanguage == null) {
      throw new WebcompilerException("Please, specify a language to compile the file with.", Status.BAD_REQUEST);
    }

    BuildResult buildResult = appBuilderService.buildApp(user.getName(), input, compilerLanguage);
    return Response.status(Status.OK).entity(buildResult).build();
  }

}
