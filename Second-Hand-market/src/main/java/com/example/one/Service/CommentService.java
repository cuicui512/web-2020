package com.example.one.Service;

import com.example.one.Dto.CommentDTO;
import com.example.one.enums.CommentTypeEnum;
import com.example.one.enums.NotificationStatusEnum;
import com.example.one.enums.NotificationTypeEnum;
import com.example.one.exception.CustomizeErrorCode;
import com.example.one.exception.CustomizeException;
import com.example.one.mapper.*;
import com.example.one.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private ExpendMapper expendMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARM_NOT_FOUND);
        }
        if (comment.getType() != null && !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException((CustomizeErrorCode.TYPE_PARM_WRONG));
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_EXIST);
            } else {
                Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
                if (question == null) {
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }
                commentMapper.insert(comment);
//                    增加评论数
                Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
                parentComment.setCommentCount(1L);
                expendMapper.incToCommentCount(parentComment);
                // 创建回复评论通知
                CreateNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(),
                        NotificationTypeEnum.REPLY_COMMENT, NotificationStatusEnum.UNREAD, question.getId());
            }

        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            expendMapper.incCommentCount(question);
            //            增加问题回复通知功能
            CreateNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(),
                    NotificationTypeEnum.REPLY_QUESTION, NotificationStatusEnum.UNREAD, question.getId());
        }
    }

    // 创建回复评论\问题通知
    private void CreateNotify(Comment comment, Long receiver, String notifierName, String outerTitle,
                              NotificationTypeEnum notificationTypeEnum,
                              NotificationStatusEnum notificationStatusEnum, Long outerId) {
        //        当接收人和发通知的人是同一个人接收通知
        if (receiver == comment.getCommentator()) {
            return;
        }

        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationTypeEnum.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(receiver);
        notification.setStatus(notificationStatusEnum.getStatus());
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }


    //查看一个方法或者类的依赖的快捷键： alt + F7
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
//        抽取变量的快捷键： ctrl + Alt + v
        //抽取参数的快捷键：ctrl + alt + p
        //抽取方法：ctrl +alt = m
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        //按照gmt_create进行排序
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //使用Lamba去重评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment 为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;

    }

    public void incLikeCount(Long id) {
        Comment record = new Comment();
        record.setLikeCount(1L);
        record.setId(id);
        expendMapper.incLikeCount(record);
    }
}
