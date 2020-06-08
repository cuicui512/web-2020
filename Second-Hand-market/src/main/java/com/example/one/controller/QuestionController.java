package com.example.one.controller;

import com.example.one.Dto.CommentDTO;
import com.example.one.Dto.QuestionDTO;
import com.example.one.Service.CommentService;
import com.example.one.Service.QuestionService;
import com.example.one.enums.CommentTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired(required = false)
    private QuestionService questionService;
    @Autowired(required = false)
    private CommentService commentService;


    @GetMapping("question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        //        获取用户头像
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //        List<CommentDTO> comment2DTOList = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        //        增加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTOList);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}

