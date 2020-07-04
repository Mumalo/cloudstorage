package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    FilesService filesService;

    @Autowired
    NotesService notesService;

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Users currentUser = authService.getCurrentUser();
        List<Credentials> credentials = credentialsService.findAllByUser(currentUser);
        List<Files> files = filesService.findAllByUser(currentUser);
        List<Notes> notes = notesService.findAllByUser(currentUser);
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("files", files);
        modelAndView.addObject("notes", notes);
        modelAndView.addObject("credentials", credentials);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
