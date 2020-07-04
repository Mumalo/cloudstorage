package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.security.CustomAuthenticationProvider;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    UsersService usersService;

    @RequestMapping("/login")
    public ModelAndView loginForm(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView signup(){
        System.out.println("signing up");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Users.getInstance());
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView register(@Valid Users user, BindingResult bindingResult, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("saving new user");
        try {
            Users existingUser = usersService.findByUsername(user.getUsername());
            if (existingUser != null){
                bindingResult.rejectValue("username", "error.user", "A user exist with username");
            } else {
                if (!bindingResult.hasErrors()){
                    usersService.save(user);
                    modelAndView.setViewName("redirect:login");
                }
            }


        } catch (Exception ex){
            System.err.println("The following exceptions occurred " + ex.getLocalizedMessage());
            ex.printStackTrace();
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong!!");
        }

        return modelAndView;
    }
}
