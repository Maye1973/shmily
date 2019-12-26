package com.shmily.summary.message;

import lombok.Data;
/**
 * controller 统一返回的对象
 * @param <T> 返回的业务对象
 */
@Data
public class MessageBean<T> implements Cloneable {

    public static final String SUCCESS_CODE = "000000";

    public static final MessageBean DEFAULT_MESSAGE_BEAN;

    static {
        DEFAULT_MESSAGE_BEAN = new MessageBean();
        DEFAULT_MESSAGE_BEAN.code = SUCCESS_CODE;
    }

    // 响应码 000000-成功 其他失败
    private String code;
    // 响应描述 失败时返回
    private String msg;
    // 具体的返回报文对象
    private T data;

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }

    public static final MessageBean cloneMessageBean() throws CloneNotSupportedException {
        return (MessageBean) DEFAULT_MESSAGE_BEAN.clone();
    }
}
