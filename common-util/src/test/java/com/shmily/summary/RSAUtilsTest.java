package com.shmily.summary;

import com.shmily.summary.util.KeyPairGeneratorUtils;
import com.shmily.summary.util.RSAUtils;

/**
 * RSAUtils 测试类型
 * create by kevin.ma on 2019/8/9 下午4:40
 **/
public class RSAUtilsTest {


    private static final String USER_HOME_PATH = System.getProperty("user.home");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String PUBLIC_KEY_PATH =
            String.join(FILE_SEPARATOR, USER_HOME_PATH, "publicKey.keystore");
    private static final String PRIVATE_KEY_PATH =
            String.join(FILE_SEPARATOR, USER_HOME_PATH, "privateKey.keystore");;

    public static void main(String[] args) {
        final String plainTxt = "kevin.ma-test-rsa";
        KeyPairGeneratorUtils.keyPairGenerator(PUBLIC_KEY_PATH, PRIVATE_KEY_PATH, "RSA");

        System.out.println(String.join("","before rsa encode text=", plainTxt));
        // RSA 加密
        String txtAfterEncode = RSAUtils.encryptByPublicKey(plainTxt, PUBLIC_KEY_PATH);
        System.out.println(String.join("","after rsa encode text=", txtAfterEncode));
        // RSA 解密
        String txtAfterDecode = RSAUtils.decryptByPrivateKey(txtAfterEncode, PRIVATE_KEY_PATH);
        System.out.println(String.join("","after rsa decode text=", txtAfterDecode));

    }
}
