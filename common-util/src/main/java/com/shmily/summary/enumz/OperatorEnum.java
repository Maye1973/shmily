package com.shmily.summary.enumz;

import lombok.Getter;

/**
 * 操作符枚举类
 * create by kevin.ma on 2019/8/11 下午12:46
 **/
@Getter
public enum OperatorEnum {

    PLUS("+"),
    MINUS("-"),
    MULTI("*"),
    DIVISION("/"),
    LEFT_BRACKETS("("),
    RIGHT_BRACKETS(")"),
    ;

    private String value;

    OperatorEnum(String value){
        this.value = value;
    }

}
