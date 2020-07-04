package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    NotesService notesService;

    @PostMapping("/save")
    public ModelAndView save(@Valid Notes note, BindingResult bindingResult) {
        System.out.println("not is " + note);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        try {
            if (!bindingResult.hasErrors()) {
                Notes existingNote = notesService.findById(note.getId());
                if (existingNote != null) {
                    notesService.update(note);
                } else {
                    notesService.save(note);
                }
                modelAndView.addObject("alertClass", "alert-success");
                modelAndView.addObject("message", "note saved successfully");
                modelAndView.addObject("notes", new Notes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong!!");
        }
        return modelAndView;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        try {
            Notes existingNote = notesService.findById(id);
            if (existingNote == null) {
                modelAndView.addObject("alertClass", "alert-danger");
                modelAndView.addObject("message", String.format("note with id %d cannot be found", id));
            } else {
                modelAndView.addObject("alertClass", "alert-success");
                modelAndView.addObject("message", "note updated successfully");
                notesService.update(existingNote);
            }
        } catch (Exception ex) {
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "an error occured while updating note");
        }
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        try {
            Notes existingNote = notesService.findById(id);
            if (existingNote == null) {
                modelAndView.addObject("alertClass", "alert-danger");
                modelAndView.addObject("message", String.format("note with id %d cannot be found", id));
            } else {
                modelAndView.addObject("alertClass", "alert-success");
                modelAndView.addObject("message", "note deleted successfully");
                notesService.delete(existingNote);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "an error occured while deleting note");
        }
        return modelAndView;
    }


}
