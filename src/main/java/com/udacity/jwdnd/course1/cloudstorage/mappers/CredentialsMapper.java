package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CredentialsMapper {
    @Insert("INSERT INTO Credentials (url, username, password, userid) VALUES (#{url}, #{username}, #{password}, #{owner.id})")
    Integer save(Credentials credentials);

    @Update("UPDATE Credentials SET username=#{username}, password=#{password}, url=#{url} WHERE id=#{id}")
    Integer update(Credentials credentials);

    @Delete("DELETE  FROM Credentials WHERE id = #{id}")
    void delete(Credentials credentials);

    @Select("SELECT * from Credentials where id = #{id}")
    Credentials findById(Integer id);

    @Select("SELECT * from Credentials where userid = #{id}")
    List<Credentials> findAllByUser(Users user);
}
