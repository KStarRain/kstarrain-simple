package com.kstarrain.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Advertisement {

    private Integer id;

    private String page;

    private String url;

    private String localPath;

    private String position;

    private String action;

    private String title;

    private Integer width;

    private Integer height;

    private String description;

    private Integer displayPriority;

    private Integer showTime;

    private Boolean needLogin;

    private String status;

    private Date startTime;

    private Date endTime;

    private String createdBy;
    private String updateBy;
    private Date createdTime;
    private Date updateTime;

    private String issueId;

    private String userType;

}