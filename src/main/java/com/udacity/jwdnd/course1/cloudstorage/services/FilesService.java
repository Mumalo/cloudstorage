package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class FilesService {

    @Autowired
    FileMapper fileMapper;
    @Autowired
    AuthService authService;

    public Integer save(MultipartFile multipartFile) throws Exception {
        Users currentUser = authService.getCurrentUser();
        Files newFile = Files.getInstance(multipartFile);
        newFile.setOwner(currentUser);
        return fileMapper.save(newFile);
    }

    public Integer update(Files file){
        return fileMapper.update(file);
    }

    public void delete(Integer fileId){
        fileMapper.delete(fileId);
    }

    public Files findById(Integer id){
        return fileMapper.findById(id);
    }

    public List<Files> findAllByUser(Users user){
        return fileMapper.findAllByUser(user);
    }
}
