# 问题解决

## 加解密问题

1. RSA 加密报：java.security.InvalidKeyException: IOException : algid parse error, not a sequence  
    > 原因：RSA加密算法需要的是 PKCS8 填充方法，故需要把私钥转为PKCS8。  
    > 解决办法：    
        1. 进入 OpenSSL 程序：$ openssl  
        2. pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -nocrypt  
        3. 把控制台输出的私钥字符串保存到新的私钥文件中。