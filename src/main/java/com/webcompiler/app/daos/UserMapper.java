package com.webcompiler.app.daos;


import com.webcompiler.app.domain.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {

    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        return new User(
                r.getString("name"),
                r.getString("password"));
    }
}
