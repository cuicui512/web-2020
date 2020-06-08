package com.example.one.controller;

import com.example.one.Dto.NotificationDTO;
import com.example.one.Service.NotificationService;
import com.example.one.enums.NotificationTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class notificationController {
    @Autowired(required = false)
    private NotificationService notificationService;

    @Autowired(required = false)
    private NotificationMapper notificationMapper;


    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id, Model model,
                          HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType() || NotificationTypeEnum.REPLY_QUESTION.getType() ==notificationDTO.getType()) {
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else{
            return "redirect:/";
        }

    }

}
