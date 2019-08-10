package com.shmily.summary;

import com.shmily.summary.enumz.MessageDigestAlgorithmEnum;
import com.shmily.summary.util.MessageDigestUtils;

/**
 * 消息摘要工具类测试类
 * create by kevin.ma on 2019/8/10 下午3:46
 **/
public class MessageDigestUtilsTest {

    public static void main(String[] args) {

        final String text = "123456789";
        final String salt = "salt";

        String digest = MessageDigestUtils.digestAsHex(text, MessageDigestAlgorithmEnum.MD5);
        System.out.println(String.join("", "md5(", text, ") = ", digest));

        digest = MessageDigestUtils.digestAsHex(text, salt, MessageDigestAlgorithmEnum.MD5);
        System.out.println(String.join("", "md5(md5(", text, ")+", salt ,") = ", digest));

        digest = MessageDigestUtils.digestAsHex(text, MessageDigestAlgorithmEnum.SHA_1);
        System.out.println(String.join("", "sha1(", text, ") = ", digest));

        digest = MessageDigestUtils.digestAsHex(text, salt, MessageDigestAlgorithmEnum.SHA_1);
        System.out.println(String.join("", "sha1(sha1(", text, ")+", salt ,") = ", digest));

        digest = MessageDigestUtils.digestAsHex(text, MessageDigestAlgorithmEnum.SHA_256);
        System.out.println(String.join("", "sha256(", text, ") = ", digest));

        digest = MessageDigestUtils.digestAsHex(text, salt, MessageDigestAlgorithmEnum.SHA_256);
        System.out.println(String.join("", "sha256(sha256(", text, ")+", salt ,") = ", digest));

        digest = MessageDigestUtils.digestAsHex(text, MessageDigestAlgorithmEnum.SHA_512);
        System.out.println(String.join("", "sha512(", text, ") = ", digest));

        digest = MessageDigestUtils.digestAsHex(text, salt, MessageDigestAlgorithmEnum.SHA_512);
        System.out.println(String.join("", "sha512(sha512(", text, ")+", salt ,") = ", digest));

    }
}
