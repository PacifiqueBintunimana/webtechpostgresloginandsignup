package io.reflectoring.delivery.controller;


import io.reflectoring.delivery.modal.UserModel;
import io.reflectoring.delivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/signup")
    public String getsignupPage(Model model){
        model.addAttribute ("registerRequest",new UserModel());
        return "signup";
    }

    @GetMapping("/signin")
    public String signinPage(Model model) {
        model.addAttribute("loginRequest",new UserModel() );
        return "signin";
    }
    @PostMapping("/signup")
    public String signupProcess(@ModelAttribute UserModel userModel){
        System.out.println("register request:" + userModel);
       UserModel registerUser = userService.registerUser(userModel.getUsername(), userModel.getFirstname(), userModel.getLastname(), userModel.getPhone(), userModel.getDateofbirth(), userModel.getPassword(),userModel.getEmail());
     return registerUser == null ?"/error" : "redirect:/signin";
    }
    @PostMapping("/signin")
    public String signinProcess(@ModelAttribute UserModel userModel, Model model){
        System.out.println("login request:" + userModel);
        UserModel authentication = userService.authentication(userModel.getEmail(), userModel.getPassword());
        if (authentication!= null)
        {
            model.addAttribute("username", authentication.getUsername());
            return "/personal_page";
        }
        else{
            return "/error";
        }

    }
}

