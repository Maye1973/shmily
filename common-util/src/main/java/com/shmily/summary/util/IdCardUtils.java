package com.shmily.summary.util;

import com.shmily.summary.exception.BaseBizException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 身份证工具类
 * create by kevin.ma on 2019/8/6 下午10:36
 **/
public class IdCardUtils {

    /**
     * 18位身份证正则表达式
     */
    private static final String IDCARD18_REG =
            "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

    /**
     * 15位身份证正则表达式
     */
    private static final String IDCARD15_REG =
            "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$";

    /**
     *  15 18 位身份证校验
     * @param idCard
     * @return
     */
    public static boolean idCardVerify(String idCard){

        if (!StringUtils.isEmpty(idCard)){
            return false;
        }

        int length = idCard.length();
        if (length != 15 && length != 18){
            return false;
        }
        return (idCard.matches(IDCARD18_REG) || idCard.matches(IDCARD15_REG)) && idCardCheckSum(idCard.toCharArray());
    }

    /**
     * 身份证最后一位校验码校验
     * @param idCard
     * @return
     */
    public static boolean idCardCheckSum(final char[] idCard){

        if (Objects.isNull(idCard)){
            return false;
        }
        int sum = 0;
        int w[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        char[] ch = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
        for (int i = 0; i < idCard.length - 1; i++) {
            sum += (idCard[i] - '0') * w[i];
        }
        int c = sum % 11;
        char code = ch[c];
        char last = idCard[idCard.length-1];
        last = last == 'x' ? 'X' : last;
        return last == code;

    }

    // 私有构造方法 不让构造该对象
    private IdCardUtils(){
        throw new BaseBizException(String.join("", "not allow instance ", this.getClass().getName()));
    }


}
