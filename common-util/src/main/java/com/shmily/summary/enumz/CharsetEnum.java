package com.shmily.summary.enumz;

import lombok.Getter;

/**
 * 字符集枚举类
 * create by kevin.ma on 2019/8/9 下午3:30
 **/
@Getter
public enum CharsetEnum {

    UTF8("UTF-8");

    private String value;

    CharsetEnum(String value){
        this.value = value;
    }
}
