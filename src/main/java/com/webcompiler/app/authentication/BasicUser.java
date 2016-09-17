package com.webcompiler.app.authentication;


import java.security.Principal;

public class BasicUser implements Principal {

  private String name;

  public BasicUser(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

}
