package com.shmily.summary.util;

import com.google.common.base.Preconditions;
import com.shmily.summary.enumz.CharsetEnum;
import com.shmily.summary.exception.BaseBizException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RSA 加解密工具类
 * create by kevin.ma on 2019/8/9 下午3:38
 **/
public class RSAUtils {


    private static final String RSA = "RSA";
    private static final Map<String, String> KEY_MAP = new ConcurrentHashMap<>();


    /**
     * 通过公钥加密
     * @param plainText
     * @param publicKeyPath
     * @return
     */
    public static String encryptByPublicKey(String plainText, String publicKeyPath){

        Preconditions.checkArgument(StringUtils.isNotEmpty(publicKeyPath),
                "参数[publicKeyPath]不可为空");
        if (StringUtils.isEmpty(plainText)) {
            return plainText;
        }

        final byte[] plainTextByte;
        try {
            plainTextByte = plainText.getBytes(CharsetEnum.UTF8.getValue());
        } catch (UnsupportedEncodingException e) {
            throw new BaseBizException(String.join("", "unSupport encoding[", CharsetEnum.UTF8.getValue(), "]"), e);
        }
        String publicKeyStr = getKey(publicKeyPath);
        PublicKey publicKey = transferPublicKeyFromString(publicKeyStr);
        return Base64.encodeBase64String(doEncodeDecode(Cipher.ENCRYPT_MODE,
                publicKey, plainTextByte));
    }

    /**
     * 通过私钥解密
     * @param cipherText
     * @param privateKeyPath
     * @return
     */
    public static String decryptByPrivateKey(String cipherText, String privateKeyPath) {

        Preconditions.checkArgument(StringUtils.isNotEmpty(privateKeyPath),
                "参数[privateKeyPath]不可为空");
        if (StringUtils.isEmpty(cipherText)) {
            return cipherText;
        }
        byte[] bytes = Base64.decodeBase64(cipherText);

        String privateKeyStr = getKey(privateKeyPath);
        PrivateKey privateKey = transferPrivateKeyFromString(privateKeyStr);
        byte[] bytesAfterDecode = doEncodeDecode(Cipher.DECRYPT_MODE, privateKey, bytes);
        try {
            return new String(bytesAfterDecode, CharsetEnum.UTF8.getValue());
        } catch (UnsupportedEncodingException e) {
            throw new BaseBizException(String.join("", "unSupport encoding[", CharsetEnum.UTF8.getValue(), "]"), e);
        }
    }

    private static byte[] doEncodeDecode(int cipherMode, Key key, byte[] txt){

        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(cipherMode, key);
            return cipher.doFinal(txt);
        } catch (NoSuchAlgorithmException e) {
            throw new BaseBizException(String.join("", "no such algorithm[", RSA, "]"), e);

        } catch (NoSuchPaddingException e) {
            throw new BaseBizException(String.join("", "no such padding"), e);

        } catch (InvalidKeyException e) {
            throw new BaseBizException(String.join("", "invalid key"), e);

        } catch (BadPaddingException e) {
            throw new BaseBizException(String.join("", "bad padding"), e);

        } catch (IllegalBlockSizeException e) {
            throw new BaseBizException(String.join("", "invalid block size"), e);
        }
    }

    private static String getKey(String keyPath){

        final String mapKey = MD5Utils.md5(keyPath);
        String keyStr = KEY_MAP.get(mapKey);
        if (StringUtils.isEmpty(keyStr)) {
            // 没有缓存 从文件加载
            keyStr = loadKeyByPath(keyPath);

            if (StringUtils.isEmpty(keyStr)) {
                throw new BaseBizException(String.join("", "key file[", keyPath, "] invalid"));
            }
            // 存入缓存
            KEY_MAP.put(mapKey, keyStr);
        }
        return keyStr;
    }

    private static PublicKey transferPublicKeyFromString(String publicKey){

        byte[] publicKeyBytes = Base64.decodeBase64(publicKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new BaseBizException(String.join("", "no such algorithm[", RSA, "]"), e);
        } catch (InvalidKeySpecException e) {
            throw new BaseBizException(String.join("", "invalid public keySpec"), e);
        }
    }

    private static PrivateKey transferPrivateKeyFromString(String privateKeyStr) {
        byte[] privateKeyBytes = Base64.decodeBase64(privateKeyStr);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new BaseBizException(String.join("", "no such algorithm[", RSA, "]"), e);
        } catch (InvalidKeySpecException e) {
            throw new BaseBizException(String.join("", "invalid private keySpec"), e);
        }
    }

    private static String loadKeyByPath(String path){
        try {
            List<String> list = IOUtils.readLines(new BufferedInputStream(new FileInputStream(new File(path))), CharsetEnum.UTF8.getValue());
            StringBuilder sb = new StringBuilder(1024);
            list.forEach(a -> sb.append(a));
            return sb.toString();
        } catch (IOException e) {
            throw new BaseBizException(String.join("", "load key file[", path, "] error:", e.getMessage()), e);
        }
    }

    private RSAUtils(){
        throw new BaseBizException(String.join("", "not allow instance ", this.getClass().getName()));
    }


}
