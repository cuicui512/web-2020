package com.example.one.Dto;

import com.example.one.exception.CustomizeErrorCode;
import com.example.one.exception.CustomizeException;
import lombok.Data;

import javax.swing.event.ListDataEvent;
import java.util.List;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T listdata;

    public static ResultDTO errorOf(Integer code ,String message){
        ResultDTO resultDTO =new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode){
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO =new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static <T> ResultDTO okOfComment(T t){
        ResultDTO resultDTO =new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setListdata(t);
        return resultDTO;
    }
    public static ResultDTO errorOf(CustomizeException e){
        return errorOf(e.getCode(),e.getMessage());
    }
}
