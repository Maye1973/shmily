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
        final String plainTxt = "kevin.ma-test-rsa234567890-09876543234567890-098765432345678909876543234567898765432345678987654323456789876543345678987654324567898765434567899876543456789987654345678909876543345678976534567897654567877777";
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
