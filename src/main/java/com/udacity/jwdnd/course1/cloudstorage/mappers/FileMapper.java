package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {

    @Select("SELECT * FROM Files WHERE id=#{id}")
    Files findById(Integer id);

    @Insert("INSERT INTO Files (filename, contenttype, userid, filesize," +
            "filedata) VALUES (#{filename},#{contenttype},#{owner.id},#{filesize},#{filedata})")
    Integer save(Files file);

    @Delete("DELETE  FROM Files where id = #{fileId}")
    void delete(Integer fileId);

    @Update("UPDATE Files SET filename=#{fileName},filedata=#{fileData} where id=#{id}")
    Integer update(Files file);

    @Select("SELECT * FROM Files where userid=#{id}")
    List<Files> findAllByUser(Users user);

}
