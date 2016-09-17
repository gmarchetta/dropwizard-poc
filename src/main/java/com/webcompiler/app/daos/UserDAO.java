package com.webcompiler.app.daos;


import com.webcompiler.app.domain.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

@RegisterMapper({UserMapper.class})
public interface UserDAO extends Transactional<UserDAO> {

    @SqlQuery("select * from user  where name = :name")
    User findDACUserByName(@Bind("name") String name);

}
