package com.shmily.summary.util;

import com.google.common.base.Preconditions;
import com.shmily.summary.enumz.CharsetEnum;
import com.shmily.summary.enumz.HMacShaAlgorithmEnum;
import com.shmily.summary.exception.BaseBizException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * hmac消息摘要实现工具类
 * create by kevin.ma on 2019/8/10 下午5:40
 **/
public class HMacMessageDigestUtils {

    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 计算字符串的摘要，text 为空 或者 null 直接返回text原内容。
     * @param text 待计算摘要的字符串
     * @return 摘要摘要(16进制字符串)
     */
    public static String digestAsHex(String text, String key, HMacShaAlgorithmEnum algorithmEnum) {

        Preconditions.checkArgument(Objects.nonNull(algorithmEnum),
                "参数[algorithmEnum]不可为空");
        SecretKeySpec secretKeySpec = new SecretKeySpec(getBytes(key, CharsetEnum.UTF8), algorithmEnum.getValue());
        Mac mac = getMac(algorithmEnum.getValue());
        try {
            mac.init(secretKeySpec);
        } catch (InvalidKeyException e) {
            throw new BaseBizException(String.join("", "invalid key[", key, "]"), e);
        }

        byte[] bytes = mac.doFinal(getBytes(text, CharsetEnum.UTF8));
        return new String(encodeHex(bytes));
    }

    private static Mac getMac(String algorithm) {
        try {
            return Mac.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new BaseBizException(String.join("", "no such algorithm[", algorithm, "]"), ex);
        }
    }

    private static byte[] getBytes(String text, CharsetEnum charsetEnum) {
        try {
            return text.getBytes(charsetEnum.getValue());
        } catch (UnsupportedEncodingException e) {
            throw new BaseBizException(String.join("", "un support charset[", charsetEnum.getValue(), "]"), e);
        }
    }

    private static char[] encodeHex(byte[] bytes) {
        char[] chars = new char[bytes.length * 2];
        for (int i = 0, size = chars.length; i < size; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf]; // b 无符号右移4位，& 0xf[0000 0000 0000 1111] 保留 高4位
            chars[i + 1] = HEX_CHARS[b & 0xf]; // b & 0xf[0000 0000 0000 1111] 保留 低4位
        }
        return chars;
    }

    private HMacMessageDigestUtils(){
        throw new BaseBizException(String.join("", "not allow instance ", this.getClass().getName()));
    }
}
