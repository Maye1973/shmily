package com.shmily.summary.controller;

import com.shmily.summary.message.MessageBean;

public class BaseController {

    public final MessageBean process(Object request, ControllerSupplier supplier) {
        try {
            // TODO 统一参数校验
            Object o = supplier.get(request);
            MessageBean messageBean = MessageBean.cloneMessageBean();
            messageBean.setData(o);
            return messageBean;
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }

        return null;
    }
}
