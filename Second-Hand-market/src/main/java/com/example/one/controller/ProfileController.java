package com.example.one.controller;

import com.example.one.Dto.PaginationDTO;
import com.example.one.Service.NotificationService;
import com.example.one.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired(required = false)
    private QuestionService questionService;

    @Autowired(required = false)
    private NotificationService notificationService;


    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的发布");
            PaginationDTO paginationDTO = questionService.profileList(user.getId(), page, size);
            model.addAttribute("Pagination", paginationDTO);
        } else if ("replies".equals(action)) {
            PaginationDTO notificationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("notification", notificationDTO);
        } else if ("information".equals(action)) {
            model.addAttribute("section", "information");
            model.addAttribute("sectionName", "我的个人信息");
            model.addAttribute("user", user);
        }
        model.addAttribute("userId", user.getId());
        return "profile";
    }

}
