package com.webcompiler.app.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Role {

    @JsonProperty
    private  String roleId;

    @JsonProperty
    private String name;

    public Role(){}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
