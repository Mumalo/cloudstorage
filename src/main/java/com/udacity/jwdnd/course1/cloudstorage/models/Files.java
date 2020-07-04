package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.Arrays;

@Entity
@Setter @Getter
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String filename;
    private String contenttype;
    private long filesize;
    @Lob
    private byte[] filedata;
    @ManyToOne
    @JoinColumn(name = "userid")
    private Users owner;

    @Override
    public String toString() {
        return "Files{" +
                "id=" + id +
                ", fileName='" + filename + '\'' +
                ", contentType='" + contenttype + '\'' +
                ", fileSize=" + filesize +
                ", fileData=" + Arrays.toString(filedata) +
                ", owner=" + owner +
                '}';
    }

    public static Files getInstance(MultipartFile multipartFile) throws IOException {
        Files newFile = new Files();
        newFile.setFilename(multipartFile.getName());
        newFile.setContenttype(multipartFile.getContentType());
        newFile.setFilesize(multipartFile.getSize());
        newFile.setFiledata(multipartFile.getBytes());
        return newFile;
    }
}
