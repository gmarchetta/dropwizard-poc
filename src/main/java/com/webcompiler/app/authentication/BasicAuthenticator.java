package com.webcompiler.app.authentication;


import com.google.inject.Inject;
import com.webcompiler.app.daos.UserDAO;
import com.webcompiler.app.domain.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import com.google.common.base.Optional;
import io.dropwizard.auth.basic.BasicCredentials;

public class BasicAuthenticator implements Authenticator<BasicCredentials, BasicUser> {
  private final UserDAO userDAO;

  @Inject
  public BasicAuthenticator(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public Optional<BasicUser> authenticate(BasicCredentials credentials) throws AuthenticationException {
    User user = userDAO.findDACUserByName(credentials.getUsername());
    if (user != null && user.getPassword().equals(credentials.getPassword())) {
      return Optional.of(new BasicUser(credentials.getUsername()));
    }
    return Optional.absent();
  }
}
