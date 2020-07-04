package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NotesMapper {

    @Select("SELECT * FROM Notes where id = #{noteId}")
    Notes findById(Integer noteId);

    @Delete("DELETE  FROM Notes where id = #{id}")
    void delete(Notes note);

    @Insert("INSERT INTO Notes (id, title, description, userid) VALUES (#{id},#{title},#{description},#{user.id})")
    Integer save(Notes note);

    @Update("UPDATE Notes SET title=#{title}, description=#{description} where id=#{id}")
    Integer update(Notes note);

    @Select("SELECT * FROM Notes where userid=#{id}")
    List<Notes> findAllByUser(Users user);
}
