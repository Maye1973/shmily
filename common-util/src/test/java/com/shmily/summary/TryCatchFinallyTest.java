package com.shmily.summary;

/**
 * trt-catch-finally 语句执行顺序
 * create by kevin.ma on 2019/8/18 下午6:32
 **/
public class TryCatchFinallyTest {

    public static void main(String[] args) {

        int i = tryNoExceptionFinallyNoReturn();
        System.out.println(String.join("", "tryNoExceptionFinallyNoReturn return = ", String.valueOf(i)));
        System.out.println("#################");
        System.out.println();

        i= tryNoExceptionFinallyReturn();
        System.out.println(String.join("", "tryNoExceptionFinallyNoReturn return = ", String.valueOf(i)));
        System.out.println("#################");
        System.out.println();

        i= tryExceptionFinallyNoReturn();
        System.out.println(String.join("", "tryNoExceptionFinallyNoReturn return = ", String.valueOf(i)));
        System.out.println("#################");
        System.out.println();


    }

    /**
     * try 不抛异常 finally 没有 return 语句。
     * 输出顺序：
     * <li> try block result = 20 </li>
     * <li> finally block result = 50 </li>
     * 返回结果：返回的是 try block 里计算的结果 20
     */
    private static int tryNoExceptionFinallyNoReturn() {

        int result = 0;
        try {
            result = 20;
            System.out.println(String.join("", "try block result = ", String.valueOf(result)));
            return result;
        } catch (Exception e) {
            System.out.println("catch block exception");
        } finally {
            result += 30;
            System.out.println(String.join("", "finally block result = ", String.valueOf(result)));
        }

        System.out.println(String.join("", "try-catch-finally end result = ", String.valueOf(result)));
        return result;
    }

    /**
     * try 不抛异常 finally 没有 return 语句。
     * 输出顺序：
     * <li> try block result = 20 </li>
     * <li> finally block result = 50 </li>
     * 返回结果：返回的是 finally block 里计算的结果 50
     */
    private static int tryNoExceptionFinallyReturn() {

        int result = 0;
        try {
            result = 20;
            System.out.println(String.join("", "try block result = ", String.valueOf(result)));
            return result;
        } catch (Exception e) {
            System.out.println("catch block exception");
        } finally {
            result += 30;
            System.out.println(String.join("", "finally block result = ", String.valueOf(result)));
            return result;
        }
    }

    /**
     * try 抛异常 finally 没有 return 语句。
     * 输出顺序：
     * <li> catch block result = 20 </li>
     * <li> finally block result = 50 </li>
     * <li> try-catch-finally end result = 50 </li>
     * 返回结果：返回的是 finally block 里计算的结果 50
     */
    private static int tryExceptionFinallyNoReturn() {

        int result = 0;
        try {
            result = 20;
            result = result / (result - 20); // exception
            System.out.println(String.join("", "try block result = ", String.valueOf(result)));
            return result;
        } catch (Exception e) {
            System.out.println(String.join("", "catch block result = ", String.valueOf(result)));
        } finally {
            result += 30;
            System.out.println(String.join("", "finally block result = ", String.valueOf(result)));
        }

        System.out.println(String.join("", "try-catch-finally end result = ", String.valueOf(result)));
        return result;
    }
}
