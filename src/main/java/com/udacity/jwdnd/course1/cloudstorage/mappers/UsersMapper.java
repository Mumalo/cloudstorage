package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersMapper {

    @Insert("INSERT INTO Users(username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    Integer save(Users user);

    @Delete("DELETE  from Users where id = #{user.id}")
    void delete(Users user);

    @Update("UPDATE Users SET first_name=#{firstName}, last_name=#{lastName} WHERE id=#{id}")
    Users update(Users user);

    @Select("SELECT * FROM Users where id = #{id}")
    Users findById(Integer id);

    @Select("SELECT * FROM Users where username=#{username}")
    Users findByUsername(String username);

}
