package com.example.one.controller;

import com.example.one.Dto.CommentCreateDTO;
import com.example.one.Dto.CommentDTO;
import com.example.one.Dto.ResultDTO;
import com.example.one.Service.CommentService;
import com.example.one.Service.QuestionService;
import com.example.one.enums.CommentTypeEnum;
import com.example.one.exception.CustomizeErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired(required = false)
    private CommentService commentService;

    @Autowired(required = false)
    private QuestionService questionService;

    Long QuestionGlobal = 0L;
    Long CommentGlobal = 0L;


    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setCommentCount(0L);
        comment.setLikeCount(0L);
        commentService.insert(comment, user);
        return ResultDTO.okOf();
    }

    //整体修改一个方法或者变量的快捷键： shift + F6
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOfComment(commentDTOList);
    }

    @ResponseBody
    @RequestMapping(value = "/comment/likeCount/{id}", method = RequestMethod.POST)
    public Object incLikeCount(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (CommentGlobal.equals(id)) {
            return ResultDTO.errorOf(CustomizeErrorCode.MORE_CLICK);
        }
        CommentGlobal = id ;
        commentService.incLikeCount(id);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/question/likeCount/{id}", method = RequestMethod.POST)
    public Object incQuestionLikeCount(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(QuestionGlobal.equals(id)){
            return ResultDTO.errorOf(CustomizeErrorCode.MORE_CLICK);
        }
        QuestionGlobal = id ;
        questionService.incLikeCount(id);
        return ResultDTO.okOf();
    }
}
