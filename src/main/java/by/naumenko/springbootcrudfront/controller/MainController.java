package by.naumenko.springbootcrudfront.controller;

import by.naumenko.springbootcrudfront.entity.User;
import by.naumenko.springbootcrudfront.servise.RoleService;
import by.naumenko.springbootcrudfront.servise.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/main")
public class MainController {

    private static final String REDIRECT = "redirect:/main";
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("")
    public String getMain(@CurrentSecurityContext(expression = "authentication.principal") User principal, User user,
                          Model model) {
        model.addAttribute("principal", principal);
        model.addAttribute("listUser", userService.findAll());
        model.addAttribute("roles", roleService.findAllRole());
        model.addAttribute("user", user);
        return "main-page";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult,
                             @RequestParam String[] authorities) {
        user.setAuthorities(roleService.addRoles(authorities));
        userService.saveUser(user);
        return REDIRECT;
    }

    @GetMapping("/{id}/update")
    public String getModalFormUserEdit(@PathVariable("id") Long id, Model model) {
            model.addAttribute("roles", roleService.findAllRole());
            model.addAttribute("user", userService.getUser(id));
            return "blocks/user-edit-form";
    }

    @GetMapping("/{id}/remove")
    public String getModalFormUserDelete(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roles", roleService.findAllRole());
        model.addAttribute("user", userService.getUser(id));
        return "blocks/user-delete-form";
    }

    @PutMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                             @RequestParam String[] authorities) {
        user.setAuthorities(roleService.addRoles(authorities));
        userService.saveUser(user);
        return REDIRECT;
    }

    @RequestMapping(value = "/remove", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteUser(@ModelAttribute("user") User user){
        userService.removeUser(user.getId());
        return REDIRECT;
    }

}
