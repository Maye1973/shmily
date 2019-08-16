package com.shmily.summary.util;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.shmily.summary.exception.BaseBizException;

import java.util.Objects;


/**
 * 排序算法实现工具类
 * create by kevin.ma on 2019/8/11 上午10:57
 **/
public class SortUtils {


    /**
     * <pre>
     * 冒泡排序
     * 平均时间复杂度: O(n*n)
     * 最好情况: O(n)
     * 最坏情况: O(n*n)
     * 空间复杂度: O(1)
     * 排序方式: 内排序
     * 稳定性: 稳定
     * </pre>
     * <pre>
     * 算法大致过程:
     * 1 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 2 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
     * 3 针对所有的元素重复以上的步骤，除了最后一个。
     * 4 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     * </pre>
     * @param elements
     * @return
     */
    public static Comparable[] bubbleSort(final Comparable[] elements) {

        Preconditions.checkArgument(Objects.nonNull(elements), "参数[elements]不可为空");
        if (elements.length == 0) {
            return elements;
        }
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0, size = elements.length - i - 1; j < size; j++) {
                if (elements[j].compareTo(elements[j+1]) > 0) {
                    // 交互位置
                    Comparable temp = elements[j];
                    elements[j] = elements[j+1];
                    elements[j+1] = temp;
                }
            }
        }
        return elements;
    }

    /**
     * <pre>
     * 选择排序
     * 平均时间复杂度: O(n*n)
     * 最好情况: O(n*n)
     * 最坏情况: O(n*n)
     * 空间复杂度: O(1)
     * 排序方式: 内排序
     * 稳定性: 不稳定
     * </pre>
     * <pre>
     * 算法大致过程:
     * 1 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置。
     * 2 再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     * 3 重复第二步，直到所有元素均排序完毕。
     * </pre>
     * @param elements
     * @return
     */
    public static Comparable[] selectionSort(final Comparable[] elements) {

        Preconditions.checkArgument(Objects.nonNull(elements), "参数[elements]不可为空");
        if (elements.length == 0) {
            return elements;
        }
        for (int i = 0; i < elements.length; i++) {
            for (int j = i + 1, size = elements.length; j < size; j++) {
                if (elements[i].compareTo(elements[j]) > 0) {
                    // 交互位置
                    Comparable temp = elements[j];
                    elements[j] = elements[i];
                    elements[i] = temp;
                }
            }
        }

        return elements;

    }

    public static void main(String[] args) {

        Integer[] integers = new Integer[]{23,442,44,11,33};
        integers = (Integer[]) SortUtils.bubbleSort(integers);
        System.out.println("bubbleSort: " + JSON.toJSONString(integers));

        String[] strings = new String[]{"a","b","f","f","c"};
        strings = (String[]) SortUtils.bubbleSort(strings);
        System.out.println("bubbleSort: " + JSON.toJSONString(strings));

        integers = new Integer[]{23,442,44,11,33};
        integers = (Integer[]) SortUtils.selectionSort(integers);
        System.out.println("selectionSort: " + JSON.toJSONString(integers));

        strings = new String[]{"a","b","f","f","c"};
        strings = (String[]) SortUtils.selectionSort(strings);
        System.out.println("selectionSort: " + JSON.toJSONString(strings));

    }

    private SortUtils(){
        throw new BaseBizException(String.join("", "not allow instance ", this.getClass().getName()));
    }
}
