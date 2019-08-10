package com.shmily.summary.util;

import com.google.common.base.Preconditions;
import com.shmily.summary.enumz.CharsetEnum;
import com.shmily.summary.enumz.MessageDigestAlgorithmEnum;
import com.shmily.summary.exception.BaseBizException;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 消息摘要工具类
 * MD5 摘要
 * SHA-1 摘要
 * SHA-256 摘要
 * SHA-512 摘要
 * create by kevin.ma on 2019/8/10 下午2:28
 **/
public class MessageDigestUtils {

    private static final String EMPTY_STRING = "";
    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 计算字符串的摘要，text 为空 或者 null 直接返回text原内容。
     * @param text 待计算摘要的字符串
     * @return 摘要摘要(16进制字符串)
     */
    public static String digestAsHex(String text, MessageDigestAlgorithmEnum algorithmEnum) {

        Preconditions.checkArgument(Objects.nonNull(algorithmEnum),
                "参数[algorithmEnum]不可为空");
        return digestAsHex(text, CharsetEnum.UTF8, algorithmEnum);
    }

    /**
     * 计算字符串的摘要，text 为空 或者 null 直接返回text原内容。
     * @param text 待计算摘要的字符串
     * @param charsetEnum 字符集
     * @return 摘要摘要(16进制字符串)
     */
    public static String digestAsHex(String text, CharsetEnum charsetEnum, MessageDigestAlgorithmEnum algorithmEnum) {
        Preconditions.checkArgument(Objects.nonNull(algorithmEnum),
                "参数[algorithmEnum]不可为空");
        return digestAsHex(text, EMPTY_STRING, charsetEnum, algorithmEnum);
    }

    /**
     * 计算字符串的摘要 salt 盐值 不为空进行两次计算 digest(digest(text)+salt)；，text 为空 或者 null 直接返回text原内容。
     * @param text 待计算摘要的字符串
     * @param salt 盐值 不为空
     * @return 摘要摘要(16进制字符串)
     */
    public static String digestAsHex(String text, String salt, MessageDigestAlgorithmEnum algorithmEnum) {
        Preconditions.checkArgument(Objects.nonNull(algorithmEnum),
                "参数[algorithmEnum]不可为空");
        return digestAsHex(text, salt, CharsetEnum.UTF8, algorithmEnum.getValue());
    }

    /**
     * 计算字符串的摘要 salt 盐值 不为空进行两次计算 digest(digest(text)+salt)；text 为空 或者 null 直接返回text原内容。
     * @param text 待计算摘要的字符串
     * @param salt 盐值 不为空
     * @param charsetEnum 字符集
     * @return 摘要摘要(16进制字符串)
     */
    public static String digestAsHex(String text,
                                     String salt,
                                     CharsetEnum charsetEnum,
                                     MessageDigestAlgorithmEnum algorithmEnum) {
        Preconditions.checkArgument(Objects.nonNull(algorithmEnum),
                "参数[algorithmEnum]不可为空");
        return digestAsHex(text, salt, charsetEnum, algorithmEnum.getValue());

    }

    /**
     * 计算字节数组的摘要，bytes 为空 或者 null 直接返回空字符串。
     * @param bytes 待计算摘要的字节数组
     * @return 摘要摘要(16进制字符串)
     */
    public static String digestAsHex(byte[] bytes, MessageDigestAlgorithmEnum algorithmEnum) {
        if (Objects.isNull(bytes) || bytes.length == 0) {
            return EMPTY_STRING;
        }
        return digestAsHexString(algorithmEnum.getValue(), bytes);
    }

    /**
     * 计算输入流的摘要
     * @param inputStream 待计算摘要的输入流
     * @return 摘要(字节数组)
     */
    public static byte[] digest(InputStream inputStream, MessageDigestAlgorithmEnum algorithmEnum) throws IOException {
        Preconditions.checkArgument(Objects.nonNull(algorithmEnum),
                    "参数[algorithmEnum]不可为空");
        return digest(algorithmEnum.getValue(), inputStream);
    }

    /**
     * 计算输入流的摘要
     * @param inputStream 待计算摘要的输入流
     * @return 摘要(16进制字符串)
     */
    public static String digestAsHex(InputStream inputStream, MessageDigestAlgorithmEnum algorithmEnum) throws IOException {
        Preconditions.checkArgument(Objects.nonNull(algorithmEnum),
                "参数[algorithmEnum]不可为空");
        return digestAsHexString(algorithmEnum.getValue(), inputStream);
    }

    private static String digestAsHex(String text, String salt, CharsetEnum charsetEnum, String algorithm) {
        if (StringUtils.isEmpty(text)) {
            return text;
        }
        if (Objects.isNull(charsetEnum)) {
            charsetEnum = CharsetEnum.UTF8;
        }
        String digest = digestAsHexString(algorithm, getBytes(text, charsetEnum));
        if (!StringUtils.isEmpty(salt)) {
            digest = digestAsHexString(algorithm, getBytes(String.join("", digest, salt), charsetEnum));
        }
        return digest;
    }

    private static String digestAsHexString(String algorithm, byte[] bytes) {
        char[] hexDigest = digestAsHexChars(algorithm, bytes);
        return new String(hexDigest);
    }

    private static String digestAsHexString(String algorithm, InputStream inputStream) throws IOException {
        char[] hexDigest = digestAsHexChars(algorithm, inputStream);
        return new String(hexDigest);
    }

    private static char[] digestAsHexChars(String algorithm, byte[] bytes) {
        byte[] digest = digest(algorithm, bytes);
        return encodeHex(digest);
    }

    private static char[] digestAsHexChars(String algorithm, InputStream inputStream) throws IOException {
        byte[] digest = digest(algorithm, inputStream);
        return encodeHex(digest);
    }

    private static byte[] digest(String algorithm, InputStream inputStream) throws IOException {
        MessageDigest messageDigest = getDigest(algorithm);
        final byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            messageDigest.update(buffer, 0, bytesRead);
        }
        return messageDigest.digest();
    }

    private static byte[] digest(String algorithm, byte[] bytes) {
        return getDigest(algorithm).digest(bytes);
    }

    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException ex) {
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

    private MessageDigestUtils(){
        throw new BaseBizException(String.join("", "not allow instance ", this.getClass().getName()));
    }
}
