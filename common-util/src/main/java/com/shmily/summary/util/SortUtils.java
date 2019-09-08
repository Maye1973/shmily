package com.shmily.summary.util;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.shmily.summary.exception.BaseBizRuntimeException;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

    /**
     * <pre>
     * 插入排序：把待排序的数组分为两部分，有序部分(开始把数组第一个元素当做有序部分)和无序部分，
     * 获取无序部分的一个元素，逐个和有序部分进行比较(从尾到头)，找到插入的位置，当无序部分为空时，排序完成。
     * 平均时间复杂度: O(n*n)
     * 最好情况: O(n)
     * 最坏情况: O(n*n)
     * 空间复杂度: O(1)
     * 排序方式: 内排序
     * 稳定性: 稳定
     * </pre>
     * <pre>
     * 算法大致过程:
     * 1 从第一个元素开始，该元素可以认为已经被排序。
     * 2 从无序部分取第一个元素A，从有序部分的尾部逐个比较。
     * 3 如果元素A小于有序部分的某个元素B，则元素B往后移动一位。
     * 4 如果元素B小于元素A，则A元素的插入位置已经找到(元素B的后面)。
     * 5 此时有序部分新增一位，无序部分减少一位。重复 2-4步。直到无序部分为空，排序结束。
     * </pre>
     * @param elements
     * @return
     */
    public static Comparable[] insertSort(final Comparable[] elements) {

        Preconditions.checkArgument(Objects.nonNull(elements), "参数[elements]不可为空");
        if (elements.length == 0) {
            return elements;
        }

        for (int i = 1; i < elements.length; i++) {
            int j = i - 1;
            Comparable value = elements[i];
            // [0, j] 有序部分 [i, elements.length] 无序部分
            for (; j >= 0; j--) {
                // 当 value 比 [0, j] 有序部分 最后一个元素都大 则没有必要再往前进行比较，
                boolean swapFlag = false;
                if (value.compareTo(elements[j]) < 0) {
                    // value < elements[j] elements[j] 往后移动一位
                    elements[j + 1] = elements[j];
                    swapFlag = true;
                }
                if (!swapFlag) {
                    break;
                }
            }
            // 找到了插入位置
            elements[j + 1] = value;
        }
        return elements;
    }

    /**
     * <pre>
     * 希尔排序：希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个更高效的版本，
     * 也称为缩小增量排序，同时该算法是冲破O(n2）的第一批算法之一。
     * 它与插入排序的不同之处在于，它会优先比较距离较远的元素。希尔排序又叫缩小增量排序。
     * 希尔排序是把记录按下表的一定增量分组，对每组使用直接插入排序算法排序；
     * 随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。
     *
     * 平均时间复杂度: O(nlog2n)
     * 最好情况: O(nlog2n)
     * 最坏情况: O(nlog2n)
     * 空间复杂度: O(1)
     * 排序方式: 内排序
     * 稳定性: 不稳定
     * </pre>
     * <pre>
     * 算法大致过程:
     * 1 选择增量 初始值 gap=length/2(希尔增量 不是最优增量)，以后每次循环后 gap /= 2。
     * 2 gap > 0 时，对[i,i+gap,i+2gap,i+3gap,,,i+ngap]，i=0,1,2,3,4,,,length-1 且 ngap <= length 的分组进行插入排序。
     * 3 当 gap < 0 时排序完成。
     * </pre>
     * @param elements
     * @return
     */
    public static Comparable[] shellSort(final Comparable[] elements) {

        Preconditions.checkArgument(Objects.nonNull(elements), "参数[elements]不可为空");
        if (elements.length == 0) {
            return elements;
        }
        // FIXME
        // TODO 时间复杂度 F1(n) = n/2 + n/4 + ... + n/n = n(1/2^1 + 1/2^2 + 1/2^3 + 1/2^log2^n)
        // TODO F1(n)/n = 1/2^1 + 1/2^2 + 1/2^3 + 1/2^log2^n
        // TODO F1(n)/2n = 1/2^2 + 1/2^3 + 1/2^4 + 1/2^log2^n + 1/2^(log2^n+1)
        // TODO F1(n)(1/n - 1/2n) = (1/2^1 + 1/2^2 + 1/2^3 + 1/2^log2^n) - (1/2^2 + 1/2^3 + 1/2^4 + 1/2^log2^n + 1/2^(log2^n+1))
        // TODO F1(n)(1/n - 1/2n) = 1/2^1 - 1/2^(log2^n+1)
        // TODO F1(n) = 2n(1/2^1 - 1/2^(log2^n+1)
        // TODO F1(n) = n(1 - 1/2^log2^n) = n - nlog2^n

        // = (n*n/2 - n*n/4)(1/2 + n/8)
        // = n*n/4(1/2 + n/8)
        // = 2n*n/8((4+n)/8)
        // = n*n/8 + n*n*n/32
        int gap = elements.length / 2;
        for (; gap > 0; gap /= 2) {
            for (int i = gap; i < elements.length; i++) {
                int j = i - gap;
                Comparable value = elements[i];
                // [0, j] 有序部分 [i, elements.length] 无序部分
                for (; j >= 0; j -= gap) {
                    // 当 value 比 [0, j] 有序部分 最后一个元素都大 则没有必要再往前进行比较，
                    boolean swapFlag = false;
                    if (value.compareTo(elements[j]) < 0) {
                        // value < elements[j + gap] elements[j] 往后移动一位
                        elements[j + gap] = elements[j];
                        swapFlag = true;
                    }
                    if (!swapFlag) {
                        break;
                    }
                }
                // 找到了插入位置
                elements[j + gap] = value;
            }
        }
        return elements;
    }

    public static void main(String[] args) {

        String[][] twoDimensionArray = new String[][]{
                {},
                {"a"},
                {"a","b"},
                {"a","b","c"},
                {"c","b","a"},
                {"a","a"},
                {"a","b","c","f","f","a"},
        };

        SortUtils.testBubbleSort(twoDimensionArray);
        SortUtils.testSelectionSort(twoDimensionArray);
        SortUtils.testInsertSort(twoDimensionArray);
        SortUtils.testShellSort(twoDimensionArray);
    }

    private static void  testBubbleSort(String[][] twoDimensionArray){
        System.out.println("######## bubbleSort #########");
        for (int i = 0, size = twoDimensionArray.length; i < size; i++) {
            String[] strings = twoDimensionArray[i];
            System.out.println("before bubbleSort: " + JSON.toJSONString(strings));
            strings = (String[]) SortUtils.bubbleSort(strings);
            System.out.println("after bubbleSort: " + JSON.toJSONString(strings));
            System.out.println();
        }
    }

    private static void  testSelectionSort(String[][] twoDimensionArray){
        System.out.println("######## selectionSort #########");
        for (int i = 0, size = twoDimensionArray.length; i < size; i++) {
            String[] strings = twoDimensionArray[i];
            System.out.println("before selectionSort: " + JSON.toJSONString(strings));
            strings = (String[]) SortUtils.selectionSort(strings);
            System.out.println("after selectionSort: " + JSON.toJSONString(strings));
            System.out.println();
        }
    }

    private static void  testInsertSort(String[][] twoDimensionArray){
        System.out.println("######## insertSort #########");
        for (int i = 0, size = twoDimensionArray.length; i < size; i++) {
            String[] strings = twoDimensionArray[i];
            System.out.println("before insertSort: " + JSON.toJSONString(strings));
            strings = (String[]) SortUtils.insertSort(strings);
            System.out.println("after insertSort: " + JSON.toJSONString(strings));
            System.out.println();
        }
    }

    private static void  testShellSort(String[][] twoDimensionArray){
        System.out.println("######## shellSort #########");
        for (int i = 0, size = twoDimensionArray.length; i < size; i++) {
            String[] strings = twoDimensionArray[i];
            System.out.println("before shellSort: " + JSON.toJSONString(strings));
            strings = (String[]) SortUtils.shellSort(strings);
            System.out.println("after shellSort: " + JSON.toJSONString(strings));
            System.out.println();
        }
    }

    private SortUtils(){
        throw new BaseBizRuntimeException(String.join("", "not allow instance ", this.getClass().getName()));
    }
}
