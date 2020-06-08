package com.example.one.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
//@Controller
//
//@RequestMapping("server.error.path:${error.path:/error}")
//public class CustomizeErrorController implements ErrorController {
//    @Override
//    public String getErrorPath(){
//
//        return  "error";
//    }
//
//    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
//    public ModelAndView errorHtml(HttpServletRequest request,
//                                  Model model){
//            HttpStatus status = getStatus(request);
//            if(status.is4xxClientError()){
//                model.addAttribute("Errormessage","估计不止我不知道这个请求的是错误的，你也知道吧！！");
//            }
//            if(status.is5xxServerError()){
//                model.addAttribute("Errormessage","页面加载错误，估计服务器下班了！！");
//            }
//            return new ModelAndView("error");
//    }
//
//    private HttpStatus getStatus(HttpServletRequest request){
//        Integer statusCode = (Integer) request
//                .getAttribute("javax.servlet.error.status_code");
//        if(statusCode ==null){
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        try{
//            return HttpStatus.valueOf(statusCode);
//        }catch(Exception ex){
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//    }
//}
@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class CustomizeErrorController implements ErrorController {
    @Override
    public String getErrorPath() {

        return "error";
    }
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()){
            model.addAttribute("Errormessage","估计不止我知道这个请求的是错误的，你也知道吧！！");
        }
        if (status.is5xxServerError()){
            model.addAttribute("Errormessage","未登陆，无法请求资源！！！");
        }
        return  new ModelAndView("error");
    }
    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
