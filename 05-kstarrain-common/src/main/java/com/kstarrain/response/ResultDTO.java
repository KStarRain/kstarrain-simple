package com.kstarrain.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultDTO<T> implements Serializable {
    private Boolean success = Boolean.TRUE;
    private String code;
    private String message;
    private Integer pageNum;
    private Integer pageSize;
    private Integer pageCount;
    private Long total;
    private T data;

    public ResultDTO() {
    }

    public ResultDTO(T data) {
        this.data = data;
    }
}
