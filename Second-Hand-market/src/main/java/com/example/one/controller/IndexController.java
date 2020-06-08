package com.example.one.controller;

import com.example.one.Dto.PaginationDTO;
import com.example.one.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {
    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "search", required = false) String search) {
        //        获取用户头像
        PaginationDTO Pagination = questionService.List(search,page, size);

        PaginationDTO recommendList = questionService.recommendList( 10);
        model.addAttribute("Pagination", Pagination);
        model.addAttribute("recommendList", recommendList);
        model.addAttribute("search", search);
        return "index";
    }
}
