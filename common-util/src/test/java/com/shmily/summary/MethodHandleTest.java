package com.shmily.summary;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;

/**
 * MethodHandle 和 nvokedynamic 练习
 * create by kevin.ma on 2019/8/31 下午4:51
 **/
public class MethodHandleTest {

    public static void main(String[] args) throws Throwable {

        Varargs varargs = new Varargs();
        varargs.asCollector();
        varargs.asVarargsCollector();
        varargs.asSpreader();

        MethodHandlerChange.dropArguments();
    }

    private static void MethodTypeTest() throws NoSuchMethodException, IllegalAccessException {
        // "HELLO".indexOf(1)
        MethodType methodType = MethodType.methodType(String.class, int.class);
        methodType.appendParameterTypes(String.class);
        methodType.insertParameterTypes(1, String.class);
        methodType.dropParameterTypes(1,2);
        methodType.changeParameterType(1, String.class);
        methodType.changeReturnType(int.class);
        methodType.changeReturnType(String.class);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle methodHandle = lookup.findVirtual(String.class, "name", methodType);
        System.out.println();
    }

    static class Varargs {
        public void normalMethod(String arg1, int arg2, int[] arg3) {
            System.out.println(String.join("-", arg1, String.valueOf(arg2), Arrays.toString(arg3)));
        }

        public void toBeSpreader(String arg1, int arg2, int arg3, int arg4) {
            System.out.println(String.join("-", arg1, String.valueOf(arg2),String.valueOf(arg3), String.valueOf(arg4)));
        }

        public void asCollector() throws Throwable {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType mt = MethodType.methodType(void.class, String.class, int.class, int[].class);
            MethodHandle md = lookup.findVirtual(Varargs.class, "normalMethod", mt);
            md = md.asCollector(int[].class, 2);
            // 实际调用 normalMethod("hello", 2, new int[]{3,4})
            md.invokeExact(this, "hello", 2, 3, 4);
        }

        public void asVarargsCollector() throws Throwable {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType mt = MethodType.methodType(void.class, String.class, int.class, int[].class);
            MethodHandle md = lookup.findVirtual(Varargs.class, "normalMethod", mt);
            md = md.asVarargsCollector(int[].class);
            // 实际调用 normalMethod("hello", 2, new int[]{3,4,5})
            md.invoke(this, "hello", 2, 3, 4, 5);
        }

        public void asSpreader() throws Throwable {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType mt = MethodType.methodType(void.class, String.class, int.class, int.class, int.class);
            MethodHandle md = lookup.findVirtual(Varargs.class, "toBeSpreader", mt);
            md = md.asSpreader(int[].class, 3);
            // 实际调用 toBeSpreader("hello", 2, 3, 4)
            // 输出 hello-2-3-4
            md.invokeExact(this, "hello", new int[]{2,3,4});
        }
    }

    static class MethodHandlerChange {

        public static void dropArguments() throws Throwable {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(String.class, int.class);
            MethodHandle methodHandle = lookup.findVirtual(String.class, "substring", methodType);
            methodHandle = methodHandle.bindTo("Hello World");
            String subStr = (String) methodHandle.invokeExact(1);
            System.out.println(String.join("=", "subStr", subStr));

            MethodHandle methodHandleNew = MethodHandles.dropArguments(methodHandle, 0, int.class);
            String subStrNew = (String) methodHandleNew.invokeExact(4, 2);
            System.out.println(String.join("=", "subStrNew", subStrNew));
        }

        public static void insertArguments() throws Throwable {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(String.class, String.class);
            MethodHandle methodHandle = lookup.findVirtual(String.class, "concat", methodType);
            String newStr = (String) methodHandle.invokeExact("Hello World", "--");
            System.out.println(String.join("=", "newStr", newStr));

            MethodHandle methodHandleNew = MethodHandles.insertArguments(methodHandle, 0, "--");
            String newStr2 = (String) methodHandleNew.invokeExact(4, 2);
            System.out.println(String.join("=", "newStr2", newStr2));
        }

        public static void filterArguments() throws Throwable {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            // 调用 Math.max(int, int)
            MethodType methodType = MethodType.methodType(int.class, int.class, int.class);
            MethodHandle methodHandle = lookup.findStatic(Math.class, "max", methodType);

            // 调用 string.length
            MethodHandle methodHandleLength
                    = lookup.findVirtual(String.class, "length", MethodType.methodType(int.class));
            // filter改变方法句柄
            MethodHandle methodHandleNew
                    = MethodHandles.filterArguments(methodHandle, 0, methodHandleLength, methodHandleLength);
            // 先使用 methodHandleLength 计算字符串长度，然后再调用 methodHandle 对应的 Math.max(int, int)
            int max = (int) methodHandleNew.invokeExact("hello", "hello world");
            // 输出 11
            System.out.println(max);
        }

        public static void foldArguments() throws Throwable {
            MethodHandleProxies.asInterfaceInstance()
        }
    }
}
