package com.shmily.summary.util;

import com.google.common.base.Preconditions;
import com.shmily.summary.enumz.CharsetEnum;
import com.shmily.summary.exception.BaseBizException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.*;

/**
 * 密钥对生成工具类
 * create by kevin.ma on 2019/8/9 下午2:49
 **/
public class KeyPairGeneratorUtils {
    
    private static final int KEY_SIZE = 1024;



    /**
     * 生成指定算法的公私密钥对，并保存到指定的文件中。
     * @param publicKeyFileAbsolutePath
     * @param privateKeyFileAbsolutePath
     * @param keyAlgorithm
     */
    public static void keyPairGenerator(String publicKeyFileAbsolutePath,
                                        String privateKeyFileAbsolutePath,
                                        String keyAlgorithm) {

        checkArgument(publicKeyFileAbsolutePath, privateKeyFileAbsolutePath, keyAlgorithm);

        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(keyAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new BaseBizException(String.join("", "no such algorithm[", keyAlgorithm, "]"), e);
        }

        keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // 存储公私钥
        storeKey(privateKey.getEncoded(), privateKeyFileAbsolutePath);
        storeKey(publicKey.getEncoded(), publicKeyFileAbsolutePath);



    }

    private static void checkArgument(String publicKeyFileAbsolutePath,
                                      String privateKeyFileAbsolutePath,
                                      String keyAlgorithm) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(publicKeyFileAbsolutePath),
                "参数[publicKeyFileAbsolutePath]不可为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(privateKeyFileAbsolutePath),
                "参数[privateKeyFileAbsolutePath]不可为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(keyAlgorithm),
                "参数[keyAlgorithm]不可为空");
    }


    private static void storeKey(byte[] key, String path){
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(path)));
            IOUtils.write(Base64.encodeBase64String(key), bufferedOutputStream, Charset.forName(CharsetEnum.UTF8.getValue()));
            bufferedOutputStream.flush();
        } catch (FileNotFoundException e) {
            throw new BaseBizException(String.join("", "no such file[", path, "]"), e);
        } catch (IOException e) {
            throw new BaseBizException(e.getMessage(), e);
        }
    }

    private KeyPairGeneratorUtils(){
        throw new BaseBizException(String.join("", "not allow instance ", this.getClass().getName()));
    }
}
