package com.shmily.summary.util;

import com.shmily.summary.exception.BaseBizRuntimeException;
import com.shmily.summary.vo.MaxMinVO;

import java.util.List;
import java.util.Objects;


/**
 * 算法实现工具类
 * create by kevin.ma on 2019/8/11 上午10:57
 **/
public class AlgorithmUtils {


    /**
     * 查询一个集合中最大值和最小值，分治算法实现。
     * @param elements
     * @return max min
     */
    public static final MaxMinVO findMaxMinElement(List<? extends Comparable> elements) {

        MaxMinVO maxMinVO = new MaxMinVO();
        if (Objects.isNull(elements) || elements.size() == 0){
            return maxMinVO;
        }

        int size = elements.size();
        if (size == 1) {
            maxMinVO.setMax(elements.get(0));
            maxMinVO.setMin(maxMinVO.getMax());
            return maxMinVO;
        }

        if (size == 2) {
            Comparable firstElement = elements.get(0);
            Comparable secondElement = elements.get(1);
            if (firstElement.compareTo(secondElement) > 1) {
                maxMinVO.setMax(firstElement);
                maxMinVO.setMin(secondElement);

            } else {
                maxMinVO.setMax(secondElement);
                maxMinVO.setMin(firstElement);
            }
            return maxMinVO;
        }

        int middleIndex = size/2;
        MaxMinVO partBefore = findMaxMinElement(elements.subList(0, middleIndex));
        MaxMinVO partAfter = findMaxMinElement(elements.subList(middleIndex, size));

        // 最大值比较
        maxMinVO.setMax(max(partAfter.getMax(), partBefore.getMax()));
        // 最小值比较
        maxMinVO.setMin(min(partAfter.getMin(), partBefore.getMin()));

        return maxMinVO;
    }

    private static Comparable min(Comparable first, Comparable second) {
        if (Objects.isNull(first)) {
            return second;
        }

        if (Objects.isNull(second)) {
            return first;
        }

        return first.compareTo(second) > 1 ? second : first;
    }

    private static Comparable max(Comparable first, Comparable second) {
        if (Objects.isNull(first)) {
            return second;
        }

        if (Objects.isNull(second)) {
            return first;
        }

        return first.compareTo(second) > 1 ? first : second;
    }

    private AlgorithmUtils(){
        throw new BaseBizRuntimeException(String.join("", "not allow instance ", this.getClass().getName()));
    }
}
