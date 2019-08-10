package com.shmily.summary.enumz;

import lombok.Getter;

/**
 * 消息摘要算法枚举
 * create by kevin.ma on 2019/8/10 下午5:03
 **/
@Getter
public enum HMacShaAlgorithmEnum {

    HMAC_SHA1("HmacSHA1"),
    HMAC_SHA256("HmacSHA256"),
    HMAC_SHA512("HmacSHA512"),
    ;

    private String value;

    HMacShaAlgorithmEnum(String value){
        this.value = value;
    }
}
