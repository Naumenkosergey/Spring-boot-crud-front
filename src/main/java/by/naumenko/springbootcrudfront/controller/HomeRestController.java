package by.naumenko.springbootcrudfront.controller;

import by.naumenko.springbootcrudfront.entity.Role;
import by.naumenko.springbootcrudfront.entity.User;
import by.naumenko.springbootcrudfront.entity.Views;
import by.naumenko.springbootcrudfront.servise.RoleService;
import by.naumenko.springbootcrudfront.servise.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class HomeRestController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    @JsonView(Views.Role.class)
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.Role.class)
    public User getOne(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id")Long id, @RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("{id}")
    public void removeUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
    }

    @GetMapping("/roles")
    @JsonView(Views.Role.class)
    public List<Role> getAllRoles(){
       return roleService.findAllRole();
    }

}
