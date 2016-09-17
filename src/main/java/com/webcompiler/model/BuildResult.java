package com.webcompiler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class BuildResult {
  @JsonProperty
  private BuildStatus buildStatus;

  @JsonProperty
  private Exception errors;

  @JsonProperty
  private String errorMessage;

  public BuildResult() {}

  public BuildResult(BuildStatus buildStatus, Exception errors) {
    this.buildStatus = buildStatus;
    this.errors = errors;
  }

  public BuildResult(BuildStatus buildStatus, String errorMessage) {
    this.buildStatus = buildStatus;
    this.errorMessage = errorMessage;
  }

  public BuildResult(BuildStatus buildStatus) {
    this.buildStatus = buildStatus;
  }

  public BuildStatus getBuildStatus() {
    return buildStatus;
  }

  public void setBuildStatus(BuildStatus buildStatus) {
    this.buildStatus = buildStatus;
  }

  public Exception getErrors() {
    return errors;
  }

  public void setErrors(Exception errors) {
    this.errors = errors;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
