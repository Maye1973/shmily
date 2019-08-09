package com.shmily.summary.util;

import com.google.common.hash.Hashing;
import com.shmily.summary.exception.BaseBizException;

/**
 * MD5工具类
 * create by kevin.ma on 2019/8/9 下午3:49
 **/
public class MD5Utils {


    public static String md5(String txt){

        return Hashing.md5().hashBytes(txt.getBytes()).toString();
    }

    private MD5Utils(){
        throw new BaseBizException(String.join("", "not allow instance ", this.getClass().getName()));
    }
}
