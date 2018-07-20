package com.wechat.entity;

public class ResultVo<T> {
    private String message;
    private Integer code;
    private Boolean success;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }
    public  Boolean isSuccess(){
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
