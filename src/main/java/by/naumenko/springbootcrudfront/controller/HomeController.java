package by.naumenko.springbootcrudfront.controller;

import by.naumenko.springbootcrudfront.entity.User;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping()
    public String getHomePage(@CurrentSecurityContext(expression = "authentication.principal") User principal,
                              Model model){
        model.addAttribute("principal", principal);
        return "home";
    }

    @GetMapping("/signin")
    public String loginPage(){
        return "login";
    }
}
