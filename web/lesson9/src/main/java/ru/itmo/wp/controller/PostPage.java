package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.security.AnyRole;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    @GetMapping("/post/{id}")
    public String view(@PathVariable String id, Model model) {
        Post post = null;
        if (isLong(id)) {
            post = postService.findById(Long.parseLong(id));
        }

        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/post/{id}")
    public String addComment(@PathVariable String id,
                             @Valid @ModelAttribute("comment") Comment comment,
                             BindingResult bindingResult,
                             HttpSession httpSession,
                             Model model) {
        Post post = null;
        if (isLong(id)) {
            post = postService.findById(Long.parseLong(id));
        }

        if (post == null) {
            putMessage(httpSession, "post is null");
            return "PostPage";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "PostPage";
        }

        comment.setUser(getUser(httpSession));
        comment.setPost(post);

        postService.saveComment(comment, post);
        putMessage(httpSession, "You published new comment");

        return "redirect:/post/" + id;
    }
}
