# 工作中问题解决小结
######

# 目录索引
  * <a href="#A">加解密问题</a>
     * <a href="#a1">RSA加密报：algid parse error, not a sequence</a>
  * <a href="#B">HTTP问题</a>
    * <a href="#b1">http请求报：NoHttpResponseException</a>

## <a name="A">加解密问题</a>
#### <a name="a.1">RSA加密报：algid parse error, not a sequence</a>
* 现象：RSA 加密报：java.security.InvalidKeyException: IOException : algid parse error, not a sequence  
* 原因：RSA加密算法需要的是 PKCS8 填充方法，故需要把私钥转为PKCS8。  
* 解决办法：    
    1. 进入 OpenSSL 程序：$ openssl  
    2. pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -nocrypt  
    3. 把控制台输出的私钥字符串保存到新的私钥文件中。
> 参考：

## <a name="B">HTTP问题</a>
#### <a name="b.1">http请求报：NoHttpResponseException</a>
* 现象：http(apache httpclient 4.4.11)请求服务端，时不时出现 ”NoHttpResponseException 域名 failed to respond“。根据官网的解释目标服务过载，重试即可。
* 原因：目标服务过载或者服务端没有开启keepalive，客户端使用连接池的技术导致使用一个已经关闭的连接请求服务端。
* 解决办法：    
    1. 捕获 NoHttpResponseException 然后重试。
    ```java
    HttpClients.custom().setRetryHandler((exception, executionCount, context) -> {
                        if (executionCount > 3) {
                            log.error("Maximum tries reached for client http pool");
                            return false;
                        }
                        if (exception instanceof NoHttpResponseException
                                || exception instanceof ConnectTimeoutException) {
                            log.warn("NoHttpResponseException on {} call", executionCount);
                            return true;
                        }
                        return false;
                    });
    ```
    2. 设置 connectionTimeToLive 默认 -1，永远存活。
    ```java
        HttpClients.custom()..setConnectionTimeToLive(20L, TimeUnit.SECONDS)
    ```

    3. 设置 keepalive 策略。
    ```java
    HttpClients.custom().setRetryHandler((response, context) -> {
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String value = he.getValue();
                if (!org.apache.commons.lang3.StringUtils.isEmpty(value)
                        && "timeout".equalsIgnoreCase(he.getName())) {
                    try {
                        return Long.parseLong(value) * 1000L;
                    } catch (NumberFormatException e) {
                        log.error("keepAlive parse long error: {}", e.getMessage(), e);
                        return KEEP_ALIVE_TIMEOUT;
                    }
                }
            });
    ```
> 设置 http 连接池 最大连接数时要考虑部署机子的数量。当 maxTotal = 200 ，部署5台机子时，和服务端建立的连接数实际是 200*5=1000 ，连接数大于服务端的最大数量时也会报”NoHttpResponseException异常。  

> 参考：https://blog.csdn.net/siantbaicn/article/details/80854528