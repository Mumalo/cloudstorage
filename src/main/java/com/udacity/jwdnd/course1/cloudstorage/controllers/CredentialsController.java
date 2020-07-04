package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
@RequestMapping("/credentials")
@Controller
public class CredentialsController {

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    HashService hashService;

    @PostMapping("/save")
    public ModelAndView createCredential(@Valid Credentials credential, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        modelAndView.addObject("credential", credential);

        try {
            if (!bindingResult.hasErrors()) {
                Credentials existingCredentials = credentialsService.finById(credential.getId());
                if (existingCredentials != null){
                    credentialsService.update(credential);
                    modelAndView.addObject("alertClass", "alert-success");
                    modelAndView.addObject("message", "Credential updated successfully");
                } else {
                    credential.setKey(hashService.generateRandomSalt());
                    credentialsService.save(credential);
                    modelAndView.addObject("message", "Credential saved successfully");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong");
        }
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteCredential(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");

        try {
            Credentials existingCredentials = credentialsService.finById(id);
            if (existingCredentials == null){
                modelAndView.addObject("alertClass", "alert-danger");
                modelAndView.addObject("message", String.format("credentials with id %d does not exist", id));
            } else {
                credentialsService.delete(existingCredentials);
                modelAndView.addObject("alertClass", "alert-success");
                modelAndView.addObject("message", "Credentials deleted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong");
        }
        return modelAndView;
    }

}
