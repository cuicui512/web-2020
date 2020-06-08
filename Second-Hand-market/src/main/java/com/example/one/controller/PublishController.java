package com.example.one.controller;

import com.example.one.Dto.PaginationDTO;
import com.example.one.Dto.QuestionDTO;
import com.example.one.Service.QuestionService;
import com.example.one.cache.TagCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
//    @Autowired(required = false)
//    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private QuestionService questionService;


    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        PaginationDTO recommendList = questionService.recommendList( 10);
        model.addAttribute("recommendList", recommendList);
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        PaginationDTO recommendList = questionService.recommendList( 10);
        model.addAttribute("recommendList", recommendList);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            @RequestParam(value = "id", required = false) Long id,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user);
        }


        if (user == null) {
            model.addAttribute("error", "*用户未登陆");
            return "publish";
        }
        if (title == null || title.equals("")) {
            model.addAttribute("error", "发布标题不能为空！！！");
            return "publish";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error", "商品描述不能为空！！！");
            return "publish";
        }
        if (tag == null || tag.equals("") || tag.equals(" ")) {
            model.addAttribute("error", "商品标签标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法商品标签" + invalid);
            return "publish";
        }
        PaginationDTO recommendList = questionService.recommendList( 10);
        model.addAttribute("recommendList", recommendList);
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.creatOrUpdate(question);

//        questionMapper.create(question);
//        返回首页
        return "redirect:/";
    }
}
