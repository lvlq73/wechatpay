package com.wechat.enums;

public enum RefundExceptionEmnu implements BaseEnum{
    REFUND_SYSTEM_ERROR(700,"系统内部错误"),
    REFUND_FAILD(701,"退款失败")
        ;
    private Integer code;
    private  String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    RefundExceptionEmnu(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
