package com.shmily.summary.enumz;

import lombok.Getter;

/**
 * 消息摘要算法枚举
 * create by kevin.ma on 2019/8/10 下午5:03
 **/
@Getter
public enum MessageDigestAlgorithmEnum {

    MD5("MD5"),
    SHA_1("SHA-1"),
    SHA_256("SHA-256"),
    SHA_512("SHA-512");

    private String value;

    MessageDigestAlgorithmEnum(String value){
        this.value = value;
    }
}
