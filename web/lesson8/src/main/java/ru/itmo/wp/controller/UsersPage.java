package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.UserStatusForm;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String usersGet(Model model) {
        model.addAttribute("userStatusForm", new UserStatusForm());
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String usersPost(@Valid @ModelAttribute("userStatusForm") UserStatusForm userStatusForm,
                            HttpSession httpSession,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "redirect:/users/all";
        }

        userService.updateDisabled(userStatusForm);

        putMessage(httpSession, "User is " + userStatusForm.getDisabled() + "d!");

        return "redirect:/users/all";
    }
}
