package com.shmily.summary;

import com.shmily.summary.enumz.HMacShaAlgorithmEnum;
import com.shmily.summary.util.HMacMessageDigestUtils;

/**
 * hmac 消息摘要工具类测试类
 * create by kevin.ma on 2019/8/10 下午5:57
 **/
public class HMacMessageDigestUtilsTest {


    public static void main(String[] args) {

        final String text = "123456789";
        final String key = "salt";
        String digest = HMacMessageDigestUtils.digestAsHex(text, key, HMacShaAlgorithmEnum.HMAC_SHA1);
        System.out.println(String.join("", "hmasha1(", text, ") = ", digest));

        digest = HMacMessageDigestUtils.digestAsHex(text, key, HMacShaAlgorithmEnum.HMAC_SHA256);
        System.out.println(String.join("", "hmasha256(", text, ") = ", digest));

        digest = HMacMessageDigestUtils.digestAsHex(text, key, HMacShaAlgorithmEnum.HMAC_SHA512);
        System.out.println(String.join("", "hmasha512(", text, ") = ", digest));
    }
}
