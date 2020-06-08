package com.example.one.Dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private String outerTitle;
    private Long notifier;
    private String notifierName;
    private Integer status;
    private String typeName;
    private Long outerid;
    private Integer type;

}
