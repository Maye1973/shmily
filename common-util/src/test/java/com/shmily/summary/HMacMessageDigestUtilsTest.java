package com.shmily.summary;

import com.shmily.summary.enumz.HMacShaAlgorithmEnum;
import com.shmily.summary.util.HMacMessageDigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * hmac 消息摘要工具类测试类
 * create by kevin.ma on 2019/8/10 下午5:57
 **/
public class HMacMessageDigestUtilsTest {


    public static void main(String[] args) {

//        final String text = "123456789";
//        final String key = "salt";
//        String digest = HMacMessageDigestUtils.digestAsHex(text, key, HMacShaAlgorithmEnum.HMAC_SHA1);
//        System.out.println(String.join("", "hmasha1(", text, ") = ", digest));
//
//        digest = HMacMessageDigestUtils.digestAsHex(text, key, HMacShaAlgorithmEnum.HMAC_SHA256);
//        System.out.println(String.join("", "hmasha256(", text, ") = ", digest));
//
//        digest = HMacMessageDigestUtils.digestAsHex(text, key, HMacShaAlgorithmEnum.HMAC_SHA512);
//        System.out.println(String.join("", "hmasha512(", text, ") = ", digest));

        lengthOfLongestSubstring("pwwkew");


    }

    private static int lengthOfLongestSubstring(String s) {
        // 1 初始长度 int tempLength = 0
        // 2 字符串转 char 数组
        // 3 循环遍历 char 数据，每个元素作为key，index 作为 value 存入 map
        // 4 每次入 map 前先判断 map keys 是否包含该字符 c
        //   4.1 包含则
        //      4.1.1 从map中删除 char数组中下标 [0,map.get(c))的数据
        //      4.1.2 tempLength = tempLength > map.size ? tempLength : map.size 然后更新 key 对应的 index
        //   4.2 不包含 直接存入map
        // 5 循环处理完后，map 的 size 就是 无重复字符的最长子串的长度。(map按照value排序后的key就是无重复字符的最长子串)
        if(null == s || s.length() == 0) {
            return 0;
        }

        if(s.length() == 1){
            return 1;
        }

        char[] charArray = s.toCharArray();
        Map<Character, Integer> map = new HashMap(s.length());
        int tempLength = 0;
        for(int i = 0, size = charArray.length; i < size; i++) {
            char c = charArray[i];
            if(map.containsKey(c)) {
                int tempSize = map.size();
                if(tempSize > tempLength) {
                    tempLength = tempSize;
                }
                int indexOfc = map.get(c);
                for(int k = indexOfc - 1; k >=0; k--) {
                    char tc = charArray[k];
                    if(map.containsKey(tc) && map.get(tc) > indexOfc) {
                        continue;
                    }
                    map.remove(tc);
                }
                map.put(c, i);
            } else {
                map.put(c, i);
            }
        }

        return tempLength > map.size() ? tempLength : map.size();
    }


    static class ListNode {
          int val;
         ListNode next;
         ListNode(int x) { val = x; }
     }

    static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // l1为空
        if(l1 == null) {
            return l2;
        }

        // l2为空
        if(l2 == null) {
            return l1;
        }
        // l1 l2都不为null 循环遍历 l1 和 l2 把对应的位置求和 回填到 l1 的位置
        // 如果l1 到末尾了 l2 还没有，则把 l2 剩余的元素加到 l1 的末尾
        // 如果 l2到末尾了，l1 还没到则返回 l1.
        int carryBit = 0;
        ListNode l1Head = l1;
        do{
            int temp = l1.val + l2.val + carryBit;
            // 进位
            if(temp > 9) {
                carryBit = 1;
                temp = temp % 10;
            }
            l1.val = temp;
            // l1 l2 都到末尾
            if(l1.next == null && l2.next == null) {
                if(carryBit > 1) {
                    l1.next = new ListNode(carryBit);
                }
                break;
            }
            // l1 到末尾 l2还有元素
            if(l1.next == null && l2.next != null) {
                l1.next = new ListNode(0);
                l1 = l1.next;
                l2 = l2.next;
                continue;
            }

            // l1 还有末尾 l2到末尾
            if(l1.next != null && l2.next == null) {
                if(carryBit < 1) {
                    // 没有进位 直接返回
                    break;
                }
                while(l1.next != null) {
                    l1 = l1.next;
                    temp = l1.val + carryBit;
                    if(temp < 10) {
                        // 无进位
                        break;
                    }
                    carryBit = 1;
                    temp = temp % 10;
                    l1.val = temp;
                }
            }
            l1 = l1.next;
            l2 = l2.next;
        }while(l1 != null && l2 != null);
        return l1Head;
    }
}
