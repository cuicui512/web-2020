package com.example.one.controller;

import com.example.one.Dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(){
        FileDTO fileDTO= new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/wechatLogo.jpg");
        return fileDTO;
    }
}
