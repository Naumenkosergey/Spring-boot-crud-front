package by.naumenko.springbootcrudfront.controller;

import by.naumenko.springbootcrudfront.entity.User;
import by.naumenko.springbootcrudfront.entity.Views;
import by.naumenko.springbootcrudfront.servise.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("home")
public class MainRestController {

    private final UserService userService;
//    private final RoleService roleService;

//    @GetMapping
//    @JsonView(Views.Age.class)
//    public ResponseEntity<List<User>> getAll() {
//        return ResponseEntity.ok(userService.findAll());
//    }
    @GetMapping
    @JsonView(Views.Age.class)
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.Password.class)
    public ResponseEntity<User> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") User userFromDb, @RequestBody User user) {
        BeanUtils.copyProperties(user, userFromDb, "id");
        return userService.saveUser(userFromDb);
    }

    @DeleteMapping("{id}")
    public void removeUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
    }
}
//    @PostMapping("/new")
//    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult,
//                             @RequestParam String[] authorities) {
//        user.setAuthorities(roleService.addRoles(authorities));
//        userService.saveUser(user);
//        return REDIRECT;
//    }

//    @GetMapping("/{id}/update")
//    public String getModalFormUserEdit(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("roles", roleService.findAllRole());
//        model.addAttribute("user", userService.getUser(id));
//        return "blocks/user-edit-form";
//    }
//
//    @GetMapping("/{id}/remove")
//    public String getModalFormUserDelete(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("roles", roleService.findAllRole());
//        model.addAttribute("user", userService.getUser(id));
//        return "blocks/user-delete-form";
//    }
//
//    @PutMapping("/update")
//    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
//                             @RequestParam String[] authorities) {
//        user.setAuthorities(roleService.addRoles(authorities));
//        userService.saveUser(user);
//        return REDIRECT;
//    }
//
//    @RequestMapping(value = "/remove", method = {RequestMethod.DELETE, RequestMethod.GET})
//    public String deleteUser(@ModelAttribute("user") User user){
//        userService.removeUser(user.getId());
//        return REDIRECT;
//    }
//}
