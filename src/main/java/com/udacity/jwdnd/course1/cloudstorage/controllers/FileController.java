package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/files")
@Controller
public class FileController {

    @Autowired
    private FilesService filesService;

    @PostMapping("/save")
    public ModelAndView save(@RequestParam("fileUpload") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        try {
            filesService.save(file);
            modelAndView.addObject("message", "file saved successfully");
            modelAndView.addObject("alertClass", "alert-sucess");

        } catch (Exception ex) {
            ex.printStackTrace();
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong!!");
        }
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteFile(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");

        try {
            filesService.delete(id);
            modelAndView.addObject("alertClass", "alert-success");
            modelAndView.addObject("message", "File deleted successfully");
        } catch (Exception e) {
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong");
        }
        return modelAndView;
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
        // Load file from database
        Files file = filesService.findById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }
}
