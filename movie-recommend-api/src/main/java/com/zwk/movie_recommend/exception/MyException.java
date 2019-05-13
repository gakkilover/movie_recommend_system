package com.zwk.movie_recommend.exception;

//自定义异常处理
public class MyException extends RuntimeException{
    private String msg;
    private String code;

    public MyException(String message, Throwable cause, String code) {
        super(message, cause);
        this.msg = message;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
