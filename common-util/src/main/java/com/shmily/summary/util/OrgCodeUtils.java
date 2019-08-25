package com.shmily.summary.util;

import com.shmily.summary.exception.BaseBizRuntimeException;

import java.util.Objects;

/**
 * 营业执照组织机构代码校验工具类
 * create by kevin.ma on 2019/8/6 下午10:36
 **/
public class OrgCodeUtils {

    /**
     * 组织机构代码 加权因子
     */
    private static final int[] ORG_CODE_WEIGHT = {3,7,9,10,5,8,4,2};

    /**
     * 统一社会信用代码 加权因子
     */
    private static final int[] UNIFIED_CODE_WEIGHT = {1,3,9,27,19,26,16,17,20,29,25,13,8,24,10,30,28};

    /**
     * 15位的营业执照校验
     * @param businessLicense
     * @see {https://wenku.baidu.com/view/19873704cc1755270722087c.html}
     * @return
     */
    public static boolean businessLicenseVerify(String businessLicense){

        boolean isValid = false;
        if (Objects.isNull(businessLicense)
                || !businessLicense.matches("^\\d{15}$")){
            return isValid;
        }

        // 校验码
        int checkCode = Integer.valueOf(String.valueOf(businessLicense.charAt(14)));
        // 前14位 A15 A14 A13 A12 A11 A10 A9 A8 A7 A6 A5 A4 A3 A2
        int[] busiLic14 = new int[14];
        int i = 0;
        for (char c : new String(businessLicense.substring(0,14)).toCharArray()) {
            busiLic14[i++] = Integer.valueOf(String.valueOf(c));
        }

        int sj;
        int pjMod11 = 10;
        for (i = 0; i < busiLic14.length; i++) {
            sj = (pjMod11 % 11) + busiLic14[i];
            int sjMod10Temp = sj % 10;
            pjMod11 = (0 == sjMod10Temp ? 10 : sjMod10Temp) * 2;
            if (i == busiLic14.length - 1) {
                isValid = (checkCode + (pjMod11 % 11)) % 10 == 1;
                break;
            }
        }
        return isValid;
    }

    /**
     * 组织机构代码校验
     * @param orgCode 9位组织机构代码（字母或者数字）
     * @see {https://zhidao.baidu.com/question/546635693.html}
     * @return
     */
    public static boolean orgCodeVerify(String orgCode){

        boolean isValid = false;
        if (Objects.isNull(orgCode) || (!orgCode.matches("^[\\d\\w]{9}$")
                && !orgCode.matches("^[\\d\\w]{8}[-][\\d\\w]{1}$"))){
            return isValid;
        }
        // 包含 - 去掉 -
        if (orgCode.matches("^[\\d\\w]{8}[-][\\d\\w]{1}$")){
            orgCode = orgCode.replace("-","");
        }
        orgCode = orgCode.toUpperCase();
        int pre8Sum = 0;
        for (int i = 0; i < 8; i++) {
            pre8Sum += orgCodeCharTransfer(orgCode.charAt(i)) * ORG_CODE_WEIGHT[i];
        }
        return orgCodeCharTransfer(orgCode.charAt(8)) == getOrgCodeVerifyCode((11 - (pre8Sum % 11)));
    }

    /**
     * 字符 '0' － '9' 对应 数值 0-9; 字符 'A' － 'Z' 对应 数值 10-35
     * @param orgChar 组织机构(统一代码)字符串中的某个字符
     * @see {http://www.360doc.com/content/11/0603/14/6155914_121418159.shtml}
     * @return
     */
    private static int orgCodeCharTransfer(char orgChar){

        if (orgChar >= 48 && orgChar <= 57){
            return orgChar - 48;
        } else if (orgChar >= 65 && orgChar <= 90){
            return orgChar - 55;
        } else {
            return orgChar;
        }
    }

    /**
     * 组织机构校验码为10时使用 'X'代替 'X'对应的数值为 33
     * @param verifyCode
     * @return
     */
    private static int getOrgCodeVerifyCode(int verifyCode){

        return verifyCode == 10 ? 33 : verifyCode;

    }

    /**
     * 统一代码由十八位的阿拉伯数字或大小写英文字母（不使用大小写的I、O、Z、S、V）组成
     * @see {http://blog.sina.com.cn/s/blog_540316260102x352.html}
     * @param unifiedCode
     * @return
     */
    public static boolean unifiedSocialCreditCodeVerify(String unifiedCode){
        boolean isValid = false;
        if (Objects.isNull(unifiedCode) || !unifiedCode.matches("^([15][1239]|[9][123]|[Yy]1)(\\d{6})([0-9a-hj-np-rtuwxyA-HJ-NP-RTUWXY]{10})$")){
            return isValid;
        }
        unifiedCode = unifiedCode.toUpperCase();
        int pre17Sum = 0;
        for (int i = 0; i < 17; i++) {
            pre17Sum += unifiedCodeCharTransfer(unifiedCode.charAt(i)) * UNIFIED_CODE_WEIGHT[i];
        }
        return unifiedCodeCharTransfer(unifiedCode.charAt(17)) == (31 - (pre17Sum % 31));
    }

    /**
     * 0-9a-hj-np-rtuwxyA-HJ-NP-RTUWXY
     * 字符 '0'-'9' 对应 数值 0-9;
     * 字符 'A'-'H' 对应 数值 10-17 去掉 I
     *     'J'-'N' 对应 18-22 去掉 O
     *     'P'-'R' 对应 23-25 去掉 S
     *     'T'-'U' 对应 26－27 去掉 V
     *     'W'-'Y' 对应 28－30 去掉 Z
     * @param unifiedChar 组织机构(统一代码)字符串中的某个字符
     * @see {http://www.360doc.com/content/11/0603/14/6155914_121418159.shtml}
     * @return
     */
    private static int unifiedCodeCharTransfer(char unifiedChar){

        if (unifiedChar >= 48 && unifiedChar <= 57){
            // '0'-'9' 对应 数值 0-9;
            return unifiedChar - 48;
        } else if (unifiedChar >= 65 && unifiedChar <= 72){
            // 'A'-'H' 对应 数值 10-17
            return unifiedChar - 55;
        } else if (unifiedChar >= 74 && unifiedChar <= 78){
            // 'J'-'N' 对应 18-22
            return unifiedChar - 56;
        } else if (unifiedChar >= 80 && unifiedChar <= 82){
            // 'P'-'R' 对应 23-25 去掉
            return unifiedChar - 57;
        } else if (unifiedChar >= 84 && unifiedChar <= 85){
            // 'T'-'U' 对应 26－27
            return unifiedChar - 58;
        } else if (unifiedChar >= 87 && unifiedChar <= 89){
            // 'W'-'Y' 对应 28－30
            return unifiedChar - 59;
        } else {
            return unifiedChar;
        }
    }

    // 私有构造方法 不让构造该对象
    private OrgCodeUtils(){
        throw new BaseBizRuntimeException(String.join("", "not allow instance ", this.getClass().getName()));
    }


}
